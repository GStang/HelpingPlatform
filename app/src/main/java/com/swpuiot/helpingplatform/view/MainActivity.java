package com.swpuiot.helpingplatform.view;

import android.app.Activity;
import android.os.Build;
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

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "欢迎您" + BmobUser.getCurrentUser(User.class).getUsername()
                , Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    public Toolbar gettoolbar() {
        return toolbar;
    }

}
