package creeper_san.weather.Pref;

import android.content.Context;
import android.support.annotation.DrawableRes;
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
    private @DrawableRes int icon = 0;

    public CatalogPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

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
        //标题
        if (getTitle()==null){
            if (getTitleRes()==0){
                textView.setText("");
            }else {
                textView.setText(getTitleRes());
            }
        }else {
            textView.setText(getTitle());
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
