package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseWindPartManager;
import creeper_san.weather.R;

public class WindPartManagerSimple extends BaseWindPartManager {
    @BindView(R.id.partWindDegTxt)TextView degreeTxt;
    @BindView(R.id.partWindDirTxt)TextView directionTxt;
    @BindView(R.id.partWindScTxt)TextView strengthTxt;
    @BindView(R.id.partWindSpdTxt)TextView speedTxt;

    public WindPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setDeg(String content) {
        degreeTxt.setText(content);
    }

    @Override
    public void setDir(String content) {
        directionTxt.setText(content);
    }

    @Override
    public void setSc(String content) {
        strengthTxt.setText(content);
    }

    @Override
    public void setSpd(String content) {
        speedTxt.setText(content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_wind_simple;
    }
}
