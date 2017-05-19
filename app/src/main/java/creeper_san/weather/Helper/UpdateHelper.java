package creeper_san.weather.Helper;

import android.content.Context;
import android.content.pm.PackageManager;

public class UpdateHelper {
    private static UpdateHelper updateHelper;

    private UpdateHelper() {}

    public static UpdateHelper getInstance(){
        if (updateHelper==null){
            updateHelper = new UpdateHelper();
        }
        return updateHelper;
    }

    public String getVersionName(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    public int getVersionCode(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public void checkUpdateAvailable(){

    }
}
