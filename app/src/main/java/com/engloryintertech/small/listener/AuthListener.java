package com.engloryintertech.small.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.engloryintertech.small.constant.AccessTokenKeeper;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.logic.LogicRequestData;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.tools.Tools;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/8/5.
 */
public class AuthListener implements WeiboAuthListener {

    private String TAG = getClass().getName();
    private Context context;
    private Activity activity;

    public AuthListener(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onComplete(Bundle values) {
        final Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);

        final String token = values.getString("access_token");
        String expires_in = values.getString("expires_in");
        final String idstr = values.getString("uid");

        if (accessToken != null && accessToken.isSessionValid()) {
            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                    new java.util.Date(accessToken.getExpiresTime()));
            AccessTokenKeeper.writeAccessToken(context, accessToken);
        } else {
            String code = values.getString("code");
            Tools.error(TAG, "code" + code);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                UsersAPI userApi = new UsersAPI(context, Constants.WB_APP_KEY, accessToken);
                userApi.show(Long.valueOf(idstr), new RequestListener() {

                    @Override
                    public void onWeiboException(WeiboException arg0) {
                        Log.e("AuthListener", "onWeiboException : " + arg0.getMessage().toString());
                    }
                    @Override
                    public void onComplete(String arg0) {
                        if (!TextUtils.isEmpty(arg0.toString())) {
                            User user = User.parse(arg0);
                            UserBean userBean = new UserBean();
                            userBean.setLoginType(Constants.UserLoginType_sina);
                            userBean.setUserName(user.screen_name);
                            userBean.setUserToken(token);
                            userBean.setOpenId(idstr);
                            userBean.setAvatarUrl(user.avatar_hd);
                            LogicRequestData.sharedInstance().ThirdPartyLogin(userBean);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Log.e(TAG, e.toString());
    }

    @Override
    public void onCancel() {
        Log.e(TAG, "已取消");
    }
}