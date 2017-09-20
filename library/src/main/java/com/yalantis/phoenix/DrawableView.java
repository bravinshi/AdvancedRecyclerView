package com.yalantis.phoenix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by shijg on 2017/9/14.
 */

public class DrawableView extends View {
    private Drawable mDrawable;
    private int mHeight = 1;
    public DrawableView(Context context) {
        super(context);
    }

    public void setHeight(int height){
        if (mHeight == height)return;
        if (height == 0){
            height = 1;
        }
        mHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

    public void setDrawable(Drawable drawable){
        mDrawable = drawable;
        mDrawable.setCallback(this);
    }

    int getCurrentHeight(){
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable == null) {
            return; // couldn't resolve the URI
        }
        if (mDrawable.getIntrinsicWidth() <= 0 || mDrawable.getIntrinsicHeight() <= 0) {
            return;     // nothing to draw (empty bounds)
        }
        canvas.clipRect(getPaddingLeft(),  getPaddingTop(),
                getRight() - getLeft() - getPaddingRight(), getBottom() - getTop() - getPaddingBottom());
        int saveCount = canvas.save();
        mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

}
