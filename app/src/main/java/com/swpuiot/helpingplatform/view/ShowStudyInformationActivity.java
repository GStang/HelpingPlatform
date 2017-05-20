package com.swpuiot.helpingplatform.view;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.swpuiot.helpingplatform.R;
import com.swpuiot.helpingplatform.adapter.StudyInformationAdapter;

import java.io.File;
import java.net.URL;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class ShowStudyInformationActivity extends AppCompatActivity {
    private WebView webView;
    private FloatingActionButton downloadInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_study_information);

        webView= (WebView) findViewById(R.id.wv_information);
        String data=getIntent().getStringExtra("bannerUri");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        final String path = getIntent().getStringExtra(StudyInformationAdapter.ShowStudyInf);
        webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=" +path);
        System.out.println("路径为" + path);

        downloadInformation= (FloatingActionButton) findViewById(R.id.fab_showInformation_download);
        downloadInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://view.officeapps.live.com/op/view.aspx?src="+path);
                File files = new File(String.valueOf(uri));
                final BmobFile file =new BmobFile(files);
                File savaFile = new File(Environment.getExternalStorageDirectory(),file.getFilename());
                file.download(savaFile, new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(ShowStudyInformationActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ShowStudyInformationActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long time) {
                        Log.i("bmob", "下载进度：" + integer + "," + time);
                    }
                });
            }
        });

    }
}
