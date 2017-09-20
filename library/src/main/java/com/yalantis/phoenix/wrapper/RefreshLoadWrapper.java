package com.yalantis.phoenix.wrapper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yalantis.phoenix.DrawableView;
import com.yalantis.phoenix.refresh_view.AdvancedDrawable;
import com.yalantis.phoenix.viewholder.MyViewHolder;

/**
 * Created by shijianguo on 2017/9/10.
 */

public abstract class RefreshLoadWrapper extends HeaderAndFooterWrapper {
    private final static int REFRESH_TYPE = 199999;
    private final static int LOAD_TYPE = 199998;
    private boolean canRefresh = false;
    private boolean canLoad = false;
    private DrawableView mRefresh;
    private DrawableView mLoad;

    public RefreshLoadWrapper(Context context) {
        super(context);
    }

    private void addRefreshImage(Context c){
        if (canRefresh){
            deleteHeader(0,false);
        }
        mRefresh = new DrawableView(c);
        addHeader(0,REFRESH_TYPE);
        canRefresh = true;
    }

    public void setRefreshDrawable(Context context,AdvancedDrawable drawable){
        addRefreshImage(context);
        mRefresh.setDrawable(drawable);
    }

    public void setLoadDrawable(Context context,AdvancedDrawable drawable){
        addLoadImage(context);
        mLoad.setDrawable(drawable);
    }

    private void addLoadImage(Context c){
        if (canLoad){
            deleteFooter(getFooterViewCount() - 1,false);
        }
        mLoad = new DrawableView(c);
        addFooter(0,LOAD_TYPE);
        canLoad = true;
    }

    public void setRefreshHeight(int height){
        mRefresh.setHeight(height);
    }
    public void setLoadHeight(int height){
        mLoad.setHeight(height);
    }

    public abstract RecyclerView.ViewHolder onCreateHeaderVH(ViewGroup parent, int viewType);
    public abstract RecyclerView.ViewHolder onCreateFooterVH(ViewGroup parent, int viewType);
    public abstract RecyclerView.ViewHolder onCreateGeneralVH(ViewGroup parent, int viewType);
    public abstract void onBindHeaderVH(RecyclerView.ViewHolder holder, int position);
    public abstract void onBindFooterVH(RecyclerView.ViewHolder holder, int position);
    public abstract void onBindGeneralVH(RecyclerView.ViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        if (viewType == REFRESH_TYPE){
            return new MyViewHolder(mRefresh);
        }
        return onCreateHeaderVH(parent, viewType);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_TYPE){
            return new MyViewHolder(mLoad);
        }
        return onCreateFooterVH(parent, viewType);
    }

    @Override
    public RecyclerView.ViewHolder onCreateGeneralViewHolder(ViewGroup parent, int viewType) {
        return onCreateGeneralVH(parent,viewType);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(position == 0 && canRefresh)) {
            onBindHeaderVH(holder, position);
        }
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(position == getFooterViewCount() - 1 && canLoad)){
            onBindFooterVH(holder,position);
        }
    }

    @Override
    public void onBindGeneralViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindGeneralVH(holder, position);
    }
}
