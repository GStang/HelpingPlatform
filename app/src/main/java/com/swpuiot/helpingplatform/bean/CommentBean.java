package com.swpuiot.helpingplatform.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by DELL on 2017/3/14.
 */
public class CommentBean extends BmobObject {
    private String content;//评论内容

    private User user;//评论的用户，Pointer类型，一对一关系

    private PostBean post; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个说说

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
