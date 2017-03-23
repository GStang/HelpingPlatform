package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstRecyclerAdapter extends RecyclerView.Adapter<FirstRecyclerAdapter.FirstViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<FirstBean>datas;
    private User user;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public FirstRecyclerAdapter(Context context,List<FirstBean>datas){
        this.context=context;
        this.datas=datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_first,parent,false);
        FirstViewHolder firstViewHolder=new FirstViewHolder(view);
        return firstViewHolder;
    }
    public void addData(int pos){
        notifyItemInserted(pos);
    }

    public void upData(List<FirstBean>datas){
         this.datas=datas;
    }

    public void removeData(int pos){
        notifyItemRemoved(pos);
    }

    @Override
    public void onBindViewHolder( final FirstViewHolder holder, final int position) {
        user=datas.get(position).getAuthor();
        BmobFile file=user.getHeadimg();
        if(user.getNickName()==null||user.getNickName().equals("")){
            holder.name.setText(user.getUsername());
        }
        else {
            holder.name.setText(user.getNickName());
        }
        if (file == null){
            Uri uri =Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
            holder.imageView.setImageURI(uri);
        }
        else {
            holder.imageView.setImageURI(datas.get(position).getAuthor().getHeadimg().getFileUrl());
        }
        holder.title.setText(datas.get(position).getTitle());
        holder.wordContext.setText(datas.get(position).getContent());
        holder.time.setText(datas.get(position).getCreatedAt());

        if(datas.get(position).getAlive()){
            Uri uri =Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_undone);
            holder.condition.setImageURI(uri);
        }
        else if (datas.get(position).getSolved()){
            Uri uri =Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_done);
            holder.condition.setImageURI(uri);
        }
        else {
            Uri uri =Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_picked);
            holder.condition.setImageURI(uri);
        }

        if(onItemClickListener!=null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class FirstViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView wordContext;
        private SimpleDraweeView imageView;
        private TextView time;
        private TextView name;
        private SimpleDraweeView condition;

        public FirstViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.tv_first_title);
            wordContext= (TextView) itemView.findViewById(R.id.tv_first_word);
            imageView= (SimpleDraweeView) itemView.findViewById(R.id.iv_first_image);
            time= (TextView) itemView.findViewById(R.id.tv_first_time);
            name= (TextView) itemView.findViewById(R.id.tv_first_name);
            condition= (SimpleDraweeView) itemView.findViewById(R.id.iv_first_condition);
        }
    }
}
