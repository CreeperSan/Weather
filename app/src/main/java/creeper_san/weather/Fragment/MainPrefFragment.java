package creeper_san.weather.Fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import creeper_san.weather.R;


public class MainPrefFragment extends PreferenceFragment {

    public MainPrefFragment() {
        Log.d("Asdasd", "onCreateView: 构造方法");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.pref_main);

        Log.d("Asdasd", "onCreateView: 到了！");
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    public MainPrefFragment init(){
        return this;
    }


    @Override
    public void onResume() {
        View view = getView();
        if (view!=null){
            View mView = view.findViewById(android.R.id.list);
            if (mView!=null){
                ((ListView)mView).setDivider(null);
                Log.d("Asdasd", "onCreateView: 设置了！");
            }else {
                Log.d("Asdasd", "onCreateView: 空的！空的！");
            }
        }
        super.onResume();
        Log.d("Asdasd", "onCreateView: onResume()!");
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.i("Main","id "+preference.getKey());
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
