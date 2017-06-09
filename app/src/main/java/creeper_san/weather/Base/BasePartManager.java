package creeper_san.weather.Base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Json.WeatherJson;

import static creeper_san.weather.Flag.PartCode.*;

public abstract class BasePartManager {
    private final String TAG = getClass().getSimpleName();
    private final int TYPE = PART_BASE;
    protected View partRootView;

    public BasePartManager(LayoutInflater inflater, ViewGroup container) {
        partRootView = inflater.inflate(getLayout(),container,false);
        initAlpha();
        ButterKnife.bind(this,partRootView);
        initView(partRootView,container);
        onViewInflated();
    }

    protected void initAlpha() {
        try {
            Integer alphaLevel = Integer.valueOf(ConfigHelper.settingGetThemeAlpha(getContext(),"0"));
            getView().setAlpha(1f-Float.valueOf(alphaLevel)*0.1f);
        } catch (NumberFormatException e) {}

    }

    public View getView() {
        return partRootView;
    }
    protected LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(getView().getContext());
    }
    protected Context getContext(){
        return getView().getContext();
    }
    protected View getViewByID(@LayoutRes int id,@Nullable ViewGroup viewGroup){
        return getLayoutInflater().inflate(id,viewGroup);
    }
    protected View getViewByID(@LayoutRes int id,@Nullable ViewGroup viewGroup,boolean isAttachToRoot){
        return getLayoutInflater().inflate(id,viewGroup,isAttachToRoot);
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
    protected void toast(String content){
        Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
    }
}
