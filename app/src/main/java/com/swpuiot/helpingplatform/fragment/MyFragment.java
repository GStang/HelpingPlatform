package com.swpuiot.helpingplatform.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.MyApplication;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.MyAdapter;
import com.swpuiot.helpingplatform.bean.TestBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.MainActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class MyFragment extends Fragment {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView tv_name;
    private User user;
    private SimpleDraweeView sdvHeader;
//    private File tempFile = null;
//    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_my);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_my);
        tv_name = (TextView) view.findViewById(R.id.tv_username);
        sdvHeader = (SimpleDraweeView) view.findViewById(R.id.sdv_head);
        user = BmobUser.getCurrentUser(User.class);
//        user.setHeadimg();
//        File file = new File()

        sdvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BmobFile bmobFile = new BmobFile();
//                user.setHeadimg(bmobFile);
            }
        });
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_my);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_chat:
                        Toast.makeText(getActivity(), "chat", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(new MyAdapter(getActivity()));
        if (user != null)
            tv_name.setText(user.getUsername());
        else
            tv_name.setText("游客您好，请登录");
        return view;
    }

    //    public File bitmapToFile(Bitmap bitmap) {
//        tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
//            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
//                bos.flush();
//                bos.close();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return tempFile;
//    }

}
