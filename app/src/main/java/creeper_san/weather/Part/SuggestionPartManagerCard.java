package creeper_san.weather.Part;

import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
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
import creeper_san.weather.Base.BaseSuggestionPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Helper.ViewHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class SuggestionPartManagerCard extends BaseSuggestionPartManager {
    @BindView(R.id.partSuggestionHintText)TextView textView;
    @BindView(R.id.partSuggestionRecyclerView)RecyclerView recyclerView;
    @BindView(R.id.partSuggestionCard)CardView cardView;

    @ColorInt int backgroundColor;
    @ColorInt int contentColor;

    private String tempTitle;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;

    public SuggestionPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        cardView.setCardBackgroundColor(backgroundColor);
        if (cardItemList == null){
            cardItemList = new ArrayList<>();
        }else {
            cardItemList.clear();
        }
    }

    @Override
    public void initViewFinish() {
        if (cardAdapter == null){
            cardAdapter = new CardAdapter();
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
            recyclerView.setAdapter(cardAdapter);
        }else {
            cardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAirBrf(String content) {
        tempTitle = "空气质量 "+content;
    }

    @Override
    public void setAirTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_air_quality,tempTitle,content));
    }

    @Override
    public void setComfBrf(String content) {
        tempTitle = "体感情况 "+content;
    }

    @Override
    public void setComfTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_directions_walk_black_24dp,tempTitle,content));
    }

    @Override
    public void setCwBrf(String content) {
        tempTitle = "洗车指数 "+content;
    }

    @Override
    public void setCwTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_directions_car_black_24dp,tempTitle,content));
    }

    @Override
    public void setDrsgBrf(String content) {
        tempTitle = "穿衣指数 "+content;
    }

    @Override
    public void setDrsgTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_cloth,tempTitle,content));
    }

    @Override
    public void setFluBrf(String content) {
        tempTitle = "流感情况 "+content;
    }

    @Override
    public void setFluTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_pill,tempTitle,content));
    }

    @Override
    public void setSportBrf(String content) {
        tempTitle = "运动指数 "+content;
    }

    @Override
    public void setSportTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_directions_bike_black_24dp,tempTitle,content));
    }

    @Override
    public void setTravBrf(String content) {
        tempTitle = "出行指数 "+content;
    }

    @Override
    public void setTravTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_flight_black_24dp,tempTitle,content));
    }

    @Override
    public void setUvBrf(String content) {
        tempTitle = "紫外线指数 "+content;
    }

    @Override
    public void setUvTxt(String content) {
        if (content!=null)
            cardItemList.add(new CardItem(R.drawable.ic_uv,tempTitle,content));
    }

    @Override
    protected int getLayout() {
        return R.layout.part_suggestion_card;
    }

    @Override
    public void setEmpty() {
        textView.setTextColor(contentColor);
        textView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    class CardItem{
        private final int imgID;
        private final String title;
        private final String description;

        public CardItem(int imgID, String title, String description) {
            this.imgID = imgID;
            this.title = title;
            this.description = description;
        }

        public int getImgID() {
            return imgID;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
    class CardAdapter extends RecyclerView.Adapter<CardHolder>{

        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CardHolder(getViewByID(R.layout.item_part_suggestion_card_content,parent,false));
        }

        @Override
        public void onBindViewHolder(final CardHolder holder, int position) {
            CardItem item = cardItemList.get(position);
            holder.descriptionText.setTextColor(contentColor);
            holder.titleText.setTextColor(contentColor);
            ViewHelper.initImageViewContentTint(holder.imageView,contentColor);
            holder.titleText.setText(item.getTitle());
            holder.descriptionText.setText(item.getDescription());
            holder.imageView.setImageResource(item.getImgID());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.titleText.getVisibility() == View.VISIBLE){
                        holder.titleText.setVisibility(View.GONE);
                        holder.descriptionText.setVisibility(View.VISIBLE);
                    }else {
                        holder.titleText.setVisibility(View.VISIBLE);
                        holder.descriptionText.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardItemList==null?0:cardItemList.size();
        }
    }
    class CardHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemPartSuggestionImage)ImageView imageView;
        @BindView(R.id.itemPartSuggestionTitle)TextView titleText;
        @BindView(R.id.itemPartSuggestionDescription)TextView descriptionText;

        public CardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
