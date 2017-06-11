package creeper_san.weather.Part;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseAqiPartManager;
import creeper_san.weather.Helper.UnitHelper;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Helper.ViewHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class AqiPartManagerCard extends BaseAqiPartManager {
    @BindView(R.id.partAqiImage)ImageView imageView;
    @BindView(R.id.partAqiCard)CardView cardView;
    @BindView(R.id.partAqiTitle)TextView titleText;
    @BindView(R.id.partAqiGrid)RecyclerView contentGrid;

    private @ColorInt int backgroundColor = Color.WHITE;
    private @ColorInt int contentColor = Color.BLACK;

    private LayoutInflater inflater;
    private List<AqiCardItem> itemList;
    private AqiCardAdapter aqiCardAdapter;

    public AqiPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        cardView.setBackgroundColor(backgroundColor);
        titleText.setTextColor(contentColor);
        if (itemList == null){
            itemList = new ArrayList<>();
        }else {
            itemList.clear();
        }
    }

    @Override
    protected void onViewInflated() {
        contentGrid.setOnTouchListener(null);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_aqi_card;
    }

    @Override
    public void setEmpty() {
        contentGrid.setVisibility(View.GONE);
        cardView.setBackgroundColor(backgroundColor);
        titleText.setTextSize(UnitHelper.dip2px(getView().getContext(),10));
        titleText.setGravity(Gravity.START);
        titleText.setAlpha(1f);
        titleText.setText("此地区暂不支持空气质量数据");
    }

    @Override
    public void initViewFinish() {
        if (aqiCardAdapter==null){
            aqiCardAdapter = new AqiCardAdapter();
            contentGrid.setLayoutManager(new GridLayoutManager(getView().getContext(),2){
                //复写LayoutManager，禁止滑动
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            });
            contentGrid.setAdapter(aqiCardAdapter);
        }else {
            aqiCardAdapter.notifyDataSetChanged();
        }
        ViewHelper.initImageViewContentTint(imageView,contentColor);
    }

    @Override
    public void setAqi(String content) {
        setUpSingleViews(content,"AQI");
    }
    @Override
    public void setCo(String content) {
        setUpSingleViews(content,"Co");
    }
    @Override
    public void setNo2(String content) {
        setUpSingleViews(content,"No2");
    }
    @Override
    public void setO3(String content) {
        setUpSingleViews(content,"O3");
    }
    @Override
    public void setPm10(String content) {
        setUpSingleViews(content,"PM10");
    }
    @Override
    public void setPm25(String content) {
        setUpSingleViews(content,"PM25");
    }
    @Override
    public void setQlty(String content) {
        titleText.setGravity(Gravity.BOTTOM | Gravity.END);
        titleText.setText(content);
    }
    @Override
    public void setSo2(String content) {
        setUpSingleViews(content,"So2");
    }

    private LayoutInflater getLayoutInflater(Context context){
        if (inflater == null){
            inflater = LayoutInflater.from(context);
        }
        return inflater;
    }
    private void setUpSingleViews(String content,String catalogValue){
        if (!content.equals("")){
            AqiCardItem item = new AqiCardItem(content,catalogValue);
            itemList.add(item);
        }
    }

    class AqiCardItem{
        private final String content;
        private final String catalog;

        public AqiCardItem(String content, String catalog) {
            this.content = content;
            this.catalog = catalog;
        }

        public String getContent() {
            return content;
        }

        public String getCatalog() {
            return catalog;
        }
    }
    class AqiCardAdapter extends RecyclerView.Adapter<AqiCardViewHolder>{

        @Override
        public AqiCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AqiCardViewHolder(LayoutInflater.from(getView().getContext()).inflate(R.layout.item_part_aqi_card_content,parent,false));
        }

        @Override
        public void onBindViewHolder(AqiCardViewHolder holder, int position) {
            AqiCardItem item = itemList.get(position);
            holder.catalogText.setText(item.getCatalog());
            holder.catalogText.setTextColor(contentColor);
            holder.valueText.setText(item.getContent());
            holder.valueText.setTextColor(contentColor);
        }

        @Override
        public int getItemCount() {
            return itemList==null?0:itemList.size();
        }
    }
    class AqiCardViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemPartAqiCardCatalog)TextView catalogText;
        @BindView(R.id.itemPartAqiCardValue)TextView valueText;

        public AqiCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
