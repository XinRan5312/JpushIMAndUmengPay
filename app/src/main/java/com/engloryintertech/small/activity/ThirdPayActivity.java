package com.engloryintertech.small.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.engloryintertech.small.R;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.model.bean.ResourcesBean;
import com.engloryintertech.small.tools.AppLoginAndShareManage;
import com.engloryintertech.small.wxapi.WXEntryActivity;
import com.engloryintertech.small.wxtest.PayActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;

/**
 * Created by admin on 2016/9/26.
 */
public class ThirdPayActivity extends BaseActivity {

	private Button zhifubao_pay,wx_pay;
	private Handler handler;
	private ResourcesBean mResourcesBean;
	private Bitmap bitmap;
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third_pay_layout,false);
		mResourcesBean = new ResourcesBean();
		mResourcesBean = mResourcesBean.getInfo();
		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
		setHandler();
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		zhifubao_pay = (Button)findViewById(R.id.zhifubao_pay);
		wx_pay = (Button)findViewById(R.id.wx_pay);
	}

	@Override
	protected void initEvents() {
		super.initEvents();
		zhifubao_pay.setOnClickListener(this);
		wx_pay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.zhifubao_pay:
				startActivity(new Intent(this,PayDemoActivity.class));
				break;
			case R.id.wx_pay:
				startActivity(new Intent(this,PayActivity.class));
				break;
		}
	}

	public void OnShare(View view){
		startActivity(new Intent(this, WXEntryActivity.class));
		sendReq();
	}

	private void sendReq() {
		new Thread(new Runnable() {
			public void run() {
				try {
					AppLoginAndShareManage appShare = new AppLoginAndShareManage();
					bitmap = appShare.sharenNeedBitmap(mResourcesBean);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 10011;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void setHandler(){
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
					case 10011:
						AppLoginAndShareManage appShare = new AppLoginAndShareManage();
						api.sendReq(appShare.getWXMediaMessage(mResourcesBean, bitmap));
						break;
					default:
						break;
				}
			}
		};
	}
}
