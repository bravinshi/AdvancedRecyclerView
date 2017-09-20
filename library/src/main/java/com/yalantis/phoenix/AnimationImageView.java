package com.yalantis.phoenix;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by shijianguo on 2017/9/10.
 */

public class AnimationImageView extends ImageView{
    private int mHeight = 1;
    public AnimationImageView(Context context) {
        super(context);
//        setScaleType(ScaleType.CENTER);
        Matrix matrix = new Matrix();
        matrix.reset();
//        matrix.setScale(1.1f,1);
        matrix.setRotate(90);
        setImageMatrix(matrix);

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
//        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY);
//        Log.d("Plus","onMeasure");
//        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    int getCurrentHeight(){
        return mHeight;
    }
}
