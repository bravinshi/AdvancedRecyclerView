package com.yalantis.phoenix.refresh_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.yalantis.phoenix.R;

/**
 * Created by shijg on 2017/9/15.
 */

public class SunAdvancedDrawable extends AdvancedDrawable {
    private Bitmap mSky;
    private Bitmap mSun;
    private Matrix mMatrix;
    private static final long MAX_OFFSET_ANIMATION_DURATION = 1000;

    private ValueAnimator valueAnimator;
    private Interpolator mInterpolator = new LinearInterpolator();

    private int mSkyHeight;

    private int mSunSize = 100;
    private float mSunLeftOffset = 220;
    private float mRotate = 0.0f;
    private int sunRoutingHeight;
    private boolean startAnimation = false;

    public SunAdvancedDrawable(Context context, final View view){
        super();
        view.post(new Runnable() {
            @Override
            public void run() {
                dWidth = view.getMeasuredWidth();
                dHeight = (int) (dWidth * 0.5f);
                mMatrix = new Matrix();
                init(view.getContext());
            }
        });
    }

    private void init(Context context){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        mSky = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky, options);
        mSky = Bitmap.createScaledBitmap(mSky, dWidth, dHeight, true);

        mSkyHeight = dHeight;
        sunRoutingHeight = (int) ((mSkyHeight - mSunSize) * 0.9);
        mSunLeftOffset = 0.3f * (float) dWidth;

        createBitmaps(context);
    }

    private void createBitmaps(Context context) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        mSky = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky, options);
        mSky = Bitmap.createScaledBitmap(mSky, dWidth, mSkyHeight, true);
        mSun = BitmapFactory.decodeResource(context.getResources(), R.drawable.sun, options);
        mSun = Bitmap.createScaledBitmap(mSun, mSunSize, mSunSize, true);
    }

    @Override
    public void start() {
        startAnimation = true;
        ensureAnimation();
        valueAnimator.start();
    }

    @Override
    public void stop() {
        startAnimation = false;
        if(valueAnimator.isRunning() || valueAnimator.isStarted()){
            valueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return valueAnimator != null && valueAnimator.isRunning();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawSky(canvas);
        drawSun(canvas);
    }

    private void ensureAnimation(){
        valueAnimator = ValueAnimator.ofFloat(0,359);
        valueAnimator.setDuration(MAX_OFFSET_ANIMATION_DURATION);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(mInterpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
    }

    private void drawSky(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();

        canvas.drawBitmap(mSky, matrix, null);
    }

    private void drawSun(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();

        float dragPercent = mPercent;
        if (dragPercent > 1){
            dragPercent = 1f + (dragPercent - 1f) * 0.4f;
        }

        int offsetY = Math.max(mSkyHeight - mSunSize - (int) (dragPercent * sunRoutingHeight),0);
        matrix.postTranslate(mSunLeftOffset,offsetY);
        matrix.postRotate(
                startAnimation ? mRotate : 360 * mPercent,
                mSunLeftOffset + mSunSize / 2,
                offsetY + mSunSize / 2);

        canvas.drawBitmap(mSun, matrix, null);
    }
}
