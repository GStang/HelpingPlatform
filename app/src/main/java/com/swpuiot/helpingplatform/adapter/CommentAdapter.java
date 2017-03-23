package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.CommentBean;

import java.util.List;

/**
 * Created by DELL on 2017/3/20.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    Context context;
    List<CommentBean> list;

    public CommentAdapter(Context context, List<CommentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.content.setText(list.get(position).getContent());
        if (list.get(position).getUser().getNickName() == null)
            holder.nickName.setText(list.get(position).getUser().getUsername());
        else
            holder.nickName.setText(list.get(position).getUser().getNickName());
        if (list.get(position).getUser().getHeadimg().getFileUrl() != null)
            holder.headImg.setImageURI(list.get(position).getUser().getHeadimg().getUrl());
        else
            holder.headImg.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none));
        holder.time.setText(list.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView nickName;
        SimpleDraweeView headImg;
        TextView time;

        public CommentHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv_comment_time);
            headImg = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
            nickName = (TextView) itemView.findViewById(R.id.tv_nickname);
            content = (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }

}
