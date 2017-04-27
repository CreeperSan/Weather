package creeper_san.weather.Helper;

//用于生成网址

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import creeper_san.weather.Application.WeatherApplication;

import static creeper_san.weather.Helper.UrlHelper.Language.*;

public class UrlHelper {
    //城市搜索API
    private final static String URL_LOCATION = "https://api.seniverse.com/v3/location/search.json?";
    private final static String URL_LIFE_SUGGESTION = "https://api.seniverse.com/v3/life/suggestion.json?";
    private final static String URI_WEATHER_DAILY = "https://api.seniverse.com/v3/weather/daily.json?";
    private final static String URI_WEATHER = "https://api.seniverse.com/v3/weather/now.json?";

    //用于计算标签
    public static String decodeSignHead(){
        String ts = String.valueOf(System.currentTimeMillis()/1000);
        String ttl = "30";
        String base="ts="+ts+"&ttl="+ttl+"&uid="+WeatherApplication.USER_ID;
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(WeatherApplication.API_KEY.getBytes(), type);
        try {
            Mac mac = Mac.getInstance(type);
            mac.init(secret);
            byte[] digest = mac.doFinal(base.getBytes());
            String result1 = Base64.encodeToString(digest, Base64.DEFAULT);
            String result = URLEncoder.encode(result1.substring(0,result1.length()-1),"utf-8");
            return base+"&sig="+result;
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     *      此方法用于生成获取城市列表的Url
     * @param cityName  城市名(支持城市ID、城市中文名、省市名称组合、拼音、拼音缩写、经纬度(经度在前，冒号分割)、IP地址)
     * @param language  语言( 可选，默认简体中文，取值范围参考Language内部类中的静态变量 )
     * @param limit     每一页的条目数( 可选，默认50 )
     * @param offset    页数( 可选，默认0 )
     * @return          返回Url
     */
    public static String generateLocateUrl(String cityName,@Language_ String language,int limit,int offset){
        return URL_LOCATION+"&q="+cityName+"&language="+language+"&limit="+limit+
                "&offset="+offset+"&"+decodeSignHead();
    }
    public static String generateLocateUrl(String cityName){
        return generateLocateUrl(cityName, CHINESE_SIMPLIFIED,50,0);
    }
    public static String generateLocateUrl(String cityName,@Language_ String language){
        return generateLocateUrl(cityName, language,50,0);
    }
    public static String generateLocateUrl(String cityName,int limit,int offset){
        return generateLocateUrl(cityName, CHINESE_SIMPLIFIED,limit,offset);
    }

    /**
     *      此方法用于生成获取生活指数的Url
     *      ! 仅仅支持中国的城市
     * @param cityName  城市名( 同上 )
     * @param language  语言 ( 同上 )
     * @return          Url
     */
    public static String generateLifeSuggestionUrl(String cityName,@Language_ String language){
        return URL_LIFE_SUGGESTION+"&location="+cityName+"&language="+language+"&"+decodeSignHead();
    }
    public static String generateLifeSuggestionUrl(String cityName){
        return generateLifeSuggestionUrl(cityName,Language.CHINESE_SIMPLIFIED);
    }

    /**
     *      此方法用于生成获取未来3天天气情况的Url
     * @param key           AppKey
     * @param location      查询的城市
     * @param language      语言
     * @param unit          单位
     * @param start         开始的天数( 5 或者 2017-03-28)
     * @param end           结束的天数(同上)
     * @return
     */
    public static String generateWeatherDailyUrl(String key,String location,@Language_ String language,String unit,String start,String end){
        return "";
    }

    /**
     *      此方法用于生成当前天气情况的Url
     * @param key
     * @param location
     * @param language
     * @param unit
     * @return
     */
    public static String generateWeatherUrl(String key,String location,@Language_ String language,String unit){
        return "";
    }



    /**
     *      支持的语言
     */
    @StringDef({CHINESE_SIMPLIFIED,CHINESE_TRADITIONAL,ENGLISH,JAPANESE,GERMAN,FRENCH
            ,SPANISH,PORTUGUESE,HINDI,INDONESIA,RUSSIAN,THAI,ARABIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Language_{}
    public static class Language{
        public final static String CHINESE_SIMPLIFIED = "zh-Hans";//简体中文 (默认)
        public final static String CHINESE_TRADITIONAL = "zh-Hant";//繁体中文
        public final static String ENGLISH = "en";//英文
        public final static String JAPANESE = "ja";//日语
        public final static String GERMAN = "de";//德语
        public final static String FRENCH = "fr";//法语
        public final static String SPANISH = "es";//西班牙语
        public final static String PORTUGUESE = "pt";//葡萄牙语
        public final static String HINDI = "hi";//印地语
        public final static String INDONESIA = "id";//印度尼西亚语
        public final static String RUSSIAN = "ru";//俄语
        public final static String THAI = "th";//泰语
        public final static String ARABIC = "ar";//阿拉伯语

    }

}
