package com.swpuiot.helpingplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.PublishACtivity;
import com.swpuiot.mylibrary.MyRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private MyRecyclerView recyclerView;
    private Context context;
    private List<PostBean> datas;
    private PostAdapter adapter;
    private User user;
    private Toolbar toolbar;
    private FloatingActionButton fabButton;
    private String lastTime;
    private SwipeRefreshLayout refresh;
    private boolean refreshing = false;
    private ProgressBar bar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        initView(view);
        bar.setVisibility(View.GONE);
        getDatasAcrossBottom();
        return view;
    }

    private void initView(View view) {
        context = getActivity();
        datas = new LinkedList<>();
        bar = (ProgressBar) view.findViewById(R.id.pb_refresh);
        adapter = new PostAdapter((AppCompatActivity) context, datas);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fb_add);
        fabButton.setOnClickListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_square);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_square_toolbar);
        recyclerView = (MyRecyclerView) view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setAutoLoadMoreEnable(true, R.layout.list_foot_loading);
        refresh.setOnRefreshListener(this);
        recyclerView.setLoadMoreListener(new MyRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Logger.i("Add");
                getDatasAcrossBottom();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        user = BmobUser.getCurrentUser(User.class);
        super.onCreate(savedInstanceState);
    }

    /**
     * 通过上拉分页加载
     */
    private void getDatasAcrossBottom() {
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("user");
        query.order("-createdAt");
        query.setSkip(datas.size());
        query.setLimit(6);
        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {
                    datas.addAll(list);
                    Logger.i(list.size() + " ");
                    if (list.size() == 6) {
                        datas.remove(datas.size() - 1);
                        Logger.i(list.size() + " ");
                        recyclerView.notifyMoreFinish(true);
                    } else {
                        recyclerView.notifyMoreFinish(false);
                    }
                } else {
                    Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
                    Logger.i(e.getMessage());

                }
            }
        });
    }

    /**
     * 通过下拉刷新加载最近数据
     */
    public void getDatas() {
        BmobQuery<PostBean> query = new BmobQuery<>();

        try {
            String time = datas.get(0).getCreatedAt();
            Logger.i(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            date = sdf.parse(time);
            query.addWhereGreaterThan("createdAt", new BmobDate(date));
        } catch (Exception e) {
            Logger.i("第一次获取数据");
        }

        query.include("user");
        query.order("createdAt");
        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "查询成功，共有" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
//                    datas.clear();
                    for (PostBean bean : list) {
                        try {
                            String s = datas.get(0).getObjectId();
                            if (bean.getObjectId().equals(s)) {
                            } else {
                                datas.add(0, bean);
                            }
                        } catch (Exception ignored) {
                            datas.add(0, bean);
                        }

                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    refresh.setRefreshing(false);
                    refreshing = false;
                    if (bar.getVisibility() == View.VISIBLE) {
                        bar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
                    refresh.setRefreshing(false);
                    refreshing = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_add:
                publishData();
                break;
        }
    }

    private void publishData() {
        Intent intent = new Intent(getActivity(), PublishACtivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
//        refresh.setRefreshing(true);
//        refreshing = true;
//        getDatas();
    }

    @Override
    public void onRefresh() {
        if (refreshing) {
            return;
        } else {
            refreshing = true;
            getDatas();
        }
    }
}

