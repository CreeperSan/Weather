package creeper_san.weather;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.io.IOException;

import creeper_san.weather.Base.BaseService;
import creeper_san.weather.Event.SearchResultEvent;
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
import creeper_san.weather.Interface.HttpStringCallback;
import creeper_san.weather.Json.SearchJson;
import creeper_san.weather.Json.UpdateJson;
import creeper_san.weather.Json.WeatherJson;
import okhttp3.Call;

public class WeatherService extends BaseService {


    /**
     *      EventBus
     */
    @Subscribe()
    public void onWeatherRequestEvent(WeatherRequestEvent event){
        getWeather(event.getCityName());
    }
    @Subscribe()
    public void onUpdateRequestEvent(UpdateRequestEvent event){
        if (event.getType() == UpdateRequestEvent.TYPE_CHECK_UPDATE){
            getUpdate();
        }else if (event.getType() == UpdateRequestEvent.TYPE_CHECK_UPDATE_HISTORY){
            getUpdateHistory();
        }
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
}
