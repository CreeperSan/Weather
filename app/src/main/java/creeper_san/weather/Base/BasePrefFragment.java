package creeper_san.weather.Base;

import android.preference.PreferenceFragment;

import creeper_san.weather.SettingActivity;

public class BasePrefFragment extends PreferenceFragment {


    protected SettingActivity getSettingActivity(){
        return (SettingActivity)getActivity();
    }

}
