package creeper_san.weather;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Helper.WeatherDatabaseHelper;
import creeper_san.weather.Item.CityItem;

public class CityManageActivity extends BaseActivity {
    @BindView(R.id.cityManagerRecycler)RecyclerView recyclerView;
    @BindView(R.id.cityManagerToolbar)Toolbar toolbar;
    @BindView(R.id.cityManagerFloatingActionButton)FloatingActionButton fab;

    private List<CityItem> cityItemList;
    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initToolbar();
        initRecyclerView();
        initFab();
    }

    private void initFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddCityActivity.class);
            }
        });
    }

    private void initToolbar()      {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("城市管理");
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        cityItemList = WeatherDatabaseHelper.getCityList(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_city_manage;
    }

    private class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CityViewHolder(inflater.inflate(R.layout.item_city_manager_list,parent,false));
        }

        @Override
        public void onBindViewHolder(final CityViewHolder holder, int position) {
            final CityItem item = cityItemList.get(position);
            holder.city.setText(item.getCity());
            holder.country.setText(item.getCnty());
            if (item.getProv()==null){
                holder.province.setVisibility(View.GONE);
            }else {
                if (item.getProv().equals("")){
                    holder.province.setVisibility(View.GONE);
                }else {
                    holder.province.setVisibility(View.VISIBLE);
                }
            }
            holder.province.setText(item.getProv());
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityItem dialogItem = cityItemList.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CityManageActivity.this);
                    builder.setTitle(dialogItem.getCity());
                    StringBuffer buffer = new StringBuffer("");
                    buffer.append("国家:"+item.getCnty());
                    if (item.getProv()!=null){
                        if (!item.getProv().equals("")){
                            buffer.append("\n省份:"+item.getProv());
                        }
                    }
                    buffer.append("\n纬度:"+dialogItem.getLat());
                    buffer.append("\n经度:"+dialogItem.getLon());
                    buffer.append("\nID:"+dialogItem.getId());
                    builder.setMessage(buffer.toString());
                    builder.setPositiveButton("确定",null);
                    builder.show();
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CityItem dialogItem = cityItemList.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CityManageActivity.this);
                    builder.setTitle("确认删除？");
                    builder.setMessage("确认删除 "+dialogItem.getCity()+" 吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            WeatherDatabaseHelper.delete(CityManageActivity.this,cityItemList.get(holder.getAdapterPosition()));
                            cityItemList.remove(holder.getAdapterPosition());
                            adapter.notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return cityItemList==null?0:cityItemList.size();
        }
    }
    class CityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemCityManagerListCity)TextView city;
        @BindView(R.id.itemCityManagerListCountry)TextView country;
        @BindView(R.id.itemCityManagerListProvince)TextView province;
        @BindView(R.id.itemCityManagerListInfo)ImageView info;

        private CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
