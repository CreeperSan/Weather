package creeper_san.weather.Part;

import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseCityPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class CityPartManagerCard extends BaseCityPartManager {
    @BindView(R.id.partCityImage)ImageView imageView;
    @BindView(R.id.partCityCard)CardView cardView;
    @BindView(R.id.partCityCountryText)TextView countryText;
    @BindView(R.id.partCityCityText)TextView cityText;
    @BindView(R.id.partCityLatText)TextView latText;
    @BindView(R.id.partCityLonText)TextView lonText;
    @BindView(R.id.partCityIDText)TextView idText;

    public CityPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        @ColorInt int backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        @ColorInt int contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        cardView.setCardBackgroundColor(backgroundColor);
        countryText.setTextColor(contentColor);
        cityText.setTextColor(contentColor);
        latText.setTextColor(contentColor);
        lonText.setTextColor(contentColor);
        idText.setTextColor(contentColor);
    }

    @Override
    public void setCity(String content) {
        cityText.setText(content);
    }

    @Override
    public void setCnty(String content) {
        countryText.setText(content);
    }

    @Override
    public void setID(String content) {
        idText.setText(content);
    }

    @Override
    public void setLat(String content) {
        latText.setText("纬度 "+content);
    }

    @Override
    public void setLon(String content) {
        lonText.setText("经度"+content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_city_card;
    }

    @Override
    public void setEmpty() {

    }
}
