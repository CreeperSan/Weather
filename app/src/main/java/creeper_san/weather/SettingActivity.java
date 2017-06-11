package creeper_san.weather;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Stack;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.RequirePermissionEvent;
import creeper_san.weather.Event.ThemePrefEvent;
import creeper_san.weather.Event.UpdateResultEvent;
import creeper_san.weather.Fragment.MainPrefFragment;
import creeper_san.weather.Json.UpdateJson;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.settingToolbar)Toolbar toolbar;
    @BindView(R.id.settingLinearLayout)LinearLayout linearLayout;

    private Stack<PreferenceFragment> fragmentStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        if (fragmentStack==null){
            fragmentStack = new Stack<>();
            fragmentStack.add(new MainPrefFragment());
            getFragmentManager().beginTransaction().add(R.id.settingLinearLayout,fragmentStack.peek()).commit();
        }
    }

    private void initToolbar() {
//        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("设置");
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initToolbarTheme(toolbar);
    }

    public void addNewPrefFragment(PreferenceFragment fragment){
        getFragmentManager().beginTransaction().hide(fragmentStack.peek()).commit();
        fragmentStack.add(fragment);
        getFragmentManager().beginTransaction().add(R.id.settingLinearLayout,fragment).commit();
    }

    /**
     *      EventBus
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequirePermissionEvent(RequirePermissionEvent event){
        if (event.getTypeID() == 0){
            ActivityCompat.requestPermissions(SettingActivity.this,new String[]{event.getPermission()},0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                toast("获取权限成功");
            }else {
                toast("获取手机文件读取权限失败，请重试或打开设置手动赋予我们权限");
            }
        }
    }

    @Override
    public void recreate() {
        super.recreate();
        getFragmentManager().beginTransaction().remove(fragmentStack.pop()).commit();
        ((LinearLayout)findViewById(R.id.settingLinearLayout)).removeAllViews();
    }

    @Override
    public void onBackPressed() {
        PreferenceFragment fragment = fragmentStack.pop();
        if (fragmentStack.empty()){
            finish();
        }else {
            getFragmentManager().beginTransaction().remove(fragment).show(fragmentStack.peek()).commit();
            setTitle("设置");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }
}
