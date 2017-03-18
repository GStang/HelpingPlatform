package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.First;

public class InfImplActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView title;
    private SimpleDraweeView headImage;
    private TextView name;
    private TextView time;
    private TextView word;
    private First first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_impl);

        first=new First();

        toolbarTitle= (TextView) findViewById(R.id.tv_im_toolbar_title);
        title= (TextView) findViewById(R.id.tv_im_title);
        headImage= (SimpleDraweeView) findViewById(R.id.simdra_im_image);
        name= (TextView) findViewById(R.id.tv_auter_name);
        time= (TextView) findViewById(R.id.tv_im_time);
        word= (TextView) findViewById(R.id.tv_im_context);

        first.initData();


        Intent intent=getIntent();
        int position=intent.getIntExtra("position",1);
        Log.e("Sucess",position+"");

        toolbarTitle.setText(first.getRecyclerTitle().get(position));
        title.setText(first.getRecyclerTitle().get(position));
        Uri uri = Uri.parse("res://com.swpuiot.helpingplatform/" + first.getRecyclerImage().get(position));

        headImage.setImageURI(uri);

        name.setText(first.getRecyclerName().get(position));
        time.setText(first.getRecyclerTime().get(position));
        word.setText(first.getRecyclerWord().get(position));

    }
}
