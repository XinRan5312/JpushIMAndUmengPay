package com.engloryintertech.small.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qixinh on 16/9/20.
 */
public class BaseWebView extends WebView {
    private boolean isDestroy = false;

    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 同步webview和登录用户的cookie
     */
    public void synCookie(String keyUrl) {
        CookieSyncManager.createInstance(this.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        //cookieManager.removeAllCookie();
        cookieManager.setCookie(keyUrl,"u=" + UserDaoHelper.getInstance().getUserId());
        cookieManager.setCookie(keyUrl,"n=" + Common.getID(BaseApplication.getApplication()));
        cookieManager.setCookie(keyUrl,"k=" + UserDaoHelper.getInstance().getUserToken());

        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (isDestroy) {
            return;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void loadUrl(String url) {
        if (isDestroy) {
            return;
        }
//        synCookie();
        if (!url.toLowerCase().startsWith("javascript:")) {
            synCookie(url);
        }
        super.loadUrl(url);
    }

    public void loadJs(String js) {
        if (isDestroy) {
            return;
        }
        super.loadUrl(js);
    }

    public void postUrl(String url, byte[] postData) {
        synCookie(url);
        super.postUrl(url, postData);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (isDestroy) {
            return;
        }
        synCookie(url);
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void destroy() {
        isDestroy = true;
        super.destroy();
    }
}
