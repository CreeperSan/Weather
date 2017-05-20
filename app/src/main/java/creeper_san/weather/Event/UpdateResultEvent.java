package creeper_san.weather.Event;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import creeper_san.weather.Exctption.JsonDecodeException;
import creeper_san.weather.Helper.UpdateHelper;
import creeper_san.weather.Json.UpdateJson;

public class UpdateResultEvent {
    private boolean isSuccess = false;
    private UpdateJson updateJson;

    public UpdateResultEvent() {
        isSuccess = false;
    }

    public UpdateResultEvent(String jsonStr){
        try {
            updateJson = new UpdateJson(jsonStr);
            isSuccess = true;
        } catch (JSONException | JsonDecodeException e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public UpdateJson getUpdateJson() {
        return updateJson;
    }
}
