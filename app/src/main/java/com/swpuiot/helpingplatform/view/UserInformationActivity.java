package com.swpuiot.helpingplatform.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInformationActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout headImage;
    private LinearLayout name;
    private LinearLayout sex;
    private LinearLayout age;
    private SimpleDraweeView showHeadImage;
    private TextView showName;
    private TextView showAge;
    private User user;
    private File tempFile;
    private PopupWindow mImagePopupWindow;
    private View iamgePopupWindow;
    private PopupWindow mSexPopupWindow;
    private View sexPopupWindow;
    private Button showImage;
    private Button photo;
    private Button takePhoto;
    private Button imageCancel;
    private RadioButton choseBoy;
    private RadioButton choseGirl;
    private LinearLayout checkBoy;
    private LinearLayout checkGirl;
    private TextView showSex;
    public static final String PHOTO_IMAGE_FILE_NAME = BmobUser.getCurrentUser(User.class).getUsername()+"head.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    public static final int REQUEST_CHANGENAME_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        headImage= (LinearLayout) findViewById(R.id.ll_userinformation_image);
        name= (LinearLayout) findViewById(R.id.ll_userinformation_name);
        sex= (LinearLayout) findViewById(R.id.ll_userinformation_sex);
        age= (LinearLayout) findViewById(R.id.ll_userinformation_age);
        headImage.setOnClickListener(this);
        name.setOnClickListener(this);
        sex.setOnClickListener(this);
        age.setOnClickListener(this);

        showHeadImage= (SimpleDraweeView) findViewById(R.id.simdra_userinformation_image);
        showName= (TextView) findViewById(R.id.tv_userinformation_name);
        showSex= (TextView) findViewById(R.id.tv_userinformation_sex);
        showAge= (TextView) findViewById(R.id.tv_userinformation_age);

        user=BmobUser.getCurrentUser(User.class);

        showName.setText(user.getUsername());

        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
        if (user==null||user.getHeadimg() == null) {
            showHeadImage.setImageURI(uri);
        } else {
            showHeadImage.setImageURI(user.getHeadimg().getFileUrl());
        }

        iamgePopupWindow=getLayoutInflater().inflate(R.layout.layout_image_toast,null);
        mImagePopupWindow=new PopupWindow(iamgePopupWindow, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,true);
        mImagePopupWindow.setTouchable(true);
        mImagePopupWindow.setOutsideTouchable(true);
        mImagePopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mImagePopupWindow.setFocusable(true);
        mImagePopupWindow.setAnimationStyle(R.style.Animation);

        mImagePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mImagePopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mImagePopupWindow != null && mImagePopupWindow.isShowing()) {
                        mImagePopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

        showImage= (Button) mImagePopupWindow.getContentView().findViewById(R.id.btn_image_show);
        photo= (Button) mImagePopupWindow.getContentView().findViewById(R.id.btn_image_photo);
        takePhoto= (Button) mImagePopupWindow.getContentView().findViewById(R.id.btn_image_take);
        imageCancel= (Button) mImagePopupWindow.getContentView().findViewById(R.id.btn_image_cancel);
        showImage.setOnClickListener(this);
        photo.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        imageCancel.setOnClickListener(this);



        sexPopupWindow=getLayoutInflater().inflate(R.layout.layout_change_sex,null);
        mSexPopupWindow=new PopupWindow(sexPopupWindow, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,true);
        mSexPopupWindow.setTouchable(true);
        mSexPopupWindow.setOutsideTouchable(true);
        mSexPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mSexPopupWindow.setFocusable(true);
        mSexPopupWindow.setAnimationStyle(R.style.Animation);

        mSexPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        mSexPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mSexPopupWindow != null && mSexPopupWindow.isShowing()) {
                        mSexPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

        choseBoy= (RadioButton) mSexPopupWindow.getContentView().findViewById(R.id.rabtn_sex_boy);
        choseGirl= (RadioButton) mSexPopupWindow.getContentView().findViewById(R.id.rabtn_sex_girl);
        checkBoy= (LinearLayout) mSexPopupWindow.getContentView().findViewById(R.id.ll_sex_boy);
        checkGirl= (LinearLayout) mSexPopupWindow.getContentView().findViewById(R.id.ll_sex_girl);
        checkBoy.setOnClickListener(this);
        checkGirl.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_userinformation_image:
                mImagePopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams imageParams = getWindow().getAttributes();
                imageParams.alpha = 0.7f;
                getWindow().setAttributes(imageParams);
                break;
            case R.id.ll_userinformation_name:
                Intent intent=new Intent(this,ChangeNameACtivity.class);
                intent.putExtra("name",showName.getText().toString());
                startActivityForResult(intent,REQUEST_CHANGENAME_CODE);
                break;
            case R.id.ll_userinformation_sex:
                mSexPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams sexParams = getWindow().getAttributes();
                sexParams.alpha = 0.7f;
                getWindow().setAttributes(sexParams);
                break;
            case R.id.ll_userinformation_age:

                break;
            case R.id.btn_image_show:
                LayoutInflater inflater=LayoutInflater.from(UserInformationActivity.this);
                View imageView=inflater.inflate(R.layout.dialog_image, null);
                final AlertDialog dialog=new AlertDialog.Builder(UserInformationActivity.this).create();
                SimpleDraweeView imageView1= (SimpleDraweeView) imageView.findViewById(R.id.simdra_show);
                System.out.println(user.getHeadimg().getFileUrl());
                imageView1.setImageURI(user.getHeadimg().getFileUrl());
                dialog.setView(imageView);
                dialog.show();
                Log.e("Success","Heae");
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
            case R.id.btn_image_photo:
                openphotoshop();
                mImagePopupWindow.dismiss();
                break;
            case R.id.btn_image_take:
                opencmera();
                mImagePopupWindow.dismiss();
                break;
            case R.id.btn_image_cancel:
                mImagePopupWindow.dismiss();
                break;
            case R.id.ll_sex_boy:
                showSex.setText(choseBoy.getText().toString());
                choseBoy.setChecked(true);
                choseGirl.setChecked(false);
                mSexPopupWindow.dismiss();
                break;
            case R.id.ll_sex_girl:
                showSex.setText(choseGirl.getText().toString());
                choseBoy.setChecked(true);
                choseBoy.setChecked(false);
                mSexPopupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    public void openphotoshop() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    public void opencmera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统的拍照功能
        // 判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    public File bitmapToFile(Bitmap bitmap) {
        tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
                bos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // 裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // 发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE: // 相册数据
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            case CAMERA_REQUEST_CODE: // 相机数据
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case RESULT_REQUEST_CODE: // 有可能点击舍弃
                if (data != null) {
                    // 拿到图片设置, 然后需要删除tempFile
                    setImageToView(data);
                }
                break;
            case REQUEST_CHANGENAME_CODE:
                if (data != null)
                    showName.setText(data.getStringExtra("name"));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置icon并上传服务器
     *
     * @param data
     */
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            final Bitmap bitmap = bundle.getParcelable("data");
            final BmobFile bmobFile = new BmobFile(bitmapToFile(bitmap));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        // 此时上传成功

                        user.setHeadimg(bmobFile);// 获取文件并赋值给实体类
                        BmobUser bmobUser = BmobUser.getCurrentUser();
                        user.update(user.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    showHeadImage.setImageURI(bmobFile.getFileUrl());
//                                    mProfileImage.setImageBitmap(bitmap);
//                                    ToastUtils.showShort(getActivity(), getString(R.string.avatar_editor_success));
                                } else {
//                                    ToastUtils.showShort(getActivity(), getString(R.string.avatar_editor_failure));
                                }
                            }
                        });
                    } else {
//                        ToastUtils.showShort(getActivity(), getString(R.string.avatar_editor_failure));
                    }
                    // 既然已经设置了图片，我们原先的就应该删除
                    if (tempFile != null) {
                        tempFile.delete();
//                        LogUtils.i("JAVA", "tempFile已删除");
                    }
                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
    }
}
