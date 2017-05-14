package creeper_san.weather.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import creeper_san.weather.Event.PartEditEvent;
import creeper_san.weather.Event.WeatherRequestEvent;
import creeper_san.weather.Event.WeatherResultEvent;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Helper.OfflineCacheHelper;
import creeper_san.weather.Item.PartItem;
import creeper_san.weather.Json.WeatherItem;
import creeper_san.weather.Part.AqiPartManagerSimple;
import creeper_san.weather.Part.BackgroundManagerSimple;
import creeper_san.weather.Part.CityPartManagerSimple;
import creeper_san.weather.Part.DailyPartManagerSimple;
import creeper_san.weather.Part.HeaderPartManagerSimple;
import creeper_san.weather.Part.OtherPartManagerSimple;
import creeper_san.weather.Part.SuggestionPartManagerSimple;
import creeper_san.weather.Part.WindPartManagerSimple;
import creeper_san.weather.R;

import static creeper_san.weather.Flag.PartCode.*;

public class WeatherFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.fragmentWeatherScrollView)ScrollView scrollView;
    @BindView(R.id.fragmentWeatherLinearLayout)LinearLayout linearLayout;
    @BindView(R.id.fragmentWeatherSwipeRefreshLayout)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fragmentWeatherBackgroundLayout)FrameLayout frameLayout;
    private boolean isNeedRefresh = false;
    private List<BasePartManager> partManagerList;
    private String cityName;
    private String ID;

    @Override
    protected void initView(View rootView) {
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        //初始化各个Manager
        clearPartManagerList();
        //此处装载背景
        frameLayout.addView(new BackgroundManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView).getView());
        //此处装载部分
        List<PartItem> partItemList = DatabaseHelper.getPartItemList(getContext());
        for (PartItem partItem:partItemList){
            BasePartManager basePartManager = getPartManagerFromTypeCode(partItem.getType(),getActivity().getLayoutInflater(), (ViewGroup) rootView);
            if (basePartManager!=null){
                partManagerList.add(basePartManager);
            }
        }
//        partManagerList.add(new HeaderPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new AqiPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new WindPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new SuggestionPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new CityPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new DailyPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
//        partManagerList.add(new OtherPartManagerSimple(getActivity().getLayoutInflater(), (ViewGroup) rootView));
        //添加Parth
        for (BasePartManager manager:partManagerList){
            linearLayout.addView(manager.getView());
        }
        //装载离线数据
        WeatherItem offlineItem = OfflineCacheHelper.getWeatherItemFromCache(getContext(),getID());
        if (offlineItem!=null){
            for (int i=0;i<offlineItem.size();i++){
                if (offlineItem.getId(i).equals(getID())){
                    setWeatherData(offlineItem,i);
                }
            }
        }
    }

    private BasePartManager getPartManagerFromTypeCode(int type,LayoutInflater inflater,ViewGroup viewGroup){
        switch (type){
            case PART_HEAD:
                return new HeaderPartManagerSimple(inflater,viewGroup);
            case PART_AQI:
                return new AqiPartManagerSimple(inflater,viewGroup);
            case PART_DAILY:
                return new DailyPartManagerSimple(inflater,viewGroup);
            case PART_WIND:
                return new WindPartManagerSimple(inflater,viewGroup);
            case PART_SUGGESTION:
                return new SuggestionPartManagerSimple(inflater,viewGroup);
            case PART_CITY:
                return new CityPartManagerSimple(inflater,viewGroup);
            case PART_OTHER:
                return new OtherPartManagerSimple(inflater,viewGroup);
            default:
                return null;
        }
    }

    @Override
    protected void onCreateViewFinish() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNeedRefresh){
            isNeedRefresh = false;
            //清空数据
            clearPartManagerList();
            //重新加载数据
            List<PartItem> partItemList = DatabaseHelper.getPartItemList(getContext());
            for (PartItem partItem:partItemList){
                BasePartManager basePartManager = getPartManagerFromTypeCode(partItem.getType(),getActivity().getLayoutInflater(), (ViewGroup) rootView);
                if (basePartManager!=null){
                    partManagerList.add(basePartManager);
                }
            }
            for (BasePartManager manager:partManagerList){
                linearLayout.addView(manager.getView());
            }
            WeatherItem offlineItem = OfflineCacheHelper.getWeatherItemFromCache(getContext(),getID());
            if (offlineItem!=null){
                for (int i=0;i<offlineItem.size();i++){
                    if (offlineItem.getId(i).equals(getID())){
                        setWeatherData(offlineItem,i);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setWeatherData(WeatherItem item, int which){
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
        if (item.getAqi(which)==null &&
                item.getCo(which)==null &&
                item.getNo2(which)==null &&
                item.getO3(which)==null &&
                item.getPm10(which)==null &&
                item.getPm25(which)==null &&
                item.getQlty(which)==null &&
                item.getSo2(which)==null){
            aqiPartManager.setEmpty();
            return;
        }
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
        if (item.getBrfAir(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfComf(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfCw(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfDrsg(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfFlu(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfSport(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfTrav(which)== null || item.getBrfAir(which).equals("") &&
                item.getBrfUv(which)== null || item.getBrfAir(which).equals("")){
            suggestionPartManager.setEmpty();
            return;
        }
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
            loge("List初始化");
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
    public void onRefresh() {
        postEvent(new WeatherRequestEvent(ID,cityName));
    }

    /**
     *      EventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherResultEvent(WeatherResultEvent event){
        swipeRefreshLayout.setRefreshing(false);
        if (event.isSuccess()){
            if (event.getWeatherItem()==null){
                loge("网络数据解析失败");
            }else {
                WeatherItem item = event.getWeatherItem();
                for (int i=0;i<item.size();i++){
                    if (item.getId(i).equals(ID)){
                        OfflineCacheHelper.saveCityOfflineData(getContext(),ID,item.getJsonObject().toString());
                        setWeatherData(item,i);
                    }
                }
            }
        }else {
            toast("连接到服务器失败，请检查你的网络连接。");
            loge("连接到服务器失败，请检查你的网络连接。");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTypeEditEvent(PartEditEvent event){
        isNeedRefresh = true;
    }
}
