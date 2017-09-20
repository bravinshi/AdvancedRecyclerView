package com.yalantis.phoenix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yalantis.phoenix.AdvancedDrawableRecyclerView;
import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;
import com.yalantis.phoenix.wrapper.RefreshLoadWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shijianguo on 2017/9/5.
 */

public class Activity3 extends AppCompatActivity {
    public static final String KEY_ICON = "icon";
    public static final String KEY_COLOR = "color";

    protected List<Map<String, Integer>> mSampleList;

    private AdvancedDrawableRecyclerView mRecyclerView;
    private RefreshLoadWrapper myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        mRecyclerView = (AdvancedDrawableRecyclerView) findViewById(R.id.recycler_view);
        myAdapter = new CustomRefreshLoadWrapper(Activity3.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Activity3.this));

        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);

        Map<String, Integer> map;
        mSampleList = new ArrayList<>();


        int[] icons = {
                R.drawable.icon_1,
                R.drawable.icon_2,
                R.drawable.icon_3};

        int[] colors = {
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna};

        for (int i = 0; i < 3; i++) {
            map = new HashMap<>();
            map.put(KEY_ICON, icons[i%3]);
            map.put(KEY_COLOR, colors[i%3]);
            mSampleList.add(map);
        }
        mRecyclerView.setAdapter(myAdapter);
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
                    Activity3.this).inflate(R.layout.list_item, parent,
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
