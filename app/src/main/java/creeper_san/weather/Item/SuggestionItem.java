package creeper_san.weather.Item;

import android.util.Log;

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

public class SuggestionItem extends BaseItem{

    private List<SuggestionSingleItem> suggestionSingleItemList;

    public SuggestionItem(String jsonStr) throws JSONException, JsonDecodeException {
        super(jsonStr);
    }
    public SuggestionItem(JSONObject jsonObject) throws JsonDecodeException {
        super(jsonObject);
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
        if (suggestionSingleItemList==null){
            suggestionSingleItemList = new ArrayList<>();
        }else {
            suggestionSingleItemList.clear();
        }
        if (jsonObject.has(KEY_HE_WEATHER5)){
            JSONArray array = jsonObject.optJSONArray(KEY_HE_WEATHER5);
            for (int i=0;i<array.length();i++){
                try {
                    SuggestionSingleItem singleItem = new SuggestionSingleItem((JSONObject) array.get(i));
                    suggestionSingleItemList.add(singleItem);
                } catch (JSONException e) {
                    throw new JsonDecodeException(TYPE_MISS,"缺少城市节点信息",CODE_MISSING);
                }
            }
        }else {
            throw new JsonDecodeException(TYPE_MISS,"缺少主体数据 HeWeather5",CODE_MISSING);
        }
    }

    public int size(){
        return suggestionSingleItemList==null?0:suggestionSingleItemList.size();
    }

    public String getCity(int which) {
        return suggestionSingleItemList.get(which).getCity();
    }

    public String getCountry(int which) {
        return suggestionSingleItemList.get(which).getCnty();
    }

    public String getId(int which) {
        return suggestionSingleItemList.get(which).getId();
    }

    public String getLat(int which) {
        return suggestionSingleItemList.get(which).getLat();
    }

    public String getLon(int which) {
        return suggestionSingleItemList.get(which).getLon();
    }

    public String getLoc(int which) {
        return suggestionSingleItemList.get(which).getLoc();
    }

    public String getUtc(int which) {
        return suggestionSingleItemList.get(which).getUtc();
    }

    public String getComfBrf(int which) {
        return suggestionSingleItemList.get(which).getComfBrf();
    }

    public String getComfTxt(int which) {
        return suggestionSingleItemList.get(which).getComfTxt();
    }

    public String getCwBrf(int which) {
        return suggestionSingleItemList.get(which).getCwBrf();
    }

    public String getCwTxt(int which) {
        return suggestionSingleItemList.get(which).getCwTxt();
    }

    public String getDrsgBrf(int which) {
        return suggestionSingleItemList.get(which).getDrsgBrf();
    }

    public String getDrsgTxt(int which) {
        return suggestionSingleItemList.get(which).getDrsgTxt();
    }

    public String getFluBrf(int which) {
        return suggestionSingleItemList.get(which).getFluBrf();
    }

    public String getFluTxt(int which) {
        return suggestionSingleItemList.get(which).getFluTxt();
    }

    public String getSportBrf(int which) {
        return suggestionSingleItemList.get(which).getSportBrf();
    }

    public String getSportTxt(int which) {
        return suggestionSingleItemList.get(which).getSportTxt();
    }

    public String getTravBrf(int which) {
        return suggestionSingleItemList.get(which).getTravBrf();
    }

    public String getTravTxt(int which) {
        return suggestionSingleItemList.get(which).getTravTxt();
    }

    public String getUvBrf(int which) {
        return suggestionSingleItemList.get(which).getUvBrf();
    }

    public String getUvTxt(int which) {
        return suggestionSingleItemList.get(which).getUvTxt();
    }


    /**
     *      单个Item
     */

    private class SuggestionSingleItem extends BaseItem{
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String loc;
        private String utc;
        private String brf_comf;
        private String txt_comf;
        private String brf_cw;
        private String txt_cw;
        private String brf_drsg;
        private String txt_drsg;
        private String brf_flu;
        private String txt_flu;
        private String brf_sport;
        private String txt_sport;
        private String brf_trav;
        private String txt_trav;
        private String brf_uv;
        private String txt_uv;

