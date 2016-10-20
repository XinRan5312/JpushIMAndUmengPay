package com.engloryintertech.small.im.ui.activitys;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.engloryintertech.small.im.XinRanApp;
import com.engloryintertech.small.im.ui.BaseActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by qixinh on 16/10/13.
 *
 *
 */
public abstract class BaseRongActivity extends BaseActivity implements  RongIM.UserInfoProvider{
    protected String mUserId;
    private String TAG=BaseRongActivity.class.getSimpleName();
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        geTokenAndConnectRong();
        connectRong("tlHrtOP22d4VVU7sbA+4sIr9U3R+cIhwHG1UcaMNBmzDeTfTt1Y3PpkwZRCuPH4nNEdzj1t3iJmUY7LdXdZ6fA==");
    }

    @Override
    public UserInfo getUserInfo(String userId) {

        return new UserInfo("userid","Small", Uri.parse("http://img5q.duitang.com/uploads/item/201408/18/20140818113623_JwLPs.thumb.224_0.jpeg"));
    }

    private UserInfo findUserInfoByid(String userId) {
        //从网络请求  setUserInfo();
        mUserId=userId;
        return getUserInfo();
    }

    private void connectRong(String token) {

        if (getApplicationInfo().packageName.equals(XinRanApp.getCurProcessName(getApplicationContext()))&&RongIM.getInstance().getCurrentConnectionStatus()== RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    Log.e(TAG, "token is error , please check token and appkey ");
                }

                @Override
                public void onSuccess(String userId) {
                     mUserId=userId;
                    Log.e(TAG, "token is Ok=="+mUserId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG,
                            "connect failure errorCode is :" + errorCode.getValue());
                }
            });
        }
    }
    private void geTokenAndConnectRong(){
        SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
        String token = sp.getString("loginToken", "");
        if(token.equals("")){
           geTokenFromNetAndConnectRong();
        }else{
            connectRong(token);
        }

    }

    private void geTokenFromNetAndConnectRong() {
       //请求完成后保存token，SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);然后连接connectRong(String token)


    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    private void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
