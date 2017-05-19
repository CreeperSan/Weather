package creeper_san.weather.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import creeper_san.weather.Base.BaseJson;
import creeper_san.weather.Exctption.JsonDecodeException;

import static creeper_san.weather.Flag.JsonKey.*;
import static creeper_san.weather.Exctption.JsonDecodeException.*;
import static creeper_san.weather.Flag.ErrorCode.*;

public class HourlyJson extends BaseJson {
    private List<HourlySingleJson> hourlySingleItemList;

    public HourlyJson(String jsonStr) throws JSONException, JsonDecodeException {
        super(jsonStr);
    }
    public HourlyJson(JSONObject jsonObject) throws JsonDecodeException {
        super(jsonObject);
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
        if (hourlySingleItemList==null){
            hourlySingleItemList = new ArrayList<>();
        }else {
            hourlySingleItemList.clear();
        }
        if (jsonObject.has(KEY_HE_WEATHER5)){
            JSONArray heWeather5JsonArray = jsonObject.optJSONArray(KEY_HE_WEATHER5);
            if (heWeather5JsonArray==null){
                throw new JsonDecodeException(TYPE_MISS_VALUE,"缺少HeWeather5内容",CODE_MISSING);
            }else {
//                log("长度 "+heWeather5JsonArray.length());
                for (int i=0;i<heWeather5JsonArray.length();i++){
                    HourlySingleJson item = new HourlySingleJson(heWeather5JsonArray.optJSONObject(i));
                    if (item==null){
                        continue;
                    }
                    hourlySingleItemList.add(item);
                }
            }
        }else {
            throw new JsonDecodeException(TYPE_MISS,"缺少Heweather5关键字",CODE_MISSING);
        }
    }

    public int size(){
        return (hourlySingleItemList==null)?0:hourlySingleItemList.size();
    }
    public int forecastSize(int which){
        if (hourlySingleItemList==null){
            return 0;
        }
        if (which >= hourlySingleItemList.size()){
            return 0;
        }
        if (hourlySingleItemList.get(which).forecastItemList == null){
            return 0;
        }
        return hourlySingleItemList.get(which).forecastItemList.size();
    }

    public String getCity(int which){
        return hourlySingleItemList.get(which).getCity();
    }
    public String getCountry(int which){
        return hourlySingleItemList.get(which).getCnty();
    }
    public String getID(int which){
        return hourlySingleItemList.get(which).getId();
    }
    public String getLat(int which){
        return hourlySingleItemList.get(which).getLat();
    }
    public String getLon(int which){
        return hourlySingleItemList.get(which).getLon();
    }
    public String getLoc(int which){
        return hourlySingleItemList.get(which).getLoc();
    }
    public String getUtc(int which){
        return hourlySingleItemList.get(which).getUtc();
    }
    public String getForecastCode(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getCode();
    }
    public String getForecastTxt(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getTxt();
    }
    public String getForecastDate(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getDate();
    }
    public String getForecastHum(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getHum();
    }
    public String getForecastPop(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getPop();
    }
    public String getForecastPres(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getPres();
    }
    public String getForecastTmp(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getTmp();
    }
    public String getForecastDeg(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getDeg();
    }
    public String getForecastDir(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getDir();
    }
    public String getForecastSc(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getSc();
    }
    public String getForecastSpd(int which,int when){
        return hourlySingleItemList.get(which).forecastItemList.get(when).getSpd();
    }



    private class HourlySingleJson extends BaseJson {
        private List<HourlySingleForecastJson> forecastItemList;
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String loc;
        private String utc;

        private HourlySingleJson(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        private HourlySingleJson(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
//            log("------------------------------------");
//            log(jsonObject.toString());
            if (forecastItemList==null){
                forecastItemList = new ArrayList<>();
            }else {
                forecastItemList.clear();
            }
            if (jsonObject.has(KEY_STATUS)){
                String status = jsonObject.optString(KEY_STATUS,CODE_MISSING);
                if (status.equals(CODE_OK)){
                    JSONObject basicJson = jsonObject.optJSONObject(KEY_BASIC);
                    if (basicJson!=null){
                        city = basicJson.optString(KEY_CITY);
                        cnty = basicJson.optString(KEY_CNTY);
                        id = basicJson.optString(KEY_ID);
                        lat = basicJson.optString(KEY_LAT);
                        lon = basicJson.optString(KEY_LON);
                        JSONObject updateJson = basicJson.optJSONObject(KEY_UPDATE);
                        if (updateJson!=null){
                            loc = updateJson.optString(KEY_LOC);
                            utc = updateJson.optString(KEY_UTC);
                        }
                    }
                    JSONArray forecastJsonArray = jsonObject.optJSONArray(KEY_HOURLY_FORECAST);
                    if (forecastJsonArray!=null){
                        for (int i=0;i<forecastJsonArray.length();i++){
                            JSONObject forecastJson = forecastJsonArray.optJSONObject(i);
                            if (forecastJson==null){
                                continue;
                            }
                            forecastItemList.add(new HourlySingleForecastJson(forecastJson));
                        }
                    }
                }else {
                    throw new JsonDecodeException(TYPE_DECODE_ERR,"status未成功",status);
                }
            }
        }

        private int size(){
            return forecastItemList.size();
        }

        private String getCity() {
            return city;
        }

        private String getCnty() {
            return cnty;
        }

        private String getId() {
            return id;
        }

        private String getLat() {
            return lat;
        }

        private String getLon() {
            return lon;
        }

        private String getLoc() {
            return loc;
        }

        private String getUtc() {
            return utc;
        }
    }





    private class HourlySingleForecastJson extends BaseJson {
        private String code;
        private String txt;
        private String date;
        private String hum;
        private String pop;
        private String pres;
        private String tmp;
        private String deg;
        private String dir;
        private String sc;
        private String spd;

        private HourlySingleForecastJson(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        private HourlySingleForecastJson(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            log("--------------------------------------");
            log(jsonObject.toString());
            JSONObject condJson = jsonObject.optJSONObject(KEY_COND);
            if (condJson!=null){
                code = condJson.optString(KEY_CODE);
                txt = condJson.optString(KEY_TXT);
            }
            date = jsonObject.optString(KEY_DATE);
            hum = jsonObject.optString(KEY_HUM);
            pop = jsonObject.optString(KEY_POP);
            pres = jsonObject.optString(KEY_PRES);
            tmp = jsonObject.optString(KEY_TMP);
            JSONObject windJson = jsonObject.optJSONObject(KEY_WIND);
            if (windJson!=null){
                deg = windJson.optString(KEY_DEG);
                dir = windJson.optString(KEY_DIR);
                sc = windJson.optString(KEY_SC);
                spd = windJson.optString(KEY_SPD);
            }
        }

        private String getCode() {
            return code;
        }

        private String getTxt() {
            return txt;
        }

        private String getDate() {
            return date;
        }

        private String getHum() {
            return hum;
        }

        private String getPop() {
            return pop;
        }

        private String getPres() {
            return pres;
        }

        private String getTmp() {
            return tmp;
        }

        private String getDeg() {
            return deg;
        }

        private String getDir() {
            return dir;
        }

        private String getSc() {
            return sc;
        }

        private String getSpd() {
            return spd;
        }
    }

}
