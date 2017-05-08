package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseDailyPartManager;
import creeper_san.weather.Json.WeatherItem;
import creeper_san.weather.R;
import creeper_san.weather.View.SimpleLineChartView;

public class DailyPartManagerSimple extends BaseDailyPartManager {
    @BindView(R.id.partDailySimpleLineChartView)SimpleLineChartView lineChartView;

    public DailyPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setData(WeatherItem weatherItem,int which) {

    }

    @Override
    protected void initView(View partRootView, ViewGroup container) {
        List<SimpleLineChartView.PointData> pointDataList = new ArrayList<>();
        for (int i=0;i<7;i++){
            SimpleLineChartView.PointData pointData = new SimpleLineChartView.PointData((int) (Math.random()*10+15));
//            SimpleLineChartView.PointData pointData = new SimpleLineChartView.PointData(50*i+100);
            pointDataList.add(pointData);
        }
        SimpleLineChartView.LineData lineData = new SimpleLineChartView.LineData(pointDataList);
        lineChartView.setData(lineData);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_daily_simple;
    }

    @Override
    protected void setEmpty() {

    }
}
