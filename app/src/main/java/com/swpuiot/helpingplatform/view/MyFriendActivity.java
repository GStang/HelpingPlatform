package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.MyFriendAdapter;
import com.swpuiot.helpingplatform.bean.Friend;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.dao.FriendDao;
import com.swpuiot.helpingplatform.utils.UserModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 联系人界面
 */
public class MyFriendActivity extends AppCompatActivity {
    private User myuser;
    private List<BmobIMUserInfo> datas;
    public BmobIMConversation c;
    private FriendDao frienddao;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyFriendAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriend);
        initView();
        frienddao = new FriendDao(this, "MyFriend.db", null, 1);
        frienddao.getWritableDatabase();
        BmobIM.connect(myuser.getObjectId(), new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(MyFriendActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
//                    getAllConversation();
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
    }

    /**
     * 获得所有的会话
     */
//    private void getAllConversation() {
//        conversations = BmobIM.getInstance().loadAllConversation();
//        for (BmobIMConversation con : conversations) {
//            Logger.i(con.getConversationTitle());
//        }
//    }

    /**
     * 初始化控件
     */
    private void initView() {
        myuser = BmobUser.getCurrentUser(User.class);
        getFriend();
        datas = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar_MyFriend);
        toolbar.inflateMenu(R.menu.menu_myfriend);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_friend) {
                    Intent intent = new Intent(MyFriendActivity.this, FindUserActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        adapter = new MyFriendAdapter(this, datas);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.rv_myfriend);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriend();
            }
        });
    }

    /**
     * 获取用户的好友列表
     */
    private void getFriend() {
        BmobQuery<Friend> query = new BmobQuery<Friend>("Friend");
        query.addWhereEqualTo("user", myuser);
        query.include("friendUser");
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                findFriends(list, e);
            }
        });
    }

    /**
     * 查找好友并添加到adapter中
     */
    private void findFriends(List<Friend> list, BmobException e) {
        if (e == null) {
            Log.i("Friend", "Success");
            for (Friend friend : list) {
                BmobIMUserInfo info = new BmobIMUserInfo
                        (friend.getFriendUser().getObjectId(), friend.getFriendUser()
                                .getUsername(), friend.getFriendUser().getAvatar());
                BmobIM.getInstance().updateUserInfo(info);
                datas.clear();
                datas.add(info);
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Log.e("IM", e.getMessage() + e.getErrorCode());
        }
    }

    /**
     * 断开服务器的连接
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmobIM.getInstance().disConnect();
    }

    @Override
    protected void onResume() {
        getFriend();
        super.onResume();
    }
}
