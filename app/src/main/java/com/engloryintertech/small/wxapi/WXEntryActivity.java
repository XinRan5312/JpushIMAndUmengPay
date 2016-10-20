package com.engloryintertech.small.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.interfaces.Interfaces;
import com.engloryintertech.small.tools.Tools;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 *   @author Jin Ge
 *   @time 2015-11-27
 *   微信登陆、分享
 */

/**
 * 该类名不能改
 * 微信登录，分享应用中必须有这个名字叫WXEntryActivity，
 * 并且必须在wxapi包名下，腾讯官方文档中有要求
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    private static String TAG = "small.WXEntryActivity";
    private IWXAPI api;
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
        api.registerApp(Constants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
        Tools.debugger(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp resp) {
        Tools.debugger(TAG, "onResp");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Tools.debugger(TAG,"onResp ERR_OK");
                Interfaces.sharedInstance().startLoginSuccessNotify(resp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Tools.debugger(TAG,"onResp ERR_USER_CANCEL");
                Interfaces.sharedInstance().startLoginFailNotify("用户取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Tools.debugger(TAG,"onResp ERR_AUTH_DENIED");
                Interfaces.sharedInstance().startLoginFailNotify("用户拒绝授权");
                break;
            default:
                Tools.debugger(TAG,"onResp 用户取消");
                Interfaces.sharedInstance().startLoginFailNotify("用户取消");
                break;
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.debugger(TAG, "onResume");
        if(isStop){
            Interfaces.sharedInstance().startLoginSuccessNotify();
            isStop = false;
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tools.debugger(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Tools.debugger(TAG, "onStop");
        isStop = true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(getIntent(), this);
        Tools.debugger(TAG, "onResp onNewIntent onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Tools.debugger(TAG, "onBackPressed");
        finish();
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Tools.debugger(TAG,"onKeyDown");
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.debugger(TAG, "onResp onDestroy");
    }
}