package com.engloryintertech.small.webview;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.engloryintertech.small.activity.BaseActivity;
import com.engloryintertech.small.global.GlobalConstants;
import com.engloryintertech.small.global.findview.FindView;
import com.engloryintertech.small.global.utils.CompatUtil;
import com.engloryintertech.small.tools.Tools;

import java.io.File;

/**
 * Created by qixinh on 16/9/20.
 */
public class BaseWebviewActivity extends BaseActivity implements IWebCallback {
    public static final String DOMAIN = "";
    public static final String DEFAULT_URL = "";
    public static final String WEBVIEW_POST_QUERY="post_query";
    public static final String WEBVIEW_URL = "url";
    public static final String WEBVIEW_TITLE = "title";
    public static final String OPEN_TYPE_KEY = "open_type_key";
    public static final int REQUEST_CODE_FOR_LOGIN = 1;
    @FindView(com.engloryintertech.small.R.id.webview)
    public BaseWebView webview;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public int openType = 0;
    public String url;
    // 标题
    public String title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openType = myBundle.getInt(OPEN_TYPE_KEY);
        url = myBundle.getString(WEBVIEW_URL);
        title = myBundle.getString(WEBVIEW_TITLE);
        CookieSyncManager.createInstance(this);
    }

    @Override
    protected void initViews() {

    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        doOpenUrl();
    }

    /**
     * 子类重写这个方法进行自定义调用
     */
    protected void doOpenUrl() {
        defaultOpenUrl(url);
    }

    @Override
    public void setContentView(View view, boolean autoInject) {
        super.setContentView(view, autoInject);
        webview.getSettings().setSavePassword(false);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if (Build.VERSION.SDK_INT >= 19) {
//            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }
//        /*** 打开本地缓存提供JS调用 **/
//        webview.getSettings().setDomStorageEnabled(true);
//        // Set cache size to 8 mb by default. should be more than enough
//        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

//        File cacheDir = this.getCacheDir();
//        if(cacheDir != null) {
//            String appCachePath = this.getCacheDir().getAbsolutePath();
//            webview.getSettings().setAppCachePath(appCachePath);
//        }

//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webview.getSettings().setAllowFileAccess(true);
//        webview.getSettings().setAppCacheEnabled(true);
//        //开启 database storage API 功能
//        webview.getSettings().setDatabaseEnabled(true);
//        //设置数据库缓存路径
//        webview.getSettings().setDatabasePath(cacheDirPath);
//        //设置  Application Caches 缓存目录
//        webview.getSettings().setAppCachePath(cacheDirPath);

        webview.getSettings().setUserAgentString(webview.getSettings().getUserAgentString() + " " + GlobalConstants
                        .SCHEME
        );
        if (CompatUtil.getSDKVersion() >= 11 && CompatUtil.getSDKVersion() <= 15) {
            webview.getSettings().setBuiltInZoomControls(true);
        } else {
            webview.getSettings().setBuiltInZoomControls(false);
        }
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setGeolocationEnabled(true);
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        webview.setWebViewClient(WebViewHelper.getViewClient(this));
        webview.setWebChromeClient(WebViewHelper.getChromeClient(this));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (CompatUtil.hasHoneycomb()) {
//            resumeWebView();
//        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void resumeWebView() {
        if (webview != null && webview.getVisibility() == View.VISIBLE) {
            webview.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // WebView.disablePlatformNotifications();
//        if (CompatUtil.hasHoneycomb()) {
//            pauseWebView();
//        }
        CookieSyncManager.getInstance().stopSync();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void pauseWebView() {
        if (webview != null && webview.getVisibility() == View.VISIBLE) {
            webview.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            // 修复音乐视频播放不停止的问题
            webview.loadData("", "text/html", "utf-8");
            webview.setVisibility(View.GONE);
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
        } catch (Throwable e) {
        } finally {
            super.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected void defaultOpenUrl(String webUrl) {
        url = webUrl;
        if (url == null || url.length() == 0) {
            url = DEFAULT_URL;
        }
//		setTitleBar(getString(com.Qunar.R.string.app_name), true);
        // 覆盖默认行为（#onBackPressed()）,直接关闭页面，因为返回键会优先判断是否有网页的goBack()
        webview.loadUrl(url);

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            startActivity(intent);
            return true;
        } else if (url.contains("ditu.google.com")) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
            return true;
        } else if (url.startsWith("1")) {

            return true;
        } else if (url.startsWith("2")) {

            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case REQUEST_CODE_FOR_LOGIN:
//                Tools.debugger("onActivityResults","REQUEST_CODE_FOR_LOGIN");
//                webview.reload();
//        }
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onClick(View v) {

    }

    /**清除缓存*/
    public void clearCacheWebView(){
        try{
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        }catch(Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir =new File(getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     * @param file
     */
    public void deleteFile(File file) {
        if(file.exists()) {
            if(file.isFile()) {
                file.delete();
            }else if(file.isDirectory()) {
                File files[] = file.listFiles();
                for(int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }
}
