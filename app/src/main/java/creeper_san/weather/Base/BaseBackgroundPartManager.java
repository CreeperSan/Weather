package creeper_san.weather.Base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creeper_san.weather.Json.WeatherJson;

import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseBackgroundPartManager extends BasePartManager {
    private static final int TYPE = PART_BACKGROUND;

    public BaseBackgroundPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setData(WeatherJson item, int which);

    public abstract void setWeather(int which, WeatherJson weatherJson);
}
