package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Application.WeatherApplication;
import creeper_san.weather.Base.BaseAqiPartManager;
import creeper_san.weather.R;

public class AqiPartManagerSimple extends BaseAqiPartManager {
    @BindView(R.id.partAqiValueAqi)TextView aqiTxt;
    @BindView(R.id.partAqiAqi)TextView aqiTitleTxt;
    @BindView(R.id.partAqiValueCo)TextView coTxt;
    @BindView(R.id.partAqiCo)TextView coTitleTxt;
    @BindView(R.id.partAqiValueNo2)TextView no2Txt;
    @BindView(R.id.partAqiNo2)TextView no2TitleTxt;
    @BindView(R.id.partAqiValueO3)TextView o3Txt;
    @BindView(R.id.partAqiO3)TextView o3TitleTxt;
    @BindView(R.id.partAqiValueSo2)TextView so2Txt;
    @BindView(R.id.partAqiSo2)TextView so2TitleTxt;
    @BindView(R.id.partAqiValuePm10)TextView pm10Txt;
    @BindView(R.id.partAqiPm10)TextView pm10TitleTxt;
    @BindView(R.id.partAqiValuePm25)TextView pm25Txt;
    @BindView(R.id.partAqiPm25)TextView pm25TitleTxt;
    @BindView(R.id.partAqiTitle)TextView title;

    public AqiPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setAqi(String content) {
        if (content==null || content.equals("")){
            aqiTxt.setVisibility(View.GONE);
            aqiTitleTxt.setVisibility(View.GONE);
        }else {
            aqiTxt.setVisibility(View.VISIBLE);
            aqiTitleTxt.setVisibility(View.VISIBLE);
        }
        aqiTxt.setText(content);
    }

    @Override
    public void setCo(String content) {
        if (content==null || content.equals("")){
            coTxt.setVisibility(View.GONE);
            coTitleTxt.setVisibility(View.GONE);
        }else {
            coTxt.setVisibility(View.VISIBLE);
            coTitleTxt.setVisibility(View.VISIBLE);
        }
        coTxt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    public void setNo2(String content) {
        if (content == null || content.equals("")){
            no2Txt.setVisibility(View.GONE);
            no2TitleTxt.setVisibility(View.GONE);
        }else {
            no2Txt.setVisibility(View.VISIBLE);
            no2TitleTxt.setVisibility(View.VISIBLE);
        }
        no2Txt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    public void setO3(String content) {
        if (content==null || content.equals("")){
            o3TitleTxt.setVisibility(View.GONE);
            o3Txt.setVisibility(View.GONE);
        }else {
            o3Txt.setVisibility(View.VISIBLE);
            o3TitleTxt.setVisibility(View.VISIBLE);
        }
        o3Txt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    public void setPm10(String content) {
        if (content==null || content.equals("")){
            pm10Txt.setVisibility(View.GONE);
            pm10TitleTxt.setVisibility(View.GONE);
        }else {
            pm10Txt.setVisibility(View.VISIBLE);
            pm10TitleTxt.setVisibility(View.VISIBLE);
        }
        pm10Txt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    public void setPm25(String content) {
        if (content == null || content.equals("")) {
            pm25TitleTxt.setVisibility(View.GONE);
            pm25Txt.setVisibility(View.GONE);
        }else {
            pm25Txt.setVisibility(View.VISIBLE);
            pm25TitleTxt.setVisibility(View.VISIBLE);
        }
        pm25Txt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    public void setQlty(String content) {
        title.setText("空气质量 "+(content==null?"未知":content));
    }

    @Override
    public void setSo2(String content) {
        if (content==null || content.equals("")){
            so2TitleTxt.setVisibility(View.GONE);
            so2Txt.setVisibility(View.GONE);
        }else {
            so2TitleTxt.setVisibility(View.VISIBLE);
            so2Txt.setVisibility(View.VISIBLE);
        }
        so2Txt.setText(content+WeatherApplication.getContext().getString(R.string.unitAqi));
    }

    @Override
    protected int getLayout() {
        return R.layout.part_aqi_simple;
    }

    @Override
    public void setEmpty() {
        coTitleTxt.setVisibility(View.GONE);
        coTxt.setVisibility(View.GONE);
        no2Txt.setVisibility(View.GONE);
        no2TitleTxt.setVisibility(View.GONE);
        o3Txt.setVisibility(View.GONE);
        o3TitleTxt.setVisibility(View.GONE);
        so2Txt.setVisibility(View.GONE);
        so2TitleTxt.setVisibility(View.GONE);
        pm10Txt.setVisibility(View.GONE);
        pm10TitleTxt.setVisibility(View.GONE);
        pm25Txt.setVisibility(View.GONE);
        pm25TitleTxt.setVisibility(View.GONE);
        aqiTxt.setVisibility(View.GONE);
        aqiTitleTxt.setVisibility(View.GONE);
        title.setText("所选择的地区没有提供空气质量数据");
    }
}
