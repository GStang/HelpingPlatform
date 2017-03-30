package com.swpuiot.helpingplatform.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;

/**
 * Created by DELL on 2017/3/30.
 */

public class ReceiveTextViewHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView simpleDraweeView;
    public  TextView textView;

    public ReceiveTextViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
        textView= (TextView) itemView.findViewById(R.id.tv_message);
    }
}
