package creeper_san.weather.Pref;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.PreferenceCategory;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import creeper_san.weather.R;

/**
 * Created by CreeperSan on 2017/5/17.
 */

public class PrefCatalog extends PreferenceCategory {
    private String title;
    private @DrawableRes int icon = 0;

    public PrefCatalog(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PrefCatalog);
        for ()
        title = typedArray.getString(R.styleable.PrefCatalog_title);
        typedArray.recycle();
    }

    public PrefCatalog(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public PrefCatalog(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public PrefCatalog(Context context) {
        this(context,null);
    }
}
