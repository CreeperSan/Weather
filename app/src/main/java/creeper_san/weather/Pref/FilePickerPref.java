package creeper_san.weather.Pref;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import creeper_san.weather.Base.BasePref;
import creeper_san.weather.Helper.ConfigHelper;
import creeper_san.weather.Helper.PermissionHelper;
import creeper_san.weather.R;

public class FilePickerPref extends BasePref {
    @BindView(R.id.prefFilePickerImage)ImageView fileTypeImage;
    @BindView(R.id.prefFilePickerTitle)TextView titleText;
    @BindView(R.id.prefFilePickerPath)TextView pathText;

    private String pathStr;
    private String dialogTitle;
    private String key;

    public FilePickerPref(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public FilePickerPref(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public FilePickerPref(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public FilePickerPref(Context context) {
        this(context,null);
    }

    public String getPathStr() {
        return pathStr;
    }
    public void setPathStr(String pathStr) {
        this.pathStr = pathStr;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        //界面
        titleText.setText(getTitle());
        pathStr = ConfigHelper.settingGetFilePickerPath(getContext(),key);
        if (pathStr==null){
            pathText.setText("尚未设置一个文件");
        }else {
            pathText.setText(pathStr);
        }
        //详细逻辑
        view.setOnClickListener(new View.OnClickListener() {
            private ImageView backImage;
            private TextView pathText;
            private RecyclerView recyclerView;
            private LinkedList<String> pathStack;
            private AlertDialog.Builder builder;

            private File file;
            private FileAdapter adapter;
            private AlertDialog dialog;

            private String makeupFilePath(){
                String path = "";
                for (String pathTemp:pathStack){
                    path =  pathTemp+"/"+path ;
                }
                return path;
            }

            @Override
            public void onClick(View v) {
                //检查权限
                if (PermissionHelper.isApiOverM()){
                    if (!PermissionHelper.hasPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("请赋予我们权限");
                        builder.setMessage("我们需要读取手机文件的权限来获取你手机上的图片");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionHelper.requirePermission(Manifest.permission.READ_EXTERNAL_STORAGE,0);
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        builder.show();
                        return;
                    }
                }
                //初始化
                pathStack = new LinkedList<String>();
                pathStack.push(Environment.getExternalStorageDirectory().getAbsolutePath());
                file = new File(pathStack.peek());
                if (!file.exists()){
                    toast("无法获取到手机文件目录，请重试。");
                    return;
                }
                adapter = new FileAdapter();
                //打开对话框
                builder = new AlertDialog.Builder(getContext());
                if (dialogTitle!=null){
                    builder.setTitle(dialogTitle);
                }
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pref_file_picker,null);
                //内容初始化
                backImage = (ImageView) dialogView.findViewById(R.id.dialogFilePickerPickerBack);
                pathText = (TextView) dialogView.findViewById(R.id.dialogFilePickerPickerPath);
                recyclerView = (RecyclerView) dialogView.findViewById(R.id.dialogFilePickerPickerList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                backImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pathStack.size()<=1){
                            toast("已经到顶啦");
                        }else {
                            pathStack.pop();
                            String path = makeupFilePath();
                            pathText.setText(path);
                            file = new File(path);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                pathText.setText(makeupFilePath());
                builder.setView(dialogView);
                dialog = builder.create();
                dialog.show();
            }

            class FileAdapter extends RecyclerView.Adapter<FileHolder>{

                @Override
                public FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new FileHolder(getViewByLayout(R.layout.item_dialog_pref_file_picker,parent,false));
                }

                @Override
                public void onBindViewHolder(FileHolder holder, int position) {
                    final File tempFile = file.listFiles()[position];
                    holder.titleText.setText(tempFile.getName());
                    String descriptionText = (tempFile.isHidden()?"隐藏 ":"")+(tempFile.isDirectory()?"文件夹 ":"文件 ");
                    holder.descriptionText.setText(descriptionText);
                    if (tempFile.isDirectory()){
                        holder.fileImage.setImageResource(R.drawable.ic_folder_open_black_24dp);
                    }else {
                        holder.fileImage.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
                    }
                    if (tempFile.isHidden()){
                        holder.fileImage.setAlpha(0.5f);
                        holder.descriptionText.setAlpha(0.5f);
                        holder.titleText.setAlpha(0.5f);
                    }else {
                        holder.fileImage.setAlpha(1f);
                        holder.descriptionText.setAlpha(1f);
                        holder.titleText.setAlpha(1f);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tempFile.isDirectory()){
                                pathStack.push(tempFile.getName());
                                String path = makeupFilePath();
                                file = new File(path);
                                pathText.setText(path);
                                adapter.notifyDataSetChanged();
                            }else {
                                dialog.dismiss();
                                onFilePicker(tempFile);
                            }
                        }
                    });
                }

                @Override
                public int getItemCount() {
                    if (file==null){
                        return 0;
                    }else {
                        if (file.listFiles()==null){
                            return 0;
                        }else {
                            return file.listFiles().length;
                        }
                    }
                }
            }
            class FileHolder extends RecyclerView.ViewHolder{
                ImageView fileImage;
                TextView titleText;
                TextView descriptionText;

                public FileHolder(View itemView) {
                    super(itemView);
                    ButterKnife.bind(FileHolder.this,itemView);
                    fileImage = (ImageView) itemView.findViewById(R.id.itemDialogFilePickerImage);
                    titleText = (TextView) itemView.findViewById(R.id.itemDialogFilePickerTitle);
                    descriptionText = (TextView) itemView.findViewById(R.id.itemDialogFilePickerDescription);
                }
            }

        });
    }

    protected void onFilePicker(File file){
        ConfigHelper.settingSetFilePickerPath(getContext(),key,file.getAbsolutePath());
        pathStr = file.getAbsolutePath();
        pathText.setText(pathStr);
        notifyChanged();
    }

    @Override
    protected int[] getAttrID() {
        return R.styleable.FilePickerPref;
    }
    @Override
    protected int getLayoutID() {
        return R.layout.pref_file_picker;
    }

    @Override
    protected void handleAttr(int attr, TypedArray typedArray) {
        switch (attr){
            case R.styleable.FilePickerPref_filePickerKey:
                key = typedArray.getString(attr);break;
            case R.styleable.FilePickerPref_filePickerDialogTitle:
                dialogTitle = typedArray.getString(attr);break;
        }
    }
}
