package com.yalantis.phoenix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.phoenix.AdvancedRecyclerView;
import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;
import com.yalantis.phoenix.viewholder.MyViewHolder;
import com.yalantis.phoenix.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shijianguo on 2017/8/18.
 */

public class AdvancedRecyclerActivity extends AppCompatActivity {

    public static final String KEY_ICON = "icon";
    public static final String KEY_COLOR = "color";

    protected List<Map<String, Integer>> mSampleList;

    private AdvancedRecyclerView mRecyclerView;
    private MyHeaderAndFooterWrapper myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        myAdapter = new MyHeaderAndFooterWrapper(AdvancedRecyclerActivity.this);

        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
//        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addHeader(0);
        myAdapter.addHeader(0);
        myAdapter.addFooter(0);
        myAdapter.addFooter(0);

     
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

        mRecyclerView = (AdvancedRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AdvancedRecyclerActivity.this));
        mRecyclerView.setAdapter(myAdapter);

        mRecyclerView.setCanLoad(true);

        mRecyclerView.setRefreshableAndLoadable(new RefreshableAndLoadable() {
            @Override
            public void onRefreshing() {
                Toast.makeText(mRecyclerView.getContext(),"refresh",Toast.LENGTH_SHORT).show();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stop();
                    }
                },2000);
            }

            @Override
            public void onLoading() {
                Toast.makeText(mRecyclerView.getContext(),"load",Toast.LENGTH_SHORT).show();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stop();
                    }
                },2000);
            }
        });
    }

    class MyHeaderAndFooterWrapper extends HeaderAndFooterWrapper {

        public MyHeaderAndFooterWrapper(Context context) {
            super(context);
        }

        @Override
        public MyViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
            TextView  view = new TextView(parent.getContext());
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
            return MyViewHolder.createViewHolder(parent.getContext(),view);
        }

        @Override
        public MyViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
            TextView  view = new TextView(parent.getContext());
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
            return MyViewHolder.createViewHolder(parent.getContext(),view);
        }

        @Override
        public RecyclerView.ViewHolder onCreateGeneralViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder1(LayoutInflater.from(
                    AdvancedRecyclerActivity.this).inflate(R.layout.list_item, parent,
                    false));
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText("这是一个Header");
            if(position % 2 == 0){
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_green_light));
            }else {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
            }
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView)holder.itemView).setText("这是一个Footer");
            if(position % 2 == 0){
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_green_light));
            }else {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));
            }
        }

        @Override
        public void onBindGeneralViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((MyViewHolder1)holder).imageViewIcon.setImageResource(mSampleList.get(position % 3).get(KEY_ICON));
            ((MyViewHolder1)holder).imageViewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"position:" + position,Toast.LENGTH_SHORT).show();
                }
            });
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
