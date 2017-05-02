package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseAqiPartManager;
import creeper_san.weather.R;

public class AqiPartManagerSimple extends BaseAqiPartManager {
    @BindView(R.id.partAqiValueAqi)TextView aqiTxt;
    @BindView(R.id.partAqiValueCo)TextView coTxt;
    @BindView(R.id.partAqiValueNo2)TextView no2Txt;
    @BindView(R.id.partAqiValueO3)TextView o3Txt;
    @BindView(R.id.partAqiValueSo2)TextView so2Txt;
    @BindView(R.id.partAqiValuePm10)TextView pm10Txt;
    @BindView(R.id.partAqiValuePm25)TextView pm25Txt;
    @BindView(R.id.partAqiTitle)TextView title;

    public AqiPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setAqi(String content) {
        aqiTxt.setText(content);
    }

    @Override
    public void setCo(String content) {
        coTxt.setText(content);
    }

    @Override
    public void setNo2(String content) {
        no2Txt.setText(content);
    }

    @Override
    public void setO3(String content) {
        o3Txt.setText(content);
    }

    @Override
    public void setPm10(String content) {
        pm10Txt.setText(content);
    }

    @Override
    public void setPm25(String content) {
        pm25Txt.setText(content);
    }

    @Override
    public void setQlty(String content) {
        title.setText("空气质量 "+content);
    }

    @Override
    public void setSo2(String content) {
        so2Txt.setText(content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_aqi_simple;
    }
}
