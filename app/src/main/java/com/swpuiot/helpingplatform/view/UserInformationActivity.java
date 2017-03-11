package com.swpuiot.helpingplatform.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
    private TextView showSex;
    private TextView showAge;
    private User user;
    private File tempFile;
    public static final String PHOTO_IMAGE_FILE_NAME = BmobUser.getCurrentUser(User.class).getUsername()+"head.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;

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

        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
        if (user==null||user.getHeadimg() == null) {
            showHeadImage.setImageURI(uri);
        } else {
            showHeadImage.setImageURI(user.getHeadimg().getFileUrl());
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_userinformation_image:
//                Toast.makeText(getActivity(), "头像", Toast.LENGTH_SHORT).show();
                if (user == null) {
                    Toast.makeText(this, "您还没有登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择照片来源")
                        .setPositiveButton("照片", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openphotoshop();
                            }
                        })
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })

                        .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                opencmera();
                            }
                        });
                builder.create().show();
                break;
            case R.id.ll_userinformation_name:

                break;
            case R.id.ll_userinformation_sex:

                break;
            case R.id.ll_userinformation_age:

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
