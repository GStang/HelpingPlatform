package com.swpuiot.helpingplatform.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class BannerLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        int imageId= (int) path;
        imageView.setImageResource(imageId);

    }

}
