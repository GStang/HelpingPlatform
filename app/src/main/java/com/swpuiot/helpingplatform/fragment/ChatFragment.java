package com.swpuiot.helpingplatform.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.ImgSubmitAdapter;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.bean.YueData;
import com.swpuiot.helpingplatform.event.CameraEvent;
import com.swpuiot.helpingplatform.event.PhotoEvent;
import com.swpuiot.helpingplatform.utils.CameraUtils;
import com.swpuiot.helpingplatform.view.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class ChatFragment extends Fragment {

    private EditText editText_time;
    private EditText editText_title;
    private EditText editText_plan;
    private EditText editText_phone;
    private ImageButton button_select;       //发布按钮
    private Toolbar toolbar_chat;
    private User user;
    private RecyclerView gridView1;              //网格显示缩略图
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ImgSubmitAdapter mAdapter;
    private ArrayList<Bitmap> imageItem;
    private ImageView imageView1;
    private File tempFile;
    public static final String PHOTO_IMAGE_FILE_NAME =
            BmobUser.getCurrentUser(User.class).getUsername() + "question.jpg";
    private boolean success = false;
    private View view;
    private static final int REQUEST_PHOTO = 2;

    private CameraUtils mCameraUtils;
    private File imageFile;
//    private ImageView img_show;
    private static final String LOG_TAG = "Camera";
    private File mediaStorageDir = null;
    private String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    private static final int PHOTO_RESULT = 300;
    private static final int TAKE_PHOTO = 200;
    private static final int CHOOSE_PHOTO = 100;

    private File currentImageFile = null;
//    private String filePath = Environment.getExternalStorageDirectory().toString()+"/aaaa";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
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

        editText_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });


        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstBean bean = new FirstBean();
                bean.setAuthor(user);
                bean.setAlive(true);
                bean.setSolved(false);
                bean.setContent(editText_plan.getText().toString());
                editText_time.setText("");
                editText_title.setText("");
                editText_plan.setText("");
                editText_phone.setText("");
            }
        });


        return view;
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //打开图片
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_OPEN) {
//            Uri uri = data.getData();
//            if (!TextUtils.isEmpty(uri.getAuthority())) {
//                //查询选择图片
//                Cursor cursor = this.getContext().getContentResolver().query(
//                        uri,
//                        new String[]{MediaStore.Images.Media.DATA},
//                        null,
//                        null,
//                        null);
//                //返回 没找到选择图片
//                if (null == cursor) {
//                    return;
//                }
//                //光标移动至开头 获取图片路径
//                cursor.moveToFirst();
//                pathImage = cursor.getString(cursor
//                        .getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//        }  //end if 打开图片
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                //处理图库返回
                case CHOOSE_PHOTO:
                    if(resultCode == Activity.RESULT_OK){
                        photoZoom(data.getData());
                    }
                    break;
                //处理相机返回
                case TAKE_PHOTO:
                    if(resultCode == Activity.RESULT_OK){
                        File file = new File(mediaStorageDir.getPath() + File.separator
                                + "IMG_" + timeStamp + ".jpg");
                        photoZoom(Uri.fromFile(file));

                    }
                    //处理裁剪返回
                case PHOTO_RESULT:
                    Bundle bundle = new Bundle();
                    try {
                        bundle = data.getExtras();
                        if (resultCode == Activity.RESULT_OK) {
                            Bitmap bitmap = bundle.getParcelable("data");
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, new ByteArrayOutputStream());
                            //修改ImageView的图片
//                            photoImage.setImageBitmap(bitmap);
                            imageItem.add(bitmap);
                        }
                    } catch (java.lang.NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
            }
    }


    public void photoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
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
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = mCameraUtils.compressImageFromFile(pathImage);
            HashMap<String, Object> map = new HashMap<>();
            map.put("itemImage", addbmp);
//            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//                @Override
//                public boolean setViewValue(View view, Object data,
//                                            String textRepresentation) {
//                    if (view instanceof ImageView && data instanceof Bitmap) {
//                        ImageView i = (ImageView) view;
//                        i.setImageBitmap((Bitmap) data);
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            gridView1.setAdapter(simpleAdapter);
//            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }


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

    @Subscribe
    public void onEventMainThread(CameraEvent event) {
        Toast.makeText(getActivity(), "相机", Toast.LENGTH_SHORT).show();
        openCramera();
    }

    private void openCramera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //存放相机返回的图片
        File file = new File(Environment.getExternalStorageDirectory().toString());
        if(file.exists()){file.delete();}
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Subscribe
    public void onEventMainThread(PhotoEvent event) {
        Toast.makeText(getActivity(), "图库", Toast.LENGTH_SHORT).show();
        openPhoto();
    }

    private void openPhoto() {

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(intent,);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //选择图片格式
        intent.setType("image/*");
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CHOOSE_PHOTO);
    }




}
