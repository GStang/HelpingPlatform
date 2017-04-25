package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.LoginActivity;
import com.swpuiot.helpingplatform.view.MainActivity;
import com.swpuiot.helpingplatform.view.MyChatActivity;
import com.swpuiot.helpingplatform.view.MyFriendActivity;
import com.swpuiot.helpingplatform.view.MySquareActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by DELL on 2017/3/8.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String[] datas = {"我的订单", "我的钱包", "我的好友", "我的动态"};
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_OUT = 1;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println(position);
        if (position == 4)
            return TYPE_OUT;
        else
            return TYPE_TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_my
//                , parent, false));
//        return holder;
        if (viewType == TYPE_TEXT) {

            MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_my
                    , parent, false));
            return holder;
        } else {
            System.out.println(viewType);
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.item_my_two
                    , parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((MyHolder)holder).textView.setText(datas[position]);
        if (position != 4) {
            ((MyHolder) holder).textView.setText(datas[position]);
            switch (position) {
                case 0:
                    ((MyHolder) holder).uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_bookpng);
                    break;
                case 1:
                    ((MyHolder) holder).uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_wallet);
                    break;
                case 2:
                    ((MyHolder) holder).uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_friend);
                    break;
                case 3:
                    ((MyHolder) holder).uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.ic_important);
                    break;
            }
//            System.out.println(((MyHolder) holder).uri);
            ((MyHolder) holder).simpleDraweeView.setImageURI(((MyHolder) holder).uri);
        }
        if (position == 4) {
            ((FootHolder) holder).btn_out.getText();
        }
    }


    @Override
    public int getItemCount() {
        return datas.length + 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        SimpleDraweeView simpleDraweeView;
        Uri uri;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_my_item);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_img);
//            simpleDraweeView.setImageURI(uri);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 2) {
                        Intent intent = new Intent(context, MyFriendActivity.class);
                        context.startActivity(intent);
                    } else if (getAdapterPosition() == 0) {
                        Intent intent = new Intent(context, MyChatActivity.class);
                        context.startActivity(intent);
                    } else if (getAdapterPosition() == 3) {
                        Intent intent = new Intent(context, MySquareActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        Button btn_out;

        public FootHolder(View itemView) {
            super(itemView);
            btn_out = (Button) itemView.findViewById(R.id.btn_out);
            btn_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobUser.logOut();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((MainActivity) context).finish();
                }
            });
        }
    }

}
