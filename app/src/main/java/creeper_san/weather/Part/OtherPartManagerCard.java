package creeper_san.weather.Part;

import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseOtherPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Helper.ViewHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class OtherPartManagerCard extends BaseOtherPartManager {
    @BindView(R.id.partOtherCard)CardView cardView;
    @BindView(R.id.partOtherImage)ImageView imageView;
    @BindView(R.id.partOtherVisTxt)TextView visText;
    @BindView(R.id.partOtherVis)TextView vis;
    @BindView(R.id.partOtherPresTxt)TextView presText;
    @BindView(R.id.partOtherPres)TextView pres;
    @BindView(R.id.partOtherPcpnTxt)TextView pcpnText;
    @BindView(R.id.partOtherPcpn)TextView pcpn;
    @BindView(R.id.partOtherHumTxt)TextView humText;
    @BindView(R.id.partOtherHum)TextView hum;


    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        @ColorInt int backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        @ColorInt int contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        cardView.setCardBackgroundColor(backgroundColor);
        ViewHelper.initImageViewContentTint(imageView,contentColor);
        vis.setTextColor(contentColor);
        visText.setTextColor(contentColor);
        pres.setTextColor(contentColor);
        presText.setTextColor(contentColor);
        pcpn.setTextColor(contentColor);
        pcpnText.setTextColor(contentColor);
        hum.setTextColor(contentColor);
        humText.setTextColor(contentColor);
    }

    public OtherPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setHum(String content) {
        hum.setText(content);
    }

    @Override
    public void setPcpn(String content) {
        pcpn.setText(content);
    }

    @Override
    public void setPres(String content) {
        pres.setText(content);
    }

    @Override
    public void setVis(String content) {
        vis.setText(content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_other_card;
    }

    @Override
    public void setEmpty() {
        String na = "N/A";
        hum.setText(na);
        vis.setText(na);
        pcpn.setText(na);
        pres.setText(na);
    }
}
