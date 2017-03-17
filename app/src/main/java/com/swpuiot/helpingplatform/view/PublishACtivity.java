package com.swpuiot.helpingplatform.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PublishAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText editContent;
    private User user;
    private List<Bitmap> datas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_activity);
        datas = new LinkedList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar_publish);
        editContent = (EditText) findViewById(R.id.et_publish);
        user = BmobUser.getCurrentUser(User.class);
        recyclerView = (RecyclerView) findViewById(R.id.rv_shoot);
        toolbar.inflateMenu(R.menu.menu_publish);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new PublishAdapter(this, datas));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        datas.add(bitmap);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_publish:
                        if (editContent.getText().toString().trim().equals("")) {
                            Toast.makeText(PublishActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        dopublish();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void dopublish() {
        PostBean postBean = new PostBean();
        postBean.setContent(editContent.getText().toString().trim());
        postBean.setUser(user);
        postBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("Error", e.getMessage() + e.getErrorCode());
                    Toast.makeText(PublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
