package me.liujia95.views.bean;

import android.graphics.Rect;

/**
 * Author: LiuJia on 2017/11/14 0014 15:02.
 * Email: liujia95me@126.com
 */

public class RectDayBean {
    public static final int FLAG_LAST_MONTH = 0;
    public static final int FLAG_THIS_MONTH = 1;
    public static final int FLAG_NEXT_MONTH = 2;
    public Rect rect;//范围
    public int count;//数字
    public int flag;//是上个月/这个月还是下个月
}
