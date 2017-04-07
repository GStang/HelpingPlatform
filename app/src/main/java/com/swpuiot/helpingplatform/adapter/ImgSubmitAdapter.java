package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.event.CameraEvent;
import com.swpuiot.helpingplatform.event.PhotoEvent;
import com.swpuiot.helpingplatform.utils.CameraUtils;
import com.swpuiot.helpingplatform.view.MainActivity;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by admin on 2017/3/31.
 */
public class ImgSubmitAdapter extends RecyclerView.Adapter<ImgSubmitAdapter.ImagesViewHolder> {
    private Context mContext;
    private List<Bitmap> datas;
    private CameraUtils utils;
    private PopupWindow mPopupWindow;
    private View popupWindow;
    private Button photo;
    private Button take;
    private Button cancel;
    private ImageView view;
    public ImgSubmitAdapter(Context context,List<Bitmap> datas){
        mContext = context;
        this.datas = datas;
        utils = new CameraUtils((AppCompatActivity) mContext);
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.item_imgssubmit, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        holder.mImageView.setImageBitmap(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public ImagesViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_img_submit);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition()==0){
                        Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
                        popupWindow = ((MainActivity) mContext).getLayoutInflater().inflate(R.layout
                                .layout_square_choseimage, null);
                        mPopupWindow = new PopupWindow(popupWindow, WindowManager.LayoutParams.MATCH_PARENT, WindowManager
                                .LayoutParams.WRAP_CONTENT, true);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(true);
                        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(((MainActivity) mContext).getResources()
                                , (Bitmap) null));
                        mPopupWindow.setFocusable(true);
                        mPopupWindow.setAnimationStyle(R.style.Animation);
                        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                WindowManager.LayoutParams params = ((MainActivity) mContext).getWindow().getAttributes();
                                params.alpha = 1f;
                                ((MainActivity) mContext).getWindow().setAttributes(params);
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

                        photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datas.size()>=9){
                                    Toast.makeText(mContext, "图片数量已经达到上限", Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                }else{
                                    EventBus.getDefault().post(new PhotoEvent());
                                }
                            }
                        });
                        take.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datas.size()>=9){
                                    Toast.makeText(mContext, "图片数量已经达到上限", Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                    return;
                                }
                                EventBus.getDefault().post(new CameraEvent());
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        WindowManager.LayoutParams imageParams = ((MainActivity) mContext).getWindow().getAttributes();
                        imageParams.alpha = 0.7f;
                        ((MainActivity) mContext).getWindow().setAttributes(imageParams);
                    }
                }
            });
        }
    }
}
