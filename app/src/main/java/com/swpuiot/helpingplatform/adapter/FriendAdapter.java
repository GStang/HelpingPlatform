package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.Friend;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.ShowUserActivity;

import java.util.List;

/**
 * Created by DELL on 2017/3/25.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private Context context;
    private List<User> datas;

    public FriendAdapter(Context context, List<User> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_finduser, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        holder.textView.setText(datas.get(position).getUsername());
        holder.simpleDraweeView.setImageURI(datas.get(position).getAvatar());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        private TextView textView;
        private Button button;
        private RelativeLayout layout;

        public FriendViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
            textView = (TextView) itemView.findViewById(R.id.tv_username);
            button = (Button) itemView.findViewById(R.id.btn_showuser_inf);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowUserActivity.class);
                }
            });
        }
    }
}
