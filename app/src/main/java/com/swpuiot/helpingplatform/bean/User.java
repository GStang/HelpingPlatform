package com.swpuiot.helpingplatform.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/6.
 */
public class User extends BmobUser {
    BmobFile headimg;

    public BmobFile getHeadimg() {
        return headimg;
    }

    public void setHeadimg(BmobFile headimg) {
        this.headimg = headimg;
    }
}
