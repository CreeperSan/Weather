package creeper_san.weather.Helper;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import creeper_san.weather.Exctption.JsonDecodeException;
import creeper_san.weather.Json.WeatherItem;

public class OfflineCacheHelper {
    private final static String FILE_PATH = "/Offline/";

    private static OfflineCacheHelper instance;

    private OfflineCacheHelper() {
        init();
    }

    private void init() {

    }

    public static void saveCityOfflineData(Context context, String cityID ,String jsonStr){
        File file = new File(context.getApplicationContext().getFilesDir()+FILE_PATH+cityID);
//        log(file.getAbsolutePath());
//        log(file.getParent());
        if (!file.exists()){
            try {
                if (!file.getParentFile().exists()){
                    if (!file.getParentFile().mkdirs()){
                        log("创建文件夹路径失败");
                    }
                }
                if (!file.createNewFile()){
                    log("创建文件失败");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                log("缓存文件创建失败");
                return;
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonStr.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log("缓存文件没有找到");
        } catch (IOException e) {
            e.printStackTrace();
            log("缓存文件写入失败");
        }
    }
    public static WeatherItem getWeatherItemFromCache(Context context,String cityID){
        File file = new File(context.getApplicationContext().getFilesDir()+FILE_PATH+cityID);
        if (file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                long length = file.length();
                byte[] buffer = new byte[(int) length];
                fis.read(buffer);
                fis.close();
                return new WeatherItem(new String(buffer));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JsonDecodeException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    private static void log(String content){
        Log.e("FileHelper",content);
    }

    public static OfflineCacheHelper getInstance(){
        if (instance == null){
            instance = new OfflineCacheHelper();
        }
        return instance;
    }
}
