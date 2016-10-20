package com.engloryintertech.small.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.engloryintertech.small.R;
import com.engloryintertech.small.adapter.MineLocationAdapter;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.map.AMapUtil;
import com.engloryintertech.small.map.GetLocation;
import com.engloryintertech.small.map.ToastUtil;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseActivity implements TextWatcher, Inputtips.InputtipsListener {

    private String TAG = getClass().getName();
    private ImageView ima_location_back;
    private AutoCompleteTextView act_search;// 输入搜索关键字
    private LinearLayout line_curren_location;
    private TextView tv_add_location;
    private ListView lv_mine_location;
    private MineLocationAdapter mineLocationAdapter;//我的地址
    private LinearLayout line_near_local;
    private DataChangedListener mDataListener = new DataChangedListener();
    private TextView tv_location_city;

    //地址管理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location,false);
        registerDataListener();
        initViews();//初始化数据
        getListView();//获得listview
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserLocationFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.GetUserLocationSuccess, mDataListener);
    }

    private void getListView() {
        getUseLocation();
    }

    private void getUseLocation() {
        //获取我的所有地址
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.GetUserAllLocation, "GetUserLocationTag"
                , BaseApplication.DataType.GetUserLocationFail, BaseApplication.DataType.GetUserLocationSuccess);
    }

    @Override
    protected void initViews() {
        ima_location_back = (ImageView) findViewById(R.id.ima_location_back);
        act_search = (AutoCompleteTextView) findViewById(R.id.act_search);//poi检索
        line_curren_location = (LinearLayout) findViewById(R.id.line_curren_location);
        tv_add_location = (TextView) findViewById(R.id.tv_add_location);//新增地址
        lv_mine_location = (ListView) findViewById(R.id.lv_mine_location);
        line_near_local = (LinearLayout) findViewById(R.id.line_near_local);
        tv_location_city = (TextView) findViewById(R.id.tv_location_city);
        tv_location_city.setOnClickListener(this);
        tv_add_location.setOnClickListener(this);
        ima_location_back.setOnClickListener(this);
        line_curren_location.setOnClickListener(this);//当前位置
        line_near_local.setOnClickListener(this);
        act_search.addTextChangedListener(this);//添加文本输入框监听事件
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_location_back:
                finish();
                break;
            case R.id.line_curren_location:
                CustomToast.showToast("定位当前位置");
                new GetLocation(getApplicationContext()).getLocation();
                Tools.error(TAG, "GetLocation" + GetLocation.LocationInfo);
                break;
            case R.id.tv_add_location:
                Intent intent = new Intent(LocationActivity.this, AddLocationActivity.class);//添加地址
                startActivityForResult(intent, 1);
                break;
            case R.id.line_near_local:
                Intent intent1 = new Intent(LocationActivity.this, NearLocalActivity.class);//附近地址
                startActivity(intent1);
                break;
            case R.id.tv_location_city:
                Intent intent2 = new Intent(LocationActivity.this, ChooseLocationActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //Inputtips
    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    LocationActivity.this,
                    R.layout.route_inputs, listString);
            act_search.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showerror(LocationActivity.this, rCode);
        }
    }

    //textwatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, "北京");
            Inputtips inputTips = new Inputtips(LocationActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == 0 && data != null) {
//            Tools.error(TAG, data.getStringExtra("name"));
//            Tools.error(TAG, data.getStringExtra("phone"));
//            Tools.error(TAG, data.getStringExtra("sex"));
//        }
        super.onActivityResult(requestCode, resultCode, data);
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
                case GetUserLocationFail:
                    Tools.error(TAG, data.toString());
                    break;
                case GetUserLocationSuccess:
                    Tools.error(TAG, data.toString());
                    Gson gson = new Gson();
                    break;
            }
        }
    }
}