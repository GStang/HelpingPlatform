package com.swpuiot.helpingplatform.adapter;

/**
 * Created by DELL on 2017/3/9.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.PostBean;

import java.net.URL;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/5.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private Context context;
    private List<PostBean> datas;

    public PostAdapter(Context context, List<PostBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        holder.tvTitle.setText(datas.get(position).getTitle());
        BmobFile file = datas.get(position).getUser().getHeadimg();
        if (file ==null){
            Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
            holder.ivTest.setImageURI(uri);
        }else {
            holder.ivTest.setImageURI(datas.get(position).getUser().getHeadimg().getFileUrl());
        }
        holder.tvContent.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class PostHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivTest;
        TextView tvTitle;
        TextView tvContent;

        public PostHolder(View itemView) {
            super(itemView);
            ivTest = (SimpleDraweeView) itemView.findViewById(R.id.iv_test);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_test);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String s = datas.get(getAdapterPosition()).getUser().getUsername();
//                    Log.e("Test",s);
                    Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


