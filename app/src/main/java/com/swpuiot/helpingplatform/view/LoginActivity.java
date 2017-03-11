package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.fragment.LoginFragment;
import com.swpuiot.helpingplatform.fragment.RegisterFragment;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
    private TextView register;
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private SimpleDraweeView simpleDraweeView;
    private User user;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        simpleDraweeView.setImageURI("res://com.swpuiot.helpingplatform/" + R.drawable.logo);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                            btnLogin.setEnabled(true);
                        }
                    }
                });
            }
        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//            }
//        });
    }

    private void init() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_logo);
        btnLogin = (Button) findViewById(R.id.btn_login);
        user = new User();
        bar = (ProgressBar) findViewById(R.id.bar_loading);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_passwords);
        register = (TextView) findViewById(R.id.tv_register);
//        rg_login = (RadioGroup) findViewById(R.id.rg_chooseLoginOrRegister);
//        loginFragment = LoginFragment.newInstance();
//        registerFragment = RegisterFragment.newInstance();
//        manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .add(R.id.content_contain, loginFragment).commit();
    }

//    public LoginFragment getLoginFragment() {
//        return loginFragment;
//    }
//
//    public RegisterFragment getRegisterFragment() {
//        return registerFragment;
//    }

    public void onRegisterClick(View view123132){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
