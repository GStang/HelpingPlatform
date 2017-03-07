package com.swpuiot.helpingplatform.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.utils.BannerLoader;
import com.swpuiot.helpingplatform.view.InfImplActivity;
import com.swpuiot.helpingplatform.view.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private Banner banner;
    private List<Integer>bannerImage;
    private List<String>bannerTitle;
    private RecyclerView recyclerView;
    private FirstRecyclerAdapter firstRecyclerAdapter;
    private List<Integer>recyclerImage;
    private List<String>recyclerTitle;
    private List<String>recyclerWord;
    private static final int REFRESH_COMPLETE=0x110;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;


    private List<String>a=Arrays.asList("你好");
    private List<String>b=Arrays.asList("好呀好呀");
    private List<Integer>c=Arrays.asList(R.drawable.car4);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first,container,false);

        bannerImage= Arrays.asList(
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car7,
                R.drawable.car8,
                R.drawable.car9
        );
        bannerTitle=Arrays.asList(
                "成都街头惊现镀金兰博基尼",
                "布加迪是否如传说中的那么厉害",
                "超跑地狱崭露头角，震撼来袭",
                "美国地产大亨的大院与超跑",
                "第四届翼灵超跑大赛拉开序幕"
        );

        recyclerImage=Arrays.asList(
                R.drawable.new1,
                R.drawable.new2,
                R.drawable.new3,
                R.drawable.new4
        );

        recyclerTitle=Arrays.asList(
                "石大春景",
                "纸上传奇",
                "田径喜讯",
                "翼灵热血"
        );

        recyclerWord=Arrays.asList(
                "石大春景|春风十里不如石大美景",
                "手绘牛人用画笔赋予动物神奇色彩，震撼人心",
                "蜀道驿传接力马拉松结束，我校几只队伍均获得不错的名次",
                "新学期到来，翼灵团队安卓组成员另类合照演绎青春"
        );

        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_first);
        firstRecyclerAdapter=new FirstRecyclerAdapter(getActivity(),recyclerTitle,recyclerWord,recyclerImage);
        recyclerView.setAdapter(firstRecyclerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        firstRecyclerAdapter.setOnItemClickListener(new FirstRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), InfImplActivity.class);
                intent.putExtra("title", recyclerTitle.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_first);
        swipeRefreshLayout.setOnRefreshListener(this);

        banner= (Banner) view.findViewById(R.id.banner_first);
        banner.setImageLoader(new BannerLoader())
                .setImages(bannerImage)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerTitles(bannerTitle)
                .isAutoPlay(true)
                .start();

        toolbar= (Toolbar) view.findViewById(R.id.toolbar_first);
        toolbar.setTitle("首页");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_first_search:
                        Intent intent=new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        return view;
    }


    @Override
    public void onRefresh() {

        Toast.makeText(getActivity(), "访问", Toast.LENGTH_SHORT).show();
        firstRecyclerAdapter.upData(a, b, c);
        firstRecyclerAdapter.notifyDataSetChanged();
        firstRecyclerAdapter.addData(0);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_first_toolbar, menu);
    }

}
