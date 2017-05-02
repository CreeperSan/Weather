package creeper_san.weather;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.SwipeRefreshLayoutEvent;
import creeper_san.weather.Fragment.WeatherFragment;
import creeper_san.weather.Helper.UrlHelper;

public class MainActivity extends BaseActivity{
    @BindView(R.id.mainToolbar)Toolbar toolbar;
    @BindView(R.id.mainNavigationBar)NavigationView navigationView;
    @BindView(R.id.mainViewPager)ViewPager viewPager;
    @BindView(R.id.mainDrawerLayout)DrawerLayout drawerLayout;
    @BindView(R.id.mainSwipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;

    private List<WeatherFragment> weatherFragmentList;

    private int touchX;
    private int touchY;
    private boolean isDrafting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initToolbar();
        initViewPager();
        initSwipeRefreshLayout();
        log(UrlHelper.generateWeatherUrl("Shenzhen"));
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
        weatherFragmentList.add(new WeatherFragment());
        weatherFragmentList.add(new WeatherFragment());
        weatherFragmentList.add(new WeatherFragment());
        viewPager.setAdapter(new TestFragmentStatePagerAdapter(getSupportFragmentManager()));
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
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    private void initSwipeRefreshLayout() {
        log("--------------");
        swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
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
                            log("isdrafting");
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
     *      接口以及回调
     */

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

    private class TestFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

        TestFragmentStatePagerAdapter(FragmentManager fm) {
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

    @Override
    protected int getLayout() {return R.layout.activity_main;}
}
