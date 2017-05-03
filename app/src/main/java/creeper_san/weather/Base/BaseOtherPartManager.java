package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseOtherPartManager extends BasePartManager {
    private static final int TYPE = PART_OTHER;

    public BaseOtherPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    public abstract void setHum(String content);
    public abstract void setPcpn(String content);
    public abstract void setPres(String content);
    public abstract void setVis(String content);
}
