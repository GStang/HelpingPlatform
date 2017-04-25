package com.swpuiot.helpingplatform.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.dao.FriendDao;
import com.swpuiot.helpingplatform.event.AddFriendEvent;
import com.swpuiot.helpingplatform.fragment.NavigationFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends AppCompatActivity {
    private User user;
    private NavigationFragment fragment = new NavigationFragment();
    private FriendDao frienddao;
    private SQLiteDatabase database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorLightYellow));
        }

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
        fragment.setRetainInstance(true);
        user = BmobUser.getCurrentUser(User.class);
        if (user != null) {
            Toast.makeText(MainActivity.this, "欢迎您" + BmobUser.getCurrentUser(User.class).getUsername()
                    , Toast.LENGTH_SHORT).show();
            BmobIM.getInstance().updateUserInfo(user.getUserInfo());
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "连接服务器失败，请检查您的网络", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            frienddao = FriendDao.getInstance(this, "MyFriend.db", null, 1);
//            frienddao.getWritableDatabase();
        } else {
            Toast.makeText(MainActivity.this, "游客登录", Toast.LENGTH_SHORT).show();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_content, fragment).commit();
    }

    /**
     * 监听接收新好友
     */
    @Subscribe
    public synchronized void onEventMainThread(AddFriendEvent event) {
        String msg = "收到了消息：";
        Logger.i(msg);
        SQLiteDatabase database = frienddao.getWritableDatabase();
        BmobIMUserInfo info = event.getInfo();
        cursor = database.rawQuery("select * from friend",null);
        while(cursor.moveToNext()){
            if (cursor.getString(0).equals(event.getInfo().getUserId())) {
                return;
            }
        }
        String sql = "insert into friend values('" + info.getUserId() + "','" + info.getName() + "','" + info.getAvatar() + "')";
        database.execSQL(sql);

//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//        datas.add(event.getInfo());
//        adapter.notifyDataSetChanged();
//        IsAggreed(event);
    }

}
