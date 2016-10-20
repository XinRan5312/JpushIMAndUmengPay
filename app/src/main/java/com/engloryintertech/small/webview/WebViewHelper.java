package com.engloryintertech.small.webview;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by qixinh on 16/9/20.
 */
public class WebViewHelper {
    public static WebViewClient getViewClient(IWebCallback activity) {
        return IMPL.getViewClient(activity);
    }

    public static WebChromeClient getChromeClient(IWebCallback activity) {
        return IMPL.getChromeClient(activity);
    }

    interface IHelper {

        WebChromeClient getChromeClient(IWebCallback activity);

        WebViewClient getViewClient(IWebCallback activity);
    }

    static final IHelper IMPL = new WebViewHelperSdk8();

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static class WebViewHelperSdk8 implements IHelper {


        @Override
        public WebChromeClient getChromeClient(IWebCallback activity) {
            return new ClientSdk8(activity);
        }

        @Override
        public WebViewClient getViewClient(IWebCallback activity) {
            return new ViewClientSdk8(activity);
        }

        static class ClientSdk8 extends WebChromeClient {

            private final IWebCallback mActivity;

            public ClientSdk8(IWebCallback activity) {
                this.mActivity = activity;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            public void onProgressChanged(WebView view, int newProgress) {
                mActivity.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(WebView view, String title) {
                mActivity.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 构建一个Builder来显示网页中的alert对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        result.confirm();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });
                builder.setCancelable(true);
                builder.create();
                builder.show();
                return true;
            }

            // 带按钮的对话框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        result.confirm();
                    }

                });
                builder.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        result.cancel();
                    }

                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });
                builder.setCancelable(true);
                builder.create();
                builder.show();
                return true;
            }

        }

        static class ViewClientSdk8 extends WebViewClient {

            private final IWebCallback mActivity;

            public ViewClientSdk8(IWebCallback context) {
                this.mActivity = context;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return mActivity.shouldOverrideUrlLoading(view, url);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mActivity.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                mActivity.onPageFinished(view, url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mActivity.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                mActivity.onReceivedSslError(view, handler, error);
            }

        }
    }
}
