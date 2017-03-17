package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.PublishActivity;

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
        PopupWindow mPopupWindow;
        View popupWindow;
        ImageView imageView;
        Button photo;
        Button take;
        Button cancel;

        public MyPhoto(View itemView) {
            super(itemView);
            popupWindow=((PublishActivity)context).getLayoutInflater().inflate(R.layout
                    .layout_square_choseimage,null);
            imageView = (ImageView) itemView.findViewById(R.id.iv_publish_shoot);

            mPopupWindow=new PopupWindow(popupWindow, WindowManager.LayoutParams.MATCH_PARENT,WindowManager
                    .LayoutParams.WRAP_CONTENT,true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(((PublishActivity) context).getResources()
                    , (Bitmap) null));
            mPopupWindow.setFocusable(true);
            mPopupWindow.setAnimationStyle(R.style.Animation);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams params = ((PublishActivity) context).getWindow().getAttributes();
                    params.alpha = 1f;
                    ((PublishActivity) context).getWindow().setAttributes(params);
                }
            });
            mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                            && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (mPopupWindow != null && mPopupWindow.isShowing()) {
                            mPopupWindow.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });
            photo = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_square_new_photo);
            take = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_square_new_take);
            cancel = (Button) mPopupWindow.getContentView().findViewById(R.id.btn_square_new_cancel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()==0){
                        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        WindowManager.LayoutParams imageParams = ((PublishActivity) context).getWindow().getAttributes();
                        imageParams.alpha = 0.7f;
                        ((PublishActivity) context).getWindow().setAttributes(imageParams);
                    }
                }
            });
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            take.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
    }
}
