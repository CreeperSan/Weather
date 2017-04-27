package creeper_san.weather.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import creeper_san.weather.BootActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(getLayout());
    }

    protected abstract @LayoutRes int getLayout();

    /**
     *      启动一个Activity
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
            intent.putExtra(key,(int) value);
        }else if (value instanceof String){
            intent.putExtra(key,(String) value);
        }else if (value instanceof Float){
            intent.putExtra(key,(float) value);
        }else if (value instanceof Boolean){
            intent.putExtra(key,(boolean) value);
        }else if (value instanceof Double){
            intent.putExtra(key,(double) value);
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
                intent.putExtra(key[i],(int) value[i]);
            }
        }else if (value[0] instanceof String){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(String) value[i]);
            }
        }else if (value[0] instanceof Float){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(float) value[i]);
            }
        }else if (value[0] instanceof Boolean){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(boolean) value[i]);
            }
        }else if (value[0] instanceof Double){
            for (int i=0;i<key.length;i++){
                intent.putExtra(key[i],(double) value[i]);
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
