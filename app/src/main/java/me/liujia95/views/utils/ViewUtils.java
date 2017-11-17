package me.liujia95.views.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: LiuJia on 2017/11/17 0017 17:58.
 * Email: liujia95me@126.com
 */

public class ViewUtils {

    public static boolean isRecyclerViewToTop(RecyclerView recyclerView) {
        View view = recyclerView.getChildAt(0);
        return recyclerView.getChildAdapterPosition(view) == 0 && view.getY() == 0;
    }

}
