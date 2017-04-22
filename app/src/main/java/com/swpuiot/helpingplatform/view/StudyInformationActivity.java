package com.swpuiot.helpingplatform.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.StudyInformationAdapter;
import com.swpuiot.helpingplatform.bean.FriendInfBean;
import com.swpuiot.helpingplatform.bean.StudyBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.fragment.InformationFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudyInformationActivity extends AppCompatActivity {
    private StudyInformationAdapter studyInformationAdapter;
    private List<StudyBean>datas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_information);

        datas=new ArrayList<>();

        getDatas();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_study_information);
        studyInformationAdapter=new StudyInformationAdapter(this,datas);
        recyclerView.setAdapter(studyInformationAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        studyInformationAdapter.setOnItemClickListener(new StudyInformationAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent=new Intent(StudyInformationActivity.this,ShowStudyInformationActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });


    }
    public void getDatas(){
        BmobQuery<StudyBean> query=new BmobQuery<>();
        query.include("author");
        final int sign=getIntent().getIntExtra(InformationFragment.InfFra,0);
        query.addWhereEqualTo("sign",sign+1);
        query.findObjects(new FindListener<StudyBean>() {
            @Override
            public void done(List<StudyBean> list, BmobException e) {
                if (e == null) {
                    datas.clear();
                    studyInformationAdapter.notifyItemRangeChanged(0, studyInformationAdapter.getItemCount());
                    datas.addAll(list);
                    studyInformationAdapter.notifyDataSetChanged();
                }else{
                    System.out.println("查询失败");
                }
            }
        });
    }

}
