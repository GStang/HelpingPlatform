package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;

/**
 * Created by DELL on 2017/3/8.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String[] datas = {"我的信息", "我的订单", "我的钱包", "我的好友", "我的收藏"};
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_OUT = 1;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println(position);
        if (position == 0)
            return TYPE_OUT;
        else if (position == 5)
            return TYPE_TEXT;
        else
            return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyHolder holder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_my
//                , parent, false));
//        return holder;
        if (viewType == TYPE_TEXT) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_my
                    , parent, false));
        } else {
            System.out.println(viewType);
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.item_my_two
                    , parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            ((MyHolder) holder).textView.setText(datas[position-1]);
        }
        if (holder instanceof FootHolder) {
            ((FootHolder) holder).btn_out.getText();
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_my_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, textView.getText(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Button", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
