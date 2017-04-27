package creeper_san.weather.Interface;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface HttpCallback {
    void onFail(Call call, IOException e,int requestCode);
    void onResult(Call call, Response response,int requestCode);
}
