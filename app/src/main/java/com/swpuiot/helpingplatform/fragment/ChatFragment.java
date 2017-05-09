package com.swpuiot.helpingplatform.fragment;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.ImgSubmitAdapter;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.event.CameraEvent;
import com.swpuiot.helpingplatform.event.PhotoEvent;
import com.swpuiot.helpingplatform.utils.CameraUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class ChatFragment extends Fragment {
    private ProgressBar progressBar;
    private EditText editText_time;
    private EditText editText_title;
    private EditText editText_plan;
    private EditText editText_phone;
    private ImageButton button_select;       //发布按钮
    private Toolbar toolbar_chat;
    private User user;
    private RecyclerView gridView1;              //网格显示缩略图
    private Bitmap bmp;                      //导入临时图片
    private ImgSubmitAdapter mAdapter;
    private ArrayList<Bitmap> imageItem;
    private ImageView imageView1;
    private File tempFile;
    public static final String PHOTO_IMAGE_FILE_NAME =
            BmobUser.getCurrentUser(User.class).getUsername() + "question.jpg";
    private CameraUtils mCameraUtils;
    private static final int TAKE_PHOTO = 200;
    private static final int CHOOSE_PHOTO = 100;
    private FirstBean bean = new FirstBean();
    private Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_load);
        toolbar_chat = (Toolbar) view.findViewById(R.id.toolbar_chat);
        editText_time = (EditText) view.findViewById(R.id.chat_edit_time);
        editText_title = (EditText) view.findViewById(R.id.chat_edit_title);
        editText_plan = (EditText) view.findViewById(R.id.chat_edit_plan);
        editText_phone = (EditText) view.findViewById(R.id.chat_edit_phone);
        button_select = (ImageButton) view.findViewById(R.id.chat_button_select);
        gridView1 = (RecyclerView) view.findViewById(R.id.gridView1);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        imageItem = new ArrayList<>();
        imageItem.add(bmp);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        mAdapter = new ImgSubmitAdapter(getActivity(), imageItem);
        user = BmobUser.getCurrentUser(User.class);
        gridView1.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        gridView1.setAdapter(mAdapter);
        EventBus.getDefault().register(this);
        mCameraUtils = new CameraUtils((AppCompatActivity) getContext());
        editText_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }

                return false;
            }
        });

        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_select.setEnabled(false);
                bean.setAuthor(user);
                bean.setTitle(editText_phone.getText().toString());
                bean.setAlive(true);
                bean.setSolved(false);
                bean.setContent(editText_plan.getText().toString());
//                List<BmobFile> files = new ArrayList<BmobFile>();
                Logger.i(imageItem.size() + "");
                if (imageItem.size() != 1) {
                    final String[] filePaths = new String[imageItem.size() - 1];
                    for (int i = 0; i < imageItem.size() - 1; i++) {
                        File file = bitmapToFile(imageItem.get(i + 1));
                        filePaths[i] = file.getPath();
                    }
                    BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
                            if (list.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                                //do something
                                Toast.makeText(getActivity(), "所有图片上传成功", Toast.LENGTH_SHORT).show();
                                bean.setFiles(list);
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
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setProgress(i3);
                        }

                        @Override
                        public void onError(int i, String s) {
                            button_select.setEnabled(true);
                        }
                    });
                } else {
                    dopublish();
                }
            }
        });
        return view;
    }

    private void dopublish() {
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Logger.i("发布成功");
                    imageItem.clear();
                    imageItem.add(bmp);
                    mAdapter.notifyDataSetChanged();
                    button_select.setEnabled(true);
                    editText_time.setText("");
                    editText_title.setText("");
                    editText_plan.setText("");
                    editText_phone.setText("");
                    progressBar.setVisibility(View.INVISIBLE);
                }
                button_select.setEnabled(true);
            }
        });
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editText_time.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //处理图库返回
            case CHOOSE_PHOTO:
                if (data != null) {
                    Uri uri = data.getData();
                    String imagePath = null;
                    if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                        String docId = DocumentsContract.getDocumentId(uri);
                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            String id = docId.split(":")[1];
                            String selection = MediaStore.Images.Media._ID + "=" + id;
                            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    selection);
                        } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            Uri contentUri = ContentUris.withAppendedId(Uri.parse
                                    ("content://downloads/public_downloads"), Long.valueOf(docId));
                            imagePath = getImagePath(uri, null);
                        }
                    } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                        imagePath = getImagePath(uri, null);
                    }
                    if (imagePath != null) {
                        Bitmap bitmap = compressImageFromFile(imagePath);
//                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        imageItem.add(bitmap);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (tempFile != null) {
                        tempFile.delete();
                        Log.i("JAVA", "tempFile已删除");
                    }
                }
                break;
            //处理相机返回
            case TAKE_PHOTO:
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                bitmap = compressImageFromFile(tempFile.getPath());
                imageItem.add(bitmap);
//                System.out.println(bitmap.getByteCount());
                mAdapter.notifyDataSetChanged();
                if (tempFile != null) {
                    tempFile.delete();
                    Log.i("JAVA", "tempFile已删除");
                }
                break;
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 压缩BitMap
     */
    public Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

//        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
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
//                simpleAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * bitmap转换为File
     */
    public File bitmapToFile(Bitmap bitmap) {
        String fileName = UUID.randomUUID().toString().substring(1, 10) + ".jpg";
        tempFile = new File(Environment.getExternalStorageDirectory(), fileName);
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

    @Subscribe
    public void onEventMainThread(CameraEvent event) {
        Toast.makeText(getActivity(), "相机", Toast.LENGTH_SHORT).show();
        openCramera();
    }

    @Subscribe
    public void onEventMainThread(PhotoEvent event) {
        Toast.makeText(getActivity(), "图库", Toast.LENGTH_SHORT).show();
        openPhoto();
    }

    private void openCramera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //存放相机返回的图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void openPhoto() {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
//        context.startActivityForResult(intent, IMAGE_REQUEST_CODE);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


}
