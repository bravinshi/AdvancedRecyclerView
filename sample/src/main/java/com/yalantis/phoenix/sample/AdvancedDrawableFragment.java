package com.yalantis.phoenix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yalantis.phoenix.AdvancedDrawableRecyclerView;
import com.yalantis.phoenix.PullToRefreshView;
import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;
import com.yalantis.phoenix.wrapper.RefreshLoadWrapper;

import java.util.Map;

/**
 * Created by Oleksii Shliama.
 */
public class AdvancedDrawableFragment extends BaseRefreshFragment {

    private AdvancedDrawableRecyclerView mRecyclerView;
    private RefreshLoadWrapper myAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_advanced_drawable, container, false);

        mRecyclerView = (AdvancedDrawableRecyclerView) rootView.findViewById(R.id.recycler_view);
        myAdapter = new CustomRefreshLoadWrapper(container.getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);

        mRecyclerView.setAdapter(myAdapter);

        mRecyclerView.setCanLoad(true);

        mRecyclerView.setRefreshableAndLoadable(new RefreshableAndLoadable() {
            @Override
            public void onRefreshing() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stopRefreshingOrLoading();
                    }
                },2000);
            }

            @Override
            public void onLoading() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stopRefreshingOrLoading();
                    }
                },2000);
            }
        });

        return rootView;
    }

    class CustomRefreshLoadWrapper extends RefreshLoadWrapper {

        public CustomRefreshLoadWrapper(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderVH(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public RecyclerView.ViewHolder onCreateFooterVH(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public RecyclerView.ViewHolder onCreateGeneralVH(ViewGroup parent, int viewType) {
            return new MyViewHolder1(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.list_item, parent,
                    false));
        }

        @Override
        public void onBindHeaderVH(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public void onBindFooterVH(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public void onBindGeneralVH(RecyclerView.ViewHolder holder,int position) {
            ((MyViewHolder1)holder).imageViewIcon.setImageResource(mSampleList.get(position % 3).get(KEY_ICON));
            ((MyViewHolder1)holder).itemView.setBackgroundResource(mSampleList.get(position % 3).get(KEY_COLOR));
        }
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        ImageView imageViewIcon;

        MyViewHolder1(View view)
        {
            super(view);
            imageViewIcon = (ImageView) view.findViewById(R.id.image_view_icon);
        }
    }
}
