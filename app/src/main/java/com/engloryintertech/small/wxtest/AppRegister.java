package com.engloryintertech.small.wxtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.engloryintertech.small.constant.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

		// ����appע�ᵽ΢��
		msgApi.registerApp(Constants.WX_APP_ID);
	}
}
