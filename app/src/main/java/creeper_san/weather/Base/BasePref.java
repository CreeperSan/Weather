package creeper_san.weather.Base;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;



public abstract class BasePref extends Preference {
    protected View rootView;

    public BasePref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,getAttrID());
        int n = typedArray.getIndexCount();
        for (int i=0;i<n;i++){
            int attr = typedArray.getIndex(i);
            handleAttr(attr,typedArray);
        }
        typedArray.recycle();
    }
    public BasePref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public BasePref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public BasePref(Context context) {
        this(context,null);
    }


    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        View view = LayoutInflater.from(getContext()).inflate(getLayoutID(),parent,false);
        rootView = view;
        ButterKnife.bind(this,view);
        return view;
    }


    protected abstract @StyleableRes int[] getAttrID();
    protected abstract @LayoutRes int getLayoutID();
    protected abstract void handleAttr(int attr,TypedArray typedArray);

    protected void log(String content){
        Log.i("Pref",content);
    }
    protected void toast(String content){
        Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
    }

}
