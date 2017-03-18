package com.swpuiot.helpingplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
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
import com.swpuiot.helpingplatform.utils.CameraUtils;
import com.swpuiot.helpingplatform.view.PublishACtivity;

import java.util.List;

/**
 * Created by DELL on 2017/3/17.
 */
public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.MyPhoto> {
    private Context context;
    public CameraUtils cameraUtils;
    private List<Bitmap> datas;
    private PopupWindow mPopupWindow;
    private View popupWindow;

    private Button photo;
    private Button take;
    private Button cancel;
    private AlertDialog dialog;
    private ImageView view;

    public PublishAdapter(Context context, List<Bitmap> datas) {
        this.datas = datas;
        this.context = context;
        cameraUtils = new CameraUtils((AppCompatActivity) context);
        dialog = new AlertDialog.Builder(context).create();
        view = new ImageView(context);
    }

    @Override
    public MyPhoto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_shoot, parent, false);
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

    class MyPhoto extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyPhoto(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_publish_shoot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == 0) {
                        popupWindow = ((PublishACtivity) context).getLayoutInflater().inflate(R.layout
                                .layout_square_choseimage, null);
                        mPopupWindow = new PopupWindow(popupWindow, WindowManager.LayoutParams.MATCH_PARENT, WindowManager
                                .LayoutParams.WRAP_CONTENT, true);
                        mPopupWindow.setTouchable(true);
                        mPopupWindow.setOutsideTouchable(true);
                        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(((PublishACtivity) context).getResources()
                                , (Bitmap) null));
                        mPopupWindow.setFocusable(true);
                        mPopupWindow.setAnimationStyle(R.style.Animation);
                        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                WindowManager.LayoutParams params = ((PublishACtivity) context).getWindow().getAttributes();
                                params.alpha = 1f;
                                ((PublishACtivity) context).getWindow().setAttributes(params);
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
                                    Toast.makeText(context, "图片数量已经达到上限", Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                }else{

                                }
                            }
                        });
                        take.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datas.size()>=9){
                                    Toast.makeText(context, "图片数量已经达到上限", Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                    return;
                                }
                                cameraUtils.openCamera();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        WindowManager.LayoutParams imageParams = ((PublishACtivity) context).getWindow().getAttributes();
                        imageParams.alpha = 0.7f;
                        ((PublishACtivity) context).getWindow().setAttributes(imageParams);
                    }else{
                        view.setImageBitmap(datas.get(getAdapterPosition()));
                        dialog.setView(view);
                        dialog.show();
                    }
                }
            });

        }
    }
}
