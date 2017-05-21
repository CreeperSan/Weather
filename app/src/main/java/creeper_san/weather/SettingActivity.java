package creeper_san.weather;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        getFragmentManager().beginTransaction().hide(fragmentStack.peek()).commit();
        fragmentStack.add(fragment);
        getFragmentManager().beginTransaction().add(R.id.settingLinearLayout,fragment).commit();
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }
}
