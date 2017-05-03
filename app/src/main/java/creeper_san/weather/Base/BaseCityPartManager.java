package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import static creeper_san.weather.Flag.PartCode.*;

public abstract class BaseCityPartManager extends BasePartManager {
    private static final int TYPE = PART_CITY;

    public BaseCityPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setCity(String content);
    public abstract void setCnty(String content);
    public abstract void setID(String content);
    public abstract void setLat(String content);
    public abstract void setLon(String content);
}
