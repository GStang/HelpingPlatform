package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FriendAdapter;
import com.swpuiot.helpingplatform.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindUserActivity extends AppCompatActivity {
    private SearchView searchView;
    private BmobQuery<User> userBmobQuery;
    private RecyclerView recyclerView;
    private FriendAdapter adapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        initView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userBmobQuery.addWhereEqualTo("username", searchView.getQuery().toString());
                userBmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
//                            Toast.makeText(FindUserActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                            if (list.size()==0){
                                Toast.makeText(FindUserActivity.this, "没有找到对应的用户", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            users.clear();
                            users.addAll(list);
                            adapter.notifyDataSetChanged();
                        }else
                        {
                            Logger.e(e.getMessage());
                        }
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_findUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();
        adapter = new FriendAdapter(FindUserActivity.this, users);
        recyclerView.setAdapter(adapter);
        userBmobQuery = new BmobQuery<>();
        searchView = (SearchView) findViewById(R.id.search_view);
    }
}
