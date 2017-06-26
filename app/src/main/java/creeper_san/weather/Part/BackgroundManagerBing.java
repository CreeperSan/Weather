package creeper_san.weather.Part;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Event.BingImageResultEvent;
import creeper_san.weather.Helper.BingImageSaveHelper;
import creeper_san.weather.Helper.BitmapHelper;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class BackgroundManagerBing extends BaseBackgroundPartManager {
    @BindView(R.id.partBackgroundBingImage)ImageView imageView;

    public BackgroundManagerBing(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBackgroundRemove() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setData(WeatherJson item, int which) {

    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        boolean isNeed1080P = ConfigHelper.settingGetBackgroundBingImageSize(getContext(),"0").equals("1");
        if (BingImageSaveHelper.INSTANCE.isNewestPicture(getContext())){
            log("文件夹根目录 " + Environment.getDataDirectory().getAbsolutePath());
            log("不是最新的背景图片");
            BingImageSaveHelper.INSTANCE.getNewestPicture(UrlHelper.generateBingImageGuoLinUrl());
        }else {
            log("是最新的背景图片");
            loadImageToImageView(BingImageSaveHelper.INSTANCE.getCacheBingPicData(getContext()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBingImageResultEvent(BingImageResultEvent event){
        log("收到Service返回的结果");
        if (event.getType() == BingImageResultEvent.ResultType.INSTANCE.getTYPE_SUCCESS()){
            log("收到Service返回的结果：成功");
            imageView.setImageBitmap(event.getImageData());
            loadImageToImageView(event.getImageData());
            BingImageSaveHelper.INSTANCE.savePictureAsNewest(event.getImageData(),getContext());
            log("图片流程完毕！");
        }else {
            log("收到Service返回的结果：失败");
            toast("获取背景失败");
        }
    }


    private void loadImageToImageView(Bitmap imageData){
        if (imageData==null){
            imageView.setBackgroundColor(Color.YELLOW);
            return;
        }
        String blurStatus = ConfigHelper.settingGetBackgroundBlur(getContext(),"0");
        switch (blurStatus){
            case "1":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(imageData,5,getContext()));break;
            case "2":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(imageData,10,getContext()));break;
            case "3":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(imageData,15,getContext()));break;
            case "4":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(imageData,20,getContext()));break;
            case "5":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(imageData,25,getContext()));break;
            default:imageView.setImageBitmap(imageData);break;
        }
    }

    @Override
    public void setWeather(int which, WeatherJson weatherJson) {}

    @Override
    protected int getLayout() {
        return R.layout.part_background_bing;
    }

    @Override
    public void setEmpty() {

    }
}
