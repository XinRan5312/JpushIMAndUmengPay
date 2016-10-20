package com.engloryintertech.small.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.model.bean.GeneralBean;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.engloryintertech.small.tools.VerificationUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码重置
 */
public class PwdRestActivity extends BaseActivity {

    private String TAG = getClass().getName();
    private EditText et_seek_tel;
    private EditText et_seek_code;
    private EditText et_seek_pwd;
    private Button btn_agree_reset;
    private ImageView ima_back;
    private TextView tv_gain_code;
    private DataChangedListener mDataListener = new DataChangedListener();
    private String tel;
    private String code;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_reset, false);
        registerDataListener();
        initViews();
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetVerifiFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetVerifiSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.CheckveriFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.CheckveriSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.ResetFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.ResetSuccess, mDataListener);
    }

    @Override
    protected void initViews() {
        et_seek_tel = (EditText) findViewById(R.id.et_seek_tel);
        et_seek_code = (EditText) findViewById(R.id.et_seek_code);
        et_seek_pwd = (EditText) findViewById(R.id.et_seek_pwd);
        btn_agree_reset = (Button) findViewById(R.id.btn_agree_reset);//确认重置密码
        ima_back = (ImageView) findViewById(R.id.ima_back);//回退
        tv_gain_code = (TextView) findViewById(R.id.tv_gain_code);//获取验证码
        btn_agree_reset.setOnClickListener(this);
        ima_back.setOnClickListener(this);
        tv_gain_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_agree_reset:
                code = et_seek_code.getText().toString();
                pwd = et_seek_pwd.getText().toString();
                tel = et_seek_tel.getText().toString();
                getReset(tel, code, pwd);
                break;
            case R.id.ima_back:
                finish();
                break;
            case R.id.tv_gain_code:
                tel = et_seek_tel.getText().toString();
                getVerfication(tel);
                break;
            default:
                break;
        }
    }

    //重置密码
    private void getReset(String tel, String code, String pwd) {
        if (tel.equals("") || tel.isEmpty()) {
            CustomToast.showToast("手机号不能为空");
            return;
        } else if (!VerificationUtil.isMobileNO(tel)) {
            CustomToast.showToast("请输入正确手机号");
            return;
        } else if (code.equals("") || code.isEmpty()) {
            CustomToast.showToast("验证码不能为空");
            return;
        } else if (pwd.equals("") || pwd.isEmpty()) {
            CustomToast.showToast("密码不能为空");
            return;
        } else {
            getConfirmPwd();
        }
    }

    //确认重置
    private void getConfirmPwd() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("messagecode", code);
        params.put("tellphone", tel);
        params.put("password", pwd);
        params.put("messagetype", "2");
        LogicRequest.sharedInstance().postRequestListLogic(TAG, HttpConstants.Resetpwd_URL, params, Constants.Http_Request_Tag_Reset
                , BaseApplication.DataType.ResetFail, BaseApplication.DataType.ResetSuccess);
    }

    private void checkVerfication() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tellphone", et_seek_tel.getText().toString());
        params.put("messagetype", "1");
        params.put("messagecode", et_seek_code.getText().toString());
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Checkverification_URL, params,
                Constants.Http_Request_Tag_Verification, BaseApplication.DataType.CheckveriFail, BaseApplication.DataType.CheckveriSuccess);
    }

    //获取验证码
    private void getVerfication(String tel) {
        if (!VerificationUtil.isMobileNO(tel)) {
            CustomToast.showToast("请输入正确手机号");
            return;
        }
        CustomToast.showToast("正在获取验证码");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tellphone", et_seek_tel.getText().toString());
        params.put("messagetype", "1");
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Verification_URL, params,
                Constants.Http_Request_Tag_Verification, BaseApplication.DataType.GetVerifiFail, BaseApplication.DataType.GetVerifiSuccess);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case GetVerifiFail:
                    Tools.error(TAG + "GetVerifiFail", data.toString());
                    break;
                case GetVerifiSuccess:
                    Tools.error(TAG + "GetVerifiSuccess", data.toString());
                    break;
                case CheckveriFail:
                    Tools.error(TAG + "CheckveriFail", data.toString());
                    break;
                case CheckveriSuccess:
                    Tools.error(TAG + "CheckveriSuccess", data.toString());
                    break;
                case ResetFail:
                    Tools.error(TAG + "ResetFail", data.toString());
                    break;
                case ResetSuccess:
                    String json = data.toString();
                    Gson gson = new Gson();
                    GeneralBean generalBean = gson.fromJson(json, GeneralBean.class);
                    String result = generalBean.getResult();
                    String message = generalBean.getMessage();
                    if (result.equals("Fail")) {
                        CustomToast.showToast(message);
                    } else {
                        CustomToast.showToast("密码重置成功");
                        startActivityWithData(null, LoginActivity.class, 0, 0);
                    }
                    break;
            }
        }
    }
}