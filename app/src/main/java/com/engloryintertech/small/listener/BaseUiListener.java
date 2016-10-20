package com.engloryintertech.small.listener;

import android.content.Context;
import android.widget.Toast;

import com.engloryintertech.small.activity.LoginActivity;
import com.engloryintertech.small.tools.Tools;
import com.engloryintertech.small.tools.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BaseUiListener implements IUiListener {

    private  String TAG=getClass().getName();
    private Context context;
    private LoginActivity loginActivity;

    public BaseUiListener() {

    }

    public BaseUiListener(Context context, LoginActivity loginActivity) {
        this.context = context;
        this.loginActivity = loginActivity;
    }

    @Override
    public void onComplete(Object response) {
//        Util.showResultDialog(context, response.toString(),
//                "登录成功");
        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
        Tools.error(TAG,response.toString());
        doComplete((JSONObject) response);
    }

    protected void doComplete(JSONObject values) {

    }

    @Override
    public void onError(UiError e) {
        Util.toastMessage(loginActivity, "onError: " + e.errorDetail);
        Util.dismissDialog();
    }

    @Override
    public void onCancel() {
        Util.toastMessage(loginActivity, "onCancel: ");
        Util.dismissDialog();
    }
}
