package creeper_san.weather.Pref;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BasePref;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.R;

public class ListPref extends BasePref {
    @BindView(R.id.prefListSummery)
    TextView summery;
    @BindView(R.id.prefListTitle)
    TextView title;

    private CharSequence[] names;
    private CharSequence[] values;
    private CharSequence[] descriptions;
    private String key;
    private String defaultValue;
    private String currentValue;
    private String dialogTitle;
    private String eventName;

    private AlertDialog dialog;


    public ListPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initValue();
    }

    public ListPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListPref(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListPref(Context context) {
        this(context, null);
    }

    @Override
    protected int[] getAttrID() {
        return R.styleable.ListPref;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        title.setText(getTitle());
        summery.setText(names[Integer.parseInt(currentValue)]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (dialogTitle != null) {
                    builder.setTitle(dialogTitle);
                }
                RecyclerView recyclerView = new RecyclerView(getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new PrefListAdapter());
                builder.setView(recyclerView);
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void initValue() {
        if (ConfigHelper.settingHasKey(getContext(), key)) {
            currentValue = ConfigHelper.settingGetValue(getContext(), key, defaultValue);
        } else {
            currentValue = defaultValue;
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.pref_list;
    }

    @Override
    protected void handleAttr(int attr, TypedArray typedArray) {
        switch (attr) {
            case R.styleable.ListPref_nameArray: {
                names = typedArray.getTextArray(attr);
                break;
            }
            case R.styleable.ListPref_valueArray: {
                values = typedArray.getTextArray(attr);
                break;
            }
            case R.styleable.ListPref_descriptionArray: {
                descriptions = typedArray.getTextArray(attr);
                break;
            }
            case R.styleable.ListPref_key:
                key = typedArray.getString(attr);
                break;
            case R.styleable.ListPref_defaultValue:
                defaultValue = typedArray.getString(attr);
                break;
            case R.styleable.ListPref_dialogTitle:
                dialogTitle = typedArray.getString(attr);
                break;
            case R.styleable.ListPref_event:
                eventName = typedArray.getString(attr);
                break;
        }
    }

    class PrefListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.prefListItemRadioButton)
        RadioButton radioButton;
        @BindView(R.id.prefListItemTitle)
        TextView title;
        @BindView(R.id.prefListItemDescription)
        TextView description;

        public PrefListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PrefListAdapter extends RecyclerView.Adapter<PrefListViewHolder> {

        @Override
        public PrefListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PrefListViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.pref_list_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final PrefListViewHolder holder, int position) {
            if (currentValue.equals(values[position])) {
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
            }
            holder.title.setText(names[position]);
            if (descriptions == null) {
                holder.description.setVisibility(View.GONE);
            } else {
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(names[position]);
            }
            //点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    dialog.dismiss();
                    if (pos == Integer.valueOf(currentValue)){//如果没做出改变
                        return;
                    }//如果做出改变了
                    ConfigHelper.settingSetThemeValue(getContext(), key, values[pos].toString());
                    if (eventName!=null){
                        try {
                            Class cls = Class.forName("creeper_san.weather.Event."+eventName);
                            Class[] parameterType = {String.class,String.class,String.class};
                            Constructor constructor = cls.getConstructor(parameterType);
                            Object[] parameters = {values[pos].toString(),currentValue,key};
                            EventBus.getDefault().post(constructor.newInstance(parameters));
                        } catch (ClassNotFoundException e) {
                            throw new IllegalStateException("类 "+eventName+" 不存在");
                        } catch (NoSuchMethodException e) {
                            throw new IllegalStateException("类的3个String参数的构造方法不存在");
                        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            throw new IllegalStateException("构造方法执行出错");
                        }
                    }
                    currentValue = values[pos].toString();
                    notifyChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return names.length;
        }
    }
}

