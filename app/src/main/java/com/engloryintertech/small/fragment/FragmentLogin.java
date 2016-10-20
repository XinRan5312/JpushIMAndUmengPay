package com.engloryintertech.small.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.LoginActivity;
import com.engloryintertech.small.activity.MainActivity;
import com.engloryintertech.small.activity.PwdRestActivity;
import com.engloryintertech.small.activity.TestGoToWebActivity;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.interfaces.Interfaces;
import com.engloryintertech.small.listener.AuthListener;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.logic.LogicRequestData;
import com.engloryintertech.small.login.ThirdParty;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.AppLoginAndShareManage;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.engloryintertech.small.tools.VerificationUtil;
import com.engloryintertech.small.wxapi.WXEntryActivity;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/15.
 */
public class FragmentLogin extends BaseFragment implements View.OnClickListener,Interfaces.LoginNotify {

    private String TAG = getClass().getName();
    private EditText et_login_pwd;
    private EditText et_login_tel;
    private TextView tv_forgetPwd;
    private LoginActivity loginActivity;
    private Button btn_login;
    public static String Phone = "";
    private DataChangedListener mDataListener = new DataChangedListener();
    private ImageView ima_qq;
    private ImageView ima_sina;
    private ImageView ima_wx;
    private IWXAPI api;
    private String userSystemJson;
    private JSONObject userInfoObject;
    private int mLoginType = Constants.UserLoginType_Normal; //用来登录成功后插入本地数据库的

