package creeper_san.weather;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Fragment.WeatherFragment;
import creeper_san.weather.Helper.UrlHelper;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mainToolbar)Toolbar toolbar;
    @BindView(R.id.mainNavigationBar)NavigationView navigationView;
    @BindView(R.id.mainViewPager)ViewPager viewPager;

    private WeatherFragment weatherFragment;
    private WeatherFragment weatherFragment1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initViewPager();
        log(UrlHelper.generateWeatherUrl("Shenzhen"));
    }



    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("Title");
    }

    private void initViewPager() {
        weatherFragment = new WeatherFragment();
        weatherFragment1 = new WeatherFragment();
        viewPager.setAdapter(new TestFragmentStatePagerAdapter(getSupportFragmentManager()));
    }


    class TestFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        public TestFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position==1?weatherFragment:weatherFragment1;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected int getLayout() {return R.layout.activity_main;}
}
