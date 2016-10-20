package com.engloryintertech.small.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加地址
 */
public class AddLocationActivity extends BaseActivity {

    private String TAG = getClass().getName();
    private ImageView ima_back_add;
    private EditText et_name;
    private EditText et_phone;
    private TextView tv_add_save;
    private RadioButton rb_nan;
    private RadioButton rb_nv;
    private DataChangedListener mDataListener = new DataChangedListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location,false);
        registerDataListener();
        initViews();
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.AddLocationFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.AddLocationSuccess, mDataListener);
    }

    @Override
    protected void initViews() {
        ima_back_add = (ImageView) findViewById(R.id.ima_back_add);
        tv_add_save = (TextView) findViewById(R.id.tv_add_save);
        rb_nan = (RadioButton) findViewById(R.id.rb_nan);
        rb_nv = (RadioButton) findViewById(R.id.rb_nv);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        ima_back_add.setOnClickListener(this);
        tv_add_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_back_add:
                finish();
                break;
            case R.id.tv_add_save:
//                Intent intent = new Intent(AddLocationActivity.this, LocationActivity.class);
//                intent.putExtra("name", et_name.getText().toString());
//                intent.putExtra("phone", et_phone.getText().toString());
//                intent.putExtra("sex", getGender());
//                setResult(0, intent);
//                finish();//最大分辨率
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("name", et_name.getText().toString());
                params.put("usersex", getGender());
                params.put("address", "");
                params.put("tellphone", et_phone.getText().toString());
                params.put("longitude", "");
                params.put("latitude", "");
                addLocation(params);
                break;
            default:
                break;
        }
    }

    /**
     * 添加收货地址
     */
    private void addLocation(Map<String, Object> params) {
        LogicRequest.sharedInstance().postRequestListLogic(TAG, HttpConstants.AddLocation, params,
                "AddLocationTag", BaseApplication.DataType.AddLocationFail, BaseApplication.DataType.AddLocationSuccess);
    }

    public String getGender() {
        if (rb_nan.isChecked()) {
            return "先生";
        } else if (rb_nv.isChecked()) {
            return "女士";
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
        super.onDestroy();
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case AddLocationFail:
                    Tools.error(TAG, data.toString());
                    break;
                case AddLocationSuccess:
                    Tools.error(TAG, data.toString());
                    break;
            }
        }
    }
}