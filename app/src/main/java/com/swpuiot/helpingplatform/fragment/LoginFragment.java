package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.MainActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DELL on 2017/3/5.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btn_login;
    private EditText et_username;
    private EditText et_password;
    private ProgressBar bar;
    private Button btn_scale;
    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login = (Button) view.findViewById(R.id.commit);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_passwords);
        bar = (ProgressBar) view.findViewById(R.id.pb_login);
        btn_scale = (Button) view.findViewById(R.id.btn_scale);
        btn_login.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        return view;
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                btn_login.setEnabled(false);
                bar.setVisibility(View.VISIBLE);
                user.setUsername(et_username.getText().toString().trim());
                user.setPassword(et_password.getText().toString().trim());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            bar.setVisibility(View.GONE);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                            btn_login.setEnabled(true);
                        }
                    }
                });
                break;
            case R.id.btn_scale:
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
