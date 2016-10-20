package com.engloryintertech.small.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.MainActivity;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.model.bean.GeneralBean;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.VerificationUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/15.
 */
public class FragmentRegister extends Fragment implements View.OnClickListener {

    private String TAG = getClass().getName();
    private View view;
    private EditText et_regist_tel;
    private EditText et_regist_code;
    private EditText et_regist_pwd;
    private Button btn_regist;
    private CheckBox cb_check_pwd;
    private TextView tv_regist_code;
    private DataChangedListener mDataListener = new DataChangedListener();
    private String tel;
    private String code;
    private String pwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        initView();
        return view;
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetVerifiFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetVerifiSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.RegisterFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.RegisterSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.CheckveriFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.CheckveriSuccess, mDataListener);
    }

    private void initView() {
        et_regist_tel = (EditText) view.findViewById(R.id.et_regist_tel);//注册手机号
        et_regist_code = (EditText) view.findViewById(R.id.et_regist_code);//验证码
        et_regist_pwd = (EditText) view.findViewById(R.id.et_regist_pwd);//设置密码
        btn_regist = (Button) view.findViewById(R.id.btn_regist);//注册
        tv_regist_code = (TextView) view.findViewById(R.id.tv_regist_code);//获取验证码
        btn_regist.setOnClickListener(this);
        tv_regist_code.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        registerDataListener();
        et_regist_tel.setText(FragmentLogin.Phone);//设置登录页面传来的手机号
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regist:
                tel = et_regist_tel.getText().toString();
                code = et_regist_code.getText().toString();
                pwd = et_regist_pwd.getText().toString();
                getRegister(tel, code, pwd);
                break;
            case R.id.tv_regist_code:
                tel = et_regist_tel.getText().toString();
                getVerfication(tel);
                break;
            default:
                break;
        }
    }

    //注册
    private void getRegister(String tel, String code, String pwd) {
        if(Common.isStringNull(tel)){
            CustomToast.showToast(getString(R.string.telephone_isempty));
            return;
        }
        if(!VerificationUtil.isMobileNO(tel)){
            CustomToast.showToast(getString(R.string.telephone_iscoorrect));
            return;
        }
        if(Common.isStringNull(code)){
            CustomToast.showToast(getString(R.string.code_isempty));
            return;
        }
        if(Common.isStringNull(pwd)){
            CustomToast.showToast(getString(R.string.password_isempty));
            return;
        }
        checkVerfication();//校验验证码
    }

    //获取验证码
    private void getVerfication(String tel) {
        if (!VerificationUtil.isMobileNO(tel)) {
            CustomToast.showToast(getString(R.string.telephone_iscoorrect));
            return;
        }
        CustomToast.showToast("正在获取验证码");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tellphone", et_regist_tel.getText().toString());
        params.put("messagetype", "1");
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Verification_URL, params,
                Constants.Http_Request_Tag_Verification, BaseApplication.DataType.GetVerifiFail, BaseApplication.DataType.GetVerifiSuccess);
    }

    //校验验证码
    private void checkVerfication() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tellphone", et_regist_tel.getText().toString());
        params.put("messagetype", "1");
        params.put("messagecode", et_regist_code.getText().toString());
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Checkverification_URL, params,
                Constants.Http_Request_Tag_Verification, BaseApplication.DataType.CheckveriFail, BaseApplication.DataType.CheckveriSuccess);
    }

    //注册
    private void getRegist() {
        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("registertype", "1");
        parames.put("messagecode", et_regist_code.getText().toString());
        parames.put("tellphone", et_regist_tel.getText().toString());
        parames.put("password", et_regist_pwd.getText().toString());
        LogicRequest.sharedInstance().postRequestListLogic(TAG, HttpConstants.Register_URL, parames, Constants.Http_Request_Tag_Verification,
                BaseApplication.DataType.RegisterFail, BaseApplication.DataType.RegisterSuccess);
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
                    CustomToast.showToast(data.toString());
                    break;
                case GetVerifiSuccess:
                    CustomToast.showToast(data.toString());
                    break;
                case CheckveriFail:
                    getResult(data.toString());
                    break;
                case CheckveriSuccess:
                    getResult(data.toString());
                    break;
                case RegisterFail:
                    CustomToast.showToast(data.toString());
                    break;
                case RegisterSuccess:
                    jsonUserInfo(data.toString());
                    break;
            }
        }
    }

    public void getResult(String json) {
        Gson gson = new Gson();
        GeneralBean generalBean = gson.fromJson(json, GeneralBean.class);
        String message = generalBean.getMessage();
        String result = generalBean.getResult();
        if (result.equals("Fail")) {
            CustomToast.showToast(message);
        } else {
            getRegist();
        }
    }

    private void jsonUserInfo(String json) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(json, UserBean.class);
        String result = userBean.Result;
        if (result.equals("Fail")) {
            String message = userBean.Message;
            CustomToast.showToast(message);
        } else {
            UserBean userList = userBean.UserList;
            userList.setLoginType(Constants.UserLoginType_Normal);
            UserDaoHelper.getInstance().saveUserBean(userList);  //注册、第三方登录成功后 都调用这个 存储用户数据
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
}