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
import creeper_san.weather.Base.BaseAqiPartManager;
import creeper_san.weather.Base.BaseCityPartManager;
import creeper_san.weather.Base.BaseDailyPartManager;
import creeper_san.weather.Base.BaseFragment;
import creeper_san.weather.Base.BaseHeaderPartManager;
import creeper_san.weather.Base.BaseOtherPartManager;
import creeper_san.weather.Base.BasePartManager;
import creeper_san.weather.Base.BaseSuggestionPartManager;
import creeper_san.weather.Base.BaseWindPartManager;
import creeper_san.weather.Event.SwipeRefreshLayoutEvent;
import creeper_san.weather.Json.WeatherItem;
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
import static creeper_san.weather.Flag.PartCode.*;

public class WeatherFragment extends BaseFragment implements OnScrollListener{
    @BindView(R.id.fragmentWeatherScrollView)ListenableScrollView scrollView;
    @BindView(R.id.fragmentWeatherLinearLayout)LinearLayout linearLayout;
    private boolean isTop = true;
    private int topY;
    private List<BasePartManager> partManagerList;
    private String cityName;
    private String ID;

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
    }

    public void setWeatherData(WeatherItem item,int which){
        //首先是头部信息
          for (BasePartManager basePartManager:partManagerList){
              switch (basePartManager.getType()){
                  case PART_HEAD:
                      handleHeadPart(item, (BaseHeaderPartManager) basePartManager,which);
                      break;
                  case PART_AQI:
                      handleAqiPart(item, (BaseAqiPartManager) basePartManager,which);
                      break;
                  case PART_DAILY:
                      handleDailyPart(item, (BaseDailyPartManager) basePartManager,which);
                      break;
                  case PART_WIND:
                      handleWindPart(item, (BaseWindPartManager) basePartManager,which);
                      break;
                  case PART_SUGGESTION:
                      handleSuggestionPart(item, (BaseSuggestionPartManager) basePartManager,which);
                      break;
                  case PART_CITY:
                      handleCityPart(item, (BaseCityPartManager) basePartManager,which);
                      break;
                  case PART_OTHER:
                      handleOtherPart(item, (BaseOtherPartManager) basePartManager,which);
                      break;
              }
          }
    }
    private void handleHeadPart(WeatherItem item, BaseHeaderPartManager headerPartManager,int which){
        headerPartManager.setTmp(item.getTmp(which));
        headerPartManager.setLoc(item.getLoc(which));
        headerPartManager.setCondCode(item.getCode(which));
        headerPartManager.setCondTxt(item.getCode(which));
        headerPartManager.setFl(item.getFl(which));
    }
    private void handleAqiPart(WeatherItem item, BaseAqiPartManager aqiPartManager, int which){
        aqiPartManager.setAqi(item.getAqi(which));
        aqiPartManager.setCo(item.getCo(which));
        aqiPartManager.setNo2(item.getNo2(which));
        aqiPartManager.setO3(item.getO3(which));
        aqiPartManager.setPm10(item.getPm10(which));
        aqiPartManager.setPm25(item.getPm25(which));
        aqiPartManager.setQlty(item.getQlty(which));
        aqiPartManager.setSo2(item.getSo2(which));
    }
    private void handleDailyPart(WeatherItem item, BaseDailyPartManager dailyPartManager, int which){
        dailyPartManager.setData(item,which);
    }
    private void handleWindPart(WeatherItem item, BaseWindPartManager windPartManager, int which){
        windPartManager.setDeg(item.getDeg(which));
        windPartManager.setDir(item.getDir(which));
        windPartManager.setSc(item.getSc(which));
        windPartManager.setSpd(item.getSpd(which));
    }
    private void handleSuggestionPart(WeatherItem item, BaseSuggestionPartManager suggestionPartManager, int which){
        suggestionPartManager.setAirBrf(item.getBrfAir(which));
        suggestionPartManager.setAirTxt(item.getTxtAir(which));
        suggestionPartManager.setComfBrf(item.getBrfComf(which));
        suggestionPartManager.setComfTxt(item.getTxtComf(which));
        suggestionPartManager.setCwBrf(item.getBrfCw(which));
        suggestionPartManager.setCwTxt(item.getTxtCw(which));
        suggestionPartManager.setDrsgBrf(item.getBrfDrsg(which));
        suggestionPartManager.setDrsgTxt(item.getTxtDrsg(which));
        suggestionPartManager.setFluBrf(item.getBrfFlu(which));
        suggestionPartManager.setFluTxt(item.getTxtFlu(which));
        suggestionPartManager.setSportBrf(item.getBrfSport(which));
        suggestionPartManager.setSportTxt(item.getTxtSport(which));
        suggestionPartManager.setTravBrf(item.getBrfTrav(which));
        suggestionPartManager.setTravTxt(item.getTxtTrav(which));
        suggestionPartManager.setUvBrf(item.getBrfUv(which));
        suggestionPartManager.setUvTxt(item.getTxtUv(which));
    }
    private void handleCityPart(WeatherItem item, BaseCityPartManager cityPartManager, int which){
        cityPartManager.setCity(item.getCity(which));
        cityPartManager.setCnty(item.getCountry(which));
        cityPartManager.setID(item.getId(which));
        cityPartManager.setLat(item.getLat(which));
        cityPartManager.setLon(item.getLon(which));
    }
    private void handleOtherPart(WeatherItem item, BaseOtherPartManager otherPartManager, int which){
        otherPartManager.setHum(item.getHum(which));
        otherPartManager.setPcpn(item.getPcpn(which));
        otherPartManager.setPres(item.getPres(which));
        otherPartManager.setVis(item.getVis(which));
    }


    private void clearPartManagerList(){
        if (partManagerList==null){
            partManagerList = new ArrayList<>();
        }else {
            partManagerList.clear();
        }
        linearLayout.removeAllViews();
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
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
