package creeper_san.weather;

import android.support.annotation.Nullable;
import android.os.Bundle;

import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Item.CityItem;
import creeper_san.weather.Item.PartItem;

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(WeatherService.class);
        if (ConfigHelper.infoIsFirstBoot(this,true)){
            initFirstBoot();
            startActivity(MainActivity.class,true);
            startActivity(GuideActivity.class);
            ConfigHelper.infoSetFirstBoot(this,false);
        }else {
            startActivity(MainActivity.class,true);
        }
    }

    private void initFirstBoot() {
        DatabaseHelper.insertCityItem(this,new CityItem("深圳","中国","CN101280601","22.54700089","114.08594513","广东"));
        DatabaseHelper.insertCityItem(this,new CityItem("北京","中国","CN101010100","39.90498734","116.40528870","北京"));
    }


    @Override
    protected int getLayout() {return R.layout.activity_boot;}
}
