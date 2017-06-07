package creeper_san.weather.Helper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import org.greenrobot.eventbus.EventBus;

import creeper_san.weather.Event.RequirePermissionEvent;

public class PermissionHelper {

    private static PermissionHelper mInstance;

    private PermissionHelper(){}

    private static PermissionHelper getInstance(){
        if (mInstance==null){
            mInstance = new PermissionHelper();
        }
        return mInstance;
    }

    public static boolean isApiOverM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasPermission(Context context, @NonNull String permission){
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requirePermission(@NonNull String permission,int typeID){
        EventBus.getDefault().post(new RequirePermissionEvent(permission,typeID));
    }

}
