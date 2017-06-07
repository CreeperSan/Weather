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
    public void onUpdateEvent(UpdateResultEvent event){
        if (event.isSuccess()){
            UpdateJson updateJson = event.getUpdateJson();
            if (updateJson.isHistory()){//查看更新历史
                dialogHistoryVersion(updateJson);
            }else {//检查更新
                PackageManager packageManager = getPackageManager();
                int currentCode = 0;
                String currentName = "";
                try {
                    currentCode = packageManager.getPackageInfo(getPackageName(),0).versionCode;
                    currentName = packageManager.getPackageInfo(getPackageName(),0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (currentCode >= updateJson.getVersion(0)){
                    toast("当前版本已经是最新啦(●'◡'●)");
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("检查更新");
                    builder.setMessage("当前版本 "+currentName+"\n"
                            +"新版本 "+updateJson.getVersionName(0)
                            +"\n更新内容 :\n"+updateJson.getDescription(0));
                    builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("下次再说",null);
                    builder.show();
                }
            }
        }
    }
    private void dialogHistoryVersion(UpdateJson json){
        log("dialog");
        if (json.getResult() == UpdateJson.RESULT_FAIL){
            toast("连接失败，请检查你的网络连接");
            return;
        }else if (json.getResult() == UpdateJson.RESULT_DECODE_ERR){
            toast("数据解析失败，请重试或联系开发者");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("历史更新");
        StringBuffer buffer = new StringBuffer("\n");
        for (int i=0;i<json.versionCount();i++){
            buffer.append(json.getVersionName(i)+"\n");
            buffer.append(json.getUpdateTime(i)+"\n");
            buffer.append(json.getDescription(i)+"\n");
            buffer.append("\n");
            buffer.append("\n");
        }
        builder.setMessage(buffer.toString());
        builder.show();
    }
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
