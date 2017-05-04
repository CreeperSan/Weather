package creeper_san.weather.Application;

import android.app.Application;
import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class WeatherApplication extends Application {
    public final static String API_KEY = "55d7287a36f040da9b742927f3aacebb";
    public static Context context;

    /**
     *      生命周期
     */
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        context = this;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String command){};

    public static Context getContext() {
        return context;
    }
}
