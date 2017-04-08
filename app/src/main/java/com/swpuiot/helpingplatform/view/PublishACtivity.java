package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PublishAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class PublishACtivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText editContent;
    private User user;
    private List<Bitmap> datas;
    private RecyclerView recyclerView;
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    public static final String PHOTO_IMAGE_FILE_NAME = BmobUser.getCurrentUser(User.class).getUsername() + "dongtai.jpg";
    private File tempFile;
    private PublishAdapter myAdapter;
    private Bitmap bitmap;
    private PostBean postBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_activity);
        datas = new LinkedList<>();
        myAdapter = new PublishAdapter(this, datas);

        toolbar = (Toolbar) findViewById(R.id.toolbar_publish);
        editContent = (EditText) findViewById(R.id.et_publish);
        user = BmobUser.getCurrentUser(User.class);
        recyclerView = (RecyclerView) findViewById(R.id.rv_shoot);
        toolbar.inflateMenu(R.menu.menu_publish);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(myAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        datas.add(bitmap);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_publish:
                        if (editContent.getText().toString().trim().equals("")) {
                            Toast.makeText(PublishACtivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        if (datas.size() != 1) {
                            postBean = new PostBean();
                            postBean.setZan(0);
                            doupdateFile();
                        } else {
                            postBean = new PostBean();
                            postBean.setZan(0);
                            dopublish();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void doupdateFile() {
        final String[] filePaths = new String[datas.size() - 1];
        for (int i = 0; i < datas.size() - 1; i++) {
            File file = myAdapter.cameraUtils.bitmapToFile(datas.get(i + 1));
            filePaths[i] = file.getPath();
        }
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                    Toast.makeText(PublishACtivity.this, "所有图片上传成功", Toast.LENGTH_SHORT).show();
                    postBean.setImgs(list);
                    for (String file : filePaths) {
                        File file1 = new File(file);
                        if (file1 != null) {
                            file1.delete();
                        }
                    }
                    dopublish();
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(PublishACtivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void dopublish() {

        postBean.setContent(editContent.getText().toString().trim());
        postBean.setUser(user);
        postBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(PublishACtivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("Error", e.getMessage() + e.getErrorCode());
                    Toast.makeText(PublishACtivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                bitmap = myAdapter.cameraUtils.compressImageFromFile(tempFile.getPath());
                datas.add(bitmap);
//                System.out.println(bitmap.getByteCount());
                myAdapter.notifyDataSetChanged();
                if (tempFile != null) {
                    tempFile.delete();
                    Log.i("JAVA", "tempFile已删除");
                }
//                myAdapter.cameraUtils.startPhotoZoom(Uri.fromFile(tempFile));
//                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case RESULT_REQUEST_CODE: // 有可能点击舍弃
//                if (data != null) {
//                    // 拿到图片设置, 然后需要删除tempFile
//                    Bundle bundle = data.getExtras();
//                    if (bundle != null) {
//                        final Bitmap bitmap = bundle.getParcelable("data");
//                        datas.add(bitmap);
//                        myAdapter.notifyDataSetChanged();
////                    myAdapter.cameraUtils.setImageToView(data);
//                    }
//                }
                break;
            case IMAGE_REQUEST_CODE:
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                bitmap = myAdapter.cameraUtils.compressImageFromFile(tempFile.getPath());
                datas.add(bitmap);
//                System.out.println(bitmap.getByteCount());
                myAdapter.notifyDataSetChanged();
                if (tempFile != null) {
                    tempFile.delete();
                    Log.i("JAVA", "tempFile已删除");
                }
                break;
        }

    }
}
