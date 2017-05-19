package creeper_san.weather.Json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import creeper_san.weather.Base.BaseJson;
import creeper_san.weather.Exctption.JsonDecodeException;

public class UpdateJson extends BaseJson {
    public final int RESULT_SUCCESS = 1;
    public final int RESULT_DECODE_ERR = 0;
    public final int RESULT_FAIL = -1;

    private int result;
    private List<UpdateSingleItem> updateSingleItemList;

    public UpdateJson(){
        result = RESULT_FAIL;
    }

    public UpdateJson(String jsonStr) throws JSONException, JsonDecodeException {
        this(new JSONObject(jsonStr));
    }
    public UpdateJson(JSONObject jsonObject){
        try {
            analyze(jsonObject);
        } catch (JsonDecodeException e) {
            result = RESULT_DECODE_ERR;
        }
    }

    @Override
    protected void analyze(JSONObject jsonObject) throws JsonDecodeException {

    }

    private class UpdateSingleItem{

    }
}
