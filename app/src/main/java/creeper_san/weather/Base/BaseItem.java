package creeper_san.weather.Base;


import org.json.JSONException;
import org.json.JSONObject;

import creeper_san.weather.Exctption.JsonDecodeException;

public abstract class BaseItem {

    public BaseItem(String jsonStr) throws JSONException, JsonDecodeException {
        this(new JSONObject(jsonStr));
    }
    public BaseItem(JSONObject jsonObject) throws JsonDecodeException{
        analyze(jsonObject);
    }

    protected abstract void analyze(JSONObject jsonObject) throws JsonDecodeException;
}
