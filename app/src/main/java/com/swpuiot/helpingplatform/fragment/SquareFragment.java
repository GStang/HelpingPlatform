package com.swpuiot.helpingplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.PublishACtivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Context context;
    private Button btnCommit;
    private Button btnGetdata;
    private List<PostBean> datas;
    private PostAdapter adapter;
    private User user;
    private Toolbar toolbar;
    private FloatingActionButton fabButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        context = getActivity();
        datas = new ArrayList<>();
        adapter = new PostAdapter((AppCompatActivity) context, datas);

        btnCommit = (Button) view.findViewById(R.id.btn_commit);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fb_add);
        fabButton.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnGetdata = (Button) view.findViewById(R.id.btn_getdate);
        btnGetdata.setOnClickListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_square);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_square_toolbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        user = BmobUser.getCurrentUser(User.class);
        super.onCreate(savedInstanceState);
    }

    public void getDatas() {
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("user");
        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "查询成功，共有" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                    System.out.println(datas.toString());
                    System.out.println(adapter);
                } else {
                    Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                commitDatas();
                break;
            case R.id.btn_getdate:
                getDatas();
                break;
            case R.id.fb_add:
                publishData();
        }
    }

    private void publishData() {
        Intent intent = new Intent(getActivity(),PublishACtivity.class);
        startActivity(intent);
    }

    private void commitDatas() {
        PostBean postBean = new PostBean();
        postBean.setContent(context.getResources().getString(R.string.content));
        postBean.setUser(user);
        postBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Error", e.getErrorCode() + e.getMessage());
                    Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

