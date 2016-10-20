package com.engloryintertech.small.im;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.engloryintertech.small.R;
import com.engloryintertech.small.jpush.JpushUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

/**
 * Created by qixinh on 16/10/8.
 * JPush:
 * AppKey :  3e92f96d978c88a6f2e59584
   Master Secret:   da03a48ade896e09216ee2dd
 */
public class XinRanApp extends Application {
    private static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
//        //注意：RongPushClient.registerGCMPush() 一定要放在 RongIM.init() 前面。
//        try {
//            RongPushClient.registerGCM(this);
//        } catch (RongException e) {
//            e.printStackTrace();
//        }
        /**
         * 初始化Jpush
         */
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        /**
         * 初始化融云
         */
        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);

            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

                DemoContext.init(this);
            }
        }

         initDisplayImageOptions();
    }

    private void initDisplayImageOptions() {

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.friend_default_portrait)
                .showImageOnFail(R.mipmap.friend_default_portrait)
                .showImageOnLoading(R.mipmap.friend_default_portrait)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    public static DisplayImageOptions getOptions() {
        return options;
    }
    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}

