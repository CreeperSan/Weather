package creeper_san.weather.Part;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Calendar;

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
        checkIsNeedUpdateBingImage(getContext());
    }

    public void checkIsNeedUpdateBingImage(Context context) {
        if (ConfigHelper.settingGetBgTheme(context,"0").equals("2")){
            //当前是bing天气
            //生成今天的日期数据
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String dateStr = String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
            if (!ConfigHelper.settingGetBingImageUpdateDate(context).equals(dateStr)){
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Glide.get(getContext()).clearDiskCache();
                    }
                }.start();
                Glide.get(getContext()).clearMemory();
                ConfigHelper.settingSetBingImageUpdateDate(context);
            }
        }
        //初始化监听器
        final RequestListener<Drawable> listener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                log("加载失败");
                toast("背景图片加载失败");
                ConfigHelper.settingSetBingImageUpdateDate(getContext(),"");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
        //加载请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ConfigHelper.settingGetBackgroundBingImageSize(getContext(),"0").equals("0")){
                    Glide.with(getContext()).load(UrlHelper.generateBingImage768pUrl()).listener(listener).into(imageView);
                }else {
                    Glide.with(getContext()).load(UrlHelper.generateBingImage1080pUrl()).listener(listener).into(imageView);
                }
            }
        },500);
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
