package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.PostBean;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/18.
 */
public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgHolder> {
    AppCompatActivity context;
    List<BmobFile> imgs;
    private DisplayMetrics dm;

    public ImgAdapter(AppCompatActivity context, List<BmobFile> datas) {
        this.context = context;
        imgs = datas;
        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;  // 屏幕宽度（像素）
        int heightPixels = dm.heightPixels;  // 屏幕高度（像素）
    }

    @Override
    public ImgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imgs, parent, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (dm.widthPixels / 3, dm.widthPixels / 3);
        view.setLayoutParams(lp);
        return new ImgHolder(view);
    }


    @Override
    public void onBindViewHolder(ImgHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(imgs.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    class ImgHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;

        public ImgHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_imgs);
        }
    }
}
