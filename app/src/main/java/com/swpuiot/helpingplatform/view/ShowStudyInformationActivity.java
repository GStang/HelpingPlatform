package com.swpuiot.helpingplatform.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swpuiot.helpingplatform.R;

public class ShowStudyInformationActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_study_information);

        webView= (WebView) findViewById(R.id.wv_information);
        String data=getIntent().getStringExtra("bannerUri");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=http://bmob-cdn-9580.b0.upaiyun.com/2017/04/06/4eef22ef4010fdc480d037ada3ed326d.docx");

    }
}
