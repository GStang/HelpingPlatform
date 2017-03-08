package com.swpuiot.helpingplatform.view;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.fragment.FirstFragment;
import com.swpuiot.helpingplatform.fragment.NavigationFragment;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private User user;
//    private FirstFragment fragment = new FirstFragment();
    private NavigationFragment fragment = new NavigationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment.setRetainInstance(true);
        user = BmobUser.getCurrentUser(User.class);
        if (user!=null) {
            Toast.makeText(MainActivity.this, "欢迎您" + BmobUser.getCurrentUser(User.class).getUsername()
                    , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "游客登录", Toast.LENGTH_SHORT).show();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_content,fragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.layout_context, fragment).commit();
    }
//    public FirstFragment getFragment(){
//        return fragment;
//    }
}
