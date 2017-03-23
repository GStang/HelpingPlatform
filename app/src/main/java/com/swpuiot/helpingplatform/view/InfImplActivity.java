package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.First;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.fragment.FirstFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class InfImplActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView title;
    private SimpleDraweeView headImage;
    private TextView name;
    private TextView time;
    private TextView word;
    private FloatingActionButton getMission;
    private List<FirstBean>datas;
    private User user;
    FirstBean firstBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_impl);

        datas = new ArrayList<>();

        toolbarTitle = (TextView) findViewById(R.id.tv_im_toolbar_title);
        title = (TextView) findViewById(R.id.tv_im_title);
        headImage = (SimpleDraweeView) findViewById(R.id.simdra_im_image);
        name = (TextView) findViewById(R.id.tv_auter_name);
        time = (TextView) findViewById(R.id.tv_im_time);
        word = (TextView) findViewById(R.id.tv_im_context);
        getMission = (FloatingActionButton) findViewById(R.id.fab_im_mission);

        getMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfImplActivity.this, "抢单", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent=getIntent();
        firstBean = (FirstBean) intent.getSerializableExtra(FirstFragment.InFlmp);

        name.setText(firstBean.getAuthor().getNickName() == null ? firstBean.getAuthor().getUsername()
                : firstBean.getAuthor().getNickName());
        word.setText(firstBean.getContent());
        if (firstBean.getAuthor().getHeadimg() == null) {
            headImage.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.loading));
        } else
            headImage.setImageURI(firstBean.getAuthor().getHeadimg().getUrl());
        title.setText(firstBean.getTitle());
        toolbarTitle.setText(firstBean.getTitle());
        time.setText(firstBean.getCreatedAt());

    }






}
