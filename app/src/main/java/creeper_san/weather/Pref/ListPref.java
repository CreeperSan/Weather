package creeper_san.weather.Pref;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BasePref;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.R;

public class ListPref extends BasePref {
    @BindView(R.id.prefListSummery)TextView summery;

    private AlertDialog dialog;
    private PrefListAdapter adapter;
    private String key;
    private CharSequence[] titles;
    private CharSequence[] itemDescriptions;
    private CharSequence[] values;
    private String currentValue;
    private String defaultValue;

    public ListPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initValue();
    }

    public ListPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public ListPref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public ListPref(Context context) {
        this(context,null);
    }

    @Override
    protected int[] getAttrID() {
        return R.styleable.ListPref;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        adapter = new PrefListAdapter();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("主题选择");
                RecyclerView recyclerView = new RecyclerView(getContext());
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                builder.setView(recyclerView);
                dialog = builder.create();
                dialog.show();
            }
        });
        summery.setText(titles[Integer.parseInt(currentValue)]);
    }


    private void initValue() {
        if (ConfigHelper.themeHasThemeColor(getContext())){
            currentValue = ConfigHelper.getTheme(getContext());
        }else {
            currentValue = defaultValue;
        }
        adapter = new PrefListAdapter();
        Toast.makeText(getContext(),"default "+currentValue,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.pref_list;
    }

    @Override
    protected void handleAttr(int attr, TypedArray typedArray) {
        switch (attr){
            case R.styleable.ListPref_nameArray:{
                titles = typedArray.getTextArray(attr);
                break;}
            case R.styleable.ListPref_valueArray:{
                values = typedArray.getTextArray(attr);
                break;}
            case R.styleable.ListPref_itemDescription:{
                itemDescriptions = typedArray.getTextArray(attr);
                break;}
            case R.styleable.ListPref_key:
                key = typedArray.getString(attr);
                break;
            case R.styleable.ListPref_defaultValue:
                defaultValue = String.valueOf(typedArray.getInt(attr,0));
                break;
        }
    }

    class PrefListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.prefListItemRadioButton)RadioButton radioButton;
        @BindView(R.id.prefListItemTitle)TextView title;
        @BindView(R.id.prefListItemDescription)TextView description;

        public PrefListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class PrefListAdapter extends RecyclerView.Adapter<PrefListViewHolder>{

        @Override
        public PrefListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PrefListViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.pref_list_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(PrefListViewHolder holder, int position) {
            holder.title.setText(titles[position]);//标题
            if (Integer.valueOf(currentValue) == position){//单选按钮
                holder.radioButton.setChecked(true);
            }else {
                holder.radioButton.setChecked(false);
            }
            if (itemDescriptions == null){//描述
                holder.description.setVisibility(View.GONE);
            }else {
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(itemDescriptions[position]);
            }
        }

        @Override
        public int getItemCount() {
            return titles==null?0:titles.length;
        }
    }

}
