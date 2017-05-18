package creeper_san.weather.Base;

import android.content.Context;
import android.preference.PreferenceGroup;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BasePrefGroup extends PreferenceGroup {
    private final String TAG = getClass().getSimpleName();


    public BasePrefGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setSelectable(false);
    }
    public BasePrefGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public BasePrefGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        View view = LayoutInflater.from(getContext()).inflate(getLayoutID(),parent,false);
        ButterKnife.bind(this,view);
        return view;
    }

    protected abstract @LayoutRes int getLayoutID();

    protected void log(String content){
        Log.i(TAG,content);
    }
}
