package com.engloryintertech.small.tools;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.engloryintertech.small.application.BaseApplication;

public class CustomToast {
	
	private static Toast mToast;
	private static final int duration = 500;
	private static Handler mHandler = new Handler();
	private static Runnable runnable = new Runnable() {
		public void run() {
			mToast.show();
		}
	};

	public static void showToast(Context mContext, String text) {
		if (mToast != null) {
			mToast.setText(text);
		} else {
			mToast = Toast.makeText(BaseApplication.getApplication(), text,duration);
		}
		mHandler.removeCallbacks(runnable);
		mHandler.postDelayed(runnable, duration);
	}

	/**引用Id*/
	public static void showToast(int resId) {
		showToast(BaseApplication.getApplication(), BaseApplication.getApplication().getString(resId));
	}

	/**String*/
	public static void showToast(String toast) {
		showToast(BaseApplication.getApplication(), toast);
	}

}