package creeper_san.weather.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import creeper_san.weather.Application.WeatherApplication;

public class ConfigHelper {
    private final static String PREF_NAME = "Config";
    private static ConfigHelper configHelper;
    private SharedPreferences configPref;

    private ConfigHelper(Context context) {
        configPref = context.getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public static ConfigHelper getInstance(Context context){
        synchronized (ConfigHelper.class){
            if (configHelper==null){
                configHelper = new ConfigHelper(context);
            }
        }
        return configHelper;
    }

}
