package creeper_san.weather.Base;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import creeper_san.weather.SettingActivity;

public abstract class BasePrefFragment extends PreferenceFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addPreferencesFromResource(getXmlID());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view!=null){
            View mView = view.findViewById(android.R.id.list);
            if (mView!=null){
                ((ListView)mView).setDivider(null);
            }
        }
    }

    protected SettingActivity getSettingActivity(){
        return (SettingActivity)getActivity();
    }

    protected void setActivityTitle(String title){
        getSettingActivity().setTitle(title);
    }

    protected abstract @XmlRes int getXmlID();

}
