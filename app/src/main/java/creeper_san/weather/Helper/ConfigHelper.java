package creeper_san.weather.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigHelper {
    private final static String PREF_NAME = "Config";
    private final static String PREF_THEME = "Theme";

    private final static String KEY_THEME = "theme";
    private final static String VALUE_THEME_DEFAULT = "0";

    private static ConfigHelper configHelper;

    private SharedPreferences configPref;
    private SharedPreferences themePref;

    private ConfigHelper(Context context) {
        configPref = context.getApplicationContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        themePref = context.getApplicationContext().getSharedPreferences(PREF_THEME,Context.MODE_PRIVATE);
    }

    public static ConfigHelper getInstance(Context context){
        synchronized (ConfigHelper.class){
            if (configHelper==null){
                configHelper = new ConfigHelper(context);
            }
        }
        return configHelper;
    }

    public static boolean themeHasKey(Context context,String key){
        return getInstance(context).themePref.contains(key);
    }

    public static String getTheme(Context context){
        return getInstance(context).themePref.getString(KEY_THEME,VALUE_THEME_DEFAULT);
    }
    public static void setTheme(Context context,String theme){
        getInstance(context).themePref.edit().putString(KEY_THEME,theme).commit();
    }
    public static boolean themeHasThemeColor(Context context){
        return themeHasKey(context,KEY_THEME);
    }

}
