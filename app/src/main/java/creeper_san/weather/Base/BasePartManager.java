package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import creeper_san.weather.Json.WeatherJson;

import static creeper_san.weather.Flag.PartCode.*;

public abstract class BasePartManager {
    private final String TAG = getClass().getSimpleName();
    private final int TYPE = PART_BASE;
    protected View partRootView;

    public BasePartManager(LayoutInflater inflater, ViewGroup container) {
        partRootView = inflater.inflate(getLayout(),container,false);
        ButterKnife.bind(this,partRootView);
        initView(partRootView,container);
        onViewInflated();
    }

    public View getView() {
        return partRootView;
    }

    public void initViewData(WeatherJson weatherJson,int which){}
    public void initViewFinish(){}

    protected void onViewInflated(){}

    protected void initView(View partRootView,ViewGroup container){}
    protected abstract @LayoutRes int getLayout();
    public abstract void setEmpty();

    public int getType() {
        return TYPE;
    }

    protected void log(String content){
        Log.i(TAG,content);
    }
}
