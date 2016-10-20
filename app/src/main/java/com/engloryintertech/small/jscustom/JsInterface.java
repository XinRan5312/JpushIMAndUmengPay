package com.engloryintertech.small.jscustom;

import android.webkit.JavascriptInterface;

import com.engloryintertech.small.tools.Tools;

/**
 * Created by admin on 2016/9/28.
 */
public class JsInterface {
	/*interface for javascript to invokes*/
	public interface wvClientClickListener {
		public void wvHasClickEnvent();
	}

	private wvClientClickListener wvEnventPro = null;
	public void setWvClientClickListener(wvClientClickListener listener) {
		wvEnventPro = listener;
	}


	public void javaFunction() {
		if(wvEnventPro != null)
			wvEnventPro.wvHasClickEnvent();
	}

	//方法暴露给JS脚本调用
	@JavascriptInterface
	public void activityFinish() {
		Tools.debugger("activityFinish", "activityFinish");
	}
}
