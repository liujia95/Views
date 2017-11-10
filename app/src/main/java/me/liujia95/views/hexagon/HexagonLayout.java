package me.liujia95.views.hexagon;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import me.liujia95.views.R;


/**
 * Author: LiuJia on 2017/11/10 0010 15:35.
 * Email: liujia95me@126.com
 */

public class HexagonLayout extends RelativeLayout {

    public HexagonLayout(Context context) {
        this(context, null);
    }

    public HexagonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HexagonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HexagonView hexagonLayout = (HexagonView) findViewById(R.id.view1);
        ObjectAnimator animator = ObjectAnimator.ofInt(hexagonLayout, "degress", 0, 360);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ObjectAnimator.INFINITE);
        animator.start();
    }
}
