package creeper_san.weather;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.CityEditEvent;
import creeper_san.weather.Event.SearchResultEvent;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Item.CityItem;
import creeper_san.weather.Json.SearchJson;

public class AddCityActivity extends BaseActivity implements TextWatcher,ServiceConnection {
    @BindView(R.id.addCityToolbar)Toolbar toolbar;
    @BindView(R.id.addCityEditText)TextInputEditText editText;
    @BindView(R.id.addCityList)RecyclerView recyclerView;
    @BindView(R.id.addCityProgressBar)ProgressBar progressBar;
    @BindView(R.id.addCityNoResult)TextView noResultText;

    private List<CityItem> cityItemList;
    private CityAdapter adapter;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initEditText();
        initRecyclerView();
        initServiceConnection();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }

    private void initServiceConnection() {
        startService(WeatherService.class);
        bindService(WeatherService.class,this);
    }
    private void initRecyclerView() {
        adapter = new CityAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void initEditText() {
        editText.addTextChangedListener(this);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("搜索城市");
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().equals("")){
            return;
        }
        if (weatherService==null){
            toast("服务尚未启动，请稍后");
            return;
        }
        showLoading();
        weatherService.searchCity(s.toString());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchResult(SearchResultEvent event){
        if (event.isSucceed()){
            hideLoading();
            SearchJson item = event.getSearchItem();
            if (item==null){
                showNoResult();
            }else {
                hideNoResult();
                clearList();
                for (int i=0;i<item.size();i++){
                    CityItem cityItem = new CityItem(item.getCity(i),item.getCountry(i),item.getID(i),
                            item.getLat(i), item.getLon(i),item.getProvince(i));
                    cityItemList.add(cityItem);
                    adapter.notifyDataSetChanged();
                }
            }
        }else {
            toast("网络连接失败，请检查你的网络连接");
        }
    }


    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void showNoResult(){
        recyclerView.setVisibility(View.GONE);
        noResultText.setVisibility(View.VISIBLE);
    }
    private void hideNoResult(){
        recyclerView.setVisibility(View.VISIBLE);
        noResultText.setVisibility(View.GONE);
    }
    private void clearList(){
        if (cityItemList==null){
            cityItemList = new ArrayList<>();
        }else {
            cityItemList.clear();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        weatherService = ((WeatherService.WeatherServiceBinder) binder).getService();
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    class CityAdapter extends RecyclerView.Adapter<CityViewHolder>{

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CityViewHolder(inflater.inflate(R.layout.item_add_city_list,parent,false));
        }

        @Override
        public void onBindViewHolder(final CityViewHolder holder, int position) {
            final CityItem item = cityItemList.get(position);
            holder.city.setText(item.getCity());
            holder.country.setText(item.getCnty());
            if (item.getProv()!=null){
                holder.province.setText(item.getProv());
                holder.province.setVisibility(View.VISIBLE);
            }else {
                holder.province.setVisibility(View.GONE);
            }
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CityItem dialogItem = cityItemList.get(holder.getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCityActivity.this);
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
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityItem insertItem = cityItemList.get(holder.getAdapterPosition());
                    if (DatabaseHelper.insertCityItem(AddCityActivity.this,insertItem)){
                        toast("添加城市成功");
                        postEvent(new CityEditEvent(getClass().getSimpleName(), CityEditEvent.TYPE_ADD,insertItem));
                    }else {
                        toast("城市已存在");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return (cityItemList==null)?0:cityItemList.size();
        }
    }
    class CityViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemAddCityListCity)TextView city;
        @BindView(R.id.itemAddCityListCountry)TextView country;
        @BindView(R.id.itemAddCityListProvince)TextView province;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_city;
    }

}
