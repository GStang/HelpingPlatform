package com.swpuiot.helpingplatform.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.net.URI;

/**
 * Created by DuZeming on 2017/3/5.
 */
public class BannerLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        int imageId= (int) path;
        imageView.setImageResource(imageId);
    }


}
