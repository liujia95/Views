package me.liujia95.views.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

import me.liujia95.views.utils.DateUtils;

/**
 * Author: LiuJia on 2017/11/13 0013 15:07.
 * Email: liujia95me@126.com
 */

public class MonthView extends View {

    private static final String TAG = MonthView.class.getSimpleName();
    private Paint paint;
    private Paint paint2;

    private Calendar calendar;
    private Date date;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calendar = Calendar.getInstance();
        paint = new Paint();
        float density = getResources().getDisplayMetrics().density;
        paint.setTextSize(14 * density);
        paint.setColor(Color.BLACK);

        paint2 = new Paint();
        paint2.setTextSize(14 * density);
        paint2.setColor(Color.GRAY);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();//高度固定
        int width = getWidth();//宽度固定

        int monthCount = DateUtils.getDaysOfMonth(date);
        int weekOfFirstDay = DateUtils.getWeekOfFirstDay(date) - 1;//因为国外周日是第一位所以-1
        int lastMonthCount = DateUtils.getLastMonthToDays(date);
        Log.e(TAG, "monthCount:" + monthCount + " weekOfFirstDay:" + weekOfFirstDay + "  lastMonthCount:" + lastMonthCount);

        int rowCount = (int) Math.ceil((monthCount + weekOfFirstDay) / 7.0f);//(当月数+当月1号对应的周到周日之间的数)/7

        //一个格子的宽度和高度
        int pieceWidth = width / 7;
        int pieceHeight = height / rowCount;
        //计数器，分别是这个月，上个月和下个月的
        int count = 0;
        int lastCount = lastMonthCount - weekOfFirstDay;
        int nextCount = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int line = 0; line < 7; line++) {
                //算出字的位置
                float textWidth = paint.measureText(String.valueOf(count));
                float x = pieceWidth / 2 - textWidth / 2 + pieceWidth * line;
                float y = pieceHeight / 2 + pieceHeight * row;
                //开始画数字
                if (lastCount < lastMonthCount) {
                    lastCount++;
                    canvas.drawText(String.valueOf(lastCount), x, y, paint2);
                } else if (count < monthCount) {
                    count++;
                    canvas.drawText(String.valueOf(count), x, y, paint);
                } else {
                    nextCount++;
                    canvas.drawText(String.valueOf(nextCount), x, y, paint2);
                }
            }
        }
    }
}
