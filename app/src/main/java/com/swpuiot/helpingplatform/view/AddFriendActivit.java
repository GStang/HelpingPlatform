package com.swpuiot.helpingplatform.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.AddFriendAdapter;
import com.swpuiot.helpingplatform.bean.Friend;
import com.swpuiot.helpingplatform.bean.NewFriend;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.dao.FriendDao;
import com.swpuiot.helpingplatform.event.AddFriendEvent;
import com.swpuiot.helpingplatform.event.AgreeEvent;
import com.swpuiot.helpingplatform.utils.AddFriendMessage;
import com.swpuiot.helpingplatform.utils.AgreeAddFriendMessage;
import com.swpuiot.helpingplatform.utils.UserModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 这个部分是收到用户添加好友的请求的处理部分
 */
public class AddFriendActivit extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AddFriendAdapter adapter;
    private List<BmobIMUserInfo> datas;
    private SQLiteDatabase database;
    private FriendDao friendDao;
    private Cursor cursor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        datas = new LinkedList<>();
        adapter = new AddFriendAdapter(this, datas);
        recyclerView = (RecyclerView) findViewById(R.id.rv_addfriend);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        friendDao = FriendDao.getInstance(null, null, null, 1);
        database = friendDao.getWritableDatabase();
        cursor = database.rawQuery("select * from friend", null);
        int num = cursor.getCount();
        if (num != 0) {
            while (cursor.moveToNext()) {
                String objectid = cursor.getString(0);
                String username = cursor.getString(1);
                String headimg = cursor.getString(2);
//                Logger.i(headimg);
                datas.add(new BmobIMUserInfo(objectid, username, headimg));
            }
        }
        adapter.notifyDataSetChanged();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.releaseReference();
        cursor.close();
    }
//    /**
//     * 监听接收新好友
//     */
//    @Subscribe
//    public void onEventMainThread(AddFriendEvent event) {
//        String msg = "收到了消息：";
//        Logger.i(msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//        datas.add(event.getInfo());
//        adapter.notifyDataSetChanged();
////        IsAggreed(event);
//    }

    /**
     * 监听接受点击了接受按钮，向对方发送确认消息
     */
    @Subscribe
    public void onEventMainThread(final AgreeEvent event) {
        final BmobIMUserInfo info = event.getInfo();
        Logger.i("发送同意消息");
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        AgreeAddFriendMessage message = new AgreeAddFriendMessage();
        conversation.sendMessage(message, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null) {
                    Toast.makeText(AddFriendActivit.this, "发送成功", Toast.LENGTH_SHORT).show();
                    Friend friend = new Friend();
                    friend.setUser(BmobUser.getCurrentUser(User.class));
                    User newfriend = new User();
                    newfriend.setObjectId(info.getUserId());
                    friend.setFriendUser(newfriend);
                    friend.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddFriendActivit.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                                datas.remove(event.getPositon());
                                adapter.notifyDataSetChanged();
                                database.delete("friend", "objectid='" + event.getInfo().getUserId() + "'", null);
                                database.execSQL("delete from friend where objectid ='" + event.getInfo().getUserId() + "'");
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(AddFriendActivit.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddFriendActivit.this, "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void IsAggreed(AddFriendEvent event) {
        BmobIMMessage msg = event.getMessage();
        String s = event.getMessage().getMsgType();
        if (s.equals("add")) {
            NewFriend friend = AddFriendMessage.convert(msg);
        }
    }


}
