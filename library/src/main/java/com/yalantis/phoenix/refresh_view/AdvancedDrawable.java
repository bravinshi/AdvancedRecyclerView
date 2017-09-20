package com.yalantis.phoenix.refresh_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yalantis.phoenix.R;
import com.yalantis.phoenix.interfacepackage.AdvancedDrawableAnimatable;

/**
 * Created by shijianguo on 2017/9/10.
 */

public abstract class AdvancedDrawable extends Drawable implements AdvancedDrawableAnimatable {
    int dWidth;
    int dHeight;
    float mPercent;
    private RecyclerView.Adapter mAdapter;
    public static final float CRITICAL_PERCENT = 0.8f;

    @Override
    public boolean isRunning() {
        return false;
    }

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

    @Override
    public boolean canStop() {
        return false;
    }

    public float getPercent(){
        return mPercent;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mAdapter = adapter;
    }
}
