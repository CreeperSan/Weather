package creeper_san.weather.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creeper_san.weather.Base.BasePrefFragment;
import creeper_san.weather.R;

/**
 * Created by CreeperSan on 2017/5/20.
 */

public class HeaderPrefFragment extends BasePrefFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.pref_header);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
