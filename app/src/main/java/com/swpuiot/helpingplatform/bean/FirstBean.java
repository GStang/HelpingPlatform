package com.swpuiot.helpingplatform.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DELL on 2017/3/17.
 */
public class FirstBean extends BmobObject {
    String title;//任务标题
    String content;//任务内容
    User author; //任务发布者
    List<BmobFile> files;//发布者头像
    Boolean isSolved;//是否被解决
    Boolean isAlive;//是否可以接单
    User acceptUser;//接单人

    public User getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(User acceptUser) {
        this.acceptUser = acceptUser;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<BmobFile> getFiles() {
        return files;
    }

    public void setFiles(List<BmobFile> files) {
        this.files = files;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
