package com.swpuiot.helpingplatform.bean;

import android.graphics.Bitmap;

import java.util.HashMap;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2017/3/7.
 */
public class YueData extends BmobObject {
    private BmobFile file;
    private String date;
    private String time;
    private String title;
    private String plan;
    private String phone;
    private Bitmap mBitmap;
    private HashMap mHashMap;

    public void setHashMap(HashMap hashMap) {
        mHashMap = hashMap;
    }

    public HashMap getHashMap() {

        return mHashMap;
    }

    public String getPhone() {
        return phone;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public BmobFile getFile() {
        return file;

    }

    public YueData() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getPhone(String s) {
        return phone;

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
