package creeper_san.weather;

import android.support.annotation.Nullable;
import android.os.Bundle;

import creeper_san.weather.Base.BaseActivity;

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MainActivity.class,true);
    }



    @Override
    protected int getLayout() {return R.layout.activity_boot;}
}
