package creeper_san.weather;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Event.ApplicationExitEvent;
import creeper_san.weather.Event.BackgroundChangeEvent;
import creeper_san.weather.Event.CityEditEvent;
import creeper_san.weather.Event.PartStylePrefChangeEvent;
import creeper_san.weather.Event.ThemePrefEvent;
import creeper_san.weather.Event.UpdateRequestEvent;
import creeper_san.weather.Event.UpdateResultEvent;
import creeper_san.weather.Event.WeatherRequestEvent;
import creeper_san.weather.Event.WeatherResultEvent;
import creeper_san.weather.Fragment.WeatherFragment;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Item.CityItem;
import creeper_san.weather.Json.UpdateJson;
import creeper_san.weather.Part.BackgroundManagerBing;
import creeper_san.weather.Part.BackgroundManagerLocal;
import creeper_san.weather.Part.BackgroundManagerSimple;

public class MainActivity extends BaseActivity implements ServiceConnection{
    @BindView(R.id.mainToolbar)Toolbar toolbar;
    @BindView(R.id.mainNavigationBar)NavigationView navigationView;
    @BindView(R.id.mainDrawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.mainViewPager)ViewPager viewPager;
    @BindView(R.id.mainBackgroundLayout)FrameLayout backgroundLayout;

    private BaseBackgroundPartManager backgroundPartManager;
    private List<WeatherFragment> weatherFragmentList;
    private WeatherAdapter adapter;
    private boolean isOrderChange = false;
    private boolean isShowing = false;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        initService();
        initToolbar();
        initNavigation();
        initFragmentList();
        initViewPager();
        initBackground();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isOrderChange){
            isOrderChange =false;
            initFragmentList();
            adapter.notifyDataSetChanged();
        }
        if (weatherFragmentList.size()>0){
            setTitle(weatherFragmentList.get(viewPager.getCurrentItem()).getCityName());
        }else {
            setTitle("请先选择城市..");
        }
        isShowing = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowing = false;
    }

    /**
     *      初始化
     */
    private void initFragmentList(){
        weatherFragmentList = new ArrayList<>();
        List<CityItem> cityItemList = DatabaseHelper.getCityList(this);
        for (CityItem cityItem:cityItemList){
            WeatherFragment fragment = new WeatherFragment();
            fragment.setID(cityItem.getId());
            fragment.setCityName(cityItem.getCity());
            weatherFragmentList.add(fragment);
        }
    }
    private void initViewPager() {
        adapter = new WeatherAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                setTitle(weatherFragmentList.get(viewPager.getCurrentItem()).getCityName());
                weatherFragmentList.get(viewPager.getCurrentItem()).checkIsNeedUpdate();
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    private void initToolbar() {
        setSupportActionBar(toolbar);
        setTitle("Title");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
            toggle.syncState();
            drawerLayout.addDrawerListener(toggle);
        }
        initToolbarTheme(toolbar);
    }
    private void initNavigation() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuMainNavigationCityManager:
                        startActivity(CityManageActivity.class);
                        break;
                    case R.id.menuMainNavigationPartManager:
                        startActivity(PartManageActivity.class);
                        break;
                    case R.id.menuMainNavigationSetting:
                        startActivity(SettingActivity.class);
                        break;
                    case R.id.menuMainNavigationAbout:
                        startActivity(AboutActivity.class);
                        break;
                    case R.id.menuMainNavigationExit:
                        postEvent(new ApplicationExitEvent());
                        break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        });
    }
    private void initService() {
        bindService(WeatherService.class,this);
        startService(WeatherService.class);
    }
    private void initBackground() {
        switch (ConfigHelper.settingGetBgTheme(this,"0")){
            case "0":
                backgroundPartManager = new BackgroundManagerSimple(getLayoutInflater(),backgroundLayout);
                break;
            case "1":
                backgroundPartManager = new BackgroundManagerLocal(getLayoutInflater(),backgroundLayout);
                break;
            case "2":
                backgroundPartManager = new BackgroundManagerBing(getLayoutInflater(),backgroundLayout);
                break;
            default:
                backgroundPartManager = new BackgroundManagerSimple(getLayoutInflater(),backgroundLayout);
                break;
        }
        backgroundPartManager.initViewData(null,0);
        backgroundLayout.addView(backgroundPartManager.getView());
    }



    /**
     *      响应事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.closeDrawer(Gravity.START);
                }else {
                    drawerLayout.openDrawer(Gravity.START);
                }
                break;
            case R.id.menuMainToolbarAddCity:
                startActivity(AddCityActivity.class);
                break;
            case R.id.menuMainToolbarRefreshWeather:
                WeatherFragment fragment = weatherFragmentList.get(viewPager.getCurrentItem());
                fragment.setRefreshState(true);
                postEvent(new WeatherRequestEvent(fragment.getID(),fragment.getCityName()));
                break;
            case R.id.menuMainToolbarTest:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     *      Event事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityEditEvent(CityEditEvent event){
        if (isShowing){
            isOrderChange =false;
            initFragmentList();
            adapter.notifyDataSetChanged();
        }else {
            isOrderChange = true;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackgroundChangeEvent(BackgroundChangeEvent event){
        String key = event.getKey();
        if (key.equals(getString(R.string.prefBackgroundBingImageSize))){
            if (backgroundPartManager instanceof BackgroundManagerBing){
                ((BackgroundManagerBing)backgroundPartManager).checkIsNeedUpdateBingImage(context);
            }
        }else if (key.equals(getString(R.string.prefBackgroundColor))){
            if (backgroundPartManager instanceof BackgroundManagerSimple){//纯色背景颜色更新
                ((BackgroundManagerSimple)backgroundPartManager).setColor(Color.parseColor("#"+event.getCurrent()));
            }
        }else if (key.equals(getString(R.string.prefBackgroundBingImageSize))){//本地图片更新
            if (backgroundPartManager instanceof  BackgroundManagerLocal){
                ((BackgroundManagerLocal)backgroundPartManager).setImage();
            }
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPartStylePrefChangeEvent(PartStylePrefChangeEvent event){
        String keyStr = event.getKey();
        if(keyStr.equals(context.getString(R.string.prefBackgroundTheme))){
            backgroundLayout.removeAllViews();
            initBackground();
        }else if (keyStr.equals(context.getString(R.string.prefBackgroundBingImageSize))){
            if (backgroundPartManager instanceof BackgroundManagerBing){
                ((BackgroundManagerBing)backgroundPartManager).initViewData(null,0);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherResultEvent(WeatherResultEvent event){
        if (!event.isSuccess()){
            toast("连接到服务器失败，请检查你的网络连接。");
        }
    }

    /**
     *      接口以及回调
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {}

    /**
     *      内部类
     */

    class WeatherAdapter extends FragmentStatePagerAdapter{

        public WeatherAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return weatherFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return weatherFragmentList.size();
        }
    }

    /**
     *      设置方法
     */
    @Override
    protected int getLayout() {return R.layout.activity_main;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        return true;
    }
}
