package me.liujia95.views.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/6/12 20:51.
 */
public class _06_ViewGroup extends ViewGroup{

    private int mScreenHeight;
    private Scroller mScroller;

    int mStart;
    int mEnd ;
    int mLastY;

    public _06_ViewGroup(Context context) {
        super(context);
    }

    public _06_ViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public _06_ViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = wm.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i=0;i<count;i++){
            View childView = getChildAt(i);
            //用遍历的方式给子View测量
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        //设置ViewGroup的高度
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        //这里让每个子View都显示完整的一屏
        mlp.height = mScreenHeight*childCount;
        setLayoutParams(mlp);
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            if(child.getVisibility()!=GONE){
                //修改每个子View的top和bottom两个属性，让他们可以依次排列下来
                child.layout(l,i*mScreenHeight,r,(i+1)*mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int)event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                //记录触摸起点
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if(getScrollY()<0){
                    dy = 0;
                }
                if(getScrollY()>getHeight()-mScreenHeight){
                    dy = 0;
                }
                scrollBy(0,dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                //记录触摸终点
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if(dScrollY>0){
                    if(dScrollY<mScreenHeight/3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY);
                    }else{
                        mScroller.startScroll(0,getScrollY(),0,mScreenHeight-dScrollY);
                    }
                }else{
                    if(-dScrollY<mScreenHeight/3){
                        mScroller.startScroll(0,getScrollY(),0,-dScrollY);
                    }else{
                        mScroller.startScroll(0,getScrollY(),0,-mScreenHeight-dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }
    }
}
