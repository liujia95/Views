package me.liujia95.views.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/6/11 10:51.
 */
public class _04_View extends View{

    private int mCircleXY;
    private float mRadius;
    private RectF mArcRectF;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private String mShowText;
    private int mShowTextSize;
    private Paint mTextPaint;

    public _04_View(Context context) {
        this(context,null);
    }

    public _04_View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public _04_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();

        //圆心
        mCircleXY = screenWidth/2;
        //圆的半径
        mRadius = (float) (screenWidth*0.5/2);
        //指定圆弧的外轮廓矩形区域
        mArcRectF = new RectF((float)(screenWidth*0.1),(float)(screenWidth*0.1),(float)(screenWidth*0.9),(float)(screenWidth*0.9));

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(50);
        mArcPaint.setColor(Color.GREEN);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);

        mShowText = "哈哈哈哈";
        mShowTextSize = 40;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);
        //绘制弧线(
        //  参数1：指定圆弧的外轮廓矩形区域，
        //  参数2：圆弧起始角度，单位为度
        //  参数3：圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度
        //  参数4：如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        //  参数5：绘制圆弧的画板属性，如颜色，是否填充等。)
        canvas.drawArc(mArcRectF,270,270,false,mArcPaint);
        //绘制文字
        canvas.drawText(mShowText,0,mShowText.length(),mCircleXY,mCircleXY+(mShowTextSize/4),mTextPaint);
    }
}
