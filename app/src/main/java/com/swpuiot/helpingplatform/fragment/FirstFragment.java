package com.swpuiot.helpingplatform.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.FirstRecyclerAdapter;
import com.swpuiot.helpingplatform.utils.BannerLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class FirstFragment extends Fragment{

    private Banner banner;
    private List<Integer>bannerImage;
    private List<String>bannerTitle;
    private RecyclerView recyclerView;
    private FirstRecyclerAdapter firstRecyclerAdapter;
    private List<Integer>recyclerImage;
    private List<String>recyclerTitle;
    private List<String>recyclerWord;


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



        banner= (Banner) view.findViewById(R.id.banner_first);
        banner.setImageLoader(new BannerLoader())
                .setImages(bannerImage)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerTitles(bannerTitle)
                .isAutoPlay(true)
                .start();



        return view;
    }

}
