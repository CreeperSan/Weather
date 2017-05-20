package creeper_san.weather.Fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import creeper_san.weather.Base.BasePrefFragment;
import creeper_san.weather.Event.UpdateRequestEvent;
import creeper_san.weather.R;


public class MainPrefFragment extends BasePrefFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.pref_main);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        View view = getView();
        if (view!=null){
            View mView = view.findViewById(android.R.id.list);
            if (mView!=null){
                ((ListView)mView).setDivider(null);
            }
        }
        super.onResume();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(getString(R.string.prefMainCheckUpdate))){//假如检查更新按下了
            EventBus.getDefault().post(new UpdateRequestEvent(UpdateRequestEvent.TYPE_CHECK_UPDATE));
            Toast.makeText(getActivity(), "检查更新", Toast.LENGTH_SHORT).show();
        }else if (preference.getKey().equals(getString(R.string.prefMainPartHeader))){//天气概况按下了
            getSettingActivity().addNewPrefFragment(new HeaderPrefFragment());
        }
        Log.i("Main","id "+preference.getKey());
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
