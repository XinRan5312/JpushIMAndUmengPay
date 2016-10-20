package com.engloryintertech.small.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.engloryintertech.small.R;

/**
 * 小喇叭点击跳转的界面
 */
public class HomeWebviewActivity extends BaseActivity {

    private String Links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_webview,false);
        initData();
        initViews();
    }

    @Override
    protected void initViews() {
//        String url = "http://www.baidu.com";
        String url = Links;
        WebView webview = (WebView) findViewById(R.id.webview_home);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.loadUrl(url);
        //webview本地应用加载网页
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Links = intent.getStringExtra("Links");
    }

    @Override
    public void onClick(View view) {

    }

}