package creeper_san.weather;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import creeper_san.weather.Base.BaseService;
import creeper_san.weather.Event.BingImageEvent;
import creeper_san.weather.Event.BingImageResultEvent;
import creeper_san.weather.Event.SearchResultEvent;
import creeper_san.weather.Event.UpdateApkDownloadEvent;
import creeper_san.weather.Event.UpdateHistoryResultEvent;
import creeper_san.weather.Event.UpdateRequestEvent;
import creeper_san.weather.Event.UpdateResultEvent;
import creeper_san.weather.Event.WeatherRequestEvent;
import creeper_san.weather.Event.WeatherResultEvent;
import creeper_san.weather.Exctption.JsonDecodeException;
import creeper_san.weather.Flag.LanguageCode;
import creeper_san.weather.Flag.LanguageCode.Language_;
import creeper_san.weather.Helper.HttpHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Interface.HttpBitmapCallback;
import creeper_san.weather.Interface.HttpStringCallback;
import creeper_san.weather.Json.SearchJson;
import creeper_san.weather.Json.UpdateJson;
import creeper_san.weather.Json.WeatherJson;
import okhttp3.Call;
import okhttp3.Response;

public class WeatherService extends BaseService {
    private DownloadApkReceiver downloadApkReceiver;
    private String downloadApkName = "0";

    long downloadID = 0;

    /**
     *      EventBus
     */
    @Subscribe()
    public void onWeatherRequestEvent(WeatherRequestEvent event){
        getWeather(event.getCityName());
    }
    @Subscribe(sticky = true)
    public void onUpdateRequestEvent(UpdateRequestEvent event){
        if (event.getType() == UpdateRequestEvent.TYPE_CHECK_UPDATE){
            getUpdate();
        }else if (event.getType() == UpdateRequestEvent.TYPE_CHECK_UPDATE_HISTORY){
            getUpdateHistory();
        }
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Subscribe()
    public void onUpdateApkDownloadEvent(UpdateApkDownloadEvent event){
        if (System.currentTimeMillis() - Long.valueOf(downloadApkName)<1000*60*60*24){
//            Toast.makeText(this,"已经在下载中",Toast.LENGTH_SHORT).show();
            return;
        }
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(UrlHelper.generateDownloadAddress()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        downloadApkName = System.currentTimeMillis()+".apk";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,downloadApkName);
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!file.exists()){
            file.mkdirs();
        }
        request.setTitle("下载更新包");
        request.setAllowedOverRoaming(true);
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadID = downloadManager.enqueue(request);
    }
    @Subscribe(sticky = true)
    public void onBingImageEvent(BingImageEvent event){
        log("收到必应图片请求");
        HttpHelper.httpGet(event.getUrl(), new HttpStringCallback() {
            @Override
            public void onFail(Call call, IOException e, int requestCode) {
                loge("获取图片地址失败");
                postEvent(new BingImageResultEvent(BingImageResultEvent.ResultType.INSTANCE.getTYPE_FAIL(),null));
            }

            @Override
            public void onResult(Call call, String result, int requestCode) {
                if (result.startsWith("http")){
                    loge("成功获取图片地址，发送获取图片请求");
                    loge("获取到的地址为 "+result);
                    HttpHelper.httpGetImage(result, new HttpBitmapCallback() {
                        @Override
                        public void onFail(@NotNull Call call, @NotNull IOException e) {
                            loge("图片服务器返回必应图片失败");
                            postEvent(new BingImageResultEvent(BingImageResultEvent.ResultType.INSTANCE.getTYPE_FAIL(),null));
                        }

                        @Override
                        public void onResult(@NotNull Call call, @NotNull Response response, @NotNull Bitmap bitmap) {
                            log("图片服务器成功返回必应图片");
                            postEvent(new BingImageResultEvent(BingImageResultEvent.ResultType.INSTANCE.getTYPE_SUCCESS(),bitmap));
                            log("已向管理器发送返回结果");
                        }
                    });
                }else {
                    postEvent(new BingImageResultEvent(BingImageResultEvent.ResultType.INSTANCE.getTYPE_FAIL(),null));
                }
            }
        });
        log("已向图片服务器发出必应图片请求");
        EventBus.getDefault().removeStickyEvent(event);
    }

    /**
     *      生命周期
     */
    @Override
    public void onCreate() {
        super.onCreate();
        downloadApkReceiver = new DownloadApkReceiver();
        registerReceiver(downloadApkReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadApkReceiver);
    }

    /**
     *      网络操作部分
     */
    public void searchCity(String cityName){
        searchCity(cityName, LanguageCode.CHINESE);
    }
    public void searchCity(String cityName,@Language_ String language){
        HttpHelper.httpGet(UrlHelper.generateSearchUrl(cityName, language), new HttpStringCallback() {
            @Override
            public void onFail(Call call, IOException e, int requestCode) {
                postEvent(new SearchResultEvent(false,null));
            }

            @Override
            public void onResult(Call call, String result, int requestCode) {
                try {
                    postEvent(new SearchResultEvent(true,new SearchJson(result)));
                } catch (JSONException | JsonDecodeException e) {
//                    e.printStackTrace();
                    postEvent(new SearchResultEvent(true,null));
                }
            }
        });
    }
    public void getWeather(String cityName){
        getWeather(cityName,LanguageCode.CHINESE);
    }
    public void getWeather(String cityName,@Language_ String language){
        HttpHelper.httpGet(UrlHelper.generateWeatherUrl(cityName), new HttpStringCallback() {
            @Override
            public void onFail(Call call, IOException e, int requestCode) {
                postEvent(new WeatherResultEvent(false,null));
            }

            @Override
            public void onResult(Call call, String result, int requestCode) {
                try {
                    postEvent(new WeatherResultEvent(true,new WeatherJson(result)));
                } catch (JSONException | JsonDecodeException e) {
//                    e.printStackTrace();
                    postEvent(new WeatherResultEvent(true,null));
                }
            }
        });
    }
    public void getUpdate(){
        HttpHelper.httpGet(UrlHelper.generateUpdateUrl(), new HttpStringCallback() {
            @Override
            public void onFail(Call call, IOException e, int requestCode) {
                postEvent(new UpdateResultEvent());
            }

            @Override
            public void onResult(Call call, String result, int requestCode) {
                postEvent(new UpdateResultEvent(result));
            }
        });
    }
    public void getUpdateHistory(){
        HttpHelper.httpGet(UrlHelper.generateUpdateHistoryUrl(), new HttpStringCallback() {
            @Override
            public void onFail(Call call, IOException e, int requestCode) {
                postEvent(new UpdateHistoryResultEvent(false,null));
            }

            @Override
            public void onResult(Call call, String result, int requestCode) {
                postEvent(new UpdateResultEvent(result));
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new WeatherServiceBinder();
    }
    class WeatherServiceBinder extends Binder{
        public WeatherService getService(){
            return WeatherService.this;
        }
    }
    class DownloadApkReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (downloadID == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)) {
                Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
