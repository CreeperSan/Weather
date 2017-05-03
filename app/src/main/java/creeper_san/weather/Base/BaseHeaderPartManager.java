package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static creeper_san.weather.Flag.PartCode.*;


public abstract class BaseHeaderPartManager extends BasePartManager {
    private final int TYPE = PART_HEAD;

    public BaseHeaderPartManager(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
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
