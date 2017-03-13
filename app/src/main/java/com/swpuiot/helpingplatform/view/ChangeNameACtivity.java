package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.swpuiot.helpingplatform.R;

public class ChangeNameACtivity extends AppCompatActivity {
    private EditText changeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_activity);

        changeName= (EditText) findViewById(R.id.et_change);

        String exName=getIntent().getStringExtra("name");
        changeName.setText(exName);


    }
}
