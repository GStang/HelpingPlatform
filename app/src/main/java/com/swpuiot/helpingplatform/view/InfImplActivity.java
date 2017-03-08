package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.swpuiot.helpingplatform.R;

public class InfImplActivity extends AppCompatActivity {

    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_impl);
        tv_show = (TextView) findViewById(R.id.tv_show);

        String str = getIntent().getStringExtra("sbj");
        String string=getIntent().getStringExtra("title");
        String string1=getIntent().getStringExtra("bannerTitle");
        if (str != null)
            tv_show.setText(str);
        if (string !=null)
            tv_show.setText(string);
        if(string1!=null)
            tv_show.setText(string1);
    }
}
