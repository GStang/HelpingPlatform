package com.swpuiot.helpingplatform.utils;

import okhttp3.OkHttpClient;

/**
 * Created by DELL on 2017/3/6.
 */
public class OkHttpUtils {
    private static OkHttpClient client = new OkHttpClient();

    public static OkHttpClient getClient() {
        return client;
    }
}
