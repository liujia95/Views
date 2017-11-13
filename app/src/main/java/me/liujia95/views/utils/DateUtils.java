package me.liujia95.views.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: LiuJia on 2017/11/13 0013 16:46.
 * Email: liujia95me@126.com
 */

public class DateUtils {

    public static int getDaysOfMonth(String date) {
        return getDaysOfMonth(format(date));
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekOfFirstDay(String date) {
        return getWeekOfFirstDay(format(date));
    }

    //获取某月第一天对应的周
    public static int getWeekOfFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(year, month, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static int getLastMonthToDays(String date) {
        return getLastMonthToDays(format(date));
    }

    public static int getLastMonthToDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date format(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
