package com.swpuiot.helpingplatform.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstRecyclerAdapter extends RecyclerView.Adapter<FirstViewHolder>{
    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FirstViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class FirstViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView wordContext;


    public FirstViewHolder(View itemView) {
        super(itemView);
    }
}