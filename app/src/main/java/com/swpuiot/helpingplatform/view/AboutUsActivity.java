package com.swpuiot.helpingplatform.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView textView = (TextView) findViewById(R.id.et_hostname);
        TextView textView1 = (TextView) findViewById(R.id.et_github);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.icon2);
        SimpleDraweeView icon = (SimpleDraweeView) findViewById(R.id.icon3);
        icon.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.logo));
        simpleDraweeView.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.yilingstudio));
        textView.setText("http://www.swpuiot.com/");
        textView1.setText("https://github.com/GStang/HelpingPlatform");
    }
}
