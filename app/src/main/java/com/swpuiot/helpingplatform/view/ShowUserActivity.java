package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.PostBean;

import java.util.ArrayList;
import java.util.List;

public class ShowUserActivity extends AppCompatActivity {
    private SimpleDraweeView background;
    private SimpleDraweeView headImg;
    private TextView name;
    private TextView information;
    private Button sendMessage;
    private Button addFriend;
    private List<PostBean>datas;
    private PostBean postBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        background= (SimpleDraweeView) findViewById(R.id.sim_show_background);
        headImg= (SimpleDraweeView) findViewById(R.id.sim_show_head);
        name= (TextView) findViewById(R.id.tv_show_name);
        information= (TextView) findViewById(R.id.tv_show_information);
        sendMessage= (Button) findViewById(R.id.btn_show_send);
        addFriend= (Button) findViewById(R.id.btn_show_add);

//        datas=new ArrayList<>();
//        Intent intent=getIntent();
//        postBean= (PostBean) intent.getSerializableExtra("friends");
//        name.setText(postBean.getUser().getNickName() == null ? postBean.getUser().getUsername()
//                : postBean.getUser().getNickName());
    }
}
