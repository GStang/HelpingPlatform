package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.bean.YueData;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class ChatFragment extends Fragment{

    private YueData yueData;
    private EditText editText_date;
    private EditText editText_time;
    private EditText editText_title;
    private EditText editText_plan;
    private EditText editText_phone;
    private ImageButton button_select;
    private ImageButton button_add;
    private Toolbar toolbar_chat;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        toolbar_chat = (Toolbar) view.findViewById(R.id.toolbar_chat);
        editText_date = (EditText) view.findViewById(R.id.chat_edit_date);
        editText_time = (EditText) view.findViewById(R.id.chat_edit_time);
        editText_title = (EditText) view.findViewById(R.id.chat_edit_title);
        editText_plan = (EditText) view.findViewById(R.id.chat_edit_plan);
        editText_phone = (EditText) view.findViewById(R.id.chat_edit_phone);
        button_select = (ImageButton) view.findViewById(R.id.chat_button_select);
        button_add = (ImageButton) view.findViewById(R.id.chat_button_add);


        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yueData = new YueData();
                yueData.setDate(editText_date.getText().toString());
                yueData.setTime(editText_time.getText().toString());
                yueData.setTitle(editText_title.getText().toString());
                yueData.setPlan(editText_plan.getText().toString());
                yueData.getPhone(editText_phone.getText().toString());
            }
        });

//        button_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent,1);
//            }
//        });

        return view;
    }

//    protected void onActivityResult
}
