package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class AerobicsActivity extends Activity implements View.OnClickListener {

    private ImageView mTitleBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        initView();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_aerobics);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);

        String frameVideo = "<html><body><iframe width=\"840\" height=\"400\" src=\"https://www.youtube.com/embed/WmQ72Bw5yiY\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe></body></html>";

        WebView displayYoutubeVideo = (WebView) findViewById(R.id.mWebView);
        displayYoutubeVideo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");

    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_new_member:
                intent.setClass(AerobicsActivity.this, RegisterMemberActivity.class);
                break;
            case R.id.common_title_back:
                finish();
                return;
            default:
                break;
        }
        startActivity(intent);
    }
}
