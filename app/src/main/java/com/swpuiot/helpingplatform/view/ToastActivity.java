
package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.swpuiot.helpingplatform.R;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.out_from_bottom, 0);
        Log.e("Success","FINISH");
    }
}
