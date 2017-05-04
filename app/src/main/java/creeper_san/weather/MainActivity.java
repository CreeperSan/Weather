package creeper_san.weather;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.CityEditEvent;
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
    @BindView(R.id.mainDrawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.mainSwipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;

    private int ppp=100;

    private WeatherFragment fragment;
    private WeatherService weatherService;

    private int touchX;
    private int touchY;
    private boolean isDrafting = false;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        initService();
        initToolbar();
        initNavigation();
        initSwipeRefreshLayout();
        log(UrlHelper.generateWeatherUrl("Shenzhen"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
        log("onDestroy();");
    }

    /**
     *      初始化
     */
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
    private void initSwipeRefreshLayout() {
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
    private void initService() {
        bindService(WeatherService.class,this);
        startService(WeatherService.class);
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
                swipeRefreshLayout.setRefreshing(true);
                refreshWeather();
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
    public void onWeatherResultEvent(WeatherResultEvent event){
//        log("收到天气结果！   "+Thread.currentThread().getName());
        swipeRefreshLayout.setRefreshing(false);
        if (event.isSuccess()){
            if (event.getWeatherItem()==null){
                toast("网络数据解析失败");
            }else {

            }
        }else {
            toast("连接到服务器失败，请检查你的网络连接。");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwipeRefreshLayoutEvent(SwipeRefreshLayoutEvent event){
//        log("收到结果 swipeEvent");
        if (!swipeRefreshLayout.isRefreshing()){
            if (event.isEnable()){
                swipeRefreshLayout.setEnabled(true);
            }else {
                swipeRefreshLayout.setEnabled(false);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityEditEvent(CityEditEvent event){
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
        refreshWeather();
    }
    private void refreshWeather(){

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
