package com.swpuiot.helpingplatform.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.mylibrary.MyRecyclerView;
import com.swpuiot.mylibrary.RecyclerItemTouchHelper;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我的动态
 *
 * @author DELL
 *         {}
 */
public class MySquareActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private MyRecyclerView MyChat;
    private SwipeRefreshLayout refreshLayout;
    private PostAdapter adapter;
    private List<PostBean> datas;
    private boolean refreshing = false;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_squaer);
        initView();
    }

    private void initView() {
        MyChat = (MyRecyclerView) findViewById(R.id.recycler_mychar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        refreshLayout.setOnRefreshListener(this);
        MyChat.setLayoutManager(new LinearLayoutManager(this));
        datas = new LinkedList<>();
        adapter = new PostAdapter(this, datas);
        MyChat.setAdapter(adapter);
        user = BmobUser.getCurrentUser(User.class);
        ItemTouchHelper.Callback callback = new RecyclerItemTouchHelper((MyRecyclerView.AutoAdapter) MyChat.getAdapter());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(MyChat);
        MyChat.setOnItemRemoveListener(new MyRecyclerView.OnItemRemoveListener() {
            @Override
            public void remove(final int i) {
                PostBean bean = datas.get(i);
                bean.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            datas.remove(i);
                            Logger.i("删除成功");
                            MyChat.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        getDatas();
    }

    @Override
    public void onRefresh() {
        if (refreshing) {
            return;
        } else {
            getDatas();
        }
    }

    private void getDatas() {
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("user");
        query.addWhereEqualTo("user", user.getObjectId());
        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {
                    datas.clear();
                    datas.addAll(list);
                    Logger.i(list.size() + " ");
                    MyChat.notifyMoreFinish(false);
                    refreshing = false;
                    refreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(MySquareActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    refreshing = false;
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
