package com.swpuiot.helpingplatform.adapter;

/**
 * Created by DELL on 2017/3/9.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.customview.CustomButton;
import com.swpuiot.helpingplatform.view.DetailsActivity;

import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DELL on 2017/3/5.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private AppCompatActivity context;
    private List<PostBean> datas;
    private User user;
    private ImgAdapter mAdapter;
    public static final String Details = "DETAILS";
//    private

    public PostAdapter(AppCompatActivity context, List<PostBean> datas) {
        this.datas = datas;
        this.context = context;

    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        user = datas.get(position).getUser();
        BmobFile file = user.getHeadimg();
        if (user.getNickName() == null || user.getNickName().equals("")) {
            System.out.println(user.getUsername());
            holder.tvNickName.setText(user.getUsername());
        } else {
            holder.tvNickName.setText(user.getNickName());
        }
        if (file == null) {
            Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
            holder.ivTest.setImageURI(uri);
        } else {
            holder.ivTest.setImageURI(datas.get(position).getUser().getHeadimg().getFileUrl());
        }
        holder.tvContent.setText(datas.get(position).getContent());
        holder.btnZan.setText(datas.get(position).getZan() == null ? "赞" + 0 + "" : "赞" + datas.get(position).getZan() + "");
        if (datas.get(position).getImgs() != null) {
            holder.recyclerView.setVisibility(View.VISIBLE);
            mAdapter = new ImgAdapter(context, datas.get(position).getImgs());
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            holder.recyclerView.setAdapter(mAdapter);
        } else {
            holder.recyclerView.setVisibility(View.GONE);
        }
        holder.tvTime.setText(datas.get(position).getCreatedAt());
//        holder.btnTalk.setText("评论"+datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class PostHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView ivTest;
        private TextView tvContent;
        private TextView tvNickName;
        private RecyclerView recyclerView;
        private CustomButton btnZan;
        private CustomButton btnTalk;
        private TextView tvTime;

        public PostHolder(View itemView) {
            super(itemView);
            initView(itemView);
//            btnZan.setText("赞"+datas.get(getAdapterPosition()).getZan());
            btnZanClick();
            btnTalksAndItemViewOnclick(itemView);
        }

        /**
         * 点击评论和itemview的逻辑
         */
        private void btnTalksAndItemViewOnclick(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Details, datas.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            btnTalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(Details, datas.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

        /**
         * 点击赞的逻辑
         */
        private void btnZanClick() {
            btnZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereRelatedTo("likes", new BmobPointer(datas.get(getAdapterPosition())));
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                boolean isContain = false;
                                for (User user : list) {
                                    if ((user.getObjectId()).equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
                                        isContain = true;
                                        break;
                                    }
                                }
                                if (isContain) {
                                    PostBean post = getPostBean("delete");
                                    post.update(datas.get(getAdapterPosition()).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.i("Zan", "delete");
                                                btnZan.setText("赞" + datas.get(getAdapterPosition()).getZan());
                                            }
                                        }
                                    });
                                } else {
                                    PostBean post = getPostBean("add");
                                    post.update(datas.get(getAdapterPosition()).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.i("Zan", "add");
                                                btnZan.setText("赞" + datas.get(getAdapterPosition()).getZan());
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            });
        }

        /**
         * 初始化界面
         */
        private void initView(View itemView) {
            btnTalk = (CustomButton) itemView.findViewById(R.id.btn_talk);
            ivTest = (SimpleDraweeView) itemView.findViewById(R.id.iv_test);
            tvNickName = (TextView) itemView.findViewById(R.id.tv_nickname);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_content);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            btnZan = (CustomButton) itemView.findViewById(R.id.btn_zan);
            btnTalk = (CustomButton) itemView.findViewById(R.id.btn_talk);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        /**
         * 点赞数目的添加和减少
         */
        @NonNull
        private PostBean getPostBean(String action) {
            PostBean post = new PostBean();
            if (action.equals("add")) {
                datas.get(getAdapterPosition()).setZan(datas.get(getAdapterPosition()).getZan() + 1);
                post.setZan(datas.get(getAdapterPosition()).getZan());
                BmobRelation relation = new BmobRelation();
                relation.add(BmobUser.getCurrentUser(User.class));
                post.setLikes(relation);
                return post;
            } else {
                datas.get(getAdapterPosition()).setZan(datas.get(getAdapterPosition()).getZan() - 1);
                post.setZan(datas.get(getAdapterPosition()).getZan());
                BmobRelation relation = new BmobRelation();
//                relation.add(BmobUser.getCurrentUser(User.class));
                relation.remove(BmobUser.getCurrentUser(User.class));
                post.setLikes(relation);
                return post;
            }
        }
    }
}