        private SuggestionSingleItem(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        private SuggestionSingleItem(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
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

        private String getComfBrf() {
            return brf_comf;
        }

        private String getComfTxt() {
            return txt_comf;
        }

        private String getCwBrf() {
            return brf_cw;
        }

        private String getCwTxt() {
            return txt_cw;
        }

        private String getDrsgBrf() {
            return brf_drsg;
        }

        private String getDrsgTxt() {
            return txt_drsg;
        }

        private String getFluBrf() {
            return brf_flu;
        }

        private String getFluTxt() {
            return txt_flu;
        }

        private String getSportBrf() {
            return brf_sport;
        }

        private String getSportTxt() {
            return txt_sport;
        }

        private String getTravBrf() {
            return brf_trav;
        }

        private String getTravTxt() {
            return txt_trav;
        }

        private String getUvBrf() {
            return brf_uv;
        }

        private String getUvTxt() {
            return txt_uv;
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
            log("----------------------------");
            log(jsonObject.toString());
            if (jsonObject.has(KEY_STATUS)){
                String status = jsonObject.optString(KEY_STATUS,CODE_MISSING);
                if (status.equals(CODE_OK)){
                    //解析basic
                    JSONObject basicJson = jsonObject.optJSONObject(KEY_BASIC);
                    if (basicJson!=null){
                        city = basicJson.optString(KEY_CITY,KEY_CITY);
                        cnty = basicJson.optString(KEY_CNTY,KEY_CNTY);
                        id = basicJson.optString(KEY_ID,KEY_ID);
                        lat = basicJson.optString(KEY_LAT,KEY_LAT);
                        lon = basicJson.optString(KEY_LON,KEY_LON);
                        //Update
                        JSONObject updateJson = basicJson.optJSONObject(KEY_UPDATE);
                        if (updateJson!=null){
                            loc = updateJson.optString(KEY_LOC,KEY_LOC);
                            utc = updateJson.optString(KEY_UTC,KEY_UTC);
                        }
                    }
                    //Suggestion
                    JSONObject suggestionJson = jsonObject.optJSONObject(KEY_SUGGESTION);
                    if (suggestionJson!=null){
                        JSONObject comfJson = suggestionJson.optJSONObject(KEY_COMF);
                        JSONObject cwJson = suggestionJson.optJSONObject(KEY_CW);
                        JSONObject drsgJson = suggestionJson.optJSONObject(KEY_DRSG);
                        JSONObject fluJson = suggestionJson.optJSONObject(KEY_FLU);
                        JSONObject sportJson = suggestionJson.optJSONObject(KEY_SPORT);
                        JSONObject travJson = suggestionJson.optJSONObject(KEY_TRAV);
                        JSONObject uvJson = suggestionJson.optJSONObject(KEY_UV);
                        if (comfJson!=null){
                            brf_comf = comfJson.optString(KEY_BRF,KEY_BRF);
                            txt_comf = comfJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (cwJson!=null){
                            brf_cw = cwJson.optString(KEY_BRF,KEY_BRF);
                            txt_cw = cwJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (drsgJson!=null){
                            brf_drsg = drsgJson.optString(KEY_BRF,KEY_BRF);
                            txt_drsg = drsgJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (fluJson!=null){
                            brf_flu = fluJson.optString(KEY_BRF,KEY_BRF);
                            txt_flu = fluJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (sportJson!=null){
                            brf_sport = sportJson.optString(KEY_BRF,KEY_BRF);
                            txt_sport = sportJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (travJson!=null){
                            brf_trav = travJson.optString(KEY_BRF,KEY_BRF);
                            txt_trav = travJson.optString(KEY_TXT,KEY_TXT);
                        }
                        if (uvJson!=null){
                            brf_uv = uvJson.optString(KEY_BRF,KEY_BRF);
                            txt_uv = uvJson.optString(KEY_TXT,KEY_TXT);
                        }
                    }

                }else {
                    throw new JsonDecodeException(TYPE_DECODE_ERR,"状态关键字不成功",status);
                }
            }else {
                throw new JsonDecodeException(TYPE_MISS,"缺少状态",CODE_MISSING);
            }
        }
    }


}
