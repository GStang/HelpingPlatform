package com.swpuiot.helpingplatform.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DELL on 2017/3/5.
 */
public class InfBean {
    private int imgId;
    private String sbj;
    public InfBean(int imgId,String sbj){
        this.imgId=imgId;
        this.sbj = sbj;
    }
    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getSbj() {
        return sbj;
    }

    public void setSbj(String sbj) {
        this.sbj = sbj;
    }
}
