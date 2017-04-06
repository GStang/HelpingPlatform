package com.swpuiot.helpingplatform.bean;

import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/6.
 */
public class User extends BmobUser {
    private BmobFile headimg;
    private Integer age;
    private Integer sex;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    String nickName;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BmobFile getHeadimg() {
        return headimg;
    }

    public void setHeadimg(BmobFile headimg) {
        this.headimg = headimg;
    }

    public BmobIMUserInfo getUserInfo(){
        return new BmobIMUserInfo(getObjectId(), getUsername(), getAvatar());
    }
}
