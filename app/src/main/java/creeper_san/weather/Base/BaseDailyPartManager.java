package creeper_san.weather.Base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import creeper_san.weather.Json.WeatherItem;
import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseDailyPartManager extends BasePartManager {
    private final int TYPE = PART_DAILY;

    public BaseDailyPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public abstract void setData(WeatherItem weatherItem,int which);

    @Override
    public int getType() {
        return TYPE;
    }
}
