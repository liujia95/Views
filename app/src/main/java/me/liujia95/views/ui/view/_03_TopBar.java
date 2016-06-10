package me.liujia95.views.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.liujia95.views.R;

/**
 * Created by Administrator on 2016/6/10 20:16.
 */
public class _03_TopBar extends RelativeLayout implements View.OnClickListener {

    public static final int RIGHT_BUTTON = 0;
    public static final int LEFT_BUTTON = 1;

    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private String mLeftText1;
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;
    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;

    private OnTopBarClickListener mListener;

    public _03_TopBar(Context context) {
        this(context,null);
    }

    public _03_TopBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public _03_TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将attrs.xml中定义的declare-styleable的所有属性的值存储到TypeArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        mLeftTextColor = ta.getColor(R.styleable.TopBar_mleftTextColor, 0);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_mleftBackground);
        mLeftText = ta.getString(R.styleable.TopBar_mleftText);

        mRightTextColor = ta.getColor(R.styleable.TopBar_mrightTextColor, 0);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_mrightBackground);
        mRightText = ta.getString(R.styleable.TopBar_mrightText);

        mTitleTextSize = ta.getDimension(R.styleable.TopBar_mtitleTextSize, 10);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_mtitleTextColor, 0);
        mTitle = ta.getString(R.styleable.TopBar_mtitle);

        ta.recycle();

        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        Log.d("aaa",mTitleTextSize+"----------");
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        //为组件设置响应的布局元素
        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(ALIGN_PARENT_LEFT,TRUE);
        //添加到ViewGroup
        addView(mLeftButton,leftParams);

        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightParams.addRule(ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightButton,rightParams);

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT,TRUE);
        addView(mTitleView,titleParams);

        mRightButton.setOnClickListener(this);
        mLeftButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(mListener == null){
            return;
        }
        if(v == mRightButton){
            mListener.rightClick();
        }else if(v == mLeftButton){
            mListener.leftClick();
        }
    }

    public void setButtonVisable(int id,boolean isVisable){
        switch(id){
            case RIGHT_BUTTON:
                if(isVisable){
                    mRightButton.setVisibility(VISIBLE);
                }else {
                    mRightButton.setVisibility(GONE);
                }
                break;
            case LEFT_BUTTON:
                if(isVisable){
                    mLeftButton.setVisibility(VISIBLE);
                }else{
                    mLeftButton.setVisibility(GONE);
                }
                break;
        }
    }

    public void setOnTopBarClickListener(OnTopBarClickListener listener){
        mListener = listener;
    }

    public interface OnTopBarClickListener{
        void leftClick();
        void rightClick();
    }

}
