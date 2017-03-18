package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;

import java.util.List;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstRecyclerAdapter extends RecyclerView.Adapter<FirstViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<String>titleList;
    private List<String>wordList;
    private List<Integer>imageList;
    private List<String>timeList;
    private List<String>nameList;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public FirstRecyclerAdapter(Context context,List<String>titleList,List<String>wordList,
                                List<Integer>imageList,List<String>timeList,List<String>nameList){
        this.context=context;
        this.titleList=titleList;
        this.wordList=wordList;
        this.imageList=imageList;
        this.timeList=timeList;
        this.nameList=nameList;
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

    public void upData(List<String>titleList,List<String>wordList,
                       List<Integer>imageList,List<String>timeList,List<String>nameList){
        this.titleList=titleList;
        this.wordList=wordList;
        this.imageList=imageList;
        this.timeList=timeList;
        this.nameList=nameList;
    }

    public void removeData(int pos){
        notifyItemRemoved(pos);
    }

    @Override
    public void onBindViewHolder(final FirstViewHolder holder, final int position) {
        holder.title.setText(titleList.get(position));
        holder.wordContext.setText(wordList.get(position));
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/"+imageList.get(position));
        holder.imageView.setImageURI(uri);
        holder.time.setText(timeList.get(position));
        holder.name.setText(nameList.get(position));

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
        return titleList.size();
    }
}
class FirstViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView wordContext;
    SimpleDraweeView imageView;
    TextView time;
    TextView name;

    public FirstViewHolder(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.tv_first_title);
        wordContext= (TextView) itemView.findViewById(R.id.tv_first_word);
        imageView= (SimpleDraweeView) itemView.findViewById(R.id.iv_first_image);
        time= (TextView) itemView.findViewById(R.id.tv_first_time);
        name= (TextView) itemView.findViewById(R.id.tv_first_name);
    }
}