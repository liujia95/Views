package me.liujia95.views.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Author: LiuJia on 2017/11/13 0013 14:47.
 * Email: liujia95me@126.com
 */

public class CalendarLayout extends LinearLayout {

    private ViewPager viewPager;
    private CalendarPagerAdapter adapter;

    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        ViewPager viewPager = new ViewPager(context);
        float density = getResources().getDisplayMetrics().density;
        viewPager.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (300 * density)));

        adapter = new CalendarPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        addView(viewPager);
    }


}
