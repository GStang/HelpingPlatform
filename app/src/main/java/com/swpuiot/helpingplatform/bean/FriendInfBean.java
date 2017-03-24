package com.swpuiot.helpingplatform.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/17.
 */
public class FriendInfBean extends BmobObject {
    String name;
    Integer sex;
    User friend;
    List<BmobFile> files;

    public void setFiles(List<BmobFile> files) {
        this.files = files;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

}
