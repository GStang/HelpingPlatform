package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.LoginActivity;
import com.swpuiot.helpingplatform.view.MainActivity;

import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DELL on 2017/3/5.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    Button btn_register;
    EditText et_username;
    EditText et_password;
    EditText et_surePassword;
    EditText et_email;
    ProgressBar bar;
    User user = new User();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        btn_register.setOnClickListener(this);

        return view;
    }

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initView(View view) {
        bar = (ProgressBar) view.findViewById(R.id.pb_register);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_passwords);
        et_surePassword = (EditText) view.findViewById(R.id.et_surePassword);
        et_email = (EditText) view.findViewById(R.id.et_email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                btn_register.setEnabled(false);
                bar.setVisibility(View.VISIBLE);
                user.setUsername(et_username.getText().toString().trim());
                user.setPassword(et_password.getText().toString().trim());
                user.setEmail(et_email.getText().toString());
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User o, BmobException e) {
                        if (e == null) {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                            o.setPassword(et_password.getText().toString().trim());
                            o.login(new SaveListener<User>() {
                                @Override
                                public void done(User user, BmobException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), "登录失败" + e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            bar.setVisibility(View.INVISIBLE);
                            Log.e("bmob", "失败:" + e.getMessage() + "," + e.getErrorCode());
                            Toast.makeText(getActivity(), "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_register.setEnabled(true);
                        }
                    }
                });
                break;
        }
    }
}
