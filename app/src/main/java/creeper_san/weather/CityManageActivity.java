package creeper_san.weather;

import android.graphics.Color;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.CityEditEvent;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Item.CityItem;
import static creeper_san.weather.Event.CityEditEvent.*;

public class CityManageActivity extends BaseActivity {
    @BindView(R.id.cityManagerRecycler)RecyclerView recyclerView;
    @BindView(R.id.cityManagerToolbar)Toolbar toolbar;
    @BindView(R.id.cityManagerFloatingActionButton)FloatingActionButton fab;
    @BindView(R.id.cityManagerLayout)RelativeLayout relativeLayout;

    private List<CityItem> cityItemList;
    private CityAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    private int source = -1;
    private int target = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initToolbar();
        initRecyclerView();
        initFab();
        initItemTouchHelper();
    }

    private void initItemTouchHelper() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (source==-1){
                    source = viewHolder.getAdapterPosition();
                }
                CityManageActivity.this.target = target.getAdapterPosition();
                Collections.swap(cityItemList,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                //内容写入数据库
                if (source!=target){
                    for (int i=(source>target)?target:source;i<=((source<target)?target:source);i++){
                        CityItem item = cityItemList.get(i);
                        DatabaseHelper.updateCityItemNum(CityManageActivity.this,item.getId(),i);
                    }
                    postEvent(new CityEditEvent(CityManageActivity.this.getClass().getSimpleName(),TYPE_ORDER,null));
                }
                //重置标识为
                source = -1;
                target = -1;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                showSnackBarDelete(cityItemList.get(pos),pos);
                cityItemList.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void initFab(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddCityActivity.class);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for (CityItem item:cityItemList){
                    log(item.getCity());
                }
                return true;
            }
        });
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle("城市管理");
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter();
        recyclerView.setAdapter(adapter);
    }
    private void initData() {
        cityItemList = DatabaseHelper.getCityList(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCityEditEvent(CityEditEvent event){
        if (event.getFrom().equals(CityManageActivity.this.getClass().getSimpleName())){//如果是自己发出的就return
            return;
        }
        if (event.getType() == TYPE_ADD){
            CityItem item = event.getItem();
            cityItemList.add(item);
            adapter.notifyItemInserted(cityItemList.size()-1);
        }else if (event.getType() == TYPE_DELETE){
            for (int i=0;i<cityItemList.size();i++){
                CityItem item = cityItemList.get(i);
                if (item.getId().equals(event.getItem().getId())){
                    cityItemList.remove(item);
                    adapter.notifyItemRemoved(i);
                }
            }
        }else if (event.getType() == TYPE_ORDER){

        }
    }

    private void showSnackBarDelete(final CityItem item, final int pos){
        Snackbar.make(relativeLayout,item.getCity()+"已删除", BaseTransientBottomBar.LENGTH_SHORT).
                setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityItemList.add(pos,item);
                        adapter.notifyItemInserted(pos);
                    }
                }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event!=1){//如果不点击撤销
                    DatabaseHelper.deleteCityItem(CityManageActivity.this,item);
                    postEvent(new CityEditEvent(CityManageActivity.this.getClass().getSimpleName(), TYPE_DELETE,item));
                }
            }
        }).show();
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
