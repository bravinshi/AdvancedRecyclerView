package com.yalantis.phoenix.interfacepackage;

import android.graphics.drawable.Animatable;

/**
 * Created by shijianguo on 2017/9/8.
 */

public interface ItemDecorAnimatable extends Animatable{

    /**
     * coder maybe should call this method before call {@link #interruptAnimation()}
     *
     * @return true if can interrupt animation ,otherwise return false
     */
    boolean canInterrupt();

    /**
     * ItemDecor's animation maybe be complicated
     * when new TouchEvent received but ItemDecor still animating,if ItemDecor support interrupt
     *u should override this method
     */
    void interruptAnimation();
}
