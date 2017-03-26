package com.swpuiot.helpingplatform.utils;

import cn.bmob.newim.bean.BmobIMExtraMessage;

/**
 * Created by DELL on 2017/3/24.
 */
public class AgreeAddFriendMessage extends BmobIMExtraMessage {
    //以下均是从extra里面抽离出来的字段，方便获取
    private String uid;//最初的发送方
    private Long time;
    private String msg;//用于通知栏显示的内容

    @Override
    public String getMsgType() {
        return "agree";
    }

    @Override
    public boolean isTransient() {
        //如果需要在对方的会话表中新增一条该类型的消息，则设置为false，表明是非暂态会话
        //此处将同意添加好友的请求设置为false，为了演示怎样向会话表和消息表中新增一个类型，在对方的会话列表中增加`我通过了你的好友验证请求，我们可以开始聊天了!`这样的类型
        return false;
    }

}
