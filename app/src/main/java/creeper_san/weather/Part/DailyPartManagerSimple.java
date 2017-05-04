package creeper_san.weather.Part;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import creeper_san.weather.Base.BaseDailyPartManager;
import creeper_san.weather.Json.WeatherItem;
import creeper_san.weather.R;

public class DailyPartManagerSimple extends BaseDailyPartManager {
//    @BindView(R.id.partDailySuitLines)SuitLines suitLines;

    public DailyPartManagerSimple(LayoutInflater inflater, ViewGroup container) {
        super(inflater, container);
    }

    @Override
    public void setData(WeatherItem weatherItem,int which) {

    }

    @Override
    protected void initView(View partRootView, ViewGroup container) {
//        List<Unit> lines = new ArrayList<>();
//        for (int i = 0; i < 14; i++) {
//            lines.add(new Unit(new SecureRandom().nextInt(48), i + ""));
//        }
//        log("kong "+(suitLines==null));
//        suitLines.feedWithAnim(lines);
    }

    @Override
    protected int getLayout() {
        return R.layout.part_daily_simple;
    }
}
