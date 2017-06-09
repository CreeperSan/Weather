package creeper_san.weather.Pref;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import java.io.File;

import creeper_san.weather.Helper.ConfigHelper;


public class ImageFilePickerPref extends FilePickerPref {

    public ImageFilePickerPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public ImageFilePickerPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public ImageFilePickerPref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ImageFilePickerPref(Context context) {
        this(context,null);
    }


    @Override
    protected void onFilePicker(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap==null){
            toast("所选文件不是图像文件，请重试");
            log("所选文件不是图像文件，请重试");
        }else {
            setPathStr(file.getAbsolutePath());
            ConfigHelper.settingSetFilePickerPath(getContext(),getKey(),getPathStr());
            pathText.setText(getPathStr());
            notifyChanged();

        }
    }
}
