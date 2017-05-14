package creeper_san.weather.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import creeper_san.weather.Application.WeatherApplication;

public class ConfigHelper {
    private final static String PREF_NAME = "Config";
    private final static String PREF_PART = "Part";

    private final static String KEY_PART_HEADER_ORDER = "HeaderOrder";
    private final static String KEY_PART_AQI_ORDER = "AQIOrder";
    private final static String KEY_PART_DAILY_ORDER = "DailyOrder";
    private final static String KEY_PART_WIND_ORDER = "WindOrder";
    private final static String KEY_PART_SUGGESTION_ORDER = "SuggestionOrder";
    private final static String KEY_PART_CITY_ORDER = "CityOrder";
    private final static String KEY_PART_OTHER_ORDER = "OtherOrder";
    private final static String KEY_PART_BACKGROUND_STATUS = "BackgroundStatus";


    private static ConfigHelper configHelper;
    private SharedPreferences configPref;
    private SharedPreferences partPref;

    private ConfigHelper(Context context) {
        configPref = context.getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        partPref = context.getApplicationContext().getSharedPreferences(PREF_PART,Context.MODE_APPEND);
    }

    public static ConfigHelper getInstance(Context context){
        synchronized (ConfigHelper.class){
            if (configHelper==null){
                configHelper = new ConfigHelper(context);
            }
        }
        return configHelper;
    }

    public static int getHeaderOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_HEADER_ORDER,0);
    }

    public static void setHeaderOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_HEADER_ORDER,order).commit();
    }

    public static int getAqiOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_AQI_ORDER,1);
    }

    public static void setAqiOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_AQI_ORDER,order).commit();
    }

    public static int getDailyOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_DAILY_ORDER,2);
    }

    public static void setDailyOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_DAILY_ORDER,order).commit();
    }

    public static int getWindOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_WIND_ORDER,3);
    }

    public static void setWindOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_WIND_ORDER,order).commit();
    }

    public static int getSuggestionOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_SUGGESTION_ORDER,4);
    }

    public static void setSuggestionOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_SUGGESTION_ORDER,order).commit();
    }

    public static int getCityOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_CITY_ORDER,5);
    }

    public static void setCityOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_CITY_ORDER,order).commit();
    }

    public static int getOtherOrder(Context context){
        return getInstance(context).partPref.getInt(KEY_PART_OTHER_ORDER,6);
    }

    public static void setOtherOrder(Context context,int order){
        getInstance(context).partPref.edit().putInt(KEY_PART_OTHER_ORDER,order).commit();
    }

    public static boolean getBackgroundStatus(Context context){
        return getInstance(context).partPref.getBoolean(KEY_PART_BACKGROUND_STATUS,true);
    }

    public static void setBackgroundState(Context context,boolean order){
        getInstance(context).partPref.edit().putBoolean(KEY_PART_HEADER_ORDER,order).commit();
    }
}
