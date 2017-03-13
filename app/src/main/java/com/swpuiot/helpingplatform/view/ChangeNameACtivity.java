package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeNameACtivity extends AppCompatActivity implements View.OnClickListener {
    private EditText changeName;
    private Button saveName;
    private Button back;
    User user;
    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_activity);
        saveName = (Button) findViewById(R.id.btn_changename_save);
        back = (Button) findViewById(R.id.btn_changename_cancel);
        changeName = (EditText) findViewById(R.id.et_change);
        saveName.setOnClickListener(this);
        user = BmobUser.getCurrentUser(User.class);
        newUser = new User();
        changeName.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_changename_save:
                newUser.setNickName(changeName.getText().toString());
                newUser.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ChangeNameACtivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, getIntent().putExtra("name", newUser.getNickName()));
                            finish();
                        } else {
                            Toast.makeText(ChangeNameACtivity.this, "更新失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_changename_cancel:
                finish();
        }
    }
}
