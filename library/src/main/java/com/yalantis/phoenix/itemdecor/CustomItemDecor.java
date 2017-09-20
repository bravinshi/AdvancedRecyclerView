package com.yalantis.phoenix.itemdecor;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yalantis.phoenix.interfacepackage.ItemDecorAnimatable;
import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;

/**
 * Created by shijianguo on 2017/9/10.
 */

public abstract class CustomItemDecor extends RecyclerView.ItemDecoration implements ItemDecorAnimatable {
    protected float refreshPercent = 0.0f;
    protected float loadPercent = 0.0f;
    private View mParent;
    protected RefreshableAndLoadable mDataSource;

    public CustomItemDecor(View parent){
        mParent = parent;
    }

    public void setRefreshPercent(float percent) {
        if (percent == refreshPercent)return;
        refreshPercent = percent;
        mParent.invalidate();
    }

    public void setLoadPercent(float percent) {
        if (percent == loadPercent)return;
        loadPercent = percent;
        mParent.invalidate();
    }

    public float getRefreshPercent() {
        return refreshPercent;
    }

    public float getLoadPercent() {
        return loadPercent;
    }

    public void setRefreshableAndLoadable(RefreshableAndLoadable dataSource){
        mDataSource = dataSource;
    }
}
