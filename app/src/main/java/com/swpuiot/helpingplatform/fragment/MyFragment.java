package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.MyAdapter;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.UserInformationActivity;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class MyFragment extends Fragment {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView tv_name;
    private User user;
    private File tempFile;
    private SimpleDraweeView sdvHeader;
    private LinearLayout userInformation;
    public static final int CHANGE_INFORMATION_SIGN = 103;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_my);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_my);
        tv_name = (TextView) view.findViewById(R.id.tv_username);
        sdvHeader = (SimpleDraweeView) view.findViewById(R.id.sdv_head);
        user = BmobUser.getCurrentUser(User.class);
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.head_none);
        if (user == null || user.getHeadimg() == null) {
            sdvHeader.setImageURI(uri);
        } else {
            sdvHeader.setImageURI(user.getHeadimg().getFileUrl());
        }

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(getActivity()));

        if (user != null)
            tv_name.setText(user.getUsername());
        else
            tv_name.setText("游客您好，请登录");

        userInformation = (LinearLayout) view.findViewById(R.id.ll_my_login);
        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(getActivity(), "您还没有登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), UserInformationActivity.class);
                startActivityForResult(intent, CHANGE_INFORMATION_SIGN);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHANGE_INFORMATION_SIGN:
                user = BmobUser.getCurrentUser(User.class);
                String url = user.getHeadimg().getFileUrl();
                if (url != null)
                    sdvHeader.setImageURI(url);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
