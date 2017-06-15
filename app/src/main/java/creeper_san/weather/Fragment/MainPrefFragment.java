package creeper_san.weather.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
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
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.IntroActivity;
import creeper_san.weather.Pref.ListPref;
import creeper_san.weather.R;
import creeper_san.weather.VersionHistoryActivity;


public class MainPrefFragment extends BasePrefFragment {

    @Override
    protected int getXmlID() {
        return R.xml.pref_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(ConfigHelper.PREF_SETTING);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key == null){
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
        if (key.equals(getString(R.string.prefMainPartHeader))){//天气概况按下了
            getSettingActivity().addNewPrefFragment(new HeaderPrefFragment());
            setActivityTitle("天气概况设置");
        }else if (key.equals(getString(R.string.prefMainPartAQI))){//空气质量按下了
            getSettingActivity().addNewPrefFragment(new AQIPrefFragment());
            setActivityTitle("空气质量设置");
        }else if (key.equals(getString(R.string.prefMainPartDaily))){//天气预报按下了
            getSettingActivity().addNewPrefFragment(new DailyPrefFragment());
            setActivityTitle("天气预报设置");
        }else if (key.equals(getString(R.string.prefMainPartSuggestion))){//建议按下了
            getSettingActivity().addNewPrefFragment(new SuggestionPrefFragment());
            setActivityTitle("生活建议设置");
        }else if (key.equals(getString(R.string.prefMainPartWind))){//风向信息按下了
            getSettingActivity().addNewPrefFragment(new WindPrefFragment());
            setActivityTitle("风向信息设置");
        }else if (key.equals(getString(R.string.prefMainPartCity))){//城市信息按下了
            getSettingActivity().addNewPrefFragment(new CityPrefFragment());
            setActivityTitle("城市信息设置");
        }else if (key.equals(getString(R.string.prefMainPartOther))){//其他信息按下了
            getSettingActivity().addNewPrefFragment(new OtherPrefFragment());
            setActivityTitle("其他信息设置");
        }else if (key.equals(getString(R.string.prefMainPartBackground))){//背景信息按下了
            getSettingActivity().addNewPrefFragment(new BackgroundPrefFragment());
            setActivityTitle("天气背景设置");
        }else if (key.equals(getString(R.string.prefMainVersionHistory))){//历史版本按下了
            startActivity(new Intent(getActivity(), VersionHistoryActivity.class));
        }else if (key.equals(getString(R.string.prefMainVersionInfo))){//当前版本说明按下了
            startActivity(new Intent(getActivity(),IntroActivity.class));
        }else if (key.equals(getString(R.string.prefMainCheckUpdate))){//检查更新按下了
            EventBus.getDefault().post(new UpdateRequestEvent(UpdateRequestEvent.TYPE_CHECK_UPDATE));
            Toast.makeText(getActivity(), "检查更新", Toast.LENGTH_SHORT).show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
