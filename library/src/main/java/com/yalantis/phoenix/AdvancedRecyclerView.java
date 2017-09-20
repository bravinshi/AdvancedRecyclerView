package com.yalantis.phoenix;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;
import com.yalantis.phoenix.itemdecor.CustomItemDecor;

/**
 * Created by shijianguo on 2017/8/17.
 */

public class AdvancedRecyclerView extends RecyclerView implements RefreshableAndLoadable {

    private boolean isSupportRefresh = false;
    private boolean isSupportLoad = false;

    private static final int DRAG_MAX_DISTANCE_V = 300;
    private static final float DRAG_RATE = 0.4f;

    public static final long MAX_OFFSET_ANIMATION_DURATION = 1000;

    private static final int INVALID_POINTER = -1;

    private float INITIAL_X = -1;
    private float INITIAL_Y = -1;

    private Interpolator mInterpolator = new LinearInterpolator();

    private static final String TAG = "plus";

    private boolean showRefreshFlag = false;
    private boolean showLoadFlag = false;

    private CustomItemDecor mItemDecor;

    public AdvancedRecyclerView(Context context) {
        this(context,null);
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

        setWillNotDraw(false);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        addItemDecoration(mItemDecor);
    }

    public void init(Context context){
        mItemDecor = new ItemDecor(this);
        post(new Runnable() {
            @Override
            public void run() {
                mItemDecor.setParentWidthAndHeight(getMeasuredWidth(),getMeasuredHeight());
            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (!mItemDecor.canStop())return true;
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if (!mItemDecor.isRunning()){
                    INITIAL_Y = MotionEventCompat.getY(ev,0);
                }else {// animating
                    cancelAnimation();
                    // 如果取消正在运行的动画，需要动用calculateInitY方法把对应的INITIAL_Y计算出来
                    // 由于RecyclerView记录的action down的位置和我们逻辑上的action down位置不一致
                    // 所以要手动生成一个MotionEvent对象作为参数调用super.onTouchEvent()来修正，否则
                    // 滑动效果会发生混乱
                    calculateInitY(MotionEventCompat.getY(ev,0),DRAG_MAX_DISTANCE_V,DRAG_RATE,
                            showRefreshFlag ? mItemDecor.getRefreshPercent() : -mItemDecor.getLoadPercent());
                    // correct action-down position
                    final MotionEvent simulateEvent = MotionEvent.obtain(ev);
                    simulateEvent.offsetLocation(0, INITIAL_Y - MotionEventCompat.getY(ev,0));
                    return super.onTouchEvent(simulateEvent);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float agentY = MotionEventCompat.getY(ev,0);
                if (agentY > INITIAL_Y){
                    // towards bottom
                    if (showLoadFlag)showLoadFlag = false;
                    if (canChildScrollUp()){
                        if(showRefreshFlag){
                            showRefreshFlag = false;
                            mItemDecor.setRefreshPercent(0);
                        }else {
                            return super.onTouchEvent(ev);
                        }
                        break;
                    }else {
                        if (!showRefreshFlag){
                            showRefreshFlag = true;
                            INITIAL_Y = agentY;
                        }
                        mItemDecor.setRefreshPercent(fixPercent(calculatePercent(INITIAL_Y,agentY,DRAG_MAX_DISTANCE_V,DRAG_RATE)));
                    }
                }else if(agentY < INITIAL_Y) {
                    // towards top
                    if (showRefreshFlag)showRefreshFlag = false;
                    if(canChildScrollBottom()){
                        if(showLoadFlag){
                            showLoadFlag = false;
                            mItemDecor.setLoadPercent(0);
                        }else {
                            return super.onTouchEvent(ev);
                        }
                        break;
                    }else {
                        if(!showLoadFlag){
                            showLoadFlag = true;
                            INITIAL_Y = agentY;
                        }
                        mItemDecor.setLoadPercent(fixPercent(Math.abs(calculatePercent(INITIAL_Y,agentY,DRAG_MAX_DISTANCE_V,DRAG_RATE))));
                    }
                }else {
                    clearState();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                actionUpOrCancel();
                return super.onTouchEvent(ev);
            case MotionEventCompat.ACTION_POINTER_UP:
                break;
        }
        return true;
    }

    void clearState(){
        mItemDecor.setRefreshPercent(0);
        mItemDecor.setLoadPercent(0);
        showLoadFlag = showRefreshFlag = false;
    }

    private void actionUpOrCancel(){
        mItemDecor.start();
    }

    private void cancelAnimation(){
        mItemDecor.stopAnimationAndSaveState();
    }

    private void calculateInitY(float agentY,int maxDragDistance,float rate,float percent){
        INITIAL_Y = agentY - percent * (float) maxDragDistance / rate;
    }

    private float calculatePercent(float initialPos,float currentPos,int maxDragDistance,float rate){
        return (currentPos - initialPos) * rate / ((float) maxDragDistance);
    }

    private float fixPercent(float initPercent){
        if (initPercent <= 1){
            return initPercent;
        }else {
            return 1f + (initPercent - 1f) * 0.6f;
        }
    }

    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(this, -1);
    }

    private boolean canChildScrollBottom(){
        return ViewCompat.canScrollVertically(this, 1);
    }

    @Override
    public void onRefreshing() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mItemDecor.stopRefreshingOrLoading();
            }
        },2000);
    }

    @Override
    public void onLoading() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mItemDecor.stopRefreshingOrLoading();
            }
        },2000);
    }

    public class ItemDecor extends CustomItemDecor {

        Paint mPaint;
        Paint ovalPaint;
        final float backgroundRadius = 60;
        final float ovalRadius = 41;
        RectF oval;
        final float START_ANGLE_MAX_OFFSET = 90;
        final float SWEEP_ANGLE_MAX_VALUE = 300;
        final float ovalWidth = (float) (ovalRadius / 3.8);
        private ValueAnimator valueAnimator;
        private ValueAnimator animator;
        private float offsetAngle = 0;
        private boolean canStopAnimation = true;
        RefreshableAndLoadable mDataSource;
        private int parentWidth = 0;
        private int parentHeight = 0;
        private static final float CRITICAL_PERCENT = 0.8f;

        public ItemDecor(RefreshableAndLoadable view) {
            super((View) view);
            init();
            this.mDataSource = view;

        }

        private void init(){
            mPaint = new Paint();
            mPaint.setColor(getResources().getColor(R.color.colorWhite));
            ovalPaint = new Paint();
            ovalPaint.setColor(getResources().getColor(R.color.colorLightBlue));
            ovalPaint.setStyle(Paint.Style.STROKE);
            ovalPaint.setStrokeWidth(ovalWidth);
            oval = new RectF();
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, State state) {
            if (showRefreshFlag && refreshPercent > 0){// refresh logo visible
                // draw background circle
                c.drawCircle(parentWidth / 2, -backgroundRadius + refreshPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius),
                        backgroundRadius, mPaint);
                if (getSweepAngle() >= SWEEP_ANGLE_MAX_VALUE){// if need, draw circle point
                    drawCirclePoint(true,c);
                }
                calculateOvalAngle();
                c.drawArc(oval,getStartAngle(),getSweepAngle(),false,ovalPaint);// draw arc
            }else if (showLoadFlag && loadPercent > 0){// load logo visible
                // draw background circle
                c.drawCircle(parentWidth / 2, parentHeight + backgroundRadius -
                        loadPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius), backgroundRadius, mPaint);
                if (getSweepAngle() >= SWEEP_ANGLE_MAX_VALUE){// if need, draw circle point
                    drawCirclePoint(false,c);
                }
                calculateOvalAngle();
                c.drawArc(oval,getStartAngle(),getSweepAngle(),false,ovalPaint);// draw arc
            }
        }

        /**
         * draw circle point
         *
         * @param refresh if true draw refresh logo point, otherwise draw load logo point
         * @param c canvas
         */
        void drawCirclePoint(boolean refresh,Canvas c){
            ovalPaint.setStyle(Paint.Style.FILL);
            // calculate zhe angle of the point relative to logo central point
            final double circleAngle = (360 - SWEEP_ANGLE_MAX_VALUE) / 2 - getStartAngle();
            // calculate X coordinate for point center
            final float circleX = parentWidth / 2 + (float) (Math.cos(circleAngle * Math.PI / 180) * ovalRadius);
            // calculate Y coordinate for point center
            float circleY;
            if (refresh){
                circleY = -backgroundRadius + refreshPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) -
                        (float) (Math.sin(circleAngle * Math.PI / 180) * ovalRadius);
            }else {
                circleY = parentHeight + backgroundRadius -
                        loadPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) -
                        (float) (Math.sin(circleAngle * Math.PI / 180) * ovalRadius);
            }
            c.drawCircle(circleX,circleY, ovalWidth / 2 + 2,ovalPaint);
            ovalPaint.setStyle(Paint.Style.STROKE);
        }

        private void calculateOvalAngle(){
            if (showRefreshFlag){
                oval.set(parentWidth / 2 - ovalRadius,-backgroundRadius + refreshPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) - ovalRadius,
                        parentWidth / 2 + ovalRadius,-backgroundRadius + refreshPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) + ovalRadius);
            }else {
                oval.set(parentWidth / 2 - ovalRadius,parentHeight + backgroundRadius - loadPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) - ovalRadius,
                        parentWidth / 2 + ovalRadius,parentHeight + backgroundRadius - loadPercent * (DRAG_MAX_DISTANCE_V + backgroundRadius) + ovalRadius);
            }
        }

        /**
         * calculate start angle if percent larger than CRITICAL_PERCENT start angle should offset a little relative to 0
         *
         * @return start angle
         */
        private float getStartAngle(){
            final float percent = showRefreshFlag ? refreshPercent : loadPercent;
            if (percent <= CRITICAL_PERCENT){
                return 0 + offsetAngle;
            }
            return START_ANGLE_MAX_OFFSET * (percent - CRITICAL_PERCENT) + offsetAngle;
        }

        /**
         * calculate oval sweep angle
         *
         * @return sweep angle
         */
        private float getSweepAngle(){
            final float percent = showRefreshFlag ? refreshPercent : loadPercent;
            if (percent > 0 && percent <= CRITICAL_PERCENT){
                return  percent / CRITICAL_PERCENT * SWEEP_ANGLE_MAX_VALUE;
            }
            return SWEEP_ANGLE_MAX_VALUE;
        }

        @Override
        public void start() {
            if (showLoadFlag && showRefreshFlag){
                throw new IllegalStateException("load state and refresh state should be mutual exclusion!");
            }
            if (showRefreshFlag){
                if (refreshPercent >= CRITICAL_PERCENT){
                    toCriticalPositionAnimation(refreshPercent);
                    initRotateAnimation();
                }else {
                    translationAnimation(refreshPercent,0);
                }
            }else {
                if (loadPercent >= CRITICAL_PERCENT){
                    toCriticalPositionAnimation(loadPercent);
                    initRotateAnimation();
                }else {
                    translationAnimation(loadPercent,0);
                }
            }
        }

        @Override
        public boolean isRunning() {
            if (animator != null){
                if(animator.isRunning() || animator.isStarted())return true;
            }
            if (valueAnimator != null){
                if(valueAnimator.isRunning() || valueAnimator.isStarted())return true;
            }
            return false;
        }

        private void toCriticalPositionAnimation(final float start){
            animator = ValueAnimator.ofFloat(start,CRITICAL_PERCENT);
            animator.setInterpolator(mInterpolator);
            animator.setDuration((long) (MAX_OFFSET_ANIMATION_DURATION * (start - CRITICAL_PERCENT) * 0.8));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    if (showRefreshFlag){
                        setRefreshPercent(value);
                    }else {
                        setLoadPercent(value);
                    }
                    if (value == CRITICAL_PERCENT){
                        startRotateAnimation();
                        if (showRefreshFlag){
                            Toast.makeText(getContext(),"refresh",Toast.LENGTH_SHORT).show();
                            if (mDataSource != null){
                                mDataSource.onRefreshing();
                            }
                        }else {
                            Toast.makeText(getContext(),"load",Toast.LENGTH_SHORT).show();
                            if (mDataSource != null){
                                mDataSource.onLoading();
                            }
                        }
                    }
                }
            });
            animator.start();
        }

        private void translationAnimation(final float start,final float end){
            animator = ValueAnimator.ofFloat(start,end);
            animator.setInterpolator(mInterpolator);
            animator.setDuration((long) (MAX_OFFSET_ANIMATION_DURATION * Math.min(start,1)));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    if (showRefreshFlag){
                        setRefreshPercent(value);
                    }else {
                        setLoadPercent(value);
                    }
                    if (value == end){
                        showLoadFlag = showRefreshFlag = false;
                    }
                }
            });
            animator.start();
        }

        void initRotateAnimation(){
            valueAnimator = ValueAnimator.ofFloat(1,360);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(1100);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offsetAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    canStopAnimation = false;
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    offsetAngle = 0;
                    canStopAnimation = true;
                    if (showRefreshFlag){
                        translationAnimation(refreshPercent,0);
                    }else if(showLoadFlag) {
                        translationAnimation(loadPercent,0);
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        private void startRotateAnimation(){
            valueAnimator.start();
        }

        @Override
        public void stopRefreshingOrLoading(){
            if (valueAnimator != null && (valueAnimator.isStarted() || valueAnimator.isRunning())){
                valueAnimator.cancel();
            }
        }

        @Override
        public void stopAnimationAndSaveState(){
            if (!canStopAnimation)return;
            if (animator != null && (animator.isStarted() || animator.isRunning())){
                animator.cancel();
            }
        }

        @Override
        public boolean canStop(){
            return canStopAnimation;
        }

        @Override
        public void setParentWidthAndHeight(int width,int height){
            parentWidth = width;
            parentHeight = height;
        }

        public void setRefreshableAndLoadable(RefreshableAndLoadable dataSource){
            mDataSource = dataSource;
        }
    }
}
