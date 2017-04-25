package com.swpuiot.helpingplatform.event;

import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by DELL on 2017/4/11.
 */
public class AgreeEvent {
    private BmobIMUserInfo info;
    private int postion;
    public AgreeEvent(BmobIMUserInfo info,int postion) {
        this.info = info;
        this.postion = postion;
    }

    public BmobIMUserInfo getInfo() {
        return info;
    }

    public void setInfo(BmobIMUserInfo info) {
        this.info = info;
    }

    public int getPositon(){return postion;}
}
