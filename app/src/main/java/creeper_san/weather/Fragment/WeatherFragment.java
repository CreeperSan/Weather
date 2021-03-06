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

import org.greenrobot.eventbus.EventBus;
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
import creeper_san.weather.Event.PartStylePrefChangeEvent;
import creeper_san.weather.Event.TemperatureUnitChangeEvent;
import creeper_san.weather.Event.ThemeAlphaChangeEvent;
import creeper_san.weather.Event.WeatherRequestEvent;
import creeper_san.weather.Event.WeatherResultEvent;
import creeper_san.weather.Event.WidgetEvent;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Helper.OfflineCacheHelper;
import creeper_san.weather.Item.PartItem;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.Part.AqiPartManagerCard;
import creeper_san.weather.Part.AqiPartManagerSimple;
import creeper_san.weather.Part.CityPartManagerCard;
import creeper_san.weather.Part.CityPartManagerSimple;
import creeper_san.weather.Part.DailyPartManagerCard;
import creeper_san.weather.Part.DailyPartManagerSimple;
import creeper_san.weather.Part.HeaderPartManagerCard;
import creeper_san.weather.Part.HeaderPartManagerSimple;
import creeper_san.weather.Part.OtherPartManagerCard;
import creeper_san.weather.Part.OtherPartManagerSimple;
import creeper_san.weather.Part.SuggestionPartManagerCard;
import creeper_san.weather.Part.SuggestionPartManagerSimple;
import creeper_san.weather.Part.WindPartManagerCard;
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
    private WeatherJson weatherJson;
    private int which;

    @Override
    protected void initView(View rootView) {
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        //初始化各个Manager
        clearPartManagerList();
        //此处装载部分
        List<PartItem> partItemList = DatabaseHelper.getPartItemList(getContext());
        for (PartItem partItem:partItemList){
            BasePartManager basePartManager = getPartManagerFromTypeCode(partItem.getType(),getActivity().getLayoutInflater(), (ViewGroup) rootView);
            if (basePartManager!=null){
                partManagerList.add(basePartManager);
            }
        }
        //添加Parth
        for (BasePartManager manager:partManagerList){
            linearLayout.addView(manager.getView());
        }
        //装载离线数据
        WeatherJson offlineItem = OfflineCacheHelper.getWeatherItemFromCache(getContext(),getID());
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
                switch(ConfigHelper.settingGetHeaderTheme(getContext(),"0")){
                    case "0":return new HeaderPartManagerSimple(inflater,viewGroup);
                    case "1":return new HeaderPartManagerCard(inflater,viewGroup);
                    default:return new HeaderPartManagerSimple(inflater,viewGroup);
                }
            case PART_AQI:
                switch(ConfigHelper.settingGetAQITheme(getContext(),"0")){
                    case "0":return new AqiPartManagerSimple(inflater,viewGroup);
                    case "1":return new AqiPartManagerCard(inflater,viewGroup);
                    default:return new AqiPartManagerSimple(inflater,viewGroup);
                }
            case PART_DAILY:
                switch (ConfigHelper.settingGetDailyTheme(getContext(),"0")){
                    case "0":return new DailyPartManagerSimple(inflater,viewGroup);
                    case "1":return new DailyPartManagerCard(inflater,viewGroup);
                    default:return new DailyPartManagerSimple(inflater,viewGroup);
                }
            case PART_WIND:
                switch (ConfigHelper.settingGetWindTheme(getContext(),"0")){
                    case "0":return new WindPartManagerSimple(inflater,viewGroup);
                    case "1":return new WindPartManagerCard(inflater,viewGroup);
                    default:return new WindPartManagerSimple(inflater,viewGroup);
                }
            case PART_SUGGESTION:
                switch (ConfigHelper.settingGetSuggestionTheme(getContext(),"0")){
                    case "0":return new SuggestionPartManagerSimple(inflater,viewGroup);
                    case "1":return new SuggestionPartManagerCard(inflater,viewGroup);
                    default:return new SuggestionPartManagerSimple(inflater,viewGroup);
                }
            case PART_CITY:
                switch (ConfigHelper.settingGetCityTheme(getContext(),"0")){
                    case "0":return new CityPartManagerSimple(inflater,viewGroup);
                    case "1":return new CityPartManagerCard(inflater,viewGroup);
                    default:return new CityPartManagerSimple(inflater,viewGroup);
                }
            case PART_OTHER:
                switch (ConfigHelper.settingGetOtherTheme(getContext(),"0")){
                    case "0":return new OtherPartManagerSimple(inflater,viewGroup);
                    case "1":return new OtherPartManagerCard(inflater,viewGroup);
                    default:return new OtherPartManagerSimple(inflater,viewGroup);
                }
            default:
                return null;
        }
    }

    @Override
    protected void onCreateViewFinish() {}

    @Override
    public void onResume() {
        super.onResume();
        checkIsNeedUpdate();
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
            WeatherJson offlineItem = OfflineCacheHelper.getWeatherItemFromCache(getContext(),getID());
            if (offlineItem!=null){
                for (int i=0;i<offlineItem.size();i++){
                    if (offlineItem.getId(i).equals(getID())){
                        setWeatherData(offlineItem,i);
                    }
                }
            }
        }
    }

    public void checkIsNeedUpdate(){
        //检查是否需要更新
        long currentTime = System.currentTimeMillis();
        if (currentTime - ConfigHelper.getCityWeatherUpdateTime(getContext(),getID(),0)>1000*60*60){
//            setRefreshState(true);
            postStickyEvent(new WeatherRequestEvent(getID(),getCityName()));
        }
    }

    public void setRefreshState(boolean isRefreshing){
        swipeRefreshLayout.setRefreshing(isRefreshing);
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

    public void setWeatherData(WeatherJson item, int which){
        this.weatherJson = item;
        this.which = which;
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
    private void handleHeadPart(WeatherJson item, BaseHeaderPartManager headerPartManager, int which){
        headerPartManager.initViewData(item,which);
        headerPartManager.setTmp(item.getTmp(which));
        headerPartManager.setLoc(item.getLoc(which));
        headerPartManager.setCondCode(item.getCode(which));
        headerPartManager.setCondTxt(item.getCode(which));
        headerPartManager.setFl(item.getFl(which));
        headerPartManager.initViewFinish();
    }
    private void handleAqiPart(WeatherJson item, BaseAqiPartManager aqiPartManager, int which){
        aqiPartManager.initViewData(item,which);
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
        aqiPartManager.initViewFinish();
    }
    private void handleDailyPart(WeatherJson item, BaseDailyPartManager dailyPartManager, int which){
        dailyPartManager.initViewData(item,which);
        dailyPartManager.setData(item,which);
        dailyPartManager.initViewFinish();
    }
    private void handleWindPart(WeatherJson item, BaseWindPartManager windPartManager, int which){
        windPartManager.initViewData(item,which);
        windPartManager.setDeg(item.getDeg(which));
        windPartManager.setDir(item.getDir(which));
        windPartManager.setSc(item.getSc(which));
        windPartManager.setSpd(item.getSpd(which));
        windPartManager.initViewFinish();
    }
    private void handleSuggestionPart(WeatherJson item, BaseSuggestionPartManager suggestionPartManager, int which){
//        log(UrlHelper.generateWeatherUrl("shenzhen"));
        suggestionPartManager.initViewData(item,which);
        if (    //记于2017.6.2 - 和风天气删除了空气质量这一指数
                (item.getBrfComf(which)==null) &&
                (item.getBrfCw(which)==null) &&
                (item.getBrfDrsg(which)==null) &&
                (item.getBrfFlu(which)==null) &&
                (item.getBrfSport(which)==null) &&
                (item.getBrfTrav(which)==null) &&
                (item.getBrfUv(which)==null)){
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
        suggestionPartManager.initViewFinish();
    }
    private void handleCityPart(WeatherJson item, BaseCityPartManager cityPartManager, int which){
        cityPartManager.initViewData(item,which);
        cityPartManager.setCity(item.getCity(which));
        cityPartManager.setCnty(item.getCountry(which));
        cityPartManager.setID(item.getId(which));
        cityPartManager.setLat(item.getLat(which));
        cityPartManager.setLon(item.getLon(which));
        cityPartManager.initViewFinish();
    }
    private void handleOtherPart(WeatherJson item, BaseOtherPartManager otherPartManager, int which){
        otherPartManager.initViewData(item,which);
        otherPartManager.setHum(item.getHum(which));
        otherPartManager.setPcpn(item.getPcpn(which));
        otherPartManager.setPres(item.getPres(which));
        otherPartManager.setVis(item.getVis(which));
        otherPartManager.initViewFinish();
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
    public void onRefresh() {
        postEvent(new WeatherRequestEvent(ID,cityName));
    }

    /**
     *      EventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN , sticky = true)
    public void onWeatherResultEvent(WeatherResultEvent event){
        swipeRefreshLayout.setRefreshing(false);
        if (event.isSuccess()){
            if (event.getWeatherJson()==null){
                loge("网络数据解析失败");
            }else {
                WeatherJson item = event.getWeatherJson();
                for (int i=0;i<item.size();i++){
                    if (item.getId(i).equals(ID)){
                        OfflineCacheHelper.saveCityOfflineData(getContext(),ID,item.getJsonObject().toString());
                        setWeatherData(item,i);
                    }
                }
                ConfigHelper.setCityWeatherUpdateTime(getContext(),getID(),System.currentTimeMillis());
                //此处通知小部件更新
                postEvent(new WidgetEvent(WidgetEvent.WidgetType.INSTANCE.getTYPE_BASE(),WidgetEvent.EventType.INSTANCE.getTYPE_UPDATE()));
            }
        }
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTypeEditEvent(PartEditEvent event){
        isNeedRefresh = true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPartStylePrefChangeEvent(PartStylePrefChangeEvent event){
        isNeedRefresh = true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThemeAlphaChangeEvent(ThemeAlphaChangeEvent event){
        isNeedRefresh = true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTemperatureUnitChangeEvent(TemperatureUnitChangeEvent event){
        if (event.getKey().equals(getString(R.string.prefHeaderTemperatureUnit))){
            for (BasePartManager basePartManager:partManagerList){
                if (basePartManager instanceof BaseHeaderPartManager){
                    handleHeadPart(weatherJson,(BaseHeaderPartManager) basePartManager,which);
                }
            }
        }else if (event.getKey().equals(getString(R.string.prefDailyTemperatureUnit))){
            for (BasePartManager basePartManager:partManagerList){
                if (basePartManager instanceof BaseDailyPartManager){
                    handleDailyPart(weatherJson,(BaseDailyPartManager) basePartManager,which);
                }
            }
        }
    }
}
