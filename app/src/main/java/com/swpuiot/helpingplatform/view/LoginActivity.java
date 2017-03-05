package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.fragment.LoginFragment;
import com.swpuiot.helpingplatform.fragment.RegisterFragment;

import java.sql.SQLOutput;

public class LoginActivity extends AppCompatActivity {
    private FragmentManager manager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private RadioGroup rg_login;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        rg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_login:
                        manager.beginTransaction().replace(R.id.content_contain, loginFragment).commit();
                        break;
                    case R.id.rb_register:
                        manager.beginTransaction().replace(R.id.content_contain, registerFragment).commit();
                        break;
                }
            }
        });



    }

    private void init() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLUE);
        }
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        System.out.println(btn_login);
        rg_login = (RadioGroup) findViewById(R.id.rg_chooseLoginOrRegister);
        loginFragment = LoginFragment.newInstance();
        registerFragment = RegisterFragment.newInstance();
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.content_contain, loginFragment).commit();
    }

}
