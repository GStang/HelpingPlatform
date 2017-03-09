package com.swpuiot.helpingplatform.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.MyApplication;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.User;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

public class LoadingActivity extends Activity {

    private SimpleDraweeView draweeView;
    private Uri uri;
    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        final User user = ((MyApplication) getApplication()).getUser();
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.loading);
        final Intent intent = new Intent(this, LoginActivity.class);
        task = new TimerTask() {
            @Override
            public void run() {
                if (user == null) {
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent1 = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 3000);
        draweeView = (SimpleDraweeView) findViewById(R.id.sdv_loading);
        if (draweeView != null) {
            draweeView.setImageURI(uri);
        }
    }
}
