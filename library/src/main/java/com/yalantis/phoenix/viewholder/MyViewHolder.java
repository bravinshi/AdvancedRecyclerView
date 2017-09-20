package com.yalantis.phoenix.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shijianguo on 2017/8/23.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public MyViewHolder(View itemView) {
        super(itemView);
    }

    public static MyViewHolder createViewHolder(Context c, View v){
        return new MyViewHolder(v);
    }
}
