package com.yalantis.phoenix.interfacepackage;

/**
 * Created by shijianguo on 2017/9/8.
 */

public interface ItemDecorAnimatable {

    void start();

    boolean canStop();

    void stopAnimationAndSaveState();

    void stopRefreshingOrLoading();

    boolean isRunning();

    void setParentWidthAndHeight(int width, int height);
}
