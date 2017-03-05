package com.swpuiot.helpingplatform.view;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.support.v4.app.FragmentActivity;

import com.swpuiot.helpingplatform.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("首页");
    }

    public Toolbar gettoolbar(){
        return toolbar;
    }

}
