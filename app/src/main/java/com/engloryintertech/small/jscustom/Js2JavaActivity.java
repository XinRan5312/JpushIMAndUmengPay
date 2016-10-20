package com.engloryintertech.small.jscustom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.engloryintertech.small.R;

/**
 * Created by admin on 2016/9/28.
 */
public class Js2JavaActivity extends Activity {
	private Button btn_show,btn_hide;
	private WebView wv;
	private JsInterface JSInterface2 = new JsInterface();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.js2java);

		btn_show = (Button)findViewById(R.id.btn_java2js_show);
		btn_hide = (Button)findViewById(R.id.btn_java2js_hide);
		wv = (WebView)findViewById(R.id.wv_js2java);

		wv.getSettings().setJavaScriptEnabled(true);
		wv.addJavascriptInterface(JSInterface2,"JSInterface2");
		wv.setWebViewClient(new webviewClient());

		wv.loadUrl("file:///android_asset/testjs_ai.html");
	}

	class webviewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			btn_show.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "You click show button", 1000).show();
					wv.loadUrl(String.format("javascript:java2js(0)"));//这里是java端调用webview的JS
				}
			});
			btn_hide.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "You click hide button", 1000).show();
					wv.loadUrl(String.format("javascript:java2js(1)"));//这里是java端调用webview的JS
				}
			});

			JSInterface2.setWvClientClickListener(new webviewClick());//这里就是js调用java端的具体实现
		}
	}

	class webviewClick implements JsInterface.wvClientClickListener {

		@Override
		public void wvHasClickEnvent() {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(), "link be on click", 1000).show();
		}

	}
}
