package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.ImgAdapter;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;

public class DetailsActivity extends AppCompatActivity {
    TextView authorName;
    TextView createTime;
    TextView content;
    SimpleDraweeView AuthorHeadImg;
    PostBean postBean;
    RecyclerView showImgs;
    ImgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        authorName = (TextView) findViewById(R.id.tv_auter_name);
        createTime = (TextView) findViewById(R.id.tv_im_time);
        content = (TextView) findViewById(R.id.tv_im_context);
        AuthorHeadImg = (SimpleDraweeView) findViewById(R.id.simdra_im_image);
        showImgs = (RecyclerView) findViewById(R.id.rv_post);

        Intent intent = getIntent();
        postBean = (PostBean) intent.getSerializableExtra(PostAdapter.Details);

        authorName.setText(postBean.getUser().getNickName() == null ? postBean.getUser().getUsername()
                : postBean.getUser().getNickName());
        content.setText(postBean.getContent());
        if (postBean.getUser().getHeadimg() == null) {
            AuthorHeadImg.setImageURI(Uri.parse("res://com.swpuiot.helpingplatform/" + R.drawable.loading));
        } else
            AuthorHeadImg.setImageURI(postBean.getUser().getHeadimg().getUrl());

        content.setText(postBean.getContent());
        createTime.setText(postBean.getCreatedAt());

        showImgs.setLayoutManager(new GridLayoutManager(this, 3));
        if (postBean.getImgs()!=null) {
            adapter = new ImgAdapter(this, postBean.getImgs());
            showImgs.setAdapter(adapter);
        }
    }
}
