package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.InfBean;
import com.swpuiot.helpingplatform.view.InfImplActivity;


import java.util.List;


/**
 * Created by DELL on 2017/3/5.
 */
public class InfRecyclerAdapter extends RecyclerView.Adapter<InfRecyclerAdapter.InfHolder> {
    private Context context;
    private List<InfBean> datas;

    public InfRecyclerAdapter(Context context, List<InfBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public InfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InfHolder holder = new InfHolder(LayoutInflater.from(context).inflate(R.layout.item_inf, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(InfHolder holder, int position) {
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/"+datas.get(position).getImgId());
        holder.iv_sbj.setImageURI(uri);
        holder.tv_sbj.setText(datas.get(position).getSbj());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class InfHolder extends RecyclerView.ViewHolder {
        TextView tv_sbj;
        SimpleDraweeView iv_sbj;

        public InfHolder(View itemView) {
            super(itemView);
            tv_sbj = (TextView) itemView.findViewById(R.id.tv_sbj);
            iv_sbj = (SimpleDraweeView) itemView.findViewById(R.id.iv_sbj);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,InfImplActivity.class);
                    intent.putExtra("sbj",tv_sbj.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

}


