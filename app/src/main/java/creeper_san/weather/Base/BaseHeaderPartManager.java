package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Json.WeatherJson;

import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseHeaderPartManager extends BasePartManager {
    private final int TYPE = PART_HEAD;
    private String unit = "0";

    public BaseHeaderPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
        unit = ConfigHelper.settingGetHeaderTemperatureUnit(getContext(),"0");
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        unit = ConfigHelper.settingGetHeaderTemperatureUnit(getContext(),"0");
        super.initViewData(weatherJson, which);
    }

    protected String getUnit() {
        return unit;
    }

    public abstract void setTmp(String content);
    public abstract void setLoc(String content);
    public abstract void setCondCode(String content);
    public abstract void setCondTxt(String content);
    public abstract void setFl(String content);

    @Override
    public int getType() {
        return TYPE;
    }
}
