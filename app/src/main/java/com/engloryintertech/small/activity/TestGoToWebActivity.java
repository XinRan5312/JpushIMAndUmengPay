package com.engloryintertech.small.activity;

import android.os.Bundle;
import android.view.View;

import com.engloryintertech.small.R;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.webview.WebActivity;

/**
 * Created by admin on 2016/10/8.
 */
public class TestGoToWebActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goto_web_layout,false);
	}

	@Override
	protected void initViews() {

	}

	@Override
	public void onClick(View v) {

	}

	/**我的订单*/
	public void GoToMyOrderList(View view){
		WebActivity.startActivity(TestGoToWebActivity.this, HttpConstants.orderListUrl, "",
				WebActivity.OPEN_TYPE_GET, false);
	}

	/**支付*/
	public void GoToPay(View view){
		WebActivity.startActivity(TestGoToWebActivity.this, HttpConstants.orderListUrl, "",
				WebActivity.OPEN_TYPE_GET, false);
	}

	/**发布服务 相册*/
	public void GoToSelectImg(View view){
		WebActivity.startActivity(TestGoToWebActivity.this, HttpConstants.PublishServerUrl, "",
				WebActivity.OPEN_TYPE_GET, false);
	}
}
