package creeper_san.weather.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import creeper_san.weather.Base.BaseJson;
import creeper_san.weather.Exctption.JsonDecodeException;

public class UpdateJson extends BaseJson {
    public final static int RESULT_SUCCESS = 1;
    public final static int RESULT_DECODE_ERR = 0;
    public final static int RESULT_FAIL = -1;
    private final static String KEY_VERSION = "version";
    private final static String KEY_VERSIONS = "versions";
    private final static String KEY_UPDATE_TIME = "update-time";
    private final static String KEY_VERSION_NAME = "version-name";
    private final static String KEY_DESCRIPTION = "description";

    private int result;
    private boolean isHistory = false;
    private List<UpdateSingleItem> updateSingleItemList;

    public UpdateJson(){
        result = RESULT_FAIL;
    }

    public UpdateJson(String jsonStr) throws JSONException, JsonDecodeException {
        this(new JSONObject(jsonStr));
    }
    public UpdateJson(JSONObject jsonObject){
        updateSingleItemList = new ArrayList<>();
        try {
            analyze(jsonObject);
            result = RESULT_SUCCESS;
        } catch (JsonDecodeException e) {
            result = RESULT_DECODE_ERR;
        }
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {
        if (jsonObject.has(KEY_VERSIONS)){
            JSONArray jsonArray = jsonObject.optJSONArray(KEY_VERSIONS);
            for (int i=0;i<jsonArray.length();i++){
                isHistory = true;
                JSONObject json = jsonArray.optJSONObject(i);
                if (json!=null){
                    UpdateSingleItem item = new UpdateSingleItem();
                    item.setVersion(jsonObject.optInt(KEY_VERSION,-1));
                    item.setUpdateTime(jsonObject.optString(KEY_UPDATE_TIME,"1970-01-01"));
                    item.setVersionName(jsonObject.optString(KEY_VERSION_NAME,""));
                    item.setDescription(jsonObject.optString(KEY_DESCRIPTION,""));
                    updateSingleItemList.add(item);
                }
            }
        }else {
            isHistory = false;
            UpdateSingleItem item = new UpdateSingleItem();
            item.setVersion(jsonObject.optInt(KEY_VERSION,-1));
            item.setUpdateTime(jsonObject.optString(KEY_UPDATE_TIME,"1970-01-01"));
            item.setVersionName(jsonObject.optString(KEY_VERSION_NAME,""));
            item.setDescription(jsonObject.optString(KEY_DESCRIPTION,""));
            updateSingleItemList.add(item);
        }
    }

    public int getVersion(int which){
        return updateSingleItemList.get(which).getVersion();
    }
    public String getVersionName(int which){
        return updateSingleItemList.get(which).getVersionName();
    }
    public String getUpdateTime(int which){
        return updateSingleItemList.get(which).getUpdateTime();
    }
    public String getDescription(int which){
        return updateSingleItemList.get(which).getDescription();
    }

    public int getResult() {
        return result;
    }

    public boolean isHistory() {
        return isHistory;
    }

    private class UpdateSingleItem{
        private int version;
        private String updateTime;
        private String versionName;
        private String description;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
