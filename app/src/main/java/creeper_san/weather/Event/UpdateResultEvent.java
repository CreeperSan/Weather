package creeper_san.weather.Event;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import creeper_san.weather.Helper.UpdateHelper;

public class UpdateResultEvent {
    public final int RESULT_SUCCESS = 1;
    public final int RESULT_DECODE_ERR = 0;
    public final int RESULT_FAIL = -1;
    private final static String KEY_UPDATE_TIME = "update-time";
    private final static String KEY_VERSION = "version";
    private final static String KEY_VERSION_NAME = "version-name";
    private final static String KEY_DESCRIPTION = "description";

    private int resultType;
    private int versionCode;
    private String updateTime;
    private String versionName;
    private String description;

    public UpdateResultEvent() {
        resultType = RESULT_FAIL;
    }

    public UpdateResultEvent(String jsonStr){
        try {
            decode(new JSONObject(jsonStr));
        } catch (JSONException e) {
            resultType = RESULT_DECODE_ERR;
        }
    }

    private void decode(JSONObject jsonObject) throws JSONException{
        try {
            versionCode = jsonObject.getInt(KEY_VERSION);
            versionName = jsonObject.getString(KEY_VERSION_NAME);
            description = jsonObject.getString(KEY_DESCRIPTION);
            updateTime = jsonObject.getString(KEY_UPDATE_TIME);
            resultType = RESULT_SUCCESS;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UpdateResultEvent(int resultType, int versionCode, String versionName,String updateTime, String description) {
        this.resultType = resultType;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
        this.updateTime = updateTime;
    }

    public int getResultType() {
        return resultType;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getDescription() {
        return description;
    }
}
