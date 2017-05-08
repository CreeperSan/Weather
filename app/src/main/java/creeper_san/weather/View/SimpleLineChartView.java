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
        textSize = array.getInt(R.styleable.SimpleLineChartView_textSize,15);
        drawType = array.getInt(R.styleable.SimpleLineChartView_drawType,TYPE_BEZIER);
        array.recycle();
        //其他数据
        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        //
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i("Click","x"+event.getX()+" y"+event.getY());
                }
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lineDataList==null){
            return;
        }
        if (lineDataList.size()==0){
            return;
        }
        float height = canvas.getHeight();
        float width = canvas.getWidth();
        paint.setTextSize(textSize);
        //第一个以及最后一个数据，计算缩进
        int startTextLength = 0;
        int endTextLength = 0;
        for (LineData lineData:lineDataList){
            int tempStartTextLength = (int) paint.measureText(lineData.getLineData().get(0)+"°")+1;
            int tempEndTextLength = (int) (paint.measureText(lineData.getLineData().get(lineData.getLineData().size()-1)+"°")+1);
            if (tempStartTextLength>startTextLength){
                startTextLength = tempStartTextLength;
            }
            if (tempEndTextLength>endTextLength){
                endTextLength = tempEndTextLength;
            }
        }
        //绘制
        //假设是绘制直线
        paint.setColor(textColor);
        startTextLength = 50;
        endTextLength = 50;
        float stepWidth = (width-startTextLength-endTextLength)/size;
        Log.e("e","width "+width+"  step "+stepWidth+" start "+startTextLength+" end "+endTextLength);
        for (LineData lineData:lineDataList){
            for (int i=0;i<size;i++){
                float value = lineData.getLineData().get(i).getValue();
                Log.i("asd","current "+i+"  size "+size +" value "+(   value    ));
                canvas.drawText(lineData.getLineData().get(i).getValue()+"°",startTextLength+i*stepWidth,50+50*i,paint);
                if (i == 0){
                    continue;
                }
                float prevValue = lineData.getLineData().get(i-1).getValue();
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
        private List<PointData> lineData;
        private String name;

        public LineData() {
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
