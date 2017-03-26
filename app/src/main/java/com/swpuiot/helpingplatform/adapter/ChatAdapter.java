package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.helpingplatform.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.BmobUser;

/**
 * Created by DELL on 2017/3/26.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //文本
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    //图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    //位置
    private final int TYPE_SEND_LOCATION = 4;
    private final int TYPE_RECEIVER_LOCATION = 5;
    //语音
    private final int TYPE_SEND_VOICE = 6;
    private final int TYPE_RECEIVER_VOICE = 7;
    //视频
    private final int TYPE_SEND_VIDEO = 8;
    private final int TYPE_RECEIVER_VIDEO = 9;

    //同意添加好友成功后的样式
    private final int TYPE_AGREE = 10;

    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;

    private List<BmobIMMessage> msgs = new ArrayList<>();

    private String currentUid = "";
    BmobIMConversation c;
    private Context context;
    /**
     * 构造函数
     */

    public ChatAdapter(Context context, BmobIMConversation c) {
        try {
            this.context = context;
            currentUid = BmobUser.getCurrentUser().getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.c = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_send,parent,false);
        return new SendTextHolder(view);
//        if (viewType == TYPE_SEND_TXT) {
//            return new SendTextHolder(parent.getContext(), parent,c,onRecyclerViewListener);
//        } else if (viewType == TYPE_SEND_IMAGE) {
//            return new SendImageHolder(parent.getContext(), parent,c,onRecyclerViewListener);
//        } else if (viewType == TYPE_SEND_LOCATION) {
//            return new SendLocationHolder(parent.getContext(), parent,c,onRecyclerViewListener);
//        } else if (viewType == TYPE_SEND_VOICE) {
//            return new SendVoiceHolder(parent.getContext(), parent,c,onRecyclerViewListener);
//        } else if (viewType == TYPE_RECEIVER_TXT) {
//            return new ReceiveTextHolder(parent.getContext(), parent,onRecyclerViewListener);
//        } else if (viewType == TYPE_RECEIVER_IMAGE) {
//            return new ReceiveImageHolder(parent.getContext(), parent,onRecyclerViewListener);
//        } else if (viewType == TYPE_RECEIVER_LOCATION) {
//            return new ReceiveLocationHolder(parent.getContext(), parent,onRecyclerViewListener);
//        } else if (viewType == TYPE_RECEIVER_VOICE) {
//            return new ReceiveVoiceHolder(parent.getContext(), parent,onRecyclerViewListener);
//        } else if (viewType == TYPE_SEND_VIDEO) {
//            return new SendVideoHolder(parent.getContext(), parent,c,onRecyclerViewListener);
//        } else if (viewType == TYPE_RECEIVER_VIDEO) {
//            return new ReceiveVideoHolder(parent.getContext(), parent,onRecyclerViewListener);
//        }else if(viewType ==TYPE_AGREE) {
//            return new AgreeHolder(parent.getContext(),parent,onRecyclerViewListener);
//        }else{//开发者自定义的其他类型，可自行处理
//            return null;
//        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        BmobIMMessage message = msgs.get(position);
//        if(message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
//            return message.getFromId().equals(currentUid) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
//        }else if(message.getMsgType().equals(BmobIMMessageType.LOCATION.getType())){
//            return message.getFromId().equals(currentUid) ? TYPE_SEND_LOCATION: TYPE_RECEIVER_LOCATION;
//        }else if(message.getMsgType().equals(BmobIMMessageType.VOICE.getType())){
//            return message.getFromId().equals(currentUid) ? TYPE_SEND_VOICE: TYPE_RECEIVER_VOICE;
//        }else if(message.getMsgType().equals(BmobIMMessageType.TEXT.getType())){
//            return message.getFromId().equals(currentUid) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
//        }else if(message.getMsgType().equals(BmobIMMessageType.VIDEO.getType())){
//            return message.getFromId().equals(currentUid) ? TYPE_SEND_VIDEO: TYPE_RECEIVER_VIDEO;
//        }else if(message.getMsgType().equals("agree")) {//显示欢迎
//            return TYPE_AGREE;
//        }else{
//            return -1;
//        }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SendTextHolder)holder).textView.setText(msgs.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public void addMessages(List<BmobIMMessage> messages) {
        msgs.addAll(0, messages);
        notifyDataSetChanged();
    }

    public void addMessage(BmobIMMessage message) {
//        msgs.addAll(Arrays.asList(message));
        msgs.add(message);
        com.orhanobut.logger.Logger.i("datachange");
        notifyDataSetChanged();
        com.orhanobut.logger.Logger.i(msgs.size()+"");
    }


    /**
     * 获取消息
     *
     * @param position
     * @return
     */
    public BmobIMMessage getItem(int position) {
        return this.msgs == null ? null : (position >= this.msgs.size() ? null : this.msgs.get(position));
    }

    /**
     * 移除消息
     *
     * @param position
     */
    public void remove(int position) {
        msgs.remove(position);
        notifyDataSetChanged();
    }

    public BmobIMMessage getFirstMessage() {
        if (null != msgs && msgs.size() > 0) {
            return msgs.get(0);
        } else {
            return null;
        }
    }

    class SendTextHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public SendTextHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
