package com.swpuiot.helpingplatform.adapter;

/**
 * Created by DELL on 2017/3/9.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.swpuiot.helpingplatform.bean.User;

import java.net.URL;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/5.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private AppCompatActivity context;
    private List<PostBean> datas;
    private User user;
    private ImgAdapter mAdapter;
//    private

    public PostAdapter(AppCompatActivity context, List<PostBean> datas) {
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
//        holder.tvTitle.setText(datas.get(position).getTitle());
        user = datas.get(position).getUser();
        BmobFile file = user.getHeadimg();
        if (user.getNickName() == null || user.getNickName().equals("")) {
            System.out.println(user.getUsername());
            holder.tvNickName.setText(user.getUsername());
        } else {
            holder.tvNickName.setText(user.getNickName());
        }
        if (file == null) {
            Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
            holder.ivTest.setImageURI(uri);
        } else {
            holder.ivTest.setImageURI(datas.get(position).getUser().getHeadimg().getFileUrl());
        }
        holder.tvContent.setText(datas.get(position).getContent());
        if (datas.get(position).getImgs() != null) {
            mAdapter = new ImgAdapter(context, datas.get(position).getImgs());
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            holder.recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class PostHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView ivTest;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvNickName;
        private RecyclerView recyclerView;

        public PostHolder(View itemView) {
            super(itemView);
            ivTest = (SimpleDraweeView) itemView.findViewById(R.id.iv_test);
            tvNickName = (TextView) itemView.findViewById(R.id.tv_nickname);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_content);

//            tvTitle = (TextView) itemView.findViewById(R.id.tv_test);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = datas.get(getAdapterPosition()).getUser().getUsername();
//                    Log.e("Test",s);
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


