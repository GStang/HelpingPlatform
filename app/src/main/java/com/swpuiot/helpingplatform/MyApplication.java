package com.swpuiot.helpingplatform;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.swpuiot.helpingplatform.service.MyMessageHandler;
import cn.bmob.newim.BmobIM;



/**
 * Created by DELL on 2017/3/6.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        BmobIM.init(getApplicationContext());
//        BmobIM.registerDefaultMessageHandler(new MyMessageHandler(this));
    }


}
