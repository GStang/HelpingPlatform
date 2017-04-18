package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.bean.Friend;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.utils.AddFriendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShowUserActivity extends AppCompatActivity implements View.OnClickListener {
    private SimpleDraweeView background;
    private SimpleDraweeView headImg;
    private TextView name;
    private TextView information;
    private Button sendMessage;
    private Button addFriend;
    BmobIMUserInfo info;//本界面的用户的信息
    private User user;//本界面的用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        background = (SimpleDraweeView) findViewById(R.id.sim_show_background);
        headImg = (SimpleDraweeView) findViewById(R.id.sim_show_head);
        name = (TextView) findViewById(R.id.tv_show_name);
        information = (TextView) findViewById(R.id.tv_show_information);
        sendMessage = (Button) findViewById(R.id.btn_show_send);
        addFriend = (Button) findViewById(R.id.btn_show_add);
        addFriend.setOnClickListener(this);
        sendMessage.setOnClickListener(this);

        user= (User) getIntent().getSerializableExtra(FirstRecyclerAdapter.ShowInf);
        info = user.getUserInfo();
        name.setText(user.getNickName() == null ? user.getUsername()
                : user.getNickName());
        if (user.getHeadimg() == null) {
            headImg.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.loading));
        } else{
            headImg.setImageURI(user.getHeadimg().getUrl());
            background.setImageURI(user.getHeadimg().getUrl());
        }
        if (user.getSex() == 1){
            information.setText("男 "+user.getAge()+"岁");
        }else
            information.setText("女 "+user.getAge()+"岁");

    }

    /**
     * 发送添加好友的请求
     */
    private void sendAddFriendMessage() {
        //启动一个会话，如果isTransient设置为true,则不会创建在本地会话表中创建记录，
        //设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名，这里只是举个例子，其实可以不需要传发送者的信息过去
        map.put("avatar", currentUser.getAvatar());//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    Logger.i("发送");
                    Toast.makeText(ShowUserActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                } else {//发送失败
                    Toast.makeText(ShowUserActivity.this, "发送失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onChatClick() {
        //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
//        c = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("Test", "This is Test");
        intent.putExtra("c", c);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_add:
                BmobQuery<Friend> query = new BmobQuery<>();
                query.addWhereEqualTo("user", BmobUser.getCurrentUser().getObjectId());
                query.addWhereEqualTo("friendUser", info.getUserId());
                query.findObjects(new FindListener<Friend>() {
                    @Override
                    public void done(List<Friend> list, BmobException e) {
                        if (e==null){
                            if (list.size()==0){
                                sendAddFriendMessage();
                            }else {
                                Toast.makeText(ShowUserActivity.this, "他已经是您的好友", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.btn_show_send:
                onChatClick();
                break;
        }
    }
}
