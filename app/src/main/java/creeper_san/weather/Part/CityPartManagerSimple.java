package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import creeper_san.weather.Base.BaseCityPartManager;
import creeper_san.weather.R;

public class CityPartManagerSimple extends BaseCityPartManager {
    @BindView(R.id.partCityCityTxt)TextView cityText;
    @BindView(R.id.partCityCntyTxt)TextView cntyText;
    @BindView(R.id.partCityIdTxt)TextView idText;
    @BindView(R.id.partCityLatTxt)TextView latTxt;
    @BindView(R.id.partCityLonTxt)TextView lonTxt;

    public CityPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setCity(String content) {
        cityText.setText(content);
    }

    @Override
    public void setCnty(String content) {
        cntyText.setText(content);
    }

    @Override
    public void setID(String content) {
        idText.setText(content);
    }

    @Override
    public void setLat(String content) {
        latTxt.setText(content);
    }

    @Override
    public void setLon(String content) {
        lonTxt.setText(content);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_city_simple;
    }

    @Override
    protected void setEmpty() {

    }
}
