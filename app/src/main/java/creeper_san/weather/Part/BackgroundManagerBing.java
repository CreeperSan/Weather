package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class BackgroundManagerBing extends BaseBackgroundPartManager {
    @BindView(R.id.partBackgroundBingImage)ImageView imageView;

    public BackgroundManagerBing(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setData(WeatherJson item, int which) {

    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        if (ConfigHelper.settingGetBackgroundBingImageSize(getContext(),"0").equals("0")){
            Glide.with(getContext()).load(UrlHelper.generateBingImage768pUrl()).into(imageView);
        }else {
            Glide.with(getContext()).load(UrlHelper.generateBingImage1080pUrl()).into(imageView);
        }
    }

    @Override
    public void setWeather(int which, WeatherJson weatherJson) {

    }

    @Override
    protected int getLayout() {
        return R.layout.part_background_bing;
    }

    @Override
    public void setEmpty() {

    }
}
