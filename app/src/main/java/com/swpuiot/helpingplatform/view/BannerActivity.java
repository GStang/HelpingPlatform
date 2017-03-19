package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swpuiot.helpingplatform.R;

public class BannerActivity extends AppCompatActivity {

    private WebView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        banner= (WebView) findViewById(R.id.wv_banner);

        String data=getIntent().getStringExtra("bannerUri");

        banner.getSettings().setJavaScriptEnabled(true);
        banner.setWebViewClient(new WebViewClient());
        banner.loadUrl(data);
    }
}
