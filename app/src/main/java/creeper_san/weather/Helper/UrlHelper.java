package creeper_san.weather.Helper;

//用于生成网址

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import creeper_san.weather.Application.WeatherApplication;
import static creeper_san.weather.Flag.LanguageCode.*;


public class UrlHelper {
    //城市搜索API
    private final static String URL_SEARCH = "https://api.heweather.com/v5/search?";//城市搜索
    private final static String URL_SUGGESTION = "https://free-api.heweather.com/v5/suggestion?";//生活指数
    private final static String URL_HOURLY = "https://free-api.heweather.com/v5/hourly?";//未来每小时
    private final static String URL_NOW = "https://free-api.heweather.com/v5/now?";//实况天气
    private final static String URL_FORECAST = "https://free-api.heweather.com/v5/forecast?";//3天天气
    private final static String URL_WEATHER = "https://free-api.heweather.com/v5/weather?";//全部天气
    private final static String URL_UPDATE = "https://creepersan.github.io/app-version/weather.json";//版本更新地址
    private final static String URL_UPDATE_HISTORY = "https://creepersan.github.io/app-version/weather-history.json";//版本历史
    private final static String URL_BING_IMAGE_768P = "https://www.dujin.org/sys/bing/1366.php";
    private final static String URL_BING_IMAGE_1080P = "https://www.dujin.org/sys/bing/1920.php";

    //用于计算标签
    public static String decodeHead(){
        return "key="+WeatherApplication.API_KEY;
    }


    /**
     *      此方法用于生成获取城市列表的Url
     * @param cityName  城市名(城市中文名、拼音、ID、IP地址)
     * @param language  返回的语言 (默认中文)
     * @return          返回Url
     */
    public static String generateSearchUrl(String cityName,@Language_ String language){
        return URL_SEARCH +"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateSearchUrl(String cityName){
        return generateSearchUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于获取生活指数 ( 仅包含国内城市 )
     * @param cityName  城市名称
     * @param language  语言(只有城市名字变成其他语言)
     * @return          返回Url
     */
    public static String generateSuggestionUrl(String cityName,@Language_ String language){
        return URL_SUGGESTION+"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateSuggestionUrl(String cityName){
        return generateSuggestionUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于获取未来每3小时预报
     * @param cityName  城市名称
     * @param language  语言
     * @return          Url
     */
    public static String generateHourlyUrl(String cityName,@Language_ String language){
        return URL_HOURLY+"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateHourlyUrl(String cityName){
        return generateHourlyUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于获取实况天气
     * @param cityName  城市名称
     * @param language  语言
     * @return          Url
     */
    public static String generateNowUrl(String cityName,@Language_ String language){
        return URL_NOW+"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateNowUrl(String cityName){
        return generateNowUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于获取3天天气预报
     * @param cityName  城市名称
     * @param language  语言
     * @return           Url
     */
    public static String generateForecastUrl(String cityName,@Language_ String language){
        return URL_FORECAST+"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateForecastUrl(String cityName){
        return generateForecastUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于生成城市天气Url
     * @param cityName  城市名字
     * @param language  语言
     * @return          URL
     */
    public static String generateWeatherUrl(String cityName,@Language_ String language){
        return URL_WEATHER+"city="+cityName+"&lang="+language+"&"+decodeHead();
    }
    public static String generateWeatherUrl(String cityName){
        return generateWeatherUrl(cityName,CHINESE);
    }

    /**
     *      此方法用于生成版本检查链接
     * @return 链接地址
     */
    public static String generateUpdateUrl(){
        return URL_UPDATE;
    }
    public static String generateUpdateHistoryUrl(){
        return URL_UPDATE_HISTORY;
    }

    /**
     *      此方法用于生成bing背景图片链接
     * @return
     */
    public static String generateBingImage768pUrl(){
        return URL_BING_IMAGE_768P;
    }
    public static String generateBingImage1080pUrl(){
        return URL_BING_IMAGE_1080P;
    }

}
