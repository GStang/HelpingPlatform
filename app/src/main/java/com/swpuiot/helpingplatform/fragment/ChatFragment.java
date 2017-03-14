package com.swpuiot.helpingplatform.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.bean.YueData;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class ChatFragment extends Fragment {

    private YueData yueData;
    private EditText editText_date;
    private EditText editText_time;
    private EditText editText_title;
    private EditText editText_plan;
    private EditText editText_phone;
    private ImageButton button_select;       //发布按钮
    //    private ImageButton chat_button_add;
    private Toolbar toolbar_chat;

    private GridView gridView1;              //网格显示缩略图
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
    private ImageView imageView1;
    private File tempFile;
    public static final String PHOTO_IMAGE_FILE_NAME = BmobUser.getCurrentUser(User.class).getUsername() + "question.jpg";
    private boolean success = false;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        toolbar_chat = (Toolbar) view.findViewById(R.id.toolbar_chat);
        editText_date = (EditText) view.findViewById(R.id.chat_edit_date);
        editText_time = (EditText) view.findViewById(R.id.chat_edit_time);
        editText_title = (EditText) view.findViewById(R.id.chat_edit_title);
        editText_plan = (EditText) view.findViewById(R.id.chat_edit_plan);
        editText_phone = (EditText) view.findViewById(R.id.chat_edit_phone);
        button_select = (ImageButton) view.findViewById(R.id.chat_button_select);
//        chat_button_add = (ImageButton) view.findViewById(R.id.chat_button_add);
        gridView1 = (GridView) view.findViewById(R.id.gridView1);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this.getContext(), imageItem,
                R.layout.fragment_chat_griditem_addpic,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }

                return false;
            }
        });

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yueData = new YueData();
                final PostBean test = new PostBean();
                yueData.setDate(editText_date.getText().toString());
                yueData.setTime(editText_time.getText().toString());
                yueData.setTitle(editText_title.getText().toString());
                yueData.setPlan(editText_plan.getText().toString());
                yueData.getPhone(editText_phone.getText().toString());

                final BmobFile file = new BmobFile(bitmapToFile(bmp));
//                test.setTest(editText_plan.getText().toString());
//                test.setImg(file);
                editText_date.setText("");
                editText_time.setText("");
                editText_title.setText("");
                editText_plan.setText("");
                editText_phone.setText("");
//
//                file.uploadblock(new UploadFileListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null) {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                            success = true;
//                            test.setTest("我是测试");
//                            test.setImg(file);
//                            test.save(new SaveListener<String>() {
//                                @Override
//                                public void done(String s, BmobException e) {
//                                    if (e == null) {
//                                        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
//                                        success = false;
//                                    } else {
//                                        Toast.makeText(getActivity(), "失败了", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        } else
//                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });



//                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();

            }
        });


        gridView1.setAdapter(simpleAdapter);


        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (imageItem.size() == 10) { //第一张为默认图片
                    Toast.makeText(getActivity(), "图片数9张已满", Toast.LENGTH_SHORT).show();
                } else if (position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(getActivity(), "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                    //通过onResume()刷新数据
                } else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //      Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = this.getContext().getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }  //end if 打开图片
    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this.getContext(),
                    imageItem, R.layout.fragment_chat_griditem_addpic,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }

//    protected void onActivityResult

    /**
     * bitmap转换为File
     */
    public File bitmapToFile(Bitmap bitmap) {
        tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
        System.out.println(tempFile.getName());
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
}
