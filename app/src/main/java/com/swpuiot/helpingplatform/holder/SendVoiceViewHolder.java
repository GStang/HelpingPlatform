package com.swpuiot.helpingplatform.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;

/**
 * Created by DELL on 2017/4/28.
 */
public class SendVoiceViewHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView simpleDraweeView;
    public ImageView imageView;
    public SendVoiceViewHolder(View itemView) {
        super(itemView);
        simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.iv_avatar);
        imageView = (ImageView) itemView.findViewById(R.id.iv_voice);
    }
}
