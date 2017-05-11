package creeper_san.weather;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import creeper_san.weather.Base.BaseActivity;

public class PartManageActivity extends BaseActivity {
    @BindView(R.id.partManageToolbar)Toolbar toolbar;
    @BindView(R.id.partManageRecyclerView)RecyclerView recyclerView;
    @BindView(R.id.partManageFloatingActionButton)FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_part_manage;
    }
}
