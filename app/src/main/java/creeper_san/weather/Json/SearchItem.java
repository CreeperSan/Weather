package creeper_san.weather.Json;

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

/**
 *      城市搜索返回的结果
 */

public class SearchItem extends BaseItem{

    private List<SearchSingleItem> searchSingleItemList;

    public SearchItem(String jsonStr) throws JSONException, JsonDecodeException {
        super(jsonStr);
    }
    public SearchItem(JSONObject jsonObject) throws JsonDecodeException {
        super(jsonObject);
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
        if (searchSingleItemList==null){
            searchSingleItemList = new ArrayList<>();
        }else {
            searchSingleItemList.clear();
        }
//        log("分析开始");
        if (jsonObject.has(KEY_HE_WEATHER5)){
            JSONArray array = jsonObject.optJSONArray(KEY_HE_WEATHER5);
            for (int i=0;i<array.length();i++){
                try {
                    SearchSingleItem singleItem = new SearchSingleItem((JSONObject) array.get(i));
                    if (singleItem==null){
                        continue;
                    }
                    searchSingleItemList.add(singleItem);
                } catch (JSONException e) {
                    throw new JsonDecodeException(TYPE_MISS,"缺少城市节点信息",CODE_MISSING);
                }
            }
        }else {
            throw new JsonDecodeException(TYPE_MISS,"HeWeather5",CODE_MISSING);
        }

    }

    //获取数据的大小
    public int size(){
        return searchSingleItemList==null?0:searchSingleItemList.size();
    }
    //获取数据
    public String getCity(int which){
        return searchSingleItemList.get(which).getCity();
    }
    public String getCountry(int which){
        return searchSingleItemList.get(which).getCnty();
    }
    public String getID(int which){
        return searchSingleItemList.get(which).getId();
    }
    public String getLat(int which){
        return searchSingleItemList.get(which).getLat();
    }
    public String getLon(int which){
        return searchSingleItemList.get(which).getLon();
    }
    public String getProvince(int which){
        return searchSingleItemList.get(which).getProv();
    }

    /**
     *      单个数据
     */

    private class SearchSingleItem extends BaseItem{
        private String city;//城市
        private String cnty;//所在国家
        private String id;//城市ID
        private String lat;//纬度(横)
        private String lon;//经度（竖）
        private String prov;//省份

        private SearchSingleItem(String jsonStr) throws JSONException, JsonDecodeException {
            super(jsonStr);
        }
        private SearchSingleItem(JSONObject jsonObject) throws JsonDecodeException {
            super(jsonObject);
        }

        @Override
        protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
//            log(jsonObject.toString()+"\n\n");
//            log("");
//            log("城市数据");
            switch (jsonObject.optString(KEY_STATUS, CODE_MISSING)){
                case CODE_OK://开始解析
                    if (jsonObject.has(KEY_BASIC)){
                        try {
                            JSONObject basicJson = jsonObject.getJSONObject(KEY_BASIC);
                            city = basicJson.optString(KEY_CITY);
                            cnty = basicJson.optString(KEY_CNTY);
                            id = basicJson.optString(KEY_ID);
                            lat = basicJson.optString(KEY_LAT);
                            lon = basicJson.optString(KEY_LON);
                            prov = basicJson.optString(KEY_PROV);
//                            log("城市名称 "+city);
//                            log("国家 "+cnty);
//                            log("ID "+id);
//                            log("纬度 "+lat);
//                            log("精度 "+lon);
//                            log("省份 "+prov);
                        } catch (JSONException e) {
                            throw new JsonDecodeException(TYPE_DECODE_ERR,"单个城市的数据basic解析出错",CODE_DECODE_ERR);
                        }
                    }else {
                        throw new JsonDecodeException(TYPE_MISS,"缺少单个城市的数据basic",CODE_MISSING);
                    }
                    break;
                default:
                    throw new JsonDecodeException(TYPE_CUSTOM ,"status状态字异常",jsonObject.optString(KEY_STATUS, CODE_MISSING));
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
        private String getProv() {
            return prov;
        }
    }

}
