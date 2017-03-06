package com.swpuiot.helpingplatform.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends Activity {

    private SimpleDraweeView draweeView;
    private Uri uri;
    private Timer timer;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.loading);
        final Intent intent = new Intent(this, LoginActivity.class);
        task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
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
