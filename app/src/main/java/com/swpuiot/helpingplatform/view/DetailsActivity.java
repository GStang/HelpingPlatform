package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.CommentAdapter;
import com.swpuiot.helpingplatform.adapter.ImgAdapter;
import com.swpuiot.helpingplatform.adapter.MyAdapter;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.CommentBean;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 这个部分是用户动态的详细资料部分
 */
public class DetailsActivity extends AppCompatActivity {
    TextView authorName;
    TextView createTime;
    TextView content;
    SimpleDraweeView AuthorHeadImg;
    PostBean postBean;
    RecyclerView showImgs;
    ImgAdapter adapter;
    RecyclerView showTalks;
    EditText sendTalks;
    CommentBean commentBean;
    CommentAdapter mAdapter;
    List<CommentBean> datas;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
        getCommentData();
    }

    private void getCommentData() {
        BmobQuery<CommentBean> commentBeanBmobQuery = new BmobQuery<>();
        commentBeanBmobQuery.order("createAt");
        commentBeanBmobQuery.addWhereEqualTo("post", postBean.getObjectId());
        commentBeanBmobQuery.include("user");
        commentBeanBmobQuery.findObjects(new FindListener<CommentBean>() {
            @Override
            public void done(List<CommentBean> list, BmobException e) {
                if (e == null) {
                    datas.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化相应的控件并设置对应的点击事件
     */
    private void init() {
        authorName = (TextView) findViewById(R.id.tv_auter_name);
        createTime = (TextView) findViewById(R.id.tv_im_time);
        content = (TextView) findViewById(R.id.tv_im_context);
        AuthorHeadImg = (SimpleDraweeView) findViewById(R.id.simdra_im_image);
        showImgs = (RecyclerView) findViewById(R.id.rv_post);
        sendTalks = (EditText) findViewById(R.id.et_sendTalks);
        showTalks = (RecyclerView) findViewById(R.id.rv_talk);
        datas = new LinkedList<>();
        mAdapter = new CommentAdapter(this, datas);
        Intent intent = getIntent();
        postBean = (PostBean) intent.getSerializableExtra(PostAdapter.Details);
        showTalks.setLayoutManager(new LinearLayoutManager(this));
        showTalks.setAdapter(mAdapter);
//        showTalks.setAdapter(adapter);
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
        if (postBean.getImgs() != null) {
            adapter = new ImgAdapter(this, postBean.getImgs());
            showImgs.setAdapter(adapter);
        }

        sendTalks.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    Log.d("Post",""+num);
//                    Toast.makeText(DetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(DetailsActivity.this.getCurrentFocus().getWindowToken(),
                                    0);
                    if (sendTalks.getText().toString().equals("")) {
//                        Toast.makeText(DetailsActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    commentBean = new CommentBean();
                    commentBean.setContent(sendTalks.getText().toString().trim());
                    sendTalks.setText("");
                    commentBean.setUser(BmobUser.getCurrentUser(User.class));
                    commentBean.setPost(postBean);
                    commentBean.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(DetailsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
//                                Log.d("Post",""+num);
                                return;
                            } else {
                                Toast.makeText(DetailsActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return false;
                }
                return false;
            }
        });
    }
}
