package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseSuggestionPartManager;
import creeper_san.weather.R;



public class SuggestionPartManagerSimple extends BaseSuggestionPartManager {
    @BindView(R.id.partSuggestionAir)TextView airTitle;
    @BindView(R.id.partSuggestionAirText)TextView airTxt;
    @BindView(R.id.partSuggestionComf)TextView comfTitle;
    @BindView(R.id.partSuggestionComfText)TextView comfTxt;
    @BindView(R.id.partSuggestionCw)TextView cwTitle;
    @BindView(R.id.partSuggestionCwText)TextView cwTxt;
    @BindView(R.id.partSuggestionDrsg)TextView drsgTitle;
    @BindView(R.id.partSuggestionDrsgText)TextView drsgTxt;
    @BindView(R.id.partSuggestionFlu)TextView fluTitle;
    @BindView(R.id.partSuggestionFluText)TextView fluTxt;
    @BindView(R.id.partSuggestionSport)TextView sportTitle;
    @BindView(R.id.partSuggestionSportText)TextView sportTxt;
    @BindView(R.id.partSuggestionTrav)TextView travTitle;
    @BindView(R.id.partSuggestionTravText)TextView travTxt;
    @BindView(R.id.partSuggestionUv)TextView uvTitle;
    @BindView(R.id.partSuggestionUvTex)TextView uvTxt;


    public SuggestionPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setAirBrf(String content) {
        if (content==null || content.equals("")){
            airTxt.setVisibility(View.GONE);
            airTitle.setVisibility(View.GONE);
        }else {
            airTxt.setVisibility(View.VISIBLE);
            airTitle.setVisibility(View.VISIBLE);
        }
        airTitle.setText("空气指数  "+content);
    }

    @Override
    public void setAirTxt(String content) {
        airTxt.setText(content);
    }

    @Override
    public void setComfBrf(String content) {
        if (content==null || content.equals("")){
            comfTitle.setVisibility(View.GONE);
            comfTxt.setVisibility(View.GONE);
        }else {
            comfTitle.setVisibility(View.VISIBLE);
            comfTxt.setVisibility(View.VISIBLE);
        }
        comfTitle.setText("体感温度  "+content);
    }

    @Override
    public void setComfTxt(String content) {
        comfTxt.setText(content);
    }

    @Override
    public void setCwBrf(String content) {
        if (content==null || content.equals("")){
            cwTxt.setVisibility(View.GONE);
            cwTitle.setVisibility(View.GONE);
        }else {
            cwTxt.setVisibility(View.VISIBLE);
            cwTitle.setVisibility(View.VISIBLE);
        }
        cwTitle.setText("洗车指数  "+content);
    }

    @Override
    public void setCwTxt(String content) {
        cwTxt.setText(content);
    }

    @Override
    public void setDrsgBrf(String content) {
        if (content==null || content.equals("")){
            drsgTitle.setVisibility(View.GONE);
            drsgTxt.setVisibility(View.GONE);
        }else {
            drsgTitle.setVisibility(View.VISIBLE);
            drsgTxt.setVisibility(View.VISIBLE);
        }
        drsgTitle.setText("穿衣指数  "+content);
    }

    @Override
    public void setDrsgTxt(String content) {
        drsgTxt.setText(content);
    }

    @Override
    public void setFluBrf(String content) {
        if (content==null || content.equals("")){
            fluTxt.setVisibility(View.GONE);
            fluTitle.setVisibility(View.GONE);
        }else {
            fluTxt.setVisibility(View.VISIBLE);
            fluTitle.setVisibility(View.VISIBLE);
        }
        fluTitle.setText("流感指数  "+content);
    }

    @Override
    public void setFluTxt(String content) {
        fluTxt.setText(content);
    }

    @Override
    public void setSportBrf(String content) {
        if (content==null || content.equals("")){
            sportTxt.setVisibility(View.GONE);
            sportTitle.setVisibility(View.GONE);
        }else {
            sportTxt.setVisibility(View.VISIBLE);
            sportTitle.setVisibility(View.VISIBLE);
        }
        sportTitle.setText("运动指数  "+content);
    }

    @Override
    public void setSportTxt(String content) {
        sportTxt.setText(content);
    }

    @Override
    public void setTravBrf(String content) {
        if (content==null || content.equals("")){
            travTxt.setVisibility(View.GONE);
            travTitle.setVisibility(View.GONE);
        }else {
            travTxt.setVisibility(View.VISIBLE);
            travTitle.setVisibility(View.VISIBLE);
        }
        travTitle.setText("出行指数  "+content);
    }

    @Override
    public void setTravTxt(String content) {
        travTxt.setText(content);
    }

    @Override
    public void setUvBrf(String content) {
        if (content==null || content.equals("")){
            uvTxt.setVisibility(View.GONE);
            uvTitle.setVisibility(View.GONE);
        }else {
            uvTxt.setVisibility(View.VISIBLE);
            uvTitle.setVisibility(View.VISIBLE);
        }
        uvTitle.setText("紫外线指数  "+content);
    }

    @Override
    public void setUvTxt(String content) {
        uvTxt.setText(content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_suggestion_simple;
    }

    @Override
    public void setEmpty() {

        airTitle.setVisibility(View.VISIBLE);
        airTitle.setText("所选择的地区没有提供生活建议数据");
        airTxt.setVisibility(View.GONE);
        comfTitle.setVisibility(View.GONE);
        comfTxt.setVisibility(View.GONE);
        cwTitle.setVisibility(View.GONE);
        cwTxt.setVisibility(View.GONE);
        drsgTitle.setVisibility(View.GONE);
        drsgTxt.setVisibility(View.GONE);
        fluTitle.setVisibility(View.GONE);
        fluTxt.setVisibility(View.GONE);
        sportTitle.setVisibility(View.GONE);
        sportTxt.setVisibility(View.GONE);
        travTitle.setVisibility(View.GONE);
        travTxt.setVisibility(View.GONE);
        uvTitle.setVisibility(View.GONE);
        uvTxt.setVisibility(View.GONE);
    }
}