    @Override
    public int initView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initFindViewById(View view) {
        loginActivity = (LoginActivity) getActivity();//得到MainActivity
        et_login_tel = (EditText) view.findViewById(R.id.et_login_tel);//登录手机号
        et_login_pwd = (EditText) view.findViewById(R.id.et_login_pwd);//登录密码
        tv_forgetPwd = (TextView) view.findViewById(R.id.tv_forgetPwd);//忘记密码
        btn_login = (Button) view.findViewById(R.id.btn_login);//登录
        ima_qq = (ImageView) view.findViewById(R.id.ima_qq);//QQ
        ima_sina = (ImageView) view.findViewById(R.id.ima_sina);//新浪
        ima_wx = (ImageView) view.findViewById(R.id.ima_wx);//微信
        btn_login.setOnClickListener(this);
        tv_forgetPwd.setOnClickListener(this);
        ima_qq.setOnClickListener(this);
        ima_sina.setOnClickListener(this);
        ima_wx.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Interfaces.sharedInstance().setLoginNotify(this);
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.WX_APP_ID, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        registerDataListener();
        super.onActivityCreated(savedInstanceState);
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.LoginFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.LoginSuccess, mDataListener);
    }

    private void registerDataListenerWX() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXAuthFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXAuthSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXGetUsetInfoFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXGetUserInfoSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXLoginAppFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXLoginAppSuccess, mDataListener);
        registerDataListenerThird();
    }

    private void registerDataListenerThird(){
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.ThirdLoginSuccess, mDataListener);
    }

    @Override
    public void onDestroy() {
        Phone = et_login_tel.getText().toString();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String tel = et_login_tel.getText().toString();
                String pwd = et_login_pwd.getText().toString();
                if (tel.equals("") || tel.isEmpty()) {
                    CustomToast.showToast("手机号不能为空");
                    return;
                } else if (!VerificationUtil.isMobileNO(tel)) {
                    CustomToast.showToast("请输入正确手机号");
                    return;
                } else if (pwd.equals("") || pwd.isEmpty()) {
                    CustomToast.showToast("密码不能为空");
                    return;
                }
                loginActivity.showDialog(loginActivity, "登录");//登录显示进度条
                mLoginType = Constants.UserLoginType_Normal;
                getLogin();
//                loginActivity.startActivity(new Intent(loginActivity, TestGoToWebActivity.class));
                break;
            case R.id.tv_forgetPwd:
                loginActivity.startActivity(new Intent(loginActivity, PwdRestActivity.class));
                break;
            case R.id.ima_qq:
                mLoginType = Constants.UserLoginType_qq;
                registerDataListenerThird();
                new ThirdParty(loginActivity, loginActivity).getQQLogin();
                break;
            case R.id.ima_sina:
                mLoginType = Constants.UserLoginType_sina;
                registerDataListenerThird();
                loginActivity.mSsoHandler.authorize(new AuthListener(loginActivity, loginActivity));
                break;
            case R.id.ima_wx:
                mLoginType = Constants.UserLoginType_wx;
                registerDataListenerWX();
                wxLogin();
                break;
            default:
                break;
        }
    }

    //登录
    private void getLogin() {
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("tellphone", et_login_tel.getText().toString());
//        params.put("password", et_login_pwd.getText().toString());
        params.put("tellphone", "15313246094");
        params.put("password", "111111");
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Login_URL, params,
                Constants.Http_Request_Tag_Login, BaseApplication.DataType.LoginFail, BaseApplication.DataType.LoginSuccess);
    }

    /**微信登录*/
    private void wxLogin(){
        // 请求授权登录
        if(Common.isNetEnable()){
            if (!api.isWXAppInstalled()) {
                CustomToast.showToast("请安装微信客户端");
            } else {
                showProgressDialog(getActivity(),true);
                startActivityWithData(null,WXEntryActivity.class,0,0);
                sendAuth();
            }
        }else{
            CustomToast.showToast("检查网络连接");
        }
    }

    private void sendAuth() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = Constants.WEIXIN_SCOPE;
        req.state = Constants.WEIXIN_STATE;
        api.sendReq(req);
    }

    @Override
    public void LoginSuccessNotify(BaseResp resp) {
        Tools.debugger(TAG, "微信授权成功登陆");
        showProgressDialog(getActivity(), true);
        SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;
//        getOpenidAndToken(sendAuthResp.code);
        getTokenOpenId(HttpConstants.GetWeChatTokenPath(sendAuthResp.code));
    }

    @Override
    public void LoginFfailNotify(String message) {
        Tools.debugger(TAG, "微信授权失败");
        CustomToast.showToast(message);
        dismissProgressDialog();
    }

    /**通过code参数,AppID,AppSecret通过API换取access_token 和 openid*/
    private void getTokenOpenId(String requestTokenUrl){
        LogicRequest.sharedInstance().getRequestListLogic("WXLogin", requestTokenUrl, Constants.Http_Request_Tag_WXLogin,
                BaseApplication.DataType.WXAuthFail, BaseApplication.DataType.WXAuthSuccess);
    }

    private void getUserInfo(String requestUserUrl){
        LogicRequest.sharedInstance().getRequestListLogic("WXLogin", requestUserUrl, Constants.Http_Request_Tag_WXLogin,
                BaseApplication.DataType.WXGetUsetInfoFail, BaseApplication.DataType.WXGetUserInfoSuccess);
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case LoginFail:
                    CustomToast.showToast("登录失败,请重新登录");
                    loginActivity.dismissDialog();
                    break;
                case LoginSuccess:
                    String json = data.toString();
                    getCheckLogin(json);
                    break;
                case WXAuthFail:
                    dismissProgressDialog();
                    CustomToast.showToast(data.toString());
                    break;
                case WXAuthSuccess:
                    userSystemJson = data.toString();
                    getUserInfo(AppLoginAndShareManage.sharedInstance().conductTokenResult(data.toString()));
                    break;
                case WXGetUsetInfoFail:
                    dismissProgressDialog();
                    CustomToast.showToast(data.toString());
                    break;
                case WXGetUserInfoSuccess:
                    LogicRequestData.sharedInstance().ThirdPartyLogin(AppLoginAndShareManage.sharedInstance().
                            setUserInfo(userSystemJson, data.toString()));
                    break;
                case ThirdLoginFail:
                    dismissProgressDialog();
                    CustomToast.showToast("网络连接失败，稍后重试。");
                    break;
                case ThirdLoginSuccess:
                     /*保存用户信息**/
                    dismissProgressDialog();
                    jsonUserInfo(data.toString());
                    break;
            }
        }
    }

    private void getCheckLogin(String json) {
        jsonUserInfo(json);
    }

    private void jsonUserInfo(String json) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(json, UserBean.class);
        String result = userBean.Result;
        if (result.equals("Fail")) {
            String message = userBean.Message;
            CustomToast.showToast(message);
            loginActivity.dismissDialog();//登录成功，关闭进度条
        } else {
            UserBean userList = userBean.UserList;
            userList.setLoginType(mLoginType);
            UserDaoHelper.getInstance().saveUserBean(userList);  //注册、第三方登录成功后 都调用这个 存储用户数据
            loginActivity.dismissDialog();//登录成功，关闭进度条
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
        Interfaces.sharedInstance().dissLoginFailNotify();
    }
}