package creeper_san.weather.Base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected <T> void postEvent(T event){
        EventBus.getDefault().post(event);
    }
    protected <T> void postStickyEvent(T event){
        EventBus.getDefault().postSticky(event);
    }
    @Subscribe
    public void onEvent(String command){}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayout(),container,false);
        ButterKnife.bind(this,rootView);
        initView(rootView);
        onCreateViewFinish();
        return rootView;
    }

    protected void onCreateViewFinish() {}

    protected abstract @LayoutRes int getLayout();

    protected void initView(View rootView){}

    protected void toast(String content){
        Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
    }
    protected void toastLong(String content){
        Toast.makeText(getContext(),content,Toast.LENGTH_LONG).show();
    }

    protected void log(String content){
        Log.i(TAG,content);
    }
    protected void logv(String content){
        Log.v(TAG,content);
    }
    protected void logd(String content){
        Log.d(TAG,content);
    }
    protected void logw(String content){
        Log.w(TAG,content);
    }
    protected void loge(String content){
        Log.e(TAG,content);
    }
}
