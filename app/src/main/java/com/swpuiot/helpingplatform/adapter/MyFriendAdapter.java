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
import com.swpuiot.helpingplatform.holder.AddFriendViewHolder;
import com.swpuiot.helpingplatform.view.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by DELL on 2017/3/24.
 */
public class MyFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<BmobIMUserInfo> datas;
    public static final int ADD_FRIEND = 1;
    public static final int ITEM = 2;

    public MyFriendAdapter(Context context, List<BmobIMUserInfo> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
//            com.orhanobut.logger.Logger.i("ADDFRIEND");
            return ADD_FRIEND;
        } else {
//            com.orhanobut.logger.Logger.i("ITEM");
            return ITEM;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD_FRIEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_addfriend, parent, false);
            return new AddFriendViewHolder(view, context);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_myfriend, parent, false);
            return new MyFriendViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyFriendViewHolder) {
            ((MyFriendViewHolder) holder).title.setText(datas.get(position - 1).getName());
            ((MyFriendViewHolder) holder).info = datas.get(position - 1);
            ((MyFriendViewHolder) holder).conversation = BmobIM.getInstance().
                    startPrivateConversation(new BmobIMUserInfo(((MyFriendViewHolder) holder).info.getUserId()
                            , ((MyFriendViewHolder) holder).info.getName(), ((MyFriendViewHolder) holder).info.getAvatar()), null);
            ((MyFriendViewHolder) holder).conversation.queryMessages(null, 1, new MessagesQueryListener() {
                @Override
                public void done(List<BmobIMMessage> list, BmobException e) {
                    String s = list.get(0).getContent();
                    SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    long createTime = list.get(0).getCreateTime();
//                    long nowtime = createTime -System.currentTimeMillis();
                    Date date = new Date(createTime);
                    if (s != null)
                        ((MyFriendViewHolder) holder).content.setText(s);
                    ((MyFriendViewHolder) holder).time.setText(sdf.format(date));

                }
            });
            if (((MyFriendViewHolder) holder).info.getAvatar() != null && !((MyFriendViewHolder) holder).info.getAvatar().equals(""))
                ((MyFriendViewHolder) holder).simpleDraweeView.setImageURI(((MyFriendViewHolder) holder).info.getAvatar());
        }
        if (holder instanceof AddFriendViewHolder) {

        }
    }


    @Override
    public int getItemCount() {
        return datas.size() + 1;
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
//            conversation =BmobIM.getInstance().startPrivateConversation(info,null);
//            conversation.queryMessages(null, 1, new MessagesQueryListener() {
//                @Override
//                public void done(List<BmobIMMessage> list, BmobException e) {
//
//                }
//            });
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
