package me.liujia95.views.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    private Paint paintTextBlack;
    private Paint paintTextGray;
    private Paint paintCircle;
    private Paint paintToday;

    private Calendar calendar;
    private Date date;
    private float density;

    //当前选中的数字
    private static int currentSelectedCount;

    static {
        Calendar calendar = Calendar.getInstance();
        currentSelectedCount = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calendar = Calendar.getInstance();
        paintTextBlack = new Paint();
        density = getResources().getDisplayMetrics().density;
        paintTextBlack.setTextSize(16 * density);
        paintTextBlack.setColor(Color.BLACK);

        paintTextGray = new Paint();
        paintTextGray.setTextSize(16 * density);
        paintTextGray.setColor(Color.GRAY);

        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(3);
        paintCircle.setColor(Color.GRAY);

        radius = density * 20;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 重新梳理一遍点击事件的逻辑：
     * 1、点击在本月内部，把currentSelectedCount赋值为当前点击的数字，是为了在滑动下一页的时候定位到这个数字
     * 2、画圈的逻辑，点击本月的时候立马画圈，或者当前
     *
     * @param canvas
     */
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
        boolean isClickToday = false;//是否选中了今天
        boolean isDrawCircle = false;//是否画了圈
        for (int row = 0; row < rowCount; row++) {
            for (int line = 0; line < 7; line++) {
                //判断点击的位置是否在矩阵内部并画圆圈
                int left = line * pieceWidth;
                int top = row * pieceHeight;
                int right = left + pieceWidth;
                int bottom = top + pieceHeight;
                Rect rect = new Rect(left, top, right, bottom);
                //开始画数字
                if (lastCount < lastMonthCount) {
                    lastCount++;
                    drawText(canvas, paintTextGray, String.valueOf(lastCount), line, row, pieceWidth, pieceHeight);
                    if (rect.contains(pointClick.x, pointClick.y) && click) {
                        currentSelectedCount = lastCount;
                        invalidate();
                        listener.clickLastMonth(lastCount);
                    }
                } else if (count < monthCount) {
                    count++;
                    Log.e(TAG, "isDrawCircle:" + isDrawCircle + "  currentSelectedCount:" + currentSelectedCount + "  count:" + count);
                    if ((rect.contains(pointClick.x, pointClick.y) ||
                            (currentSelectedCount == count && !click)) && !isDrawCircle) {
                        currentSelectedCount = count;
                        isDrawCircle = true;
                        if (isToday(count)) {
                            isClickToday = true;
                            paintCircle.setStyle(Paint.Style.FILL);
                            paintCircle.setColor(Color.GREEN);
                        } else {
                            isClickToday = false;
                            paintCircle.setStyle(Paint.Style.STROKE);
                            paintCircle.setColor(Color.GRAY);
                        }
                        canvas.drawCircle(left + pieceWidth / 2, top + pieceHeight / 2, radius, paintCircle);
                    }
                    if (isToday(count)) {
                        paintTextBlack.setColor(isClickToday ? Color.WHITE : Color.GREEN);
                    } else {
                        paintTextBlack.setColor(Color.BLACK);
                    }
                    drawText(canvas, paintTextBlack, String.valueOf(count), line, row, pieceWidth, pieceHeight);
                } else {
                    nextCount++;
                    drawText(canvas, paintTextGray, String.valueOf(nextCount), line, row, pieceWidth, pieceHeight);
                    if (rect.contains(pointClick.x, pointClick.y) && click) {
                        currentSelectedCount = nextCount;
                        invalidate();
                        listener.clickNextMonth(nextCount);
                    }
                }
            }
        }

        //完整走了一遍重绘后关闭点击响应
        click = false;
    }

    private void drawText(Canvas canvas, Paint paint, String text, int line, int row, int pieceWidth, int pieceHeight) {
        //算出字的位置
        float textWidth = paint.measureText(text);
        float textHeight = paint.getTextSize() - 3 * density;
        float x = pieceWidth / 2 - textWidth / 2 + pieceWidth * line;
        float y = pieceHeight / 2 + textHeight / 2 + pieceHeight * row;
        canvas.drawText(text, x, y, paint);
//        canvas.drawRect(x, y - textHeight, x + textWidth, y, paintCircle);
    }

    private boolean isToday(int day) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH) == day) {
            return true;
        }
        return false;
    }


    long downTime;
    float downX;
    float downY;
    float radius;
    Point pointClick = new Point(-1, -1);
    boolean click;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                //要满足3个条件才算是一次有效的点击：点下去的和抬起的范围是否在一个格子内，是否是在1秒内执行完成
                float offsetX = Math.abs(upX - downX);
                float offsetY = Math.abs(upY - downY);
                long offsetTime = System.currentTimeMillis() - downTime;
                if (offsetTime < 1000 && offsetX < 10 && offsetY < 10) {
                    pointClick.set(upX, upY);
                    click = true;
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void clickLastMonth(int day);

        void clickNextMonth(int day);
    }

}
