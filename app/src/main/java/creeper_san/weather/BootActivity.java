package creeper_san.weather;

import android.support.annotation.Nullable;
import android.os.Bundle;

import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Helper.UrlHelper;

public class BootActivity extends BaseActivity {


    @Override
    protected int getLayout() {return R.layout.activity_boot;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log(UrlHelper.generateForecastUrl("shenzhen", UrlHelper.Language.CHINESE));
    }

}
