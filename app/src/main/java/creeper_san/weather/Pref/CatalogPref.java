package creeper_san.weather.Pref;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BasePrefGroup;
import creeper_san.weather.R;


public class CatalogPref extends BasePrefGroup {
    @BindView(R.id.prefCatalogTitle)TextView textView;
    @BindView(R.id.prefCatalogIcon)ImageView imageView;

    private String title;
    private int titleRes;
    private @ColorInt int textColor=0;
    private @Dimension int textSize;
    private @DrawableRes int icon = 0;

    public CatalogPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CatalogPref);
        int n = typedArray.getIndexCount();
        for (int i=0;i<n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CatalogPref_textColor:
                    textColor = typedArray.getColor(attr, ContextCompat.getColor(context,R.color.colorAccent));
                    break;
                case R.styleable.CatalogPref_textSize:
                    textSize = typedArray.getDimensionPixelSize(attr,64);
                    break;
            }
        }
        typedArray.recycle();
    }
    public CatalogPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public CatalogPref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        //标题文本
        if (getTitle()==null){
            if (getTitleRes()==0){
                textView.setText("");
            }else {
                textView.setText(getTitleRes());
            }
        }else {
            textView.setText(getTitle());
        }
        //标题文本颜色
        if (textColor == 0){
            textView.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorAccent));
        }else {
            textView.setTextColor(textColor);
        }
        //图标
        if (getIcon()==null){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(getIcon());
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.pref_catalog;
    }
}
