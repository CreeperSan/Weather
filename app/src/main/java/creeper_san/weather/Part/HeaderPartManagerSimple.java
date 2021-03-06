package creeper_san.weather.Part;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Application.WeatherApplication;
import creeper_san.weather.Base.BaseHeaderPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Helper.UnitHelper;
import creeper_san.weather.R;


public class HeaderPartManagerSimple extends BaseHeaderPartManager implements ViewTreeObserver.OnGlobalLayoutListener{
    @BindView(R.id.partHeaderTmp)TextView temperatureTxt;
    @BindView(R.id.partHeaderWeatherTxt)TextView weatherTxt;
    @BindView(R.id.partHeaderTmpFeeling)TextView temperatureFeelingTxt;
    @BindView(R.id.partHeaderUpdateTime)TextView updateTimeTxt;
    @BindView(R.id.partHeaderWeatherImage)ImageView weatherImage;
    private View tempContainer;

    public HeaderPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setTmp(String content) {
        if (getUnit().equals("0")){
            temperatureTxt.setText(content+getContext().getString(R.string.unitTemperature));
        }else {
            try {
                int celsius = Integer.valueOf(content);
                temperatureTxt.setText(UnitHelper.celsiusToFahrenheit(celsius)+getContext().getString(R.string.unitTemperature));
            } catch (NumberFormatException e) {
                temperatureTxt.setText(content+getContext().getString(R.string.unitTemperature));
            }
        }
    }

    @Override
    public void setLoc(String content) {
        updateTimeTxt.setText("更新于"+content);
    }

    @Override
    public void setCondCode(String content) {
        weatherImage.setImageResource(ResHelper.getWeatherImageWeatherCode(content));
    }

    @Override
    public void setCondTxt(String content) {
        weatherTxt.setText(WeatherApplication.getContext().getString(ResHelper.getStringIDFromWeatherCode(content)));
    }

    @Override
    public void setFl(String content) {
        if (getUnit().equals("0")){
            temperatureFeelingTxt.setText(content+getContext().getString(R.string.unitTemperature));
        }else {
            try {
                int celsius = Integer.valueOf(content);
                temperatureFeelingTxt.setText(UnitHelper.celsiusToFahrenheit(celsius)+getContext().getString(R.string.unitTemperature));
            } catch (NumberFormatException e) {
                temperatureFeelingTxt.setText(content+getContext().getString(R.string.unitTemperature));
            }
        }
    }

    @Override
    protected void initView(final View partRootView, ViewGroup container) {
        tempContainer = container;
        partRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_head_simple;
    }

    @Override
    public void setEmpty() {

    }

    @Override
    public void onGlobalLayout() {
        try {
            partRootView.setMinimumHeight(tempContainer.getHeight());
            tempContainer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        partRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


}
