package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class BackgroundManagerSimple extends BaseBackgroundPartManager {

    public BackgroundManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setData(WeatherJson item, int which) {

    }

    @Override
    public void setWeather(int which, WeatherJson weatherJson) {

    }

    @Override
    protected int getLayout() {
        return R.layout.part_background_simple;
    }

    @Override
    public void setEmpty() {

    }
}
