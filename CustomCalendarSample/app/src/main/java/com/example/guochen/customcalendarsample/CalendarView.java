package com.example.guochen.customcalendarsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/28.
 */
public class CalendarView extends View {
    private Context mContext;
    private int mColumn = 7;
    private int mRow = 6;
    private Paint mPaint;
    private int mCurrYear,mCurrMonth,mCurrDay;
    private int selectedYear,selectedMonth,selectedDay;
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
        selectedYear = mCurrYear;
        mCurrMonth = calendar.get(java.util.Calendar.MONTH);
        selectedMonth = mCurrMonth;
        mCurrDay = calendar.get(java.util.Calendar.DATE);
        selectedDay = mCurrDay;
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
        int mDaysOfMonth = getMonthDays(selYear, selMonth);
        int mWeekOfFirstDayInMonth = getFirstDayWeek(selYear,selMonth);
        Toast.makeText(mContext,selectedYear+"/"+(selectedMonth+1)+"/"+selectedDay,Toast.LENGTH_SHORT).show();
        for(int day = 1; day <= mDaysOfMonth ; day++){
            dayString = day + "";
            int column = (day + mWeekOfFirstDayInMonth - 2)%7;
            int row = (day + mWeekOfFirstDayInMonth -2)/7;
            dayStrings[row][column] = day;
            int startX = (column * mColumnSize + (mColumnSize - (int)(mPaint.measureText(dayString)))/2);
            int startY = (row * mRowSize + mRowSize/2 - (int)(mPaint.ascent() + mPaint.descent())/2);

            int a = java.util.Calendar.DAY_OF_MONTH;
            if(day == selectedDay)
            {

                int startRecX = column * mColumnSize;
                int startRecY = row * mRowSize;
                int endRecX = (column + 1) * mColumnSize;
                int endRecY = (row + 1) * mRowSize;
                mPaint.setColor(Color.parseColor("#1fc2f3"));
                canvas.drawRect(startRecX,startRecY,endRecX,endRecY,mPaint);
                mPaint.setColor(Color.parseColor("#ff0000"));
            }
            else{
                mPaint.setColor(Color.BLACK);
            }

            canvas.drawText(dayString,startX,startY,mPaint);
        }
    }

    private void updateSelectDay(int clickX, int clickY){
        int column = clickX/mColumnSize;
        int row = clickY/mRowSize;
        selectedDay = dayStrings[row][column];
    }

    private void updateCurrentDate(int oneMonth){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(selectedYear,selectedMonth,1);
        calendar.add(java.util.Calendar.MONTH, oneMonth);
        selectedYear = calendar.get(java.util.Calendar.YEAR);
        selYear = selectedYear;
        selectedMonth = calendar.get(java.util.Calendar.MONTH);
        selMonth = selectedMonth;
        selectedDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    int downX = 0;
    int downY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX =(int) event.getX();
                downY =(int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int)event.getX();
                int upY = (int)event.getY();
                if(Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10){
                    updateSelectDay((upX + downX)/2,(upY + downY)/2);
                    invalidate();
                }else if(upX - downX > 30)
                {
                    updateCurrentDate(-1);
                    invalidate();
                }else if(upX - downX < -30) {
                    updateCurrentDate(1);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void initSize()
    {
        mColumnSize = getWidth() / mColumn;
        mRowSize = getHeight() / mRow;
    }

    private int getMonthDays(int year, int month){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if(year == 0 || month == 0){
            calendar.set(java.util.Calendar.DATE, calendar.getActualMaximum(java.util.Calendar.DATE));
        }else{
            calendar.set(year,month,1);
            calendar.set(java.util.Calendar.DATE,calendar.getActualMaximum(java.util.Calendar.DATE));
        }
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    private int getFirstDayWeek(int year, int month){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if(year == 0 || month == 0){
            calendar.set(java.util.Calendar.DAY_OF_MONTH,1);
        }else{
            calendar.set(year,month,1);
            selYear = 0;
            selMonth = 0;
        }
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }
}
