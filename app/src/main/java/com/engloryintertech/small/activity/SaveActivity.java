package com.engloryintertech.small.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.CustomToast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户保存
 */
public class SaveActivity extends BaseActivity {

    private String TAG = getClass().getName();
    private static File mTmpFile = null;
    private Button btn_save;
    private byte save_user = 0;
    private DataChangedListener mDataListener = new DataChangedListener();
    private static HashMap<String, File> files;
    private Button btn_change;
    private Button btn_approve;
    private HashMap<String, Object> params;
    private long userid = 39;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    LogicRequest.sharedInstance().postRequestUpFile(TAG, HttpConstants.Saveuser_URL, params, "avatar", files, Constants.Http_Request_Tag_SaveUser
                            , BaseApplication.DataType.SaveUserFail, BaseApplication.DataType.SaveUserSuccess, save_user);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save, false);
        registerDataListener();
        initViews();
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.SaveUserFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.SaveUserSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserInfoFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserInfoSuccess, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.ApproveFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.ApproveSuccess, mDataListener);
    }

    public void getSaveUser() {
        params = new HashMap<String, Object>();
        params.put("username", "Change");
        params.put("usernickname", "Change");
        params.put("address", "上地南口");
        params.put("birthday", getTime());
        params.put("usersex", 4);
        params.put("profession", "送快递");
        params.put("company", "集团");
        params.put("email", "17602885qq@.com");
        params.put("signature", "个性签名");
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void initViews() {
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_approve = (Button) findViewById(R.id.btn_approve);
        btn_save.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_approve.setOnClickListener(this);
    }

    protected static void isShowCamera() {
        // 是否显示调用相机拍照
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStorageDirectory().toString() + "/aaa";
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            mTmpFile = new File(file, System.currentTimeMillis() + "jpg");
            files = new HashMap<String, File>();
            files.put("avatar", mTmpFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            getActivity().startActivityForResult(cameraIntent, 100);
        } else {
            CustomToast.showToast("没有系统照相机发现");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                isShowCamera();
                break;
            case R.id.btn_change:
                getUserInfo();//获取用户信息
                break;
            case R.id.btn_approve:
                getApprove();//获取认证信息
                break;
        }
    }

    private void getApprove() {
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.GetApprove_URL, "GetApprove", BaseApplication.DataType
                .ApproveFail, BaseApplication.DataType.ApproveSuccess);
    }

    //查询用户所有信息
    private void getUserInfo() {
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("userid", 39);
        params.put("touserid", userid);
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.GetUserInfo_URL, params, "GetUserInfo",
                BaseApplication.DataType.GetUserInfoFail, BaseApplication.DataType.GetUserInfoSuccess);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Log.e("SaveActivity______", "测试");
            getSaveUser();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case SaveUserFail:
                    Log.e("SaveActivity______", "SaveUserFail" + data.toString());
                    break;
                case SaveUserSuccess:
                    Log.e("SaveActivity______", "SaveUserSuccess" + data.toString());
                    break;
                case GetUserInfoFail:
                    Log.e("SaveActivity______", "GetUserDataFail" + data.toString());
                    break;
                case GetUserInfoSuccess:
                    Success:
                    Log.e("SaveActivity______", "GetUserDataSuccess" + data.toString());
                    break;
                case ApproveFail:
                    break;
                case ApproveSuccess:
                    break;
            }
        }
    }

    public String getTime() {
        int year = 1992;
        int month = 7;
        int day = 12;
        String monthStr = "";
        String dayStr = "";
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = month + "";
        }

        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = day + "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append((year)).append("-")
                .append((monthStr)).append("-")
                .append((dayStr));
        return sb.toString();
    }
}