package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseSuggestionPartManager extends BasePartManager {
    private final static int TYPE = 4;

    public BaseSuggestionPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setAirBrf(String content);
    public abstract void setAirTxt(String content);
    public abstract void setComfBrf(String content);
    public abstract void setComfTxt(String content);
    public abstract void setCwBrf(String content);
    public abstract void setCwTxt(String content);
    public abstract void setDrsgBrf(String content);
    public abstract void setDrsgTxt(String content);
    public abstract void setFluBrf(String content);
    public abstract void setFluTxt(String content);
    public abstract void setSportBrf(String content);
    public abstract void setSportTxt(String content);
    public abstract void setTravBrf(String content);
    public abstract void setTravTxt(String content);
    public abstract void setUvBrf(String content);
    public abstract void setUvTxt(String content);



}
