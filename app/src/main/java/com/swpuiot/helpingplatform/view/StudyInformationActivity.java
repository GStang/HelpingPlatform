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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyInformationActivity extends AppCompatActivity {
    private List<String>title;
    private List<String>authorName;
    private StudyInformationAdapter studyInformationAdapter;
    private List<StudyBean>datas;
    private User user;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_information);

        datas=new ArrayList<>();

        title= Arrays.asList(
                "大学物理所有重要公式及其例题讲解",
                "Android关于编译的那些坑，十分钟解决编译难点",
                "托福雅思重点单词及考试题型分析"
        );
        authorName=Arrays.asList(
                "BY:笨笨的故事",
                "BY:唐光圣",
                "BY:宋飞飞"
        );

        recyclerView= (RecyclerView) findViewById(R.id.recycler_study_information);
        studyInformationAdapter=new StudyInformationAdapter(this,datas);
        recyclerView.setAdapter(studyInformationAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        studyInformationAdapter.setOnItemClickListener(new StudyInformationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(StudyInformationActivity.this,ShowStudyInformationActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

}
