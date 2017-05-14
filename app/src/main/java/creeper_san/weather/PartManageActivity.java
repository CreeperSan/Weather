package creeper_san.weather;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Event.PartEditEvent;
import creeper_san.weather.Helper.DatabaseHelper;
import creeper_san.weather.Helper.ResHelper;
import creeper_san.weather.Item.PartItem;

import static creeper_san.weather.Flag.PartCode.*;

public class PartManageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.partManageToolbar)Toolbar toolbar;
    @BindView(R.id.partManageRecyclerView)RecyclerView recyclerView;
    @BindView(R.id.partManageFloatingActionButton)FloatingActionButton fab;

    private final static int TYPES[] = {PART_HEAD,PART_AQI,PART_DAILY,PART_WIND,PART_SUGGESTION,
            PART_CITY,PART_OTHER};
    private List<PartItem> partItemList;
    private PartAdapter adapter;
    private int source = -1;
    private int target = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partItemList = DatabaseHelper.getPartItemList(this);
        initToolbar();
        initRecyclerView();
        initFloatingActionButton();
        initItemTouchHelper();
    }

    private void initItemTouchHelper() {
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (source==-1){
                    source = viewHolder.getAdapterPosition();
                }
                if (PartManageActivity.this.target == target.getAdapterPosition()){
                    return false;
                }
                PartManageActivity.this.target = target.getAdapterPosition();
                int sourcePos = viewHolder.getAdapterPosition();
                int endPos = target.getAdapterPosition();
                int sourceType = partItemList.get(sourcePos).getType();
                int endType = partItemList.get(endPos).getType();
                partItemList.get(sourcePos).setType(endType);
                partItemList.get(endPos).setType(sourceType);
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                if (sourcePos!=-1 && endPos!=-1){
//                    logw("数据库已更新，交换的2个为 "+source+"  "+target);
                    DatabaseHelper.updatePartItemType(PartManageActivity.this,partItemList.get(sourcePos));
                    DatabaseHelper.updatePartItemType(PartManageActivity.this,partItemList.get(endPos));
                    notifyPartChange(PartEditEvent.EDIT_REORDER);
                }
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                DatabaseHelper.deletePartItem(PartManageActivity.this,partItemList.get(viewHolder.getAdapterPosition()));
                partItemList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                notifyPartChange(PartEditEvent.EDIT_REMOVE);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    source = -1;
                    target = -1;
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void notifyPartChange(int part){
        partItemList = DatabaseHelper.getPartItemList(this);
        postEvent(new PartEditEvent(part));
    }

    private void initToolbar() {
        setTitle("天气部件管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initFloatingActionButton() {
        fab.setOnClickListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PartAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_part_manage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Float Action Button的点击事件
    @Override
    public void onClick(View v) {
        String[] titles = new String[TYPES.length];
        for (int i=0;i<TYPES.length;i++){
            titles[i] = ResHelper.getPartNameFromCode(this,TYPES[i]);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择你想要添加的部件");
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                //检查是否存在
                for (PartItem partItem:partItemList){
                    if (partItem.getType() == TYPES[which]){
                        builder.setMessage("该项已经存在于其中，确认添加吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int confirmWhich) {
                                addItem(which);
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        builder.show();
                        return;
                    }
                }
                addItem(which);
            }
        });
        builder.show();
    }

    private void addItem(int type){
        PartItem partItem = new PartItem(PartManageActivity.this,TYPES[type]);
        partItem.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        partItemList.add(partItem);
        adapter.notifyItemInserted(partItemList.size()-1);
        DatabaseHelper.insertPartItem(this,partItem);
        notifyPartChange(PartEditEvent.EDIT_ADD);
    }

    private class PartAdapter extends RecyclerView.Adapter<PartViewHolder>{
        @Override
        public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PartViewHolder(getLayoutInflater().inflate(R.layout.item_part_manager_list,parent,false));
        }
        @Override
        public void onBindViewHolder(final PartViewHolder holder, int position) {
            PartItem partItem = partItemList.get(position);
            holder.textView.setText(partItem.getName());
            switch (partItem.getType()){
                case PART_HEAD:
                    holder.imageView.setImageResource(R.drawable.ic_part_header);break;
                case PART_AQI:
                    holder.imageView.setImageResource(R.drawable.ic_part_aqi);break;
                case PART_DAILY:
                    holder.imageView.setImageResource(R.drawable.ic_part_daily);break;
                case PART_WIND:
                    holder.imageView.setImageResource(R.drawable.ic_part_wind);break;
                case PART_SUGGESTION:
                    holder.imageView.setImageResource(R.drawable.ic_part_suggestion);break;
                case PART_OTHER:
                    holder.imageView.setImageResource(R.drawable.ic_part_other);break;
                case PART_CITY:
                    holder.imageView.setImageResource(R.drawable.ic_part_city);break;
                default:
                    holder.imageView.setImageResource(R.drawable.ic_unknown);break;
            }
        }
        @Override
        public int getItemCount() {
            return partItemList==null?0:partItemList.size();
        }
    }
    class PartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemPartManagerTypeImage)ImageView imageView;
        @BindView(R.id.itemPartManagerTypeName)TextView textView;

        public PartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
