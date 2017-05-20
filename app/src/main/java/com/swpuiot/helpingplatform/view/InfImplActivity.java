package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.First;
import com.swpuiot.helpingplatform.bean.FirstBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.fragment.FirstFragment;
import com.swpuiot.helpingplatform.utils.CameraUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class InfImplActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView title;
    private SimpleDraweeView headImage;
    private TextView name;
    private TextView time;
    private TextView word;
    private FloatingActionButton getMission;
    private List<FirstBean> datas;
    FirstBean firstBean;
    FirstBean newFirstBean;
    private String id;

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

        Intent intent = getIntent();
        firstBean = (FirstBean) intent.getSerializableExtra(FirstFragment.InFlmp);
        id=intent.getStringExtra("send_id");

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

        getMission = (FloatingActionButton) findViewById(R.id.fab_im_mission);

        getMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<FirstBean> query = new BmobQuery<FirstBean>();
                query.getObject(id, new QueryListener<FirstBean>() {
                    @Override
                    public void done(FirstBean firstBean, BmobException e) {
                        if(e==null){
                            if(firstBean.getAlive()){
                                getMissionMethod();
                            }else {
                                Toast.makeText(InfImplActivity.this,"这个已经被抢走了哟！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(InfImplActivity.this, "服务器错误，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                System.out.println("查询id为"+firstBean.getObjectId());
//                if(avaiableGetMission()){
//                    getMissionMethod();
//                }else {
//                    Toast.makeText(InfImplActivity.this,"这个已经被抢走了哟！",Toast.LENGTH_SHORT).show();
//
//                }
            }
        });

    }

    public void getMissionMethod(){
        newFirstBean = new FirstBean();
        newFirstBean.setAlive(false);
        newFirstBean.setAcceptUser(BmobUser.getCurrentUser(User.class));
        newFirstBean.update(firstBean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(InfImplActivity.this, "抢单成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InfImplActivity.this, "抢单失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//    public boolean avaiableGetMission() {
//        final boolean[] ava = {false};
//        BmobQuery<FirstBean> query = new BmobQuery<FirstBean>();
////        int id=getIntent().getIntExtra("send_id",0);
//        System.out.println("传送id为"+id);
//        query.getObject(id + "", new QueryListener<FirstBean>() {
//            @Override
//            public boolean done(FirstBean object, BmobException e) {
//                if (e == null) {
//                    boolean b = object.getAlive();
//                    System.out.println("存在值为："+b);
//                    return b;
//                } else {
//                    System.out.println("查询状态错误");
//                }
//                return object.getAlive();
//            }
//        });
//
//        return ava[0];
//    }


}
