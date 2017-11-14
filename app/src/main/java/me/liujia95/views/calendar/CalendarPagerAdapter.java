package me.liujia95.views.calendar;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Author: LiuJia on 2017/11/13 0013 15:03.
 * Email: liujia95me@126.com
 */

public class CalendarPagerAdapter extends PagerAdapter {

    private static final String TAG = CalendarPagerAdapter.class.getSimpleName();
    //最初的数目
    private int flagCount = Integer.MAX_VALUE / 2;
    Calendar calendar = Calendar.getInstance();

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView monthView = new MonthView(container.getContext());
        monthView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int offset = position - flagCount;
        calendar.add(Calendar.MONTH, offset);
        monthView.setDate(calendar.getTime());
        flagCount = position;
        monthView.setOnClickListener(listener);
        container.addView(monthView);
        return monthView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    MonthView.OnClickListener listener;

    public void setOnClickListener(MonthView.OnClickListener listener) {
        this.listener = listener;
    }
}
