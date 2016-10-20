package com.engloryintertech.small.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.BaseActivity;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.logic.LogicRequestData;
import com.engloryintertech.small.nets.utils.OkHttpUtils;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.PictureProcesse;
import com.engloryintertech.small.tools.Tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by qixinh on 16/9/20.
 */
public class WebActivity extends BaseWebviewActivity implements View.OnClickListener {
    public static final int OPEN_TYPE_GET = 0; // GET请求
    public static final int OPEN_TYPE_POST = OPEN_TYPE_GET + 1; // POST请求
    private Timer refWebViewTimer;
    private Bitmap bitmap;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_test);
        webview.addJavascriptInterface(new JsToNative(this), "toNative");
        registerDataListener();
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.UpLoadImageFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.UpLoadImageSuccess, mDataListener);
    }

    @Override
    protected void doOpenUrl() {
        openAndInitUrl(url, "");
    }

    private void openAndInitUrl(String webUrl, String webQuery) {
        url = webUrl;
        if (url == null || url.length() == 0) {
            url = DEFAULT_URL;
        }
        if (openType == OPEN_TYPE_GET) {
            // get方式打开
            webview.loadUrl(url);
//            webview.loadUrl("file:///android_asset/orderList.html");
//            webview.loadUrl("file:///android_asset/testjs_ai.html");
        } else if (openType == OPEN_TYPE_POST) {
            // post方式打开
            try {
                webview.postUrl(webUrl, webQuery != null ? webQuery.getBytes("utf-8") : "".getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
        if (refWebViewTimer == null) {
            refWebViewTimer = new Timer();
            refWebViewTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (webview != null && webview.getVisibility() == View.VISIBLE) {
                        webview.postInvalidate();
                    }
                }
            }, 200, 140);
        }
    }

    @Override
    public void onClick(View v) {
        // 根据崩溃日志，添加非空判断
        if (url == null || url.length() == 0) {
            url = DEFAULT_URL;
        }

//            webview.goBack();
//
//            webview.reload();
//
//            webview.goForward();
//
//            webview.stopLoading();
//

    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (refWebViewTimer != null) {
                refWebViewTimer.cancel();
                refWebViewTimer = null;
            }
        } catch (Throwable e) {
        }
    }

    public static void startActivity(Activity context, String url,String postQuery, int webViewType,
                                     boolean isPost) {
        Tools.debugger("WebActivity","startActivity url : " + url);
        Bundle bundle = new Bundle();
        if (isPost) {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putString(BaseWebviewActivity.WEBVIEW_POST_QUERY, postQuery);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_POST);
        } else {
            bundle.putString(BaseWebviewActivity.WEBVIEW_URL, url);
            bundle.putInt(WebActivity.OPEN_TYPE_KEY, WebActivity.OPEN_TYPE_GET);
        }
        Intent intent = new Intent();
        intent.setClass(context,WebActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
        OkHttpUtils.getInstance().cancelTag(Constants.Http_Request_Tag_UpLoadImage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        addBackClickListner();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    // 注入js函数监听
    private void addBackClickListner() {
        webview.goBack();
    }

    //获取本地图片的路径
    public static String selectImage(Intent data){
        Uri selectedImage = data.getData();
        if(selectedImage!=null){
            String uriStr=selectedImage.toString();
            String path=uriStr.substring(10,uriStr.length());
            if(path.startsWith("com.sec.android.gallery3d")){
                return null;
            }
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = BaseApplication.getApplication().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.Request_Camera){
            if(resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    Bitmap bitmap = PictureProcesse.getBitmap(mTmpFile);
                    String filePath = PictureProcesse.saveBitmap(bitmap, mTmpFile);
                    mTmpFile = new File(filePath);
                    LogicRequestData.sharedInstance().upLoadImage(Constants.upLoadImage_Type_Resource,mTmpFile);
                }
            }else{
                if(mTmpFile != null && mTmpFile.exists()){
                    mTmpFile.delete();
                }
            }
        }else if(requestCode == Constants.Request_IMAGE && resultCode == Activity.RESULT_OK && data != null){
//            webview.loadUrl("javascript:addImgUrl()");
            Uri selectedImage = data.getData();
            selectedImage.fromFile(mTmpFile);
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            mTmpFile = new File(imagePath);
            bitmap = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath(), PictureProcesse.getBitmapOption(2)); //将图片的长和宽缩小味原来的1/2
            String filePath = PictureProcesse.saveBitmap(bitmap, mTmpFile);
            mTmpFile = new File(filePath);
            c.close();
            LogicRequestData.sharedInstance().upLoadImage(Constants.upLoadImage_Type_Resource, mTmpFile);
        }
    }

    /**将图片地址给js*/
    private void selectImageToJs(int index,String url){
        Tools.debugger("selectImg", "selectImageToJs index : " + index);
//        webview.loadUrl("javascript:addImgUrl('" + index + "," + url + "')");
        webview.loadUrl("javascript:addImgUrl("+index+","+url+")");
//        webview.loadUrl("javascript:addImgUrl(1,2)");
    }

    private DataChangedListener mDataListener = new DataChangedListener();
    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, final Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case UpLoadImageSuccess:
                    final String imageUrl = data.toString();
                    Tools.debugger("selectImage", "mDataListener imageUrl : " + imageUrl);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            webview.loadUrl("javascript:addImgUrl()");
                            selectImageToJs(BaseActivity.mIndexJs,imageUrl);
                        }
                    }, 500);
                    break;
                case UpLoadImageFail:
                    CustomToast.showToast("连接服务器失败");
                    break;
            }
        }
    }

}
