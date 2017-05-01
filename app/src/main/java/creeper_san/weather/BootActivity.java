package creeper_san.weather;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Exctption.JsonDecodeException;
import creeper_san.weather.Flag.LanguageCode;
import creeper_san.weather.Helper.HttpHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Interface.HttpStringCallback;
import creeper_san.weather.Item.ForecastItem;
import creeper_san.weather.Item.WeatherItem;
import okhttp3.Call;

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MainActivity.class,true);
    }



    @Override
    protected int getLayout() {return R.layout.activity_boot;}
}
