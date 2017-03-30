package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.MyFriendAdapter;
import com.swpuiot.helpingplatform.bean.Friend;
import com.swpuiot.helpingplatform.bean.User;
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

public class MyFriendActivity extends AppCompatActivity {
    //    private TextView textView;
    private User user;
    private User myuser;
    //    private SimpleDraweeView sdv;
//    private TextView textView;
//    private LinearLayout linearLayout;
    private List<BmobIMUserInfo> datas;
    public BmobIMConversation c;
    private RecyclerView recyclerView;
    private MyFriendAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriend);
        initView();
        BmobIM.connect(myuser.getObjectId(), new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(MyFriendActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
    }

    private void initView() {
        myuser = BmobUser.getCurrentUser(User.class);
        datas = new ArrayList<>();
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
                datas.add(info);
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Log.e("IM", e.getMessage() + e.getErrorCode());
        }
    }

//    /**
//     查询本地会话
//     */
//    public void query(){
//        UserModel.getInstance().queryFriends(new FindListener<Friend>() {
//
//            @Override
//            public void done(List<Friend> list, BmobException e) {
//                if (e==null){
//                    datas.addAll(list);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//
//
//
//        });
//    }
//        linearLayout = (LinearLayout) findViewById(R.id.ll_test);
//        textView = (TextView) findViewById(R.id.tv_test_name);
//        sdv = (SimpleDraweeView) findViewById(R.id.sdv_testhead);
//        myuser = BmobUser.getCurrentUser(User.class);
//        BmobQuery<User> query = new BmobQuery<>();
//        query.addWhereEqualTo("username", "笨笨的故事");
//        query.findObjects(new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e == null) {
//                    user = list.get(0);
//                    sdv.setImageURI(user.getHeadimg().getUrl());
//                    textView.setText(user.getUsername());
//                }
//            }
//        });
//        BmobIM.connect(myuser.getObjectId(), new ConnectListener() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(MyFriendActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
//                    Log.e("IM", "Connection Success");
//                } else {
//                    Log.e("IM", e.getErrorCode() + e.getMessage());
//                }
//            }
//        });
//
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                c = BmobIM.getInstance().startPrivateConversation(new BmobIMUserInfo(user.getObjectId()
//                        , user.getUsername(), user.getAvatar()), null);
//                Intent intent = new Intent(MyFriendActivity.this, ChatActivity.class);
//                intent.putExtra("c", c);
//                startActivity(intent);
//            }
//        });
//
////        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
////            @Override
////            public void onChange(ConnectionStatus status) {
////                Log.i("IM", status.getCode() + status.getMsg());
////            }
////        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BmobIM.getInstance().disConnect();
//    }
}
