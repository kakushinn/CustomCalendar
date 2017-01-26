package com.example.guochen.customcalendarsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by guochen on 2017/01/24.
 */
public class WeekDayView extends View {
    private Paint mPaint;
    //上横线颜色
    private int mTopLineColor = Color.parseColor("#CCE4F2");
    //下横线颜色
    private int mBottomLineColor = Color.parseColor("#CCE4F2");
    //周一到周五的颜色
    private int mWeedayColor = Color.parseColor("#1FC2F3");
    //周六、周日的颜色
    private int mWeekendColor = Color.parseColor("#fa4451");
    //获取星期的TitleBar
    private String[] weekString = new String[]{"日","一","二","三","四","五","六"};
    //星期的字体大小
    private int mWeekSize = 14;
    //获取屏幕参数
    private DisplayMetrics displayMetrics;
    //Context
    private Context mContext;
    
    public WeekDayView(Context context) {
        super(context);
        mContext = context;
    }

    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        displayMetrics = context.getResources().getDisplayMetrics();
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(heightMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST) {
            widthSize = displayMetrics.densityDpi * 300;
        }

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = displayMetrics.densityDpi * 30;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        //画上横线
        mPaint.setColor(mTopLineColor);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(0,0,width,0,mPaint);

        //画下横线
        mPaint.setColor(mBottomLineColor);
        canvas.drawLine(0, height, width,height,mPaint);

        //写字
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setTextSize(displayMetrics.scaledDensity * mWeekSize);
        int columnWidth = width/7 ;
        for(int i=0;i<weekString.length;i++){
            String text = weekString[i];
            int fontSize = (int)mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontSize)/2;
            int startY = (int) (height/2 - (mPaint.ascent() + mPaint.descent())/2);

            if(text.indexOf("日") > -1|| text.indexOf("六") > -1){
                mPaint.setColor(mWeekendColor);
            }else{
                mPaint.setColor(mWeedayColor);
            }
            canvas.drawText(text,startX,startY,mPaint);
        }
    }
}
