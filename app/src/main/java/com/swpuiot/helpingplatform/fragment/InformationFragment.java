package com.swpuiot.helpingplatform.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.InfRecyclerAdapter;
import com.swpuiot.helpingplatform.bean.InfBean;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class InformationFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<InfBean> datas;
    private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_infmation);
        datas = new LinkedList<>();
        for (int i = 0; i < 8; i++)
            datas.add(new InfBean(R.drawable.login, "测试"));
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_information);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new InfRecyclerAdapter(getActivity(), datas));
        return view;
    }
}
