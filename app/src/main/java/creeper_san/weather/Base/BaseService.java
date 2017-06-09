package creeper_san.weather.Base;

import android.app.Service;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import creeper_san.weather.Event.ApplicationExitEvent;

public abstract class BaseService extends Service {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     *      EventBus
     */
    protected <T> void postEvent(T event){
        EventBus.getDefault().post(event);
    }
    protected <T> void postStickyEvent(T event){
        EventBus.getDefault().postSticky(event);
    }
    @Subscribe
    public void onEvent(String command){
    }
    @Subscribe()
    public void onApplicationExitEvent(ApplicationExitEvent event){
        stopSelf();
    }

    /**
     *      Log
     */
    protected void log(String content){
        Log.i(TAG,content);
    }
    protected void logv(String content){
        Log.v(TAG,content);
    }
    protected void logd(String content){
        Log.d(TAG,content);
    }
    protected void logw(String content){
        Log.w(TAG,content);
    }
    protected void loge(String content){
        Log.e(TAG,content);
    }
}
