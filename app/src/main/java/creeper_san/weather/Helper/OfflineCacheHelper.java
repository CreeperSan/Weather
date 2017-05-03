package creeper_san.weather.Helper;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OfflineCacheHelper {
    private static String FILE_PATH = "/Cache/";

    private static OfflineCacheHelper instance;

    private OfflineCacheHelper() {
        init();
    }

    private void init() {

    }

    public static void saveCityOfflineData(Context context, String cityID){
        File file = new File(context.getApplicationContext().getFilesDir(),cityID+".json");
        if (file.exists()){
            log("文件已存在");
        }else {
            try {
                if (file.createNewFile()){
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write("你好世界".getBytes());
                    fos.close();
                    log("文件写入完毕");
                }else {
                    log("!!!!!!!!!!创建文件失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
                log("!!!!!!!!!!异常");
            }
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
