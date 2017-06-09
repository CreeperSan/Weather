package creeper_san.weather.Part;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;

public class BackgroundManagerLocal extends BaseBackgroundPartManager {
    @BindView(R.id.partBackgroundLocalImage)ImageView imageView;

    public BackgroundManagerLocal(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        setImage();
    }

    public void setImage(){
        String pathStr = ConfigHelper.settingGetFilePickerPath(getContext(),"prefBackgroundLocalImageKey");
        Glide.with(getContext()).load(pathStr).into(imageView);
    }

    @Override
    public void setData(WeatherJson item, int which) {

    }

    @Override
    public void setWeather(int which, WeatherJson weatherJson) {

    }

    @Override
    protected int getLayout() {
        return R.layout.part_background_local;
    }

    @Override
    public void setEmpty() {

    }
}
