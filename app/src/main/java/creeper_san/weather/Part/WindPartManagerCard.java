package creeper_san.weather.Part;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseWindPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;


public class WindPartManagerCard extends BaseWindPartManager {
    @BindView(R.id.partWindCard)CardView cardView;
    @BindView(R.id.partWindWindmills)LottieAnimationView windmillsAnime;
    @BindView(R.id.partWindPointer)LottieAnimationView pointerAnime;
    @BindView(R.id.partWindDegree)TextView degreeText;
    @BindView(R.id.partWindDegreeTxt)TextView degreeTxtText;
    @BindView(R.id.partWindLevel)TextView levelText;
    @BindView(R.id.partWindSpd)TextView speedText;

    private @ColorInt int contentColor;
    private @ColorInt int backgroundColor;

    public WindPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        backgroundColor = ResHelper.getColorFromWeatherCode(weatherJson.getCode(which));
        contentColor = ResHelper.getContentColorFromWeatherCode(weatherJson.getCode(which));
        cardView.setCardBackgroundColor(backgroundColor);
        degreeText.setTextColor(contentColor);
        degreeTxtText.setTextColor(contentColor);
        levelText.setTextColor(contentColor);
        speedText.setTextColor(contentColor);
        if (contentColor == Color.BLACK){
            pointerAnime.setAnimation("lottie/part/wind_pointer_black.json");
            windmillsAnime.setAnimation("lottie/part/wind_windmills_black.json");
        }else {
            pointerAnime.setAnimation("lottie/part/wind_pointer.json");
            windmillsAnime.setAnimation("lottie/part/wind_windmills.json");
        }
        pointerAnime.loop(true);
        pointerAnime.playAnimation();
        windmillsAnime.loop(true);
    }

    @Override
    public void setDeg(String content) {
        degreeText.setText(content+"°");
        try {
            Integer degree = Integer.valueOf(content);
            pointerAnime.setRotation(degree);
        } catch (NumberFormatException e) {}
    }

    @Override
    public void setDir(String content) {
        degreeTxtText.setText(content);
    }

    @Override
    public void setSc(String content) {
        levelText.setText(content+" 级");
        try {
            int speed = Integer.valueOf(content.substring(0,1));
            setLottieAnimeDuration(windmillsAnime,1000-speed*100);
        } catch (NumberFormatException e) {}
    }

    @Override
    public void setSpd(String content) {
        speedText.setText(content+" Km/h");
    }

    @Override
    protected int getLayout() {
        return R.layout.part_wind_card;
    }

    @Override
    public void setEmpty() {

    }

    private void setLottieAnimeDuration(final LottieAnimationView view, int duration){

        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setProgress((Float) animation.getAnimatedValue());
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(null);
        animator.start();
    }
}
