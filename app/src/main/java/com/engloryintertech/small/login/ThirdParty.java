package com.engloryintertech.small.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.engloryintertech.small.activity.LoginActivity;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.listener.BaseUiListener;
import com.engloryintertech.small.logic.LogicRequestData;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ThirdParty {

    private String TAG = getClass().getName();
    private Context context;
    private Activity activity;
    //QQ登录
    private Tencent mTencent;
    private UserInfo mInfo;//用户信息
    public static QQAuth mQQAuth;
    //微信登录

    public ThirdParty(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void getQQLogin() {
        mQQAuth = QQAuth.createInstance(Constants.QQ_APP_ID, context);//QQ登录
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, context);//QQ登录
        if (!mQQAuth.isSessionValid()) {
            IUiListener listener = new BaseUiListener(context, (LoginActivity) activity) {
                @Override
                protected void doComplete(JSONObject values) {
                    Tools.debugger("getQQLogin","doComplete : " + values.toString());
                    updateUserInfo(values);
                }
            };
            mQQAuth.login(activity, "all", listener);
            mTencent.login(activity, "all", listener);
        } else {
            mQQAuth.logout(context);
//            updateUserInfo(context);
        }
    }

    /**
     * QQ登录成功后返回的用户信息
     */
    private void updateUserInfo(final JSONObject jsonObject) {

        if (mQQAuth != null && mQQAuth.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Tools.debugger("getQQLogin", "onComplete : " + response.toString());
                    try {
                        String token = jsonObject.getString(Constants.QQ_Login_Json_Token);
                        String expires = jsonObject.getString(Constants.QQ_Login_Json_Expires );
                        String openId = jsonObject.getString(Constants.QQ_Login_Json_OpenId );
                        if (!TextUtils. isEmpty(token) && !TextUtils. isEmpty(openId)) {
                            //设置身份的token
                            mTencent.setAccessToken(token, expires);
                            mTencent.setOpenId(openId);
                        }

                        JSONObject userResultJson = new JSONObject(response.toString());
                        String image = userResultJson.getString("figureurl").toString();
                        String nickname = userResultJson.getString("nickname").toString();

                        UserBean userBean = new UserBean();
                        userBean.setLoginType(Constants.UserLoginType_qq);
                        userBean.setUserToken(token);
                        userBean.setOpenId(openId);//取值赋值 只是作为参数传给服务器
                        userBean.setAvatarUrl(image);
                        userBean.setUserName(nickname);

                        LogicRequestData.sharedInstance().ThirdPartyLogin(userBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.debugger("getQQLogin", "JSONException : " + e.getMessage().toString());
                    }
                }

                @Override
                public void onCancel() {
                    CustomToast.showToast("已取消");
                }
            };
            mInfo = new UserInfo(context, mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }
}