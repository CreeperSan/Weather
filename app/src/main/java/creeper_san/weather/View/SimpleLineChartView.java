package creeper_san.weather.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import creeper_san.weather.R;

public class SimpleLineChartView extends View {
    public final static int TYPE_LINE = 1;
    public final static int TYPE_BEZIER = 2;

    private List<LineData> lineDataList;
    private int drawType;
    private @ColorInt int textColor;
    private @ColorInt int lineColor;
    private int textSize;
    private Paint paint;

    private float max = Float.MIN_VALUE;
    private float min = Float.MAX_VALUE;
    private int size = Integer.MAX_VALUE;

    public SimpleLineChartView(Context context) {
        this(context,null);
    }
    public SimpleLineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SimpleLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SimpleLineChartView,defStyleAttr,0);
        textColor = array.getColor(R.styleable.SimpleLineChartView_textColor, Color.WHITE);
        lineColor = array.getColor(R.styleable.SimpleLineChartView_lineColor,Color.WHITE);
        textSize = array.getDimensionPixelSize(R.styleable.SimpleLineChartView_textSize,15);
        drawType = array.getInt(R.styleable.SimpleLineChartView_drawType,TYPE_BEZIER);
        array.recycle();
        //其他数据
        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lineDataList==null){
            canvas.drawText("Not Support In This Area",canvas.getWidth()/2,canvas.getHeight()/2,paint);
            return;
        }
        if (lineDataList.size()<=1 | size<=1){
            canvas.drawText("Not Support In This Area",canvas.getWidth()/2,canvas.getHeight()/2,paint);
            return;
        }
        float height = canvas.getHeight();
        float width = canvas.getWidth();
        paint.setTextSize(textSize);
        //绘制
        //假设是绘制直线
        paint.setColor(textColor);
        int startTextLength = textSize*5;
        float stepWidth = (width-startTextLength*2)/(size-1);
//        Log.e("e","width "+width+"  step "+stepWidth+" start "+startTextLength+" end "+endTextLength);
        for (LineData lineData:lineDataList){
            int gravity = lineData.getGravityValue();
            for (int i=0;i<size;i++){
                float value = lineData.getLineData().get(i).getValue();
//                Log.i("asd","current "+i+"  size "+size +" value "+(   value    ));

                //绘制文字相对曲线的方向
                if (gravity==LineData.GRAVITY_TOP){
                    canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f+textSize*1.3f),paint);
                }else if (gravity==LineData.GRAVITY_BOTTOM){
                    canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f-textSize*2.3f),paint);
                }else{
                    if (Math.random()>0.5){
                        canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f-textSize*2.3f),paint);
                    }else {
                        canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f+textSize*1.3f),paint);
                    }
                }
//                canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f-textSize),paint);
                if (i == 0){
                    continue;
                }
                float prevValue = lineData.getLineData().get(i-1).getValue();
                //成功的画线方法
                canvas.drawLine(startTextLength+(i-1)*stepWidth,//x
                        height - ((((prevValue-min)/(max-min))*height*0.8f)+height*0.1f),//y
                        startTextLength+i*stepWidth,//x
                        height - ((((value-min)/(max-min))*height*0.8f)+height*0.1f),//y
                        paint);
            }
        }
    }

    public void setData(LineData lineData){
        List<LineData> lineDataList = new ArrayList<>();
        lineDataList.add(lineData);
        setData(lineDataList);
    }
    public void setData(List<LineData> lineDataList){
        this.lineDataList = lineDataList;
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        size = Integer.MAX_VALUE;
        for (LineData lineData:lineDataList){
            for (PointData pointData:lineData.getLineData()){
                if (pointData.getValue()>max){
                    max = pointData.getValue();
                }
                if (pointData.getValue()<min){
                    min = pointData.getValue();
                }
            }
            if (size > lineData.getLineData().size()){
                size = lineData.getLineData().size();
            }
        }
        notifyDataChange();
    }
    public void setType(int type){
        drawType = type;
    }

    public void notifyDataChange(){
        invalidate();
    }

    public static class PointData{
        private float value;

        public PointData() {
        }
        public PointData(float value) {
            this.value = value;
        }

        public float getValue() {
            return value;
        }
        public void setValue(float value) {
            this.value = value;
        }
    }
    public static class LineData{
        public final static int GRAVITY_TOP = 0;
        public final static int GRAVITY_BOTTOM = 1;
        public final static int GRAVITY_RANDOM = 2;

        private List<PointData> lineData;
        private int gravityValue = GRAVITY_TOP;
        private String name;

        public LineData() {
        }

        public LineData setGravityValue(int gravityValue) {
            this.gravityValue = gravityValue;
            return this;
        }

        public int getGravityValue() {
            return gravityValue;
        }

        public LineData(List<PointData> lineData, int gravityValue) {
            this.lineData = lineData;
            this.gravityValue = gravityValue;
        }

        public LineData(List<PointData> lineData) {
            this.lineData = lineData;
        }

        public List<PointData> getLineData() {
            return lineData;
        }

        public void setLineData(List<PointData> lineData) {
            this.lineData = lineData;
        }

        public LineData(List<PointData> lineData, String name) {
            this.lineData = lineData;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
