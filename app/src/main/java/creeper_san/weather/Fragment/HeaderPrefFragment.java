package creeper_san.weather.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import creeper_san.weather.Base.BasePrefFragment;
import creeper_san.weather.R;


public class HeaderPrefFragment extends BasePrefFragment {

    @Override
    protected int getXmlID() {
        return R.xml.pref_header;
    }
}
