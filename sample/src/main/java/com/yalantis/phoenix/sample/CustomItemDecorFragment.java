package com.yalantis.phoenix.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.phoenix.AdvancedRecyclerView;
import com.yalantis.phoenix.PullToRefreshView;
import com.yalantis.phoenix.interfacepackage.RefreshableAndLoadable;
import com.yalantis.phoenix.viewholder.MyViewHolder;
import com.yalantis.phoenix.wrapper.HeaderAndFooterWrapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Oleksii Shliama.
 */
public class CustomItemDecorFragment extends BaseRefreshFragment {

    private AdvancedRecyclerView mRecyclerView;
    private MyHeaderAndFooterWrapper myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_recycler_view, container, false);
        myAdapter = new MyHeaderAndFooterWrapper(container.getContext());

        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);
        myAdapter.addGeneral(0);

        mRecyclerView = (AdvancedRecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
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
                        myAdapter.addGeneral(0);
                        myAdapter.addGeneral(0);
                        myAdapter.notifyDataSetChanged();
                    }
                },2000);
            }
        });

        return rootView;
    }

    class MyHeaderAndFooterWrapper extends HeaderAndFooterWrapper {

        public MyHeaderAndFooterWrapper(Context context) {
            super(context);
        }

        @Override
        public MyViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(parent.getContext());
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
                    parent.getContext()).inflate(R.layout.list_item, parent,
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
