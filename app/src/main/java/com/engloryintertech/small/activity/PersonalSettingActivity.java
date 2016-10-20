package com.engloryintertech.small.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.nets.utils.OkHttpUtils;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomImageLoader;
import com.engloryintertech.small.tools.ImageLoaderOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户设置
 */
public class PersonalSettingActivity extends BaseActivity {

    private TextView buy_title_textview, buy_title_right;
    private EditText setting_user_name_context,setting_user_sex_context,setting_user_age_context,
            setting_edit_job,setting_edit_company,setting_edit_shchool, setting_edit_email,setting_edit_explain;
    private ImageView setting_user_header;
    private UserBean mUserBean;
    private boolean isChangeUserInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalaetting_layout, false);
        initData();
        initViews();
        initEvents();
    }

    @Override
    protected void initData() {
        super.initData();
        registerDataListener();
        getUserInfo();
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.SaveUserFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.SaveUserSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserInfoFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserInfoSuccess, mDataListener);
    }

    @Override
    protected void initViews() {
        buy_title_textview = (TextView) findViewById(R.id.buy_title_textview);
        buy_title_textview.setText("个人设置");
        buy_title_right = (TextView) findViewById(R.id.buy_title_right);
        buy_title_right.setVisibility(View.VISIBLE);
        buy_title_right.setText("保存");
        setting_user_name_context = (EditText) findViewById(R.id.setting_user_name_context);
        setting_user_sex_context = (EditText) findViewById(R.id.setting_user_sex_context);
        setting_user_age_context = (EditText) findViewById(R.id.setting_user_age_context);
        setting_edit_job = (EditText) findViewById(R.id.setting_edit_job);
        setting_edit_company = (EditText) findViewById(R.id.setting_edit_company);
        setting_edit_shchool = (EditText) findViewById(R.id.setting_edit_shchool);
        setting_edit_email = (EditText) findViewById(R.id.setting_edit_email);
        setting_edit_explain = (EditText) findViewById(R.id.setting_edit_explain);
        setting_user_header = (ImageView) findViewById(R.id.setting_user_header);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        buy_title_right.setOnClickListener(this);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("touserid", UserDaoHelper.getInstance().getUserId());
        LogicRequest.sharedInstance().getRequestListLogic("getUserInfo", HttpConstants.GetUserInfo_URL, params,
                Constants.Http_Request_Tag_PersonalSetting, BaseApplication.DataType.GetUserInfoFail, BaseApplication.DataType.GetUserInfoSuccess);
    }

    private DataChangedListener mDataListener = new DataChangedListener();
    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case GetUserInfoFail:
                    mUserBean = UserDaoHelper.getInstance().getUserInfo();
                    setUserInfo();
                    break;
                case GetUserInfoSuccess:
                    mUserBean = UserDaoHelper.getInstance().getUserInfo();
                    setUserInfo();
                    break;
                case SaveUserFail:
                    break;
                case SaveUserSuccess:
                    break;
            }
        }
    }

    /**设置用户信息*/
    private void setUserInfo(){
        setting_user_header = (ImageView) findViewById(R.id.setting_user_header);
        setUserInfoEdit(mUserBean.getUserName(),setting_user_name_context,"设置昵称");
        setUserInfoEdit(mUserBean.getSex() + "",setting_user_sex_context,"设置性别");
        setUserInfoEdit(mUserBean.getAge() + "",setting_user_age_context,"设置年龄");
        setUserInfoEdit(mUserBean.getProfession(),setting_edit_job,"填写职业，发现同行");
        setUserInfoEdit(mUserBean.getCompany(),setting_edit_company,"填写公司，发现同事");
        setUserInfoEdit(mUserBean.getSchool(),setting_edit_shchool,"--");
        setUserInfoEdit(mUserBean.getEmail(),setting_edit_email,"--");
        setUserInfoEdit(mUserBean.getSignature(),setting_edit_explain,"--");
        CustomImageLoader.getInstance(BaseApplication.getApplication()).displayImage(mUserBean.getAvatarUrl(),setting_user_header,
                ImageLoaderOptions.User_Pic_Option());
    }

    private void setUserInfoEdit(String userInfo,EditText editText,String hintStr){
        if(Common.isStringNull(userInfo)){
            editText.setHint(hintStr);
            editText.setHintTextColor(getResources().getColor(R.color.setting_edit_hint_color));
        }else{
            editText.setText(userInfo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_title_right:
                if (isChangeUserInfo) {
                    /**保存用户信息*/
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
        OkHttpUtils.getInstance().cancelTag(Constants.Http_Request_Tag_PersonalSetting);
    }
}
