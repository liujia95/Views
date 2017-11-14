package me.liujia95.views.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;

/**
 * Author: LiuJia on 2017/11/13 0013 14:47.
 * Email: liujia95me@126.com
 */

public class CalendarLayout extends LinearLayout {
    private static final String TAG = CalendarLayout.class.getSimpleName();
    private ViewPager viewPager;
    private CalendarPagerAdapter adapter;
    Calendar calendar = Calendar.getInstance();
    int flagCount = Integer.MAX_VALUE / 2;


    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        final ViewPager viewPager = new ViewPager(context);
        float density = getResources().getDisplayMetrics().density;
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (300 * density)));

        adapter = new CalendarPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        viewPager.setOffscreenPageLimit(-1);
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
                Log.e(TAG, "position:" + position + " offset:" + offset);
                Log.e(TAG, calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                flagCount = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
