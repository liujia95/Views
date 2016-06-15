package me.liujia95.views.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/6/11 14:00.
 */
public class _05_View extends View {

    private int mRectCount = 10;
    private int mWidth;
    private int mRectWidth = 50;
    private int mRectHeight = 300;

    private int offset = 15;

    private Paint mPaint;

    public _05_View(Context context) {
        this(context,null);
    }

    public _05_View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public _05_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mRectHeight= getHeight();
        mRectWidth = (int)(mWidth*0.6/mRectCount);
        LinearGradient mLinearGradient = new LinearGradient(0,0,mRectWidth,mRectHeight,
                Color.YELLOW,Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0;i<mRectCount;i++){
            double random = Math.random();
            //每个矩形的高
            float currentHeight = (float) (mRectHeight*random);
            canvas.drawRect((float)(mWidth*0.4/2+mRectWidth*i+offset),
                    currentHeight,(float)(mWidth*0.4/2+mRectWidth*(i+1)),
                    mRectHeight,mPaint);
        }
    }
}
