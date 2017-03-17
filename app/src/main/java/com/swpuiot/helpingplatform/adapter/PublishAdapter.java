package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;

import java.util.List;

/**
 * Created by DELL on 2017/3/17.
 */
public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.MyPhoto> {
    private Context context;
    private List<Bitmap> datas;
    public PublishAdapter(Context context,List<Bitmap> datas){
        this.datas = datas;
        this.context = context;

    }
    @Override
    public MyPhoto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_shoot,parent,false);
        return new MyPhoto(view);
    }

    @Override
    public void onBindViewHolder(MyPhoto holder, int position) {
        holder.imageView.setImageBitmap(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyPhoto extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyPhoto(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_publish_shoot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()==0){
                        Toast.makeText(context, "添加照片", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
