package creeper_san.weather.Part;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
        try {
            FileInputStream fileInputStream = new FileInputStream(pathStr);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            String blurStatus = ConfigHelper.settingGetBackgroundBlur(getContext(),"0");
            imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,25,getContext()));
//            switch (blurStatus){
//                case "1":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,5,getContext()));break;
//                case "2":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,10,getContext()));break;
//                case "3":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,15,getContext()));break;
//                case "4":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,20,getContext()));break;
//                case "5":imageView.setImageBitmap(BitmapHelper.INSTANCE.blur(bitmap,25,getContext()));break;
//                default:imageView.setImageBitmap(bitmap);break;
//            }
            log("模糊等级为 "+blurStatus);
        } catch (FileNotFoundException e) {
            toast("对应的图片文件没有找到，请重新选择");
            return;
        }
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
