package me.liujia95.views.hexagon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: LiuJia on 2017/11/10 0010 11:55.
 * Email: liujia95me@126.com
 */

public class HexagonView extends View {
    int centerX;
    int centerY;
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    int multiple = 200;
    private SweepGradient shader;
    private Matrix matrix;
    private boolean first;
    int degress;

    public HexagonView(Context context) {
        this(context, null);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new CornerPathEffect(20));

        paint2.setStrokeWidth(10);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setPathEffect(new CornerPathEffect(20));
        paint2.setStrokeCap(Paint.Cap.ROUND);
        shader = new SweepGradient(0, 0, new int[]{Color.parseColor("#00FFFFFF"), Color.parseColor("#00FFFFFF"), Color.parseColor("#FF000000")}, new float[]{0, 0.5f, 1});
        matrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        matrix = new Matrix();
        matrix.setRotate(180, 0, 0);
        shader.setLocalMatrix(matrix);
        canvas.translate(centerX, centerY);
        canvas.drawPath(getHexagonPath(multiple), paint);

        matrix.setRotate(degress, 0, 0);
        shader.setLocalMatrix(matrix);
        paint2.setShader(shader);
        canvas.drawPath(getHexagonPath(multiple + 20), paint2);
    }

    public void setDegress(int degress) {
        this.degress = degress;
        invalidate();
    }

    public int getDegress() {
        return degress;
    }

    private Path getHexagonPath(int multiple) {
        Path path = new Path();
        path.moveTo(-2 * multiple, 0);
        path.lineTo(-1 * multiple, (float) -Math.sqrt(3) * multiple);
        path.lineTo(1 * multiple, (float) -Math.sqrt(3) * multiple);
        path.lineTo(2 * multiple, 0);
        path.lineTo(1 * multiple, (float) Math.sqrt(3) * multiple);
        path.lineTo(-1 * multiple, (float) Math.sqrt(3) * multiple);
        path.close();
        return path;
    }
}
