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
import creeper_san.weather.Base.BaseHeaderPartManager;
import creeper_san.weather.R;


public class HeaderPartManagerSimple extends BaseHeaderPartManager implements ViewTreeObserver.OnGlobalLayoutListener{
    @BindView(R.id.partHeaderTmp)TextView temperatureTxt;
    @BindView(R.id.partHeaderWeatherTxt)TextView weatherTxt;
    @BindView(R.id.partHeaderTmpFeeling)TextView temperatureFeelingTxt;
    @BindView(R.id.partHeaderUpdateTime)TextView updateTimeTxt;
    @BindView(R.id.partHeaderWeatherImage)ImageView weatherImage;
    private  View tempContainer;

    public HeaderPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }


    @Override
    public void setTmp(String content) {
        temperatureTxt.setText(content);
    }

    @Override
    public void setLoc(String content) {
        updateTimeTxt.setText(content);
    }

    @Override
    public void setCondCode(String content) {
        weatherImage.setImageResource(R.drawable.ic_sunny);
    }

    @Override
    public void setCondTxt(String content) {
        weatherTxt.setText(content);
    }

    @Override
    public void setFl(String content) {
        temperatureFeelingTxt.setText(content);
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
    public void onGlobalLayout() {
        partRootView.setMinimumHeight(tempContainer.getHeight());
        tempContainer = null;
        partRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


}
