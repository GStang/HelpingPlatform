package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.event.AgreeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.newim.bean.BmobIMUserInfo;


/**
 * Created by DELL on 2017/4/11.
 */
public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder> {
    private List<BmobIMUserInfo> datas;
    private Context context;

    public AddFriendAdapter(Context context, List datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public AddFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_myfriend,parent,false);
        return new AddFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddFriendViewHolder holder, int position) {
//        holder.Time.setText();
        holder.draweeView.setImageURI(datas.get(position).getAvatar());
        holder.Content.setText("很高兴认识你");
        holder.Username.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class AddFriendViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        TextView Username;
        TextView Content;
        TextView Time;
        public AddFriendViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
            Username = (TextView) itemView.findViewById(R.id.tv_friend_name);
            Content = (TextView) itemView.findViewById(R.id.tv_friend_content);
            Time = (TextView) itemView.findViewById(R.id.tv_friend_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.i("OnClick");
                    EventBus.getDefault().post(new AgreeEvent(datas.get(getAdapterPosition()),getAdapterPosition()));
                }
            });
        }
    }

}
