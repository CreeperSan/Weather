package creeper_san.weather.Base;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BasePartManager {
    private final String TAG = getClass().getSimpleName();
    private final int TYPE = -1;
    protected View partRootView;

    public BasePartManager(LayoutInflater inflater, ViewGroup container) {
        partRootView = inflater.inflate(getLayout(),container,false);
        ButterKnife.bind(this,partRootView);
        initView(partRootView,container);
    }

    public View getView() {
        return partRootView;
    }

    protected void initView(View partRootView,ViewGroup container){};
    protected abstract @LayoutRes int getLayout();

    public int getType() {
        return TYPE;
    }

    protected void log(String content){
        Log.i(TAG,content);
    }
}
