package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class BaseWindPartManager extends BasePartManager {
    private final static int TYPE = 3;

    public BaseWindPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setDeg(String content);
    public abstract void setDir(String content);
    public abstract void setSc(String content);
    public abstract void setSpd(String content);

}
