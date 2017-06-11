package creeper_san.weather.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import creeper_san.weather.Event.ApplicationExitEvent;
import creeper_san.weather.Event.ThemePrefEvent;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.ThemeDecodeHelper;
import creeper_san.weather.R;

public abstract class BaseActivity extends AppCompatActivity {
    private final String EVENT_BUS_BASE_FINISH = "Finish";

    private final String TAG = getClass().getSimpleName();
    protected Context context;
    protected LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initTheme();
        setContentView(getLayout());
        inflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTheme(){
        setTheme(ThemeDecodeHelper.getThemeID(ConfigHelper.settingGetTheme(context,"0")));
    }



    /**
     *      EventBus
     */
    protected <T> void postEvent(T event){
        EventBus.getDefault().post(event);
    }
    protected <T> void postStickyEvent(T event){
        EventBus.getDefault().postSticky(event);
    }
    @Subscribe
    public void onEvent(String command){
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThemePrefEvent(ThemePrefEvent event){
        onThemePrefChange(event);
    }
    @Subscribe()
    public void onApplicationExitEvent(ApplicationExitEvent event){
        finish();
    }
    protected void onThemePrefChange(ThemePrefEvent event){
        recreate();
    }

    /**
     *      主题设置
     */
    protected void initToolbarTheme(Toolbar toolbar){
    }
    /**
     *      抽象方法
     */

    protected abstract @LayoutRes int getLayout();

    /**
     *      检查系统版本
     */
    public boolean isSystemOver(int systemVersion){
        return Build.VERSION.SDK_INT > systemVersion;
    }
    public boolean isSystemEqualOrOver(int systemVersion){
        return Build.VERSION.SDK_INT >= systemVersion;
    }
    public boolean isSystemEqual(int systemVersion){
        return Build.VERSION.SDK_INT == systemVersion;
    }
    public boolean isSystemUnder(int systemVersion){
        return Build.VERSION.SDK_INT < systemVersion;
    }
    public boolean isSystemEqualOrUnder(int systemVersion){
        return Build.VERSION.SDK_INT <= systemVersion;
    }

    /**
     *      Activity、Service、BroadcastReceiver
     */
    protected void startActivity(Class cls){
        startActivity(cls,false);
    }
    protected void startActivity(Class cls,boolean isFinish){
        Intent intent = new Intent(BaseActivity.this,cls);
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }
    protected <T> void startActivity(Class cls,String key,T value){
        startActivity(cls,key,value,false);
    }
    protected <T> void startActivity(Class cls,String key,T value,boolean isFinish){
        Intent intent = new Intent(BaseActivity.this,cls);
        if (value instanceof Integer){
            intent.putExtra(key,(Integer) value);
        }else if (value instanceof String){
            intent.putExtra(key,(String) value);
        }else if (value instanceof Float){
            intent.putExtra(key,(Float) value);
        }else if (value instanceof Boolean){
            intent.putExtra(key,(Boolean) value);
        }else if (value instanceof Double){
            intent.putExtra(key,(Double) value);
        }else {
            return;
        }
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }
    protected <T> void startActivity(Class cls,String[] key,T[] value){
        startActivity(cls, key, value, false);
    }
    protected <T> void startActivity(Class cls,String[] key,T[] value,boolean isFinish){
        Intent intent = new Intent(BaseActivity.this,cls);
        if (value[0] instanceof Integer){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(Integer) value[i]);
            }
        }else if (value[0] instanceof String){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(String) value[i]);
            }
        }else if (value[0] instanceof Float){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(Float) value[i]);
            }
        }else if (value[0] instanceof Boolean){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(Boolean) value[i]);
            }
        }else if (value[0] instanceof Double){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(Double) value[i]);
            }
        }else {
            return;
        }
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }
    protected <T> void startActivity(Class cls, ArrayList<String> key, ArrayList<T> value){
        startActivity(cls, key, value,false);
    }
    protected <T> void startActivity(Class cls, ArrayList<String> key, ArrayList<T> value,boolean isFinish){
        if (value.get(0) instanceof Integer){
            startActivity(cls, (String[]) key.toArray(new String[key.size()]), (Integer[]) value.toArray(new Integer[value.size()]), isFinish);
        }else if(value.get(0) instanceof String){
            startActivity(cls, (String[]) key.toArray(new String[key.size()]), (String[]) value.toArray(new String[value.size()]), isFinish);
        }else if(value.get(0) instanceof Float){
            startActivity(cls, (String[]) key.toArray(new String[key.size()]), (Float[]) value.toArray(new Float[value.size()]), isFinish);
        }else if(value.get(0) instanceof Boolean){
            startActivity(cls, (String[]) key.toArray(new String[key.size()]), (Boolean[]) value.toArray(new Boolean[value.size()]), isFinish);
        }else if(value.get(0) instanceof Double){
            startActivity(cls, (String[]) key.toArray(new String[key.size()]), (Double[]) value.toArray(new Double[value.size()]), isFinish);
        }
    }
    protected void startService(Class cls){
        Intent intent = new Intent(BaseActivity.this,cls);
        startService(intent);
    }
    protected void bindService(Class cls, ServiceConnection connection){
        Intent intent = new Intent(BaseActivity.this,cls);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }
    protected void bindStartService(Class cls,ServiceConnection connection){
        Intent intent = new Intent(BaseActivity.this,cls);
        bindService(intent,connection,BIND_AUTO_CREATE);
        startService(intent);
    }
    protected void registerReceiver(BroadcastReceiver receiver,String... filter){
        IntentFilter intentFilter = new IntentFilter();
        for (String filterStr:filter){
            intentFilter.addAction(filterStr);
        }
        registerReceiver(receiver,intentFilter);
    }
    protected void registerReceiver(BroadcastReceiver receiver,List<String> filter){
        registerReceiver(receiver,(String[])filter.toArray());
    }

    /**
     *      Toast & Log
     */
    protected void toast(String content){
        Toast.makeText(BaseActivity.this,content,Toast.LENGTH_SHORT).show();
    }
    protected void toastLong(String content){
        Toast.makeText(BaseActivity.this,content,Toast.LENGTH_LONG).show();
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
