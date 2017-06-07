package creeper_san.weather.Helper;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.widget.ImageView;

import creeper_san.weather.R;

public class ViewHelper {

    public static void initImageViewContentTint(ImageView imageView,@ColorInt int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (color == Color.WHITE){
                imageView.setImageTintList(imageView.getResources().getColorStateList(R.color.white,imageView.getContext().getTheme()));
            }else {
                imageView.setImageTintList(imageView.getResources().getColorStateList(R.color.black,imageView.getContext().getTheme()));
            }
        }else {
            if (color == Color.WHITE){
                imageView.setImageTintList(imageView.getResources().getColorStateList(R.color.white));
            }else {
                imageView.setImageTintList(imageView.getResources().getColorStateList(R.color.black));
            }
        }

    }
}
