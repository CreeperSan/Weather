package creeper_san.weather.Part;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseDailyPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Helper.UnitHelper;
import creeper_san.weather.Helper.ViewHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class DailyPartManagerCard extends BaseDailyPartManager {
    @BindView(R.id.partDailyCard)CardView cardView;
    @BindView(R.id.partDailyImage)ImageView imageView;
    @BindView(R.id.partDailyRecyclerView)RecyclerView recyclerView;

    @ColorInt int backgroundColor;
    @ColorInt int contentColor;

    private List<CardItem> cardItemList;
    private CardAdapter adapter;

    public DailyPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        super.initViewData(weatherJson,which);
        backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        ViewHelper.initImageViewContentTint(imageView,contentColor);
        cardView.setCardBackgroundColor(backgroundColor);
        if (cardItemList==null){
            cardItemList = new ArrayList<>();
        }else {
            cardItemList.clear();
        }
    }



    @Override
    public void setData(WeatherJson weatherJson, int which) {
        Calendar calendar = Calendar.getInstance();
        if (getUnit().equals("0")){//摄氏度
            for (int i=0;i<weatherJson.dailySize(which);i++){
                CardItem item = new CardItem(
                        calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)
                        ,getContext().getString(ResHelper.getStringIDFromWeatherCode(weatherJson.getDailyCodeD(which,i)))
                        ,weatherJson.getDailyTmpMin(which,i)+"°",weatherJson.getDailyTmpMax(which,i)+"°"
                        ,ResHelper.getWeatherImageWeatherCode(weatherJson.getDailyCodeD(which,i))
                );
                cardItemList.add(item);
                calendar.add(Calendar.DAY_OF_YEAR,1);
            }
        }else {//华氏度
            for (int i=0;i<weatherJson.dailySize(which);i++){
                boolean isCatch = false;
                int fMax = 0;
                int fMin = 0;
                try {
                    fMax = UnitHelper.celsiusToFahrenheit(Integer.valueOf(weatherJson.getDailyTmpMax(which,i)));
                    fMin = UnitHelper.celsiusToFahrenheit(Integer.valueOf(weatherJson.getDailyTmpMin(which,i)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    isCatch = true;
                }
                CardItem item = new CardItem(
                        calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)
                        ,getContext().getString(ResHelper.getStringIDFromWeatherCode(weatherJson.getDailyCodeD(which,i)))
                        ,isCatch?weatherJson.getDailyTmpMin(which,i)+"℃":fMin+"°"
                        ,isCatch?weatherJson.getDailyTmpMax(which,i)+"℃":fMax+"°"
                        ,ResHelper.getWeatherImageWeatherCode(weatherJson.getDailyCodeD(which,i))
                );
                cardItemList.add(item);
                calendar.add(Calendar.DAY_OF_YEAR,1);
            }
        }
    }

    @Override
    public void initViewFinish() {
        if (adapter==null){
            adapter = new CardAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.part_daily_card;
    }

    @Override
    public void setEmpty() {

    }

    class CardItem{
        private final String date;
        private final String weather;
        private final String tmpMax;
        private final String tmpMin;
        private final @DrawableRes int drawableId;

        public CardItem(String date, String weather, String tmpMax, String tmpMin,@DrawableRes int drawableId) {
            this.date = date;
            this.weather = weather;
            this.tmpMax = tmpMax;
            this.tmpMin = tmpMin;
            this.drawableId = drawableId;
        }

        public String getDate() {
            return date;
        }

        public String getWeather() {
            return weather;
        }

        public String getTmpMax() {
            return tmpMax;
        }

        public String getTmpMin() {
            return tmpMin;
        }

        public int getDrawableId() {
            return drawableId;
        }
    }
    class CardAdapter extends RecyclerView.Adapter<CardHolder>{

        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CardHolder(getViewByID(R.layout.item_part_daily_card_content,parent,false));
        }

        @Override
        public void onBindViewHolder(CardHolder holder, int position) {
            CardItem cardItem = cardItemList.get(position);
            ViewHelper.initImageViewContentTint(holder.imageView,contentColor);
            holder.dateText.setTextColor(contentColor);
            holder.tempText.setTextColor(contentColor);
            holder.dateText.setText(cardItem.getDate());
            holder.tempText.setText(cardItem.getWeather()+"  "+cardItem.getTmpMin()+"-"+cardItem.getTmpMax());
            holder.imageView.setImageResource(cardItem.getDrawableId());
        }

        @Override
        public int getItemCount() {
            return cardItemList==null?0:cardItemList.size();
        }
    }
    class CardHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemPartDailyCardImage)ImageView imageView;
        @BindView(R.id.itemPartDailyCardDate)TextView dateText;
        @BindView(R.id.itemPartDailyCardTemperature)TextView tempText;

        public CardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
