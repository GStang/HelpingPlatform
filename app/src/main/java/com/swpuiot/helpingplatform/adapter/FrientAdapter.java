package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.swpuiot.helpingplatform.bean.Friend;

import java.util.List;

/**
 * Created by DELL on 2017/3/25.
 */
public class FrientAdapter extends RecyclerView.Adapter<FrientAdapter.FriendViewHolder>{
    private Context context;
    private List<Friend> datas;

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder{

        public FriendViewHolder(View itemView) {
            super(itemView);
        }
    }
}
