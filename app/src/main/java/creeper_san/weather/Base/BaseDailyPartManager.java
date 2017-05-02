package creeper_san.weather.Base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import creeper_san.weather.Item.WeatherItem;


public abstract class BaseDailyPartManager extends BasePartManager {
    private final int TYPE = 2;

    public BaseDailyPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public abstract void setData(WeatherItem weatherItem);

    @Override
    public int getType() {
        return TYPE;
    }
}
