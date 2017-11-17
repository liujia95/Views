package me.liujia95.views.calendar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import me.liujia95.views.utils.ViewUtils;

/**
 * Author: LiuJia on 2017/11/13 0013 14:47.
 * Email: liujia95me@126.com
 */

public class CalendarLayout extends LinearLayout {
    private static final String TAG = CalendarLayout.class.getSimpleName();
    private CalendarPagerAdapter adapter;
    Calendar calendar = Calendar.getInstance();
    int flagCount = Integer.MAX_VALUE / 2;
    int minVpHeight;
    int maxVpHeight;
    private ViewPager viewPager;
    private float density;
    private RecyclerView recyclerView;


    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        viewPager = new ViewPager(context);
        density = getResources().getDisplayMetrics().density;
        minVpHeight = (int) (density * 60);
        maxVpHeight = (int) (density * 300);
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxVpHeight));

        adapter = new CalendarPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        addView(viewPager);

        adapter.setOnClickListener(new MonthView.OnClickListener() {
            @Override
            public void clickLastMonth(int day) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }

            @Override
            public void clickNextMonth(int day) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int offset = position - flagCount;
                calendar.add(Calendar.MONTH, offset);
                MonthView monthView = (MonthView) viewPager.findViewWithTag(position);
                monthView.setCurrentSelectedCount();
                Log.e(TAG, "position:" + position + " offset:" + offset + "  selectedCount:" + MonthView.currentSelectedCount);
                Log.e(TAG, calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                flagCount = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //todo:测试用，待删
        recyclerView = new RecyclerView(context);
        recyclerView.setBackgroundColor(Color.RED);
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyViewHolder) holder).loadData(position + "");
            }

            @Override
            public int getItemCount() {
                return 100;
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv1;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv1 = (TextView) itemView.findViewById(android.R.id.text1);
                }

                public void loadData(String s) {
                    tv1.setText(s);
                }
            }
        });
    }

    /**
     * 外部传进来RecyclerView
     *
     * @param recyclerView
     */
    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.setBackgroundColor(Color.RED);
        addView(recyclerView);
    }

    //todo(高亮)------------------------ 以下为滑动相关 ------------------------------

    float downY;
    float downX;
    float minMove = 30;//判定的最小距离为30个像素点
    float offsetY;
    boolean isHorizontal;
    boolean isVertical;
    float flagY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flagY = 0;
                downX = ev.getRawX();
                downY = ev.getRawY();
                isHorizontal = false;
                isVertical = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = ev.getRawX() - downX;
                float offsetY = ev.getRawY() - downY;

                if (Math.abs(offsetX) > minMove && !isVertical) {
                    isHorizontal = true;
                    return super.dispatchTouchEvent(ev);
                } else if (Math.abs(offsetY) > minMove && !isHorizontal) {
                    isVertical = true;
                    LinearLayout.LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
                    if (layoutParams.topMargin <= (-maxVpHeight + minVpHeight) && offsetY < 0) {
                        flagY = 0;
                        return super.dispatchTouchEvent(ev);
                    } else if (layoutParams.topMargin >= 0 && offsetY > 0) {
                        flagY = 0;
                        return super.dispatchTouchEvent(ev);
                    } else if (!ViewUtils.isRecyclerViewToTop(recyclerView) && offsetY > 0) {//判断RecyclerView是否滑到顶部
                        flagY = offsetY;
                        return super.dispatchTouchEvent(ev);
                    } else {
                        flagY = offsetY - flagY;
                        setViewPagerTopMargin((int) (flagY + layoutParams.topMargin));
                        flagY = offsetY;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //松手时判断松手的位置做ViewPager的动画
                LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
                int to = 0;
                if (layoutParams.topMargin < -maxVpHeight / 2) {
                    to = -maxVpHeight + minVpHeight;
                }
                startViewPagerAnimator(layoutParams.topMargin, to);
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    private void startViewPagerAnimator(int from, int to) {
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
                layoutParams.topMargin = value;
                viewPager.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    private void setViewPagerTopMargin(int topMargin) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) viewPager.getLayoutParams();
        layoutParams.topMargin = topMargin;
        if (layoutParams.topMargin <= (-maxVpHeight + minVpHeight)) {
            layoutParams.topMargin = -maxVpHeight + minVpHeight;
        } else if (layoutParams.topMargin >= 0) {
            layoutParams.topMargin = 0;
        }
        viewPager.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }
}
