package com.swpuiot.helpingplatform.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.swpuiot.helpingplatform.R;
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
    private List<Integer>imageList;
    private List<String>titleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first,container,false);

        imageList= Arrays.asList(
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car7,
                R.drawable.car8,
                R.drawable.car9
        );
        titleList=Arrays.asList(
                "成都街头惊现镀金兰博基尼",
                "布加迪是否如传说中的那么厉害",
                "超跑地狱崭露头角，震撼来袭",
                "美国地产大亨的大院与超跑",
                "第四届翼灵超跑大赛拉开序幕"
        );

        banner= (Banner) view.findViewById(R.id.banner_first);
        banner.setImageLoader(new BannerLoader())
                .setImages(imageList)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .start();

        return view;
    }
}
