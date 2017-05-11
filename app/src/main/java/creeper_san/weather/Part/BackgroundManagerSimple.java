package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Json.WeatherItem;
import creeper_san.weather.R;

public class BackgroundManagerSimple extends BaseBackgroundPartManager {

    public BackgroundManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setData(WeatherItem item, int which) {

    }

    @Override
    public void setWeather(int which, WeatherItem weatherItem) {

    }

    @Override
    protected int getLayout() {
        return R.layout.part_background_simple;
    }

    @Override
    public void setEmpty() {

    }
}
