package creeper_san.weather;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;
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
        fragmentStack = new Stack<>();
        fragmentStack.add(new MainPrefFragment());
        getFragmentManager().beginTransaction().add(R.id.settingLinearLayout,fragmentStack.peek()).commit();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("设置");
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addNewPrefFragment(PreferenceFragment fragment){
        fragmentStack.add(fragment);
        getFragmentManager().beginTransaction().replace(R.id.settingLinearLayout,fragmentStack.peek()).commit();
    }

    @Override
    public void onBackPressed() {
        fragmentStack.pop();
        if (fragmentStack.empty()){
            finish();
        }else {
            getFragmentManager().beginTransaction().replace(R.id.settingLinearLayout,fragmentStack.peek()).commit();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateResultEvent event){
        if (event.isSuccess()){
            UpdateJson updateJson = event.getUpdateJson();
            if (updateJson.isHistory()){//查看更新历史

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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("检查更新");
                builder.setMessage("当前版本 "+currentName+"\n"
                    +(currentCode >= updateJson.getVersion(0)?"当前版本已是最新":"新版本 "+updateJson.getVersionName(0))
                    +(currentCode >= updateJson.getVersion(0)?"":"\n更新内容 :\n"+updateJson.getDescription(0)));
                builder.setPositiveButton((currentCode >= updateJson.getVersion(0) ? "确定" : "更新"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }
}
