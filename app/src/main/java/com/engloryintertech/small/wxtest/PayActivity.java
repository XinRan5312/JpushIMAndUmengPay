package com.engloryintertech.small.wxtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.model.bean.MallPayBean;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class PayActivity extends Activity {

	private IWXAPI api;
	private MallPayBean mWXPayBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wx_pay);
		registerDataListener();
		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);

		Button appay_btn_info = (Button) findViewById(R.id.appay_btn_info);
		appay_btn_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPayDetail();
			}
		});

		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Common.isNetEnable()){
					if (!api.isWXAppInstalled()) {
						CustomToast.showToast("请安装微信客户端");
					} else {
						goToPay();
					}
				}else{
					CustomToast.showToast("检查网络连接");
				}
			}
		});
	}

	private void registerDataListener(){
		BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXPayOrderInforFail, mDataListener);
		BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.WXPayOrderInforSuccess, mDataListener);
	}

	/**获取订单信息*/
	private void addPayDetail(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderlistid", "15313246094");//订单编号
		params.put("payamount", "0.01");
		params.put("paymethod", Constants.Pay_WX);
		LogicRequest.sharedInstance().postRequestListLogic("addPayDetail", HttpConstants.addPayDetail, params, "requestTag",
				BaseApplication.DataType.WXPayOrderInforFail, BaseApplication.DataType.WXPayOrderInforSuccess);
	}

	/**去支付*/
	private void goToPay(){
		PayReq req = new PayReq();
		req.appId			= mWXPayBean.getAppId();
		req.partnerId		= mWXPayBean.getPartnerId();
		req.packageValue	= mWXPayBean.getPackageValue();
		req.prepayId		= mWXPayBean.getPrepayId();
		req.nonceStr		= mWXPayBean.getNonceStr();
		req.timeStamp		= mWXPayBean.getTimeStamp() + "";
		req.sign			= mWXPayBean.getSign();
		Tools.debugger("goToPay", "req appId : " + req.appId + ",partnerId : " + req.partnerId
				+ ",prepayId : " + req.prepayId + ",nonceStr : " + req.nonceStr + ",timeStamp : " + req.timeStamp
				+ ",packageValue : " + req.packageValue + ",sign : " + req.sign);
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.registerApp(Constants.WX_APP_ID);
		api.sendReq(req);
	}

	private DataChangedListener mDataListener = new DataChangedListener();
	private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
		@Override
		public void onChange(BaseApplication.DataType type, Object data) {
			// TODO Auto-generated method stub
			switch (type) {
				case WXPayOrderInforSuccess:
					mWXPayBean = (MallPayBean) data;
					break;
			}
		}
	}
}
