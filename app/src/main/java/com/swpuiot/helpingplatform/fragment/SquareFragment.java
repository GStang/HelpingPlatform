package com.swpuiot.helpingplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.view.MainActivity;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_square,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("广场");
        return view;
    }
}
