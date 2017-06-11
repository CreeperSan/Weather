package creeper_san.weather.Base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Json.WeatherJson;
import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseDailyPartManager extends BasePartManager {
    private final int TYPE = PART_DAILY;
    private String unit = "0";

    public BaseDailyPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
        unit = ConfigHelper.settingGetDailyTemperatureUnit(getContext(),"0");
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        unit = ConfigHelper.settingGetDailyTemperatureUnit(getContext(),"0");
        super.initViewData(weatherJson, which);
    }

    protected String getUnit() {
        return unit;
    }

    public abstract void setData(WeatherJson weatherJson, int which);

    @Override
    public int getType() {
        return TYPE;
    }
}
