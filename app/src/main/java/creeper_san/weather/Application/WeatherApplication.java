package creeper_san.weather.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class WeatherApplication extends Application {
    public final static String API_KEY = "55d7287a36f040da9b742927f3aacebb";
    private static boolean isNeedMissUpdate = false;
    private static boolean isNotifyUpdate = true;
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

    public static boolean isNotifyUpdate() {
        return isNotifyUpdate;
    }

    public static void setIsNotifyUpdate(boolean isNotifyUpdate) {
        WeatherApplication.isNotifyUpdate = isNotifyUpdate;
        if (!isNotifyUpdate){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    WeatherApplication.isNotifyUpdate = true;
                }
            },3000);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    public static boolean isNeedMissUpdate() {
        return isNeedMissUpdate;
    }

    public static void setIsNeedMissUpdate(boolean isNeedMissUpdate) {
        WeatherApplication.isNeedMissUpdate = isNeedMissUpdate;
    }

    @Subscribe
    public void onEvent(String command){};

    public static Context getContext() {
        return context;
    }
}
