package com.engloryintertech.small.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

/**
 * Created by qixinh on 16/9/20.
 */
public interface IWebCallback {
    void onProgressChanged(WebView view, int newProgress);

    void onReceivedTitle(WebView view, String title);

    boolean shouldOverrideUrlLoading(WebView view, String url);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);
}
