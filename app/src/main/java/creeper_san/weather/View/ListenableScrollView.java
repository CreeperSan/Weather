package creeper_san.weather.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;



public class ListenableScrollView extends ScrollView {
    private OnScrollListener onScrollListener;

    public ListenableScrollView(Context context) {
        this(context,null);
    }

    public ListenableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ListenableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener!=null){
            onScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener{
        void onScroll(int i,int t,int oldl,int oldt);
    }
}
