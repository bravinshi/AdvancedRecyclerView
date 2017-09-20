package com.yalantis.phoenix.refresh_view;

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by shijianguo on 2017/9/10.
 */

public abstract class AdvancedDrawable extends Drawable implements Animatable {
    int dWidth;
    int dHeight;
    float mPercent;
    private RecyclerView.Adapter mAdapter;
    public static final float CRITICAL_PERCENT = 0.8f;

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return dWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return dHeight;
    }

    public void setPercent(float percent,boolean invalidate){
        mPercent = percent;
        if (mAdapter != null && invalidate) mAdapter.notifyDataSetChanged();
    }

    public float getPercent(){
        return mPercent;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mAdapter = adapter;
    }
}
