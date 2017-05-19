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

public class ForecastJson extends BaseJson {
    private List<ForecastSingleJson> singleItemList;

    public ForecastJson(String jsonStr) throws JSONException, JsonDecodeException {
        super(jsonStr);
    }
    public ForecastJson(JSONObject jsonObject) throws JsonDecodeException {
        super(jsonObject);
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
        if (singleItemList==null){
            singleItemList = new ArrayList<>();
        }else {
            singleItemList.clear();
        }
        if (jsonObject.has(KEY_HE_WEATHER5)){
            JSONArray heWeatherArray = jsonObject.optJSONArray(KEY_HE_WEATHER5);
            if (heWeatherArray!=null){
                for (int i=0;i<heWeatherArray.length();i++){
                    ForecastSingleJson item = new ForecastSingleJson(heWeatherArray.optJSONObject(i));
                    if (item!=null){
                        singleItemList.add(item);
                    }
                }
            }else {
                throw new JsonDecodeException(TYPE_MISS,"缺少HeWeather5值",CODE_MISSING);
            }
        }else {
            throw new JsonDecodeException(TYPE_MISS,"缺少HeWeather5键值",CODE_MISSING);
        }
    }

    public int size(){
        return singleItemList==null?0:singleItemList.size();
    }
    public int dailySize(int which){
        if (singleItemList==null){
            return 0;
        }
        if (singleItemList.get(which).dailyItemList ==null){
            return 0;
        }
        return singleItemList.get(which).dailyItemList.size();
    }

    public String getCity(int which){
        return singleItemList.get(which).getCity();
    }
    public String getCountry(int which){
        return singleItemList.get(which).getCnty();
    }
    public String getID(int which){
        return singleItemList.get(which).getId();
    }
    public String getLat(int which){
        return singleItemList.get(which).getLat();
    }
    public String getLon(int which){
        return singleItemList.get(which).getLon();
    }
    public String getLoc(int which){
        return singleItemList.get(which).getLoc();
    }
    public String getUtc(int which){
        return singleItemList.get(which).getUtc();
    }

    public String getForecastMr(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getMr();
    }
    public String getForecastMs(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getMs();
    }
    public String getForecastSr(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getSr();
    }
    public String getForecastSs(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getSs();
    }
    public String getForecastCodeD(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getCode_d();
    }
    public String getForecastCodeN(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getCode_n();
    }
    public String getForecastTxtD(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getTxt_d();
    }
    public String getForecastTxtN(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getTxt_n();
    }
    public String getForecastDate(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getDate();
    }
    public String getForecastHum(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getHum();
    }
    public String getForecastPcpn(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getPcpn();
    }
    public String getForecastPop(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getPop();
    }
    public String getForecastPres(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getPres();
    }
    public String getForecastTemMax(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getTmpMax();
    }
    public String getForecastTmpMin(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getTmpMin();
    }
    public String getForecastUv(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getUv();
    }
    public String getForecastVis(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getVis();
    }
    public String getForecastDeg(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getDeg();
    }
    public String getForecastDir(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getDir();
    }
    public String getForecastSc(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getSc();
    }
    public String getForecastSpd(int which,int when){
        return singleItemList.get(which).dailyItemList.get(when).getSpd();
    }

    //----------------------------------------------------------------------------------------------
    private class ForecastSingleJson extends BaseJson {
        private List<ForecastSingleDailyJson> dailyItemList;
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String loc;
        private String utc;

        public ForecastSingleJson(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        public ForecastSingleJson(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            if (dailyItemList==null){
                dailyItemList = new ArrayList<>();
            }else {
                dailyItemList.clear();
            }
            log("================================================");
            log(jsonObject.toString());
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
                    //每天的天气预报
                    JSONArray dailyJsonArray = jsonObject.optJSONArray(KEY_DAILY_FORECAST);
                    if (dailyJsonArray!=null){
                        for (int i=0;i<dailyJsonArray.length();i++){
                            ForecastSingleDailyJson item = new ForecastSingleDailyJson(dailyJsonArray.optJSONObject(i));
                            if (item!=null){
                                dailyItemList.add(item);
                            }
                        }
                    }
                }else {
                    throw new JsonDecodeException(TYPE_DECODE_ERR,"status不成功",status);
                }
            }else {
                throw new JsonDecodeException(TYPE_MISS,"status找不到",CODE_MISSING);
            }
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



    //----------------------------------------------------------------------------------------------
    private class ForecastSingleDailyJson extends BaseJson {
        private String mr;
        private String ms;
        private String sr;
        private String ss;
        private String code_d;
        private String code_n;
        private String txt_d;
        private String txt_n;
        private String date;
        private String hum;
        private String pcpn;
        private String pop;
        private String pres;
        private String tmpMax;
        private String tmpMin;
        private String uv;
        private String vis;
        private String deg;
        private String dir;
        private String sc;
        private String spd;

        public ForecastSingleDailyJson(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        public ForecastSingleDailyJson(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            JSONObject astroJson = jsonObject.optJSONObject(KEY_ASTRO);
            if (astroJson!=null){
                mr = astroJson.optString(KEY_MR);
                ms = astroJson.optString(KEY_MS);
                sr = astroJson.optString(KEY_SR);
                ss = astroJson.optString(KEY_SS);
            }
            JSONObject condJson = jsonObject.optJSONObject(KEY_COND);
            if (condJson!=null){
                code_d = condJson.optString(KEY_CODE_D);
                code_n = condJson.optString(KEY_CODE_N);
                txt_d = condJson.optString(KEY_TXT_D);
                txt_n = condJson.optString(KEY_TXT_N);
            }
            date = jsonObject.optString(KEY_DATE);
            hum = jsonObject.optString(KEY_HUM);
            pcpn = jsonObject.optString(KEY_PCPN);
            pop = jsonObject.optString(KEY_POP);
            pres = jsonObject.optString(KEY_PRES);
            JSONObject tmpJson = jsonObject.optJSONObject(KEY_TMP);
            if (tmpJson!=null){
                tmpMax = tmpJson.optString(KEY_MAX);
                tmpMin = tmpJson.optString(KEY_MIN);
            }
            uv = jsonObject.optString(KEY_UV);
            vis = jsonObject.optString(KEY_VIS);
            JSONObject windJson = jsonObject.optJSONObject(KEY_WIND);
            if (windJson!=null){
                deg = windJson.optString(KEY_DEG);
                dir = windJson.optString(KEY_DIR);
                sc = windJson.optString(KEY_SC);
                spd = windJson.optString(KEY_SPD);
            }
        }

        private String getMr() {
            return mr;
        }

        private String getMs() {
            return ms;
        }

        private String getSr() {
            return sr;
        }

        private String getSs() {
            return ss;
        }

        private String getCode_d() {
            return code_d;
        }

        private String getCode_n() {
            return code_n;
        }

        private String getTxt_d() {
            return txt_d;
        }

        private String getTxt_n() {
            return txt_n;
        }

        private String getDate() {
            return date;
        }

        private String getHum() {
            return hum;
        }

        private String getPcpn() {
            return pcpn;
        }

        private String getPop() {
            return pop;
        }

        private String getPres() {
            return pres;
        }

        private String getTmpMax() {
            return tmpMax;
        }

        private String getTmpMin() {
            return tmpMin;
        }

        private String getUv() {
            return uv;
        }

        private String getVis() {
            return vis;
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
