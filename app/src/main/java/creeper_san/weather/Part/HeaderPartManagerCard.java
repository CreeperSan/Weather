package creeper_san.weather.Part;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseHeaderPartManager;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.R;


public class HeaderPartManagerCard extends BaseHeaderPartManager {
    @BindView(R.id.partHeaderTmp)TextView tempText;
    @BindView(R.id.partHeaderTmpFeeling)TextView tempFeelingText;
    @BindView(R.id.partHeaderWeatherTxt)TextView weatherText;
    @BindView(R.id.partHeaderWeatherImage)ImageView weatherImage;
    @BindView(R.id.partHeaderUpdateTime)TextView updateText;
    @BindView(R.id.partHeaderBackground)ImageView backgroundImage;

    public HeaderPartManagerCard(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setTmp(String content) {
        tempText.setText(content+"℃");
    }

    @Override
    public void setLoc(String content) {
        updateText.setText(content);
    }

    @Override
    public void setCondCode(String content) {
        switch (content){
            case "100":
                backgroundImage.setBackgroundColor(Color.parseColor("#63a1d7"));
                setContentColorWhite();
                break;
            case "101":
            case "102":
            case "103":
            case "104":
                backgroundImage.setBackgroundColor(Color.parseColor("#6f8fc0"));
                setContentColorWhite();
                break;
            case "200":
            case "201":
            case "202":
            case "203":
            case "204":
            case "205":
                backgroundImage.setBackgroundColor(Color.parseColor("#E7E7E7"));
                setContentColorBlack();
                break;
            case "206":
            case "207":
            case "208":
                backgroundImage.setBackgroundColor(Color.parseColor("#dbdbdb"));
                setContentColorBlack();
                break;
            case "209":
            case "210":
            case "211":
            case "212":
            case "213":
                backgroundImage.setBackgroundColor(Color.parseColor("#c0c0c0"));
                setContentColorWhite();
                break;
            case "300":
            case "301":
            case "302":
            case "303":
            case "304":
            case "305":
            case "306":
            case "307":
            case "308":
            case "309":
            case "310":
            case "311":
            case "312":
            case "313":
                backgroundImage.setBackgroundColor(Color.parseColor("#9db4c5"));
                setContentColorWhite();
                break;
            case "400":
            case "401":
            case "402":
            case "403":
            case "404":
            case "405":
            case "406":
            case "407":
                backgroundImage.setBackgroundColor(Color.parseColor("#6f8fc0"));
                setContentColorWhite();
                break;
            case "500":
            case "501":
            case "502":
                backgroundImage.setBackgroundColor(Color.parseColor("#6f8fc0"));
                setContentColorWhite();
                break;
            case "503":
            case "504":
            case "505":
            case "506":
            case "507":
            case "508":
                backgroundImage.setBackgroundColor(Color.parseColor("#e19e36"));
                setContentColorWhite();
                break;
            case "900":
                backgroundImage.setBackgroundColor(Color.parseColor("#ff7d29"));
                setContentColorWhite();
                break;
            case "901":
                backgroundImage.setBackgroundColor(Color.parseColor("#afdee4"));
                setContentColorBlack();
                break;
            default:
                backgroundImage.setBackgroundColor(Color.parseColor("#ffffff"));
                setContentColorBlack();
                break;
        }
        weatherImage.setImageResource(ResHelper.getWeatherImageWeatherCode(content));
    }

    private void setContentColorWhite(){
        tempText.setTextColor(Color.WHITE);
        tempFeelingText.setTextColor(Color.WHITE);
        weatherText.setTextColor(Color.WHITE);
        updateText.setTextColor(Color.WHITE);
        weatherImage.setColorFilter(Color.WHITE);
    }
    private void setContentColorBlack(){
        tempText.setTextColor(Color.BLACK);
        tempFeelingText.setTextColor(Color.BLACK);
        weatherText.setTextColor(Color.BLACK);
        updateText.setTextColor(Color.BLACK);
        weatherImage.setColorFilter(Color.BLACK);
    }

    @Override
    public void setCondTxt(String content) {
        weatherText.setText(ResHelper.getStringIDFromWeatherCode(content));
    }

    @Override
    public void setFl(String content) {
        tempFeelingText.setText(content+"℃");
    }

    @Override
    protected int getLayout() {
        return R.layout.part_head_card;
    }

    @Override
    public void setEmpty() {
        weatherImage.setImageResource(R.drawable.ic_unknown);
        tempFeelingText.setVisibility(View.GONE);
        tempText.setVisibility(View.GONE);
        updateText.setVisibility(View.GONE);
        weatherText.setVisibility(View.GONE);
    }
}
