package creeper_san.weather.Pref;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import creeper_san.weather.Base.BasePref;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.R;

public class ColorPickerPref extends BasePref {
    @BindView(R.id.prefColorPickerTitle)TextView titleText;
    @BindView(R.id.prefColorPickerHint)TextView hintText;
    @BindView(R.id.prefColorPickerPreview)ImageView previewImage;

    private String eventName;
    private String key;
    private String dialogTitle;

    public ColorPickerPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initValue();
    }
    public ColorPickerPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public ColorPickerPref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ColorPickerPref(Context context) {
        this(context,null);
    }


    private void initValue() {

    }

    @Override
    protected void onBindView(final View view) {
        super.onBindView(view);
        String getHexStr = ConfigHelper.settingGetBackgroundColor(getContext(),"000000");
        titleText.setText(getTitle());
        hintText.setText("#"+getHexStr);
        previewImage.setBackgroundColor(Color.parseColor("#"+getHexStr));
        view.setOnClickListener(new View.OnClickListener() {
            CardView cardView;
            TextView hexText;
            TextView redText;
            SeekBar redSeek;
            TextView greenText;
            SeekBar greenSeek;
            TextView blueText;
            SeekBar blueSeek;
            SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String getHexStr = ConfigHelper.settingGetBackgroundColor(getContext(),"000000");
                    String progressColorStr = Integer.toHexString(progress);
                    if (progressColorStr.length()<=1){
                        progressColorStr = "0"+progressColorStr;
                    }
                    if (seekBar == redSeek){
                        getHexStr = progressColorStr+getHexStr.substring(2,6);
                    }else if (seekBar == greenSeek){
                        getHexStr = getHexStr.substring(0,2) + progressColorStr+getHexStr.substring(4);
                    }else {
                        getHexStr = getHexStr.substring(0,4) + progressColorStr;
                    }
                    setupColor(getHexStr,seekBar);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    ConfigHelper.settingSetBackgroundColor(getContext(),hexText.getText().toString().trim());
                }
            };

            private void setupColor(String colorStr,@Nullable View causeView){
                @ColorInt int color = Color.parseColor("#"+colorStr);
                int red = Integer.parseInt(colorStr.substring(0,2),16);
                int green = Integer.parseInt(colorStr.substring(2,4),16);
                int blue = Integer.parseInt(colorStr.substring(4),16);
                cardView.setCardBackgroundColor(color);
                if (causeView!=hexText){
                    hexText.setText(colorStr);
                }
                if (causeView!=redText){
                    redText.setText(String.valueOf(red));
                }
                if (causeView!=redSeek){
                    redSeek.setProgress(red);
                }
                if (causeView!=greenText){
                    greenText.setText(String.valueOf(green));
                }
                if (causeView!=greenSeek){
                    greenSeek.setProgress(green);
                }
                if (causeView!=blueText){
                    blueText.setText(String.valueOf(blue));
                }
                if (causeView!=blueSeek){
                    blueSeek.setProgress(blue);
                }
            }

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (dialogTitle!=null){
                    builder.setTitle(dialogTitle);
                }
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pref_color_picker,null);
                cardView = (CardView) dialogView.findViewById(R.id.dialogColorPickerCard);
                hexText = (TextView) dialogView.findViewById(R.id.dialogColorPickerHex);
                redText = (TextView) dialogView.findViewById(R.id.dialogColorPickerRedValue);
                redSeek = (SeekBar) dialogView.findViewById(R.id.dialogColorPickerRedSeekBar);
                greenText = (TextView) dialogView.findViewById(R.id.dialogColorPickerGreenValue);
                greenSeek = (SeekBar) dialogView.findViewById(R.id.dialogColorPickerGreenSeekBar);
                blueText = (TextView) dialogView.findViewById(R.id.dialogColorPickerBlueValue);
                blueSeek = (SeekBar) dialogView.findViewById(R.id.dialogColorPickerBlueSeekBar);
                //初始化
                setupColor(ConfigHelper.settingGetBackgroundColor(getContext(),"ffffff"),null);
                //监听器
                blueSeek.setOnSeekBarChangeListener(listener);
                greenSeek.setOnSeekBarChangeListener(listener);
                redSeek.setOnSeekBarChangeListener(listener);
                //其他
                builder.setView(dialogView);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String colorHexStr = ConfigHelper.settingGetBackgroundColor(getContext(),"000000");
                        previewImage.setBackgroundColor(Color.parseColor("#"+colorHexStr));
                        hintText.setText("#"+colorHexStr);
                        notifyChanged();
                        notifyBackgroundColorChange();
                    }
                });
                builder.show();
            }
        });
    }

    private void notifyBackgroundColorChange(){
        if (eventName!=null){
            try {
                Class cls = Class.forName("creeper_san.weather.Event."+eventName);
                Class[] parameterType = {String.class,String.class,String.class};
                Constructor constructor = cls.getConstructor(parameterType);
                String colorHex = ConfigHelper.settingGetBackgroundColor(getContext(),"ffffff");
                Object[] parameters = {colorHex,colorHex,key};
                EventBus.getDefault().post(constructor.newInstance(parameters));
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("类 "+eventName+" 不存在");
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("类的3个String参数的构造方法不存在");
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int[] getAttrID() {
        return R.styleable.ColorPickerPref;
    }
    @Override
    protected int getLayoutID() {
        return R.layout.pref_color_picker;
    }

    @Override
    protected void handleAttr(int attr, TypedArray typedArray) {
        switch (attr){
            case R.styleable.ColorPickerPref_colorPickerKey:
                key = typedArray.getString(attr);break;
            case R.styleable.ColorPickerPref_colorPickEvent:
                eventName = typedArray.getString(attr);break;
            case R.styleable.ColorPickerPref_colorPickerDialogTitle:
                dialogTitle = typedArray.getString(attr);break;
        }
    }
}
