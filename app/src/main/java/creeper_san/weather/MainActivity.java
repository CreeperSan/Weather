package creeper_san.weather;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.SwipeRefreshLayoutEvent;
import creeper_san.weather.Event.WeatherResultEvent;
import creeper_san.weather.Fragment.WeatherFragment;
import creeper_san.weather.Helper.OfflineCacheHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Helper.WeatherDatabaseHelper;
import creeper_san.weather.Item.CityItem;
import creeper_san.weather.Json.WeatherItem;

public class MainActivity extends BaseActivity implements ServiceConnection,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.mainToolbar)Toolbar toolbar;
    @BindView(R.id.mainNavigationBar)NavigationView navigationView;
    @BindView(R.id.mainViewPager)ViewPager viewPager;
    @BindView(R.id.mainDrawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.mainSwipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;

    private List<WeatherFragment> weatherFragmentList;
    private WeatherService weatherService;
    private WeatherFragmentStatePagerAdapter adapter;

    private int touchX;
    private int touchY;
    private boolean isDrafting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initService();
        initToolbar();
        initNavigation();
        initViewPager();
        initSwipeRefreshLayout();
        log(UrlHelper.generateWeatherUrl("Shenzhen"));
    }

    private void initService() {
        bindService(WeatherService.class,this);
        startService(WeatherService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    /**
     *      初始化
     */
    private void initData() {
        clearFragmentList();
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
    }
    private void initViewPager() {
        List<CityItem> cityItemList = WeatherDatabaseHelper.getCityList(this);
        for (CityItem cityItem:cityItemList){
            WeatherFragment fragment = new WeatherFragment();
            fragment.setCityName(cityItem.getCity());
            fragment.setID(cityItem.getId());
            weatherFragmentList.add(fragment);
        }
        if (cityItemList.size()>0){
            setTitle(weatherFragmentList.get(0).getCityName());
        }else {
            setTitle("添加一个城市...");
        }
        //初始画列表数据
        adapter = new WeatherFragmentStatePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    if (isDrafting || swipeRefreshLayout.isRefreshing()){
                        return false;
                    }
                    isDrafting = true;
                    swipeRefreshLayout.setEnabled(false);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    isDrafting = false;
                }
//                log("Viewpager点击事件 ");
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                refreshSwipeRefreshLayoutState();
                setTitle(weatherFragmentList.get(position).getCityName());
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        viewPager.setOffscreenPageLimit(cityItemList.size());
    }
    private void initSwipeRefreshLayout() {
        log("--------------");
        swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchX = (int) event.getX();
                        touchY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isDrafting){
//                            log("isdrafting");
                            return false;
                        }else {
                            int xDistance = (int) Math.abs(event.getX() - touchX);
                            int yDistance = (int) Math.abs(event.getY() - touchY);
                            if (xDistance>yDistance && xDistance>8){
                                swipeRefreshLayout.setEnabled(false);
                                isDrafting = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        isDrafting = false;
                        break;
                }
                return false;
            }
        });
    }
    private void initNavigation() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuMainNavigationCityManager:
                        startActivity(CityManageActivity.class);
                        break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                return true;
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe
    public void onSwipeRefreshLayoutEvent(SwipeRefreshLayoutEvent event){
        if (!swipeRefreshLayout.isRefreshing()){
            if (event.isEnable()){
                swipeRefreshLayout.setEnabled(true);
            }else {
                swipeRefreshLayout.setEnabled(false);
            }
        }
    }
    /**
     *      Event事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherResultEvent(WeatherResultEvent event){
        swipeRefreshLayout.setRefreshing(false);
        if (event.isSuccess()){
            if (event.getWeatherItem()==null){
                toast("网络数据解析失败");
            }else {
                WeatherItem item = event.getWeatherItem();
                //离线缓存到文件
                OfflineCacheHelper.saveCityOfflineData(MainActivity.this,item.getId(0));
                for (int i=0;i<item.size();i++){
                    for (WeatherFragment fragment:weatherFragmentList){
                        if (fragment.getID().equals(item.getId(i))){
                            fragment.setWeatherData(item,i);
                        }
                    }
                }
            }
        }else {
            toast("连接到服务器失败，请检查你的网络连接。");
        }
    }

    /**
     *      接口以及回调
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        weatherService = ((WeatherService.WeatherServiceBinder)binder).getService();
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {}
    @Override
    public void onRefresh() {
        if (weatherService==null){
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        if (weatherFragmentList.size()>0){
            weatherService.getWeather(weatherFragmentList.get(viewPager.getCurrentItem()).getID());
        }else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    /**
     *      View状态刷新
     */
    private void refreshSwipeRefreshLayoutState(){
        if (weatherFragmentList.get(viewPager.getCurrentItem()).isTop()){
            swipeRefreshLayout.setEnabled(true);
        }else {
            swipeRefreshLayout.setEnabled(false);
        }
    }
    /**
     *      数据刷新
     */
    private void clearFragmentList(){
        if (weatherFragmentList == null){
            weatherFragmentList = new ArrayList<>();
        }else {
            weatherFragmentList.clear();
        }
    }




    /**
     *      内部类
     */

    private class WeatherFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        WeatherFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return weatherFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return weatherFragmentList==null?0:weatherFragmentList.size();
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
