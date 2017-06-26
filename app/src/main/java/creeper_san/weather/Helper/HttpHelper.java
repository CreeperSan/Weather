package creeper_san.weather.Helper;

//用于Api数据获取

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;

import creeper_san.weather.Interface.HttpBitmapCallback;
import creeper_san.weather.Interface.HttpCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {
    public final static int REQUEST_CODE_DEFAULT = -1;

    private static HttpHelper mInstance;//自身的实例
    private OkHttpClient httpClient;

    private HttpHelper() {
        httpClient = new OkHttpClient();
    }

    private static HttpHelper getInstance() {
        synchronized (HttpHelper.class){
            if (mInstance == null){
                mInstance = new HttpHelper();
            }
        }
        return mInstance;
    }

    private void _httpGet(String url,HttpCallback callback){
        _httpGet(url,REQUEST_CODE_DEFAULT,callback);
    }
    private void _httpGet(String url, final int requestCode , final HttpCallback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(call,e,requestCode);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResult(call,response,requestCode);
            }
        });
    }

    public static void httpGet(String url,HttpCallback callback){
        getInstance()._httpGet(url,callback);
    }
    public static void httpGet(String url,int requestCode,HttpCallback callback){
        getInstance()._httpGet(url,requestCode,callback);
    }
    public static void httpGetImage(String url, final HttpBitmapCallback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = getInstance().httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                callback.onResult(call,response,bmp);
            }
        });
    }

}
