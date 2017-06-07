package creeper_san.weather.Part;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class BackgroundManagerSimple extends BaseBackgroundPartManager {
    @BindView(R.id.partBackgroundLayout)LinearLayout linearLayout;

    public BackgroundManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    public void setColor(@ColorInt int color){
        linearLayout.setBackgroundColor(color);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        linearLayout.setBackgroundColor(Color.parseColor("#"+ConfigHelper.settingGetBackgroundColor(getContext(),"ffffff")));
    }

    @Override
    public void setData(WeatherJson item, int which) {

    }

    @Override
    public void setWeather(int which, WeatherJson weatherJson) {

    }

    @Override
    protected int getLayout() {
        return R.layout.part_background_simple;
    }

    @Override
    public void setEmpty() {

    }
}
