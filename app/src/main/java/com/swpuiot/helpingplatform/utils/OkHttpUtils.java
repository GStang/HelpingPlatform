package com.swpuiot.helpingplatform.utils;

import android.os.Bundle;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/3/4.
 */
public class OkHttpUtils {

    private static final OkHttpUtils utils = new OkHttpUtils();
    OkHttpClient client = new OkHttpClient();

    private OkHttpUtils() {
        client.setCookieHandler(new CookieManager());

    }

    public static OkHttpUtils getInstance() {
        return utils;
    }

    public Response get(URL url) throws IOException {
        Request request = new Request.Builder().url(url).header("User-Agent", "okHttp").build();
        Response response = null;
        response = client.newCall(request).execute();
        return response;
    }

    public Response post(URL url, RequestBody body) throws IOException {
        client.getCookieHandler();
        Request request = new Request.Builder().url(url).header("User-Agent", "okHttp").post(body).build();
        return client.newCall(request).execute();
    }
}
