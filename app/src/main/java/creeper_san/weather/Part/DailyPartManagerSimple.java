package creeper_san.weather.Part;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseDailyPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Json.WeatherItem;
import creeper_san.weather.R;
import creeper_san.weather.View.SimpleLineChartView;

public class DailyPartManagerSimple extends BaseDailyPartManager {
    @BindView(R.id.partDailySimpleLineChartView)SimpleLineChartView lineChartView;
    @BindView(R.id.partDailyRecyclerView)RecyclerView recyclerView;
    private LayoutInflater layoutInflater;

    public DailyPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
        this.layoutInflater = inflater;
    }

    @Override
    public void setData(WeatherItem weatherItem,int which) {
        List<SimpleLineChartView.PointData> pointMaxList = new ArrayList<>();
        List<SimpleLineChartView.PointData> pointMinList = new ArrayList<>();
        List<WeatherDataItem> weatherDataItemList = new ArrayList<>();
        for (int i=0;i<weatherItem.dailySize(which);i++){
            float max;
            float min;
            try {
                max = Float.parseFloat(weatherItem.getDailyTmpMax(which,i));
                min = Float.parseFloat(weatherItem.getDailyTmpMin(which,i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                max = 0;
                min = 0;
            }
            SimpleLineChartView.PointData pointDataMax = new SimpleLineChartView.PointData(max);
            SimpleLineChartView.PointData pointDataMin = new SimpleLineChartView.PointData(min);
            pointMaxList.add(pointDataMax);
            pointMinList.add(pointDataMin);
            //天气预告列表
            WeatherDataItem weatherDataItem = new WeatherDataItem(weatherItem.getDailyCodeD(which,i),weatherItem.getDailyTxtD(which,i));
            weatherDataItemList.add(weatherDataItem);
        }
        SimpleLineChartView.LineData maxTempLineData = new SimpleLineChartView.LineData(pointMaxList).setGravityValue(SimpleLineChartView.LineData.GRAVITY_TOP);
        SimpleLineChartView.LineData minTempLineData = new SimpleLineChartView.LineData(pointMinList).setGravityValue(SimpleLineChartView.LineData.GRAVITY_BOTTOM);
        List<SimpleLineChartView.LineData> lineDataList = new ArrayList<>();
        lineDataList.add(maxTempLineData);
        lineDataList.add(minTempLineData);
        lineChartView.setData(lineDataList);
        //天气预告列表
        WeatherAdapter adapter = new WeatherAdapter(weatherDataItemList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initView(View partRootView, ViewGroup container) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
    @Override
    protected int getLayout() {
        return R.layout.part_daily_simple;
    }
    @Override
    public void setEmpty() {

    }

    class WeatherDataItem{
        private final String code;
        private final String text;

        public WeatherDataItem(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public String getText() {
            return text;
        }
    }
    class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder>{
        private List<WeatherDataItem> weatherDataItemList;

        public WeatherAdapter(List<WeatherDataItem> weatherDataItemList) {
            this.weatherDataItemList = weatherDataItemList;
        }

        @Override
        public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WeatherHolder(layoutInflater.inflate(R.layout.item_part_daily_simple,parent,false));
        }

        @Override
        public void onBindViewHolder(WeatherHolder holder, int position) {
            WeatherDataItem item = weatherDataItemList.get(position);
            holder.imageView.setImageResource(ResHelper.getWeatherImageWeatherCode(item.getCode()));
            holder.textView.setText(ResHelper.getStringIDFromWeatherCode(item.getCode()));
        }

        @Override
        public int getItemCount() {
            return weatherDataItemList==null?0:weatherDataItemList.size();
        }
    }
    class WeatherHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemPartDailySimpleImageView)ImageView imageView;
        @BindView(R.id.itemPartDailySimpleListText)TextView textView;

        public WeatherHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
