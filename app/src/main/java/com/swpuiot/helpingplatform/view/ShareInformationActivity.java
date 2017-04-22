package com.swpuiot.helpingplatform.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.StudyBean;
import com.swpuiot.helpingplatform.bean.User;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ShareInformationActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button choseFile;
    private Button upInformation;
    private TextView showFileName;
    private StudyBean studyBean;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_information);

        choseFile= (Button) findViewById(R.id.btn_share_file);
        showFileName= (TextView) findViewById(R.id.tv_share_file_name);

        choseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 1);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ShareInformationActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                    }
            }


        });

        spinner= (Spinner) findViewById(R.id.spi_kinds);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    showFileName.setText(path);
                    Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);

        upInformation= (Button) findViewById(R.id.btn_information_up);
        upInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemId() == 0) {
                    Toast.makeText(ShareInformationActivity.this, "请选择分类", Toast.LENGTH_SHORT).show();
                } else {
                    final StudyBean bean = new StudyBean();
                    Uri uri = data.getData();
                    File files = new File(FileUtils.getPath(ShareInformationActivity.this, uri));
                    final BmobFile file = new BmobFile(files);
                    file.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                bean.setAuthor(BmobUser.getCurrentUser(User.class));
                                List<BmobFile> list = new LinkedList<BmobFile>();
                                list.add(file);
                                bean.setSign(spinner.getSelectedItemPosition());
                                bean.setStudyFile(file);
                                bean.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(ShareInformationActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            System.out.println("上传失败");
                                        }
                                    }
                                });
                            } else {
                                System.out.println("上传失败");
                            }
                        }
                    });
                }
            }
        });
    }

    public static class FileUtils {
        public static String getPath(Context context, Uri uri) {

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {"_data"};
                Cursor cursor = null;

                try {
                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    // Eat it
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }
    }

    public int getSign(String sign){
        int number = 0;
        if (sign.equals("高数")){
            number = 1;
        }else if (sign.equals("大物")){
            number = 2;
        }else if (sign.equals("化学")){
            number = 3;
        }else if (sign.equals("线代")){
            number = 4;
        }else if (sign.equals("模电")){
            number = 5;
        }else if (sign.equals("英语")){
            number = 6;
        }else if (sign.equals("JAVA")){
            number = 7;
        }else {
            number = 8;
        }


        return number;
    }
}
