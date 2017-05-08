package creeper_san.weather.Part;

import android.view.LayoutInflater;
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
        airTitle.setText("空气指数  "+content);
    }

    @Override
    public void setAirTxt(String content) {
        airTxt.setText(content);
    }

    @Override
    public void setComfBrf(String content) {
        comfTitle.setText("体感温度  "+content);
    }

    @Override
    public void setComfTxt(String content) {
        comfTxt.setText(content);
    }

    @Override
    public void setCwBrf(String content) {
        cwTitle.setText("洗车指数  "+content);
    }

    @Override
    public void setCwTxt(String content) {
        cwTxt.setText(content);
    }

    @Override
    public void setDrsgBrf(String content) {
        drsgTitle.setText("穿衣指数  "+content);
    }

    @Override
    public void setDrsgTxt(String content) {
        drsgTxt.setText(content);
    }

    @Override
    public void setFluBrf(String content) {
        fluTitle.setText("流感指数  "+content);
    }

    @Override
    public void setFluTxt(String content) {
        fluTxt.setText(content);
    }

    @Override
    public void setSportBrf(String content) {
        sportTitle.setText("运动指数  "+content);
    }

    @Override
    public void setSportTxt(String content) {
        sportTxt.setText(content);
    }

    @Override
    public void setTravBrf(String content) {
        travTitle.setText("出行指数  "+content);
    }

    @Override
    public void setTravTxt(String content) {
        travTxt.setText(content);
    }

    @Override
    public void setUvBrf(String content) {
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
    protected void setEmpty() {

    }
}
