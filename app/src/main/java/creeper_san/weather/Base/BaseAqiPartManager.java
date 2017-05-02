package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public abstract class BaseAqiPartManager extends BasePartManager {
    private final static int TYPE = 1;

    public BaseAqiPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setAqi(String content);
    public abstract void setCo(String content);
    public abstract void setNo2(String content);
    public abstract void setO3(String content);
    public abstract void setPm10(String content);
    public abstract void setPm25(String content);
    public abstract void setQlty(String content);
    public abstract void setSo2(String content);
}
