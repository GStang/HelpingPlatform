package com.swpuiot.helpingplatform.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.swpuiot.helpingplatform.view.AddFriendActivit;

/**
 * Created by DELL on 2017/3/31.
 */
public class AddFriendViewHolder extends RecyclerView.ViewHolder {
    public AddFriendViewHolder(View itemView, final Context context) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFriendActivit.class);
                context.startActivity(intent);
            }
        });
    }

}
