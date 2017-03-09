package com.swpuiot.helpingplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.InfRecyclerAdapter;
import com.swpuiot.helpingplatform.adapter.TestAdapter;
import com.swpuiot.helpingplatform.bean.InfBean;
import com.swpuiot.helpingplatform.bean.TestBean;
import com.swpuiot.helpingplatform.view.MainActivity;
import com.swpuiot.helpingplatform.view.SearchActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<TestBean> datas;
    private Button btnGetDatas;
    private TestAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_test);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_square);
        setHasOptionsMenu(true);
        btnGetDatas = (Button) view.findViewById(R.id.btn_get);
        toolbar.inflateMenu(R.menu.menu_square_toolbar);
        datas = new ArrayList<>();
        adapter = new TestAdapter(getActivity(), datas);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_square_search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        btnGetDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatas();
            }
        });

        return view;
    }

    public void getDatas() {
        BmobQuery<TestBean> query = new BmobQuery<>("TestBean");
        query.setLimit(2);
        query.findObjects(new FindListener<TestBean>() {
            @Override
            public void done(List<TestBean> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "查询成功,共有" + list.size(), Toast.LENGTH_SHORT).show();
                    System.out.println(list);
                    TestBean bean = list.get(0);
                    System.out.println(bean);
                    System.out.println(bean.getTest());
                    System.out.println(bean.getImg().getFileUrl());
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("bmob", "失败" + e.getMessage());
                }
            }
        });
    }

}
