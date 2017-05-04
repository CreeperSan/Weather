package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Application.WeatherApplication;
import creeper_san.weather.Base.BaseOtherPartManager;
import creeper_san.weather.R;

public class OtherPartManagerSimple extends BaseOtherPartManager {
    @BindView(R.id.partOtherHumTxt)TextView humText;
    @BindView(R.id.partOtherPcpnTxt)TextView pcpnText;
    @BindView(R.id.partOtherPresTxt)TextView presText;
    @BindView(R.id.partOtherVisTxt)TextView visText;

    public OtherPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setHum(String content) {
        humText.setText(content+WeatherApplication.getContext().getString(R.string.unitHum));
    }

    @Override
    public void setPcpn(String content) {
        pcpnText.setText(content+WeatherApplication.getContext().getString(R.string.unitPcpn));
    }

    @Override
    public void setPres(String content) {
        presText.setText(content+WeatherApplication.getContext().getString(R.string.unitPres));
    }

    @Override
    public void setVis(String content) {
        visText.setText(content+ WeatherApplication.getContext().getString(R.string.unitVis));
    }

    @Override
    protected int getLayout() {
        return R.layout.part_other_simple;
    }
}
