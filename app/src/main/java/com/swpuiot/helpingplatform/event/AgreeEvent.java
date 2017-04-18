package com.swpuiot.helpingplatform.event;

import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by DELL on 2017/4/11.
 */
public class AgreeEvent {
    BmobIMUserInfo info;

    public AgreeEvent(BmobIMUserInfo info) {
        this.info = info;
    }

    public BmobIMUserInfo getInfo() {
        return info;
    }

    public void setInfo(BmobIMUserInfo info) {
        this.info = info;
    }
}
