package com.swpuiot.helpingplatform.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by DELL on 2017/3/9.
 */
public class PostBean extends BmobObject {
    private List<BmobFile> imgs;//图片
    private String title;//帖子标题
    private String content;//帖子内容
    private User user;//帖子的作者
    private BmobRelation likes;
    private int zan;

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<BmobFile> getImgs() {
        return imgs;
    }

    public void setImgs(List<BmobFile> imgs) {
        this.imgs = imgs;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
