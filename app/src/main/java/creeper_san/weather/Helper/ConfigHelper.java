package creeper_san.weather.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import creeper_san.weather.Event.ThemeChangeEvent;
import creeper_san.weather.R;

public class ConfigHelper {
    private final static String PREF_NAME = "Config";
    private final static String PREF_SETTING = "Setting";
    private final static String PREF_INFO = "Info";
    private final static String PREF_UPDATE_TIME = "UpdateTime";

    private final static String KEY_THEME = "theme";
    private final static String KEY_VERSION = "version";
    private final static String KEY_BING_UPDATE_DATE = "BingImageUpdateDate";
    private final static String VALUE_THEME_DEFAULT = "0";

    private static ConfigHelper configHelper;

    private SharedPreferences configPref;
    private SharedPreferences settingPref;
    private SharedPreferences infoPref;
    private SharedPreferences updateTimePref;

    private ConfigHelper(Context context) {
        configPref = context.getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        settingPref = context.getApplicationContext().getSharedPreferences(PREF_SETTING,Context.MODE_PRIVATE);
        infoPref = context.getApplicationContext().getSharedPreferences(PREF_INFO,Context.MODE_PRIVATE);
        updateTimePref = context.getApplicationContext().getSharedPreferences(PREF_UPDATE_TIME,Context.MODE_PRIVATE);
    }

    public static synchronized ConfigHelper getInstance(Context context){
        synchronized (ConfigHelper.class){
            if (configHelper==null){
                configHelper = new ConfigHelper(context);
            }
        }
        return configHelper;
    }

    /**
     *      写入城市更新的时间
     */
    public static void setCityWeatherUpdateTime(Context context,String cityID,long updateTimeStamp){
        getInstance(context).updateTimePref.edit().putLong(cityID,updateTimeStamp).commit();
    }
    public static long getCityWeatherUpdateTime(Context context,String cityID,long defaultReturn){
        return getInstance(context).updateTimePref.getLong(cityID,defaultReturn);
    }

    /**
     *      获取指定的值
     */
    public static String settingGetTheme(Context context,String defaultTheme){
        return getInstance(context).settingPref.getString("prefMainThemeColor",defaultTheme);
    }
    public static String settingGetHeaderStyle(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefHeaderTheme",defaultValue);
    }
    public static String settingGetAQITheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefAQITheme",defaultValue);
    }
    public static String settingGetWindTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefWindTheme",defaultValue);
    }
    public static String settingGetCityTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefCityTheme",defaultValue);
    }
    public static String settingGetOtherTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefOtherTheme",defaultValue);
    }
    public static String settingGetSuggestionTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefSuggestionTheme",defaultValue);
    }
    public static String settingGetDailyTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefDailyTheme",defaultValue);
    }
    public static String settingGetBgTheme(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefBackgroundTheme",defaultValue);
    }
    public static @ColorInt int settingGetBgColor(Context context, @ColorInt int defaultColor){
        return getInstance(context).settingPref.getInt("prefBackgroundColor",defaultColor);
    }
    public static String settingGetBackgroundColor(Context context,String defaultColor){
        String tempColorHex = getInstance(context).settingPref.getString("prefBackgroundColor",defaultColor);
        try {
            Color.parseColor("#"+tempColorHex);
            return tempColorHex;
        }catch (Exception e){
            return defaultColor;
        }
    }
    public static String settingGetBackgroundBingImageSize(Context context,String defaultSize){
        return getInstance(context).settingPref.getString("prefBackgroundBingImageSize",defaultSize);
    }
    public static @Nullable String settingGetFilePickerPath(Context context, String key){
        String path = getInstance(context).configPref.getString(key,"");
        if (path.equals("")){
            return null;
        }else {
            return path;
        }
    }
    public static String settingGetBingImageUpdateDate(Context context){
        return getInstance(context).updateTimePref.getString(KEY_BING_UPDATE_DATE,"");
    }
    public static String settingGetThemeAlpha(Context context,String defaultValue){
        return getInstance(context).settingPref.getString("prefMainThemeAlpha",defaultValue);
    }

    /**
     *      设置Pref
     */
    public static boolean settingHasKey(Context context,String key){
        return getInstance(context).settingPref.contains(key);
    }
    public static String settingGetValue(Context context,String key,String defaultValue){
        return getInstance(context).settingPref.getString(key,defaultValue);
    }
    public static void settingSetThemeValue(Context context, String key, String value){
        getInstance(context).settingPref.edit().putString(key,value).commit();
        if (key.equals(context.getString(R.string.prefMainThemeColor))){
            EventBus.getDefault().post(new ThemeChangeEvent(value));
        }
    }
    public static void settingSetBackgroundColor(Context context,String value){
        getInstance(context).settingPref.edit().putString("prefBackgroundColor",value).commit();
    }
    public static void settingSetFilePickerPath(Context context,String key,String path){
        getInstance(context).configPref.edit().putString(key,path).commit();
    }
    public static void settingSetBingImageUpdateDate(Context context){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateStr = String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
        getInstance(context).updateTimePref.edit().putString(KEY_BING_UPDATE_DATE,dateStr).commit();
    }//设置必应图片更新的日并保存
    public static void settingSetBingImageUpdateDate(Context context,String value){
        getInstance(context).updateTimePref.edit().putString(KEY_BING_UPDATE_DATE,value).commit();
    }//设置必应图片更新的日并保存制定的值

    public static boolean isFirstBoot(Context context,PackageManager packageManager){
        try {
            int currentVersionCode = packageManager.getPackageInfo(context.getPackageName(),0).versionCode;
            int saveVersionCode = getInstance(context).infoPref.getInt(KEY_VERSION,-1);
            if (currentVersionCode == saveVersionCode){
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void setVersion(Context context,int versionCode){
        getInstance(context).infoPref.edit().putInt(KEY_VERSION,versionCode).commit();
    }

}
