package creeper_san.weather.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import org.greenrobot.eventbus.EventBus;

import creeper_san.weather.Event.ThemeChangeEvent;
import creeper_san.weather.R;

public class ConfigHelper {
    private final static String PREF_NAME = "Config";
    private final static String PREF_SETTING = "Setting";
    private final static String PREF_INFO = "Info";

    private final static String KEY_THEME = "theme";
    private final static String KEY_VERSION = "version";
    private final static String VALUE_THEME_DEFAULT = "0";

    private static ConfigHelper configHelper;

    private SharedPreferences configPref;
    private SharedPreferences settingPref;
    private SharedPreferences infoPref;

    private ConfigHelper(Context context) {
        configPref = context.getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        settingPref = context.getApplicationContext().getSharedPreferences(PREF_SETTING,Context.MODE_PRIVATE);
        infoPref = context.getApplicationContext().getSharedPreferences(PREF_INFO,Context.MODE_PRIVATE);
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


    /**
     *      设置Pref
     */
    public static boolean settingHasKey(Context context,String key){
        return getInstance(context).settingPref.contains(key);
    }
    public static String settingGetValue(Context context,String key,String defaultValue){
        return getInstance(context).settingPref.getString(key,defaultValue);
    }
    public static void settingSetValue(Context context,String key,String value){
        getInstance(context).settingPref.edit().putString(key,value).commit();
        if (key.equals(context.getString(R.string.prefMainThemeColor))){
            EventBus.getDefault().post(new ThemeChangeEvent(value));
        }
    }

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
