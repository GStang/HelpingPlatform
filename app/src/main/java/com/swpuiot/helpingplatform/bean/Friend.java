package com.swpuiot.helpingplatform.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by DELL on 2017/3/24.
 */
public class Friend extends BmobObject {
    //用户
    private User user;
    //好友
    private User friendUser;

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
