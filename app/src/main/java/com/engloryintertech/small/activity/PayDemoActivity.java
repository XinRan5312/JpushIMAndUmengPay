package com.engloryintertech.small.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.engloryintertech.small.R;
import com.engloryintertech.small.alipaypay.AuthResult;
import com.engloryintertech.small.alipaypay.OrderInfoUtil2_0;
import com.engloryintertech.small.alipaypay.PayResult;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomToast;
import java.util.HashMap;
import java.util.Map;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016090601858192";

	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	public String RSA_PRIVATE;

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						Toast.makeText(PayDemoActivity.this,
								"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
								.show();
					} else {
						// 其他状态值则为授权失败
						Toast.makeText(PayDemoActivity.this,
								"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

					}
					break;
				}
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alipay_pay_main);
		registerDataListener();
		RSA_PRIVATE = BaseApplication.getApplication().getResources().getString(R.string.value_sign);
	}

	private void registerDataListener(){
		BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.AliPayOrderInforFail, mDataListener);
		BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.AliPayOrderInforSuccess, mDataListener);
	}

	public void getOrderInfo(View view){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderlistid", "15313246094");//订单编号
		params.put("payamount", "0.01");
		params.put("paymethod", Constants.Pay_AliPay);
		LogicRequest.sharedInstance().postRequestListLogic("addPayDetail", HttpConstants.addPayDetail, params, "requestTag",
				BaseApplication.DataType.AliPayOrderInforFail, BaseApplication.DataType.AliPayOrderInforSuccess);
	}

	private DataChangedListener mDataListener = new DataChangedListener();
	private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
		@Override
		public void onChange(BaseApplication.DataType type, Object data) {
			// TODO Auto-generated method stub
			switch (type) {
				case AliPayOrderInforSuccess:
					gotoAliPay(data.toString());
					break;
			}
		}
	}

	public void goToPay(View view){
	}

	private void gotoAliPay(final String str){
		if(!Common.isStringNull(str)){
			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					PayTask alipay = new PayTask(PayDemoActivity.this);
					Map<String, String> result = alipay.payV2(str, true);
					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}else{
			CustomToast.showToast("订单信息是空的");
		}
	}

	/**
	 * 支付宝支付业务
	 *
	 * @param v
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * orderInfo的获取必须来自服务端；
		 */
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

}

