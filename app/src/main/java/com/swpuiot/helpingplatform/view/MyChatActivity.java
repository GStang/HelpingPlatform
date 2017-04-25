package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.mylibrary.MyRecyclerView;
import com.swpuiot.mylibrary.RecyclerItemTouchHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我的订单
 *
 * @author Dell
 */
public class MyChatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private MyRecyclerView MyChat;
    private SwipeRefreshLayout refreshLayout;
    private FirstRecyclerAdapter adapter;
    private List<FirstBean> datas;
    private boolean refreshing = false;
    private User user;
    public static final String InFlmp = "InFlmp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat);
        initView();
        ItemTouchHelper.Callback callback = new RecyclerItemTouchHelper((MyRecyclerView.AutoAdapter) MyChat.getAdapter());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(MyChat);

    }

    private void initView() {
        MyChat = (MyRecyclerView) findViewById(R.id.recycler_mychar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        refreshLayout.setOnRefreshListener(this);
        MyChat.setLayoutManager(new LinearLayoutManager(this));
        MyChat.setAutoLoadMoreEnable(true, R.layout.list_foot_loading);
        datas = new LinkedList<>();
        adapter = new FirstRecyclerAdapter(this, datas);
        adapter.setHeadEnable(false);
        MyChat.setAdapter(adapter);
        user = BmobUser.getCurrentUser(User.class);
        MyChat.setLoadMoreListener(new MyRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Logger.i("Add");
                getDatasAcrossBottom();
//                MyChat.notifyMoreFinish(true);
            }
        });

        adapter.setOnItemClickListener(new FirstRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyChatActivity.this, InfImplActivity.class);
                intent.putExtra(InFlmp, datas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        MyChat.setOnItemRemoveListener(new MyRecyclerView.OnItemRemoveListener() {
            @Override
            public void remove(final int i) {
                FirstBean bean = datas.get(i);
                bean.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            datas.remove(i);
                            Logger.i("删除成功");
                            MyChat.getAdapter().notifyDataSetChanged();
                            getDatasAcrossBottom();
                        }
                    }
                });
            }
        });
        getDatasAcrossBottom();
    }

    @Override
    public void onRefresh() {
        if (refreshing) {
            return;
        } else {
            getDatas();
        }
    }

    /**
     * 通过上拉分页加载
     */
    private void getDatasAcrossBottom() {
        BmobQuery<FirstBean> query = new BmobQuery<>();
        query.include("author");
        query.addWhereEqualTo("author", user.getObjectId());
        query.order("-createdAt");
        query.setSkip(datas.size());
        query.setLimit(6);
        query.findObjects(new FindListener<FirstBean>() {
            @Override
            public void done(List<FirstBean> list, BmobException e) {
                if (e == null) {
                    datas.addAll(list);
                    Logger.i(list.size() + " ");
                    if (list.size() == 6) {
                        datas.remove(datas.size() - 1);
                        Logger.i(list.size() + " ");
                        MyChat.notifyMoreFinish(true);
                    } else {
                        MyChat.notifyMoreFinish(false);
                    }
                } else {
                    Toast.makeText(MyChatActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    Logger.i(e.getMessage());

                }
            }
        });
    }

    /**
     * 下拉刷新获取最新数据
     */
    private void getDatas() {
        BmobQuery<FirstBean> query = new BmobQuery<>();
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

        query.include("author");
        query.addWhereEqualTo("author", user.getObjectId());
        query.order("createdAt");
        query.findObjects(new FindListener<FirstBean>() {
            @Override
            public void done(List<FirstBean> list, BmobException e) {
                if (e == null) {
                    for (FirstBean f : list) {
                        try {
                            String s = datas.get(0).getObjectId();
                            if (f.getObjectId().equals(s)) {

                            } else {
                                datas.add(0, f);
                            }
                        } catch (Exception ignored) {
                            datas.add(0, f);
                        }

                    }
                    Logger.i(list.size() + " ");
                    MyChat.getAdapter().notifyDataSetChanged();
                    refreshing = false;
                    refreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(MyChatActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    Logger.i(e.getMessage());
                    refreshing = false;
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
