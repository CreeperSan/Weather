package creeper_san.weather;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import creeper_san.weather.Application.WeatherApplication;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Helper.HttpHelper;
import creeper_san.weather.Interface.HttpCallback;
import creeper_san.weather.Interface.HttpStringCallback;
import okhttp3.Call;
import okhttp3.Response;

public class BootActivity extends BaseActivity {


    @Override
    protected int getLayout() {return R.layout.activity_boot;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
