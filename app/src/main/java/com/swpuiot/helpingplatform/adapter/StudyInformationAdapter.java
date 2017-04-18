package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.StudyBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.ShowStudyInformationActivity;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DuZeming on 2017/4/6.
 */
public class StudyInformationAdapter extends RecyclerView.Adapter<StudyInformationAdapter.StudyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<StudyBean> datas;
    private User user;
    public static final String ShowStudyInf="ShowStudyInf";


    public StudyInformationAdapter(Context context,List<StudyBean>datas){
        this.context=context;
        this.datas=datas;
        inflater=LayoutInflater.from(context);
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public StudyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_study,parent,false);
        StudyViewHolder studyViewHolder=new StudyViewHolder(view);
        return studyViewHolder;
    }

    @Override
    public void onBindViewHolder(final StudyViewHolder holder, final int position) {
        user=datas.get(position).getAuthor();
        BmobFile file=datas.get(position).getStudyFile();

        holder.title.setText(file.getFilename());
        holder.author.setText(user.getUsername());

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

    class StudyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView author;

        public StudyViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.tv_study_title);
            author= (TextView) itemView.findViewById(R.id.tv_study_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ShowStudyInformationActivity.class);
                    intent.putExtra(ShowStudyInf,datas.get(getAdapterPosition()).getStudyFile().getUrl());
                    context.startActivity(intent);
                }
            });
        }

    }
}
