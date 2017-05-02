package creeper_san.weather.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseFragment;
import creeper_san.weather.Base.BasePartManager;
import creeper_san.weather.Event.SwipeRefreshLayoutEvent;
import creeper_san.weather.Part.AqiPartManagerSimple;
import creeper_san.weather.Part.CityPartManagerSimple;
import creeper_san.weather.Part.DailyPartManagerSimple;
import creeper_san.weather.Part.HeaderPartManagerSimple;
import creeper_san.weather.Part.OtherPartManagerSimple;
import creeper_san.weather.Part.SuggestionPartManagerSimple;
import creeper_san.weather.Part.WindPartManagerSimple;
import creeper_san.weather.R;
import creeper_san.weather.View.ListenableScrollView;
import creeper_san.weather.View.ListenableScrollView.OnScrollListener;

public class WeatherFragment extends BaseFragment implements OnScrollListener{
    @BindView(R.id.fragmentWeatherScrollView)ListenableScrollView scrollView;
    @BindView(R.id.fragmentWeatherLinearLayout)LinearLayout linearLayout;
    private boolean isTop = true;
    private int topY;
    private List<BasePartManager> partManagerList;


    @Override
    protected void initView(View rootView) {
        //ScrollView
        scrollView.setOnScrollListener(this);
        //初始化各个Manager
        clearPartManagerList();
        partManagerList.add(new HeaderPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new AqiPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new WindPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new SuggestionPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new CityPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new DailyPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        partManagerList.add(new OtherPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        //添加Parthg
        for (BasePartManager manager:partManagerList){
            linearLayout.addView(manager.getView());
        }
        //尝试打印ID
//        log("---------- ID ----------");
//        for (BasePartManager manager:partManagerList){
//            log("ID "+manager.getType());
//        }
    }

    private void clearPartManagerList(){
        if (partManagerList==null){
            partManagerList = new ArrayList<>();
        }else {
            partManagerList.clear();
        }
        linearLayout.removeAllViews();
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onScroll(int i, int t, int oldl, int oldt) {
        topY = t;
        if (topY<=0){
            if (!isTop){
                isTop = true;
                EventBus.getDefault().post(new SwipeRefreshLayoutEvent(true));
            }
        }else {
            if (isTop){
                isTop = false;
                EventBus.getDefault().post(new SwipeRefreshLayoutEvent(false));
            }
        }
    }

    public boolean isTop() {
        return isTop;
    }
}
