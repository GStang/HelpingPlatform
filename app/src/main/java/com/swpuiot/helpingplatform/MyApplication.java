package com.swpuiot.helpingplatform;

import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.MainActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by DELL on 2017/3/6.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        Bmob.initialize(getApplicationContext(), "9541a295b6acddf6d9bf7ddb52f1227c");
    }
}
