package creeper_san.weather.Interface;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public abstract class HttpStringCallback implements HttpCallback {



    public abstract void onFail(Call call, IOException e, int requestCode);

    public abstract void onResult(Call call,String result,int requestCode);

    @Override
    public void onResult(Call call, Response response, int requestCode) {
        try {
            onResult(call, response.body().string(), requestCode);
        } catch (IOException e) {
            onResult(call, "", requestCode);
        }
    }
}
