package com.swpuiot.helpingplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.helpingplatform.R;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class ChatFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }
}
