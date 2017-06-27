package creeper_san.weather.Part;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import creeper_san.weather.Base.BaseBackgroundPartManager;
import creeper_san.weather.Helper.BingImageSaveHelper;
import creeper_san.weather.Helper.BitmapHelper;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.PermissionHelper;
import creeper_san.weather.Json.WeatherJson;
import creeper_san.weather.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class BackgroundManagerLocal extends BaseBackgroundPartManager {
    private Handler handler = new Handler(getContext().getMainLooper());
    @BindView(R.id.partBackgroundLocalImage)ImageView imageView;

    public BackgroundManagerLocal(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void initViewData(WeatherJson weatherJson, int which) {
        setImage();
    }

    public void setImage(){
        final String pathStr = ConfigHelper.settingGetFilePickerPath(getContext(),"prefBackgroundLocalImageKey");
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (!PermissionHelper.hasPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                toast("我们没有读取文件的权限。请赋予我们权限。");
                return;
            }
        }
        if (pathStr == null){
            toast("所指定的图片路径为空，请进入设置选择");
            return;
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    FileInputStream fileInputStream = new FileInputStream(pathStr);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    String blurStatus = ConfigHelper.settingGetBackgroundBlur(getContext(),"0");
                    final ByteArrayOutputStream baos = BitmapHelper.INSTANCE.bitmapToByteArraySteam(bitmap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            switch (ConfigHelper.settingGetBackgroundBlur(getContext(),"0")){
                                case "1":Glide.with(getContext()).load(baos.toByteArray()).crossFade(1000).bitmapTransform(new BlurTransformation(getContext(),5)).into(imageView);break;
                                case "2":Glide.with(getContext()).load(baos.toByteArray()).crossFade(1000).bitmapTransform(new BlurTransformation(getContext(),10)).into(imageView);break;
                                case "3":Glide.with(getContext()).load(baos.toByteArray()).crossFade(1000).bitmapTransform(new BlurTransformation(getContext(),15)).into(imageView);break;
                                case "4":Glide.with(getContext()).load(baos.toByteArray()).crossFade(1000).bitmapTransform(new BlurTransformation(getContext(),20)).into(imageView);break;
                                case "5":Glide.with(getContext()).load(baos.toByteArray()).crossFade(1000).bitmapTransform(new BlurTransformation(getContext(),25)).into(imageView);break;
                                default:Glide.with(getContext()).load(baos.toByteArray()).into(imageView);break;
                            }
                        }
                    });
                    log("模糊等级为 "+blurStatus);
                } catch (FileNotFoundException e) {
                    toast("对应的图片文件没有找到，请重新选择");
                    return;
                }
            }
        }.start();
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
