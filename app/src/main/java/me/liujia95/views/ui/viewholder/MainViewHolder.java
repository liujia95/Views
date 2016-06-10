package me.liujia95.views.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/10 11:41.
 */
public class MainViewHolder extends RecyclerView.ViewHolder {

    private final TextView mText;

    public MainViewHolder(View itemView) {
        super(itemView);
        mText = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public MainViewHolder loadData(String text){
        mText.setText(text);
        return null;
    }
}
