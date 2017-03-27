package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.ChatActivity;

import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by DELL on 2017/3/24.
 */
public class MyFriendAdapter extends RecyclerView.Adapter<MyFriendAdapter.MyFriendViewHolder> {
    Context context;
    private List<BmobIMUserInfo> datas;

    public MyFriendAdapter(Context context, List<BmobIMUserInfo> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_myfriend, parent, false);
//        MyFriendViewHolder holder = new MyFriendViewHolder(view);
        return new MyFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFriendViewHolder holder, int position) {
        holder.title.setText(datas.get(position).getName());
        holder.info = datas.get(position);
        holder.conversation = BmobIM.getInstance().startPrivateConversation(new BmobIMUserInfo(holder.info.getUserId()
                , holder.info.getName(), holder.info.getAvatar()), null);
        if (holder.info.getAvatar() != null && !holder.info.getAvatar().equals(""))
            holder.simpleDraweeView.setImageURI(holder.info.getAvatar());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyFriendViewHolder extends RecyclerView.ViewHolder {
        BmobIMConversation conversation;
        private SimpleDraweeView simpleDraweeView;
        private TextView content;
        private TextView title;
        private TextView time;
        private BmobIMUserInfo info;

        public MyFriendViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
            content = (TextView) itemView.findViewById(R.id.tv_friend_content);
            title = (TextView) itemView.findViewById(R.id.tv_friend_name);
            time = (TextView) itemView.findViewById(R.id.tv_friend_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("c", conversation);
                    context.startActivity(intent);
                }
            });
        }
    }
}
