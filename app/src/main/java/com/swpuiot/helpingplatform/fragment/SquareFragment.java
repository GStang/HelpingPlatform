package com.swpuiot.helpingplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.PostAdapter;
import com.swpuiot.helpingplatform.bean.PostBean;
import com.swpuiot.helpingplatform.bean.User;
import com.swpuiot.helpingplatform.view.PublishACtivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class SquareFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private Context context;
    //    private Button btnCommit;
//    private Button btnGetdata;
    private List<PostBean> datas;
    private PostAdapter adapter;
    private User user;
    private Toolbar toolbar;
    private FloatingActionButton fabButton;
    private String lastTime;
    private SwipeRefreshLayout refresh;
    private boolean refreshing = false;
    private ProgressBar bar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        initView(view);
        refreshing = true;
        refresh.setRefreshing(true);
        bar.setVisibility(View.GONE);
        getDatas();
        return view;
    }

    private void initView(View view) {
        context = getActivity();
        datas = new LinkedList<>();
        bar = (ProgressBar) view.findViewById(R.id.pb_refresh);
        adapter = new PostAdapter((AppCompatActivity) context, datas);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
//        btnCommit = (Button) view.findViewById(R.id.btn_commit);
        fabButton = (FloatingActionButton) view.findViewById(R.id.fb_add);
        fabButton.setOnClickListener(this);
//        btnCommit.setOnClickListener(this);
//        btnGetdata = (Button) view.findViewById(R.id.btn_getdate);
//        btnGetdata.setOnClickListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_square);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_square_toolbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
//        recyclerView.addView(new TextView(context),0);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        user = BmobUser.getCurrentUser(User.class);
        super.onCreate(savedInstanceState);
    }

    public void getDatas() {
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            date = sdf.parse(lastTime);
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("user");
        query.order("-createdAt");
//        query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "查询成功，共有" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                    datas.clear();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                    lastTime = list.get(0).getCreatedAt();
                    refresh.setRefreshing(false);
                    refreshing = false;
                    if (bar.getVisibility()==View.VISIBLE){
                        bar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_add:
                publishData();
                break;
        }
    }

    private void publishData() {
        Intent intent = new Intent(getActivity(), PublishACtivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh.setRefreshing(true);
        refreshing = true;
        getDatas();
    }

    @Override
    public void onRefresh() {
        if (refreshing) {
            return;
        } else {
            refreshing = true;
            getDatas();
        }
    }
}

