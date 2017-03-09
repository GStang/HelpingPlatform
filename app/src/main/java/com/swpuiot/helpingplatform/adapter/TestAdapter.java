package com.swpuiot.helpingplatform.adapter;

/**
 * Created by DELL on 2017/3/9.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.InfBean;
import com.swpuiot.helpingplatform.bean.TestBean;
import com.swpuiot.helpingplatform.view.InfImplActivity;

import java.util.List;

/**
 * Created by DELL on 2017/3/5.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
    private Context context;
    private List<TestBean> datas;

    public TestAdapter(Context context, List<TestBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);

        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        holder.textView.setText(datas.get(position).getTest());
//        System.out.println(datas.get(position).getImg().getFileUrl());
        holder.ivTest.setImageURI(datas.get(position).getImg().getFileUrl());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class TestHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivTest;
        TextView textView;

        public TestHolder(View itemView) {
            super(itemView);
            ivTest = (SimpleDraweeView) itemView.findViewById(R.id.iv_test);
            textView = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }
}


