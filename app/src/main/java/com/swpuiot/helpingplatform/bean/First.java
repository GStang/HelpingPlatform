package com.swpuiot.helpingplatform.bean;

import com.swpuiot.helpingplatform.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DuZeming on 2017/3/18.
 */
public class First {

    private List<Integer> recyclerImage;
    private List<String>recyclerTitle;
    private List<String>recyclerWord;
    private List<String>recyclerName;
    private List<String>recyclerTime;

    public void initData(){
        recyclerImage= Arrays.asList(
                R.drawable.new1,
                R.drawable.new2,
                R.drawable.new3,
                R.drawable.new4
        );
        recyclerName=Arrays.asList(
                "笨笨的故事",
                "唐光圣",
                "苏烟梧桐",
                "羊荣毅",
                "吴豪杰"
        );

        recyclerTime=Arrays.asList(
                "3.18 17:14",
                "3.18 17:10",
                "3.18 17:07",
                "3.18 17:05",
                "3.18 17:00"
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
    }

    public void setRecyclerImage(List<Integer> recyclerImage) {

        this.recyclerImage = recyclerImage;

    }

    public void setRecyclerName(List<String> recyclerName) {


        this.recyclerName = recyclerName;
    }

    public void setRecyclerTime(List<String> recyclerTime) {

        this.recyclerTime = recyclerTime;
    }

    public void setRecyclerTitle(List<String> recyclerTitle) {

        this.recyclerTitle = recyclerTitle;
    }

    public void setRecyclerWord(List<String> recyclerWord) {

        this.recyclerWord = recyclerWord;
    }

    public List<String> getRecyclerName() {
        return recyclerName;
    }

    public List<Integer> getRecyclerImage() {
        return recyclerImage;
    }

    public List<String> getRecyclerTime() {
        return recyclerTime;
    }

    public List<String> getRecyclerTitle() {
        return recyclerTitle;
    }

    public List<String> getRecyclerWord() {
        return recyclerWord;
    }
}
