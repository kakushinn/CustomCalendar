package com.example.guochen.customcalendarsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Administrator on 2017/1/28.
 */
public class CalendarView extends View {
    private Context mContext;
    private int mColumn = 7;
    private int mRow = 6;
    private Paint mPaint;
    private int mCurrYear,mCurrMonth,mCurrDay;
    private DisplayMetrics metrics;
    private int[][] dayStrings;
    private int mColumnSize;
    private int mRowSize;
    private int daySize = 18;
    private int selYear,selMonth;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        mPaint = new Paint();
        metrics = context.getResources().getDisplayMetrics();
        mCurrYear = calendar.get(java.util.Calendar.YEAR);
        mCurrMonth = calendar.get(java.util.Calendar.MONTH);
        mCurrDay = calendar.get(java.util.Calendar.DATE);
        dayStrings = new int[6][7];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST)
        {
            widthSize = metrics.densityDpi * 300;
        }

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = metrics.densityDpi * 200;
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(metrics.scaledDensity * daySize);
        String dayString;
        int mDaysOfMonth = getMonthDays(selYear,selMonth);
        int mWeekOfFirstDayInMonth = getFirstDayWeek(selYear,selMonth);
        for(int day = 1; day < mDaysOfMonth ; day++){
            dayString = day + "";
            int column = (day + mWeekOfFirstDayInMonth - 2)%7;
            int row = (day + mWeekOfFirstDayInMonth -2)/7;
            dayStrings[row][column] = day;
            int startX = (column * mColumnSize + (mColumnSize - (int)(mPaint.measureText(dayString)))/2);
            int startY = (row * mRowSize + mRowSize/2 - (int)(mPaint.ascent() + mPaint.descent())/2);

            int a = java.util.Calendar.DAY_OF_MONTH;
            if(day == mCurrDay)
            {
                mPaint.setColor(Color.parseColor("#ff0000"));
            }
            else{
                mPaint.setColor(Color.BLACK);
            }

            canvas.drawText(dayString,startX,startY,mPaint);
        }
    }

    private void initSize()
    {
        mColumnSize = getWidth() / mColumn;
        mRowSize = getHeight() / mRow;
    }

    private int getMonthDays(int year, int month){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DATE, calendar.getActualMaximum(java.util.Calendar.DATE));
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    private int getFirstDayWeek(int year, int month){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DAY_OF_MONTH,1);
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }
}
