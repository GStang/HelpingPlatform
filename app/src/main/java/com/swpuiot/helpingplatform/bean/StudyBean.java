package com.swpuiot.helpingplatform.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DuZeming on 2017/4/6.
 */
public class StudyBean extends BmobObject {
    private BmobFile studyFile;
    private User author;
    private Integer sign;

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public void setStudyFile(BmobFile studyFile) {
        this.studyFile = studyFile;
    }

    public BmobFile getStudyFile() {
        return studyFile;
    }

    public Integer getSign() {
        return sign;
    }

    public User getAuthor() {
        return author;
    }
}
