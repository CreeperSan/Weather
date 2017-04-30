package creeper_san.weather.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import creeper_san.weather.Base.BaseItem;
import creeper_san.weather.Exctption.JsonDecodeException;

import static creeper_san.weather.Exctption.JsonDecodeException.*;
import static creeper_san.weather.Flag.ErrorCode.*;
import static creeper_san.weather.Flag.JsonKey.*;


public class WeatherItem extends BaseItem {
    private List<WeatherSingleItem> singleItemList;

    public WeatherItem(String jsonStr) throws JSONException, JsonDecodeException {
        super(jsonStr);
    }
    public WeatherItem(JSONObject jsonObject) throws JsonDecodeException {
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
            JSONArray heWeather5Json = jsonObject.optJSONArray(KEY_HE_WEATHER5);
            if (heWeather5Json == null){
                throw new JsonDecodeException(TYPE_MISS,"缺少HeWeather5值",CODE_MISSING);
            }
            for (int i=0;i<heWeather5Json.length();i++){
                WeatherSingleItem item = new WeatherSingleItem(heWeather5Json.optJSONObject(i));
                if (item!=null){
                    singleItemList.add(item);
                }
            }
        }else {
            throw new JsonDecodeException(TYPE_MISS,"缺少HeWeather5键",CODE_MISSING);
        }
    }

    private class WeatherSingleItem extends BaseItem{
        private List<DailyItem> dailyItemList;
        private List<HourlyItem> hourlyItemList;
        private String aqi;
        private String co;
        private String no2;
        private String o3;
        private String pm10;
        private String pm25;
        private String qlty;
        private String so2;
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String loc;
        private String utc;
        private String code;
        private String txt;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private String deg;
        private String dir;
        private String sc;
        private String spd;
        private String brfAir;
        private String txtAir;
        private String brfComf;
        private String txtComf;
        private String brfCw;
        private String txtCw;
        private String brfDrsg;
        private String txtDrsg;
        private String brfFlu;
        private String txtFlu;
        private String brfSport;
        private String txtSport;
        private String brfTrav;
        private String txtTrav;
        private String brfUv;
        private String txtUv;

        public WeatherSingleItem(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        public WeatherSingleItem(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            String status = jsonObject.optString(KEY_STATUS,CODE_MISSING);
            if (!status.equals(CODE_OK)){
                throw new JsonDecodeException(TYPE_DECODE_ERR,"status值不正确",status);
            }
            //解析basic
            JSONObject basicJson = jsonObject.optJSONObject(KEY_BASIC);
            if (basicJson!=null){
                city = basicJson.optString(KEY_CITY,KEY_CITY);
                cnty = basicJson.optString(KEY_CNTY,KEY_CNTY);
                id = basicJson.optString(KEY_ID,KEY_ID);
                lat = basicJson.optString(KEY_LAT,KEY_LAT);
                lon = basicJson.optString(KEY_LON,KEY_LON);
                JSONObject updateJson = basicJson.optJSONObject(KEY_UPDATE);
                if (updateJson!=null){
                    loc = updateJson.optString(KEY_LOC,KEY_LOC);
                    utc = updateJson.optString(KEY_UTC,KEY_UTC);
                }
            }
            //解析Now
            JSONObject nowJson = jsonObject.optJSONObject(KEY_NOW);
            if (nowJson!=null){
                JSONObject condJson = nowJson.optJSONObject(KEY_COND);
                if (condJson!=null){
                    code = condJson.optString(KEY_CODE,KEY_CODE);
                    txt = condJson.optString(KEY_TXT,KEY_TXT);
                }
                fl = nowJson.optString(KEY_FL,KEY_FL);
                hum = nowJson.optString(KEY_HUM,KEY_HUM);
                pcpn = nowJson.optString(KEY_PCPN,KEY_PCPN);
                pres = nowJson.optString(KEY_PRES,KEY_PRES);
                tmp = nowJson.optString(KEY_TMP,KEY_TMP);
                vis = nowJson.optString(KEY_VIS,KEY_VIS);
                JSONObject windJson = nowJson.optJSONObject(KEY_WIND);
                if (windJson!=null){
                    deg = windJson.optString(KEY_DEG,KEY_DEG);
                    dir = windJson.optString(KEY_DIR,KEY_DIR);
                    sc = windJson.optString(KEY_SC,KEY_SC);
                    spd = windJson.optString(KEY_SPD,KEY_SPD);
                }
            }
            //解析Suggestion
            JSONObject suggestionJson = jsonObject.optJSONObject(KEY_SUGGESTION);
            if (suggestionJson!=null){
                JSONObject comfJson = suggestionJson.optJSONObject(KEY_COMF);
                if (comfJson!=null){
                    brfComf = comfJson.optString(KEY_BRF,KEY_BRF);
                    txtComf = comfJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject cwJson = suggestionJson.optJSONObject(KEY_CW);
                if (cwJson!=null){
                    brfCw = cwJson.optString(KEY_BRF,KEY_BRF);
                    txtCw = cwJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject drsgJson = suggestionJson.optJSONObject(KEY_DRSG);
                if (drsgJson!=null){
                    brfDrsg = drsgJson.optString(KEY_BRF,KEY_BRF);
                    txtDrsg = drsgJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject fluJson = suggestionJson.optJSONObject(KEY_FLU);
                if (fluJson!=null){
                    brfFlu = fluJson.optString(KEY_BRF,KEY_BRF);
                    txtFlu = fluJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject sportJson = suggestionJson.optJSONObject(KEY_SPORT);
                if (sportJson!=null){
                    brfSport = sportJson.optString(KEY_BRF,KEY_BRF);
                    txtSport = sportJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject travJson = suggestionJson.optJSONObject(KEY_TRAV);
                if (travJson!=null){
                    brfTrav = travJson.optString(KEY_BRF,KEY_BRF);
                    txtTrav = travJson.optString(KEY_TXT,KEY_TXT);
                }
                JSONObject uvJson = suggestionJson.optJSONObject(KEY_UV);
                if (uvJson!=null){
                    brfUv = uvJson.optString(KEY_BRF,KEY_BRF);
                    txtUv = uvJson.optString(KEY_TXT,KEY_TXT);
                }
            }
            //解析hourly
            if (hourlyItemList==null){
                hourlyItemList = new ArrayList<>();
            }else {
                hourlyItemList.clear();
            }
            JSONArray hourlyJsonArray = jsonObject.optJSONArray(KEY_HOURLY_FORECAST);
            if (hourlyJsonArray!=null){
                for (int i=0;i<hourlyJsonArray.length();i++){
                    HourlyItem item = new HourlyItem(hourlyJsonArray.optJSONObject(i));
                    if (item!=null){
                        hourlyItemList.add(item);
                    }
                }
            }
            //解析daily
            if (dailyItemList==null){
                dailyItemList = new ArrayList<>();
            }else {
                dailyItemList.clear();
            }
            JSONArray dailyJsonArray = jsonObject.optJSONArray(KEY_DAILY_FORECAST);
            if (dailyJsonArray!=null){
                for (int i=0;i<dailyJsonArray.length();i++){
                    DailyItem item = new DailyItem(dailyJsonArray.optJSONObject(i));
                    if (item!=null){
                        dailyItemList.add(item);
                    }
                }
            }
        }
    }

    private class DailyItem extends BaseItem{
        private String mr;
        private String ms;
        private String sr;
        private String ss;
        private String codeD;
        private String codeN;
        private String txtD;
        private String txtN;
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

        public DailyItem(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        public DailyItem(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            JSONObject astroJson = jsonObject.optJSONObject(KEY_ASTRO);
            if (astroJson!=null){
                mr = astroJson.optString(KEY_MR,KEY_MR);
                ms = astroJson.optString(KEY_MS,KEY_MS);
                sr = astroJson.optString(KEY_SR,KEY_SR);
                ss = astroJson.optString(KEY_SS,KEY_SS);
            }
            JSONObject condJson = jsonObject.optJSONObject(KEY_COND);
            if (condJson!=null){
                codeD = condJson.optString(KEY_CODE_D,KEY_CODE_D);
                codeN = condJson.optString(KEY_CODE_N,KEY_CODE_N);
                txtD = condJson.optString(KEY_TXT_D,KEY_TXT_D);
                txtN = condJson.optString(KEY_TXT_D,KEY_TXT_D);
            }
            date = jsonObject.optString(KEY_DATE,KEY_DATE);
            hum = jsonObject.optString(KEY_HUM,KEY_HUM);
            pcpn = jsonObject.optString(KEY_PCPN,KEY_PCPN);
            pop = jsonObject.optString(KEY_POP,KEY_POP);
            pres = jsonObject.optString(KEY_PRES,KEY_PRES);
            JSONObject tmpJson = jsonObject.optJSONObject(KEY_TMP);
            if (tmpJson!=null){
                tmpMax = tmpJson.optString(KEY_MAX);
                tmpMin = tmpJson.optString(KEY_MIN);
            }
            uv = jsonObject.optString(KEY_UV);
            vis = jsonObject.optString(KEY_VIS);
            JSONObject windJson = jsonObject.optJSONObject(KEY_WIND);
            //!!!!!!!!!!!!!!!!!!
        }
    }

    private class HourlyItem extends BaseItem{
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

        public HourlyItem(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        public HourlyItem(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            JSONObject condJson = jsonObject.optJSONObject(KEY_COND);
            if (condJson!=null){
                code = condJson.optString(KEY_CODE,KEY_CODE);
                txt = condJson.optString(KEY_TXT,KEY_TXT);
            }
            date = jsonObject.optString(KEY_DATE,KEY_DATE);
            hum = jsonObject.optString(KEY_HUM,KEY_HUM);
            pop = jsonObject.optString(KEY_POP,KEY_POP);
            pres = jsonObject.optString(KEY_PRES,KEY_PRES);
            tmp = jsonObject.optString(KEY_TMP,KEY_TMP);
            JSONObject windJson = jsonObject.optJSONObject(KEY_WIND);
            if (windJson!=null){
                deg = windJson.optString(KEY_DEG,KEY_DEG);
                dir = windJson.optString(KEY_DIR,KEY_DIR);
                sc = windJson.optString(KEY_SC,KEY_SC);
                spd = windJson.optString(KEY_SPD,KEY_SPD);
            }
        }
    }
}
