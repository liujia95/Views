package me.liujia95.views.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/10 12:02.
 */
public class _01_View extends TextView{

    private Paint mPaint1;
    private Paint mPaint2;

    public _01_View(Context context) {
        this(context,null);
    }

    public _01_View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public _01_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);// 设置风格为实心

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint1);
        //绘制内层矩形
        canvas.drawRect(10,10,getMeasuredWidth()-10,getMeasuredHeight()-10,mPaint2);
        canvas.save();
        //绘制文字前平移10像素
        canvas.translate(10,10);
        //在回调父方法前，实现自己的逻辑，对TextView来说就是在绘制文本内容前
        super.onDraw(canvas);
        canvas.restore();
    }
}
