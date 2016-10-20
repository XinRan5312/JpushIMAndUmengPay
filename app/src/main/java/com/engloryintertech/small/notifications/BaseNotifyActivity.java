package com.engloryintertech.small.notifications;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.BaseActivity;

/**
 * Created by qixinh on 16/10/20.
 */
public class BaseNotifyActivity extends BaseActivity {

    private NotificationManager mNotificationManager;
    /**
     * 通知栏按钮点击事件对应的ACTION
     */
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    private boolean isPlay;
    private String TAG = "BaseNotifyActivity";
    private ButtonBroadcastReceiver bReceiver;
    private boolean isPause;
    private boolean isCustom;
    private int mProgress;
    private int notifyId;
    private boolean indeterminate;

    public void clearNotify(int notifyId) {
        mNotificationManager.cancel(notifyId);
    }

    public void clearNotify(String tag, int notifyId) {
        mNotificationManager.cancel(tag, notifyId);
    }

    public void clearNotifyAll() {
        mNotificationManager.cancelAll();
    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    public void shwoCustomVieNotify(int notifyId) {
        NotificationCompat.Builder mBuilder = null;
        //先设定RemoteViews
        RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.notify_view_custom);
        //设置对应IMAGEVIEW的ID的资源图片
        view_custom.setImageViewResource(R.id.custom_icon, R.drawable.icon);
//		view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
//		view_custom.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
        //设置显示
//		view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
//		view_custom.setLong(R.id.tv_custom_time,"setTime", System.currentTimeMillis());//不知道为啥会报错，过会看看日志
        //设置number
//		NumberFormat num = NumberFormat.getIntegerInstance();
//		view_custom.setTextViewText(R.id.tv_custom_num, num.format(this.number));
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContent(view_custom)
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("有新资讯")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
                .setSmallIcon(R.drawable.icon);
//		mNotificationManager.notify(notifyId, mBuilder.build());
        Notification notify = mBuilder.build();
        notify.contentView = view_custom;
//		Notification notify = new Notification();
//		notify.icon = R.drawable.icon;
//		notify.contentView = view_custom;
//		notify.contentIntent = getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
        mNotificationManager.notify(notifyId, notify);
    }

    /**
     * 带按钮的通知栏
     */
    public void showButtonNotify() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notify_view_custom_button);
        mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
        //API3.0 以上的时候显示按钮，否则消失
        mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "周杰伦");
        mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "七里香");
        //如果版本号低于（3。0），那么不显示按钮
        if (getSystemVersion() <= 9) {
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
        } else {
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
            //
            if (true) {
                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
            } else {
                mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
            }
        }

        //点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        /* 上一首按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
        //这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		/* 播放/暂停  按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		/* 下一首 按钮  */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.drawable.sing_icon);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        //会报错，还在找解决思路
//		notify.contentView = mRemoteViews;
//		notify.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        mNotificationManager.notify(200, notify);
    }

    @Override
    protected void initViews() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取当前系统SDK版本号
     */
    public int getSystemVersion() {
		/*获取当前系统的android版本号*/
        int version = android.os.Build.VERSION.SDK_INT;
        return version;
    }

    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return version
     * @throws Exception
     */
    public static String getAppVersion(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String versionName = packInfo.versionName;
        return versionName;
    }

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /**
     * 上一首 按钮点击 ID
     */
    public final static int BUTTON_PREV_ID = 1;
    /**
     * 播放/暂停 按钮点击 ID
     */
    public final static int BUTTON_PALY_ID = 2;
    /**
     * 下一首 按钮点击 ID
     */
    public final static int BUTTON_NEXT_ID = 3;
    /**
     *	 广播监听按钮点击时间
     */

    /**
     * 带按钮的通知栏点击广播接收
     */
    public void initButtonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(bReceiver, intentFilter);
    }

    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_PREV_ID:
                        Log.d(TAG, "上一首");
                        Toast.makeText(getApplicationContext(), "上一首", Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_PALY_ID:
                        String play_status = "";
                        isPlay = !isPlay;
                        if (isPlay) {
                            play_status = "开始播放";
                        } else {
                            play_status = "已暂停";
                        }
                        showButtonNotify();
                        Log.d(TAG, play_status);
                        Toast.makeText(getApplicationContext(), play_status, Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_NEXT_ID:
                        Log.d(TAG, "下一首");
                        Toast.makeText(getApplicationContext(), "下一首", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (bReceiver != null) {
            unregisterReceiver(bReceiver);
        }
        super.onDestroy();
    }

    //######################################################
    //######################################################
    NotificationCompat.Builder mBuilder;
    DownloadThread downloadThread;

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setContentIntent(getDefalutIntent(0))
                        // .setNumber(number)//显示数量
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                        // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                        // requires VIBRATE permission
                .setSmallIcon(R.drawable.icon);
    }

    /**
     * 显示带进度条通知栏
     */
    public void showProgressNotify(int notifyId, boolean indeterminate, int progress) {

        mBuilder.setContentTitle("等待下载")
                .setContentText("进度:")
                .setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
        if (indeterminate) {
            //不确定进度的
            mBuilder.setProgress(0, 0, true);
        } else {
            //确定进度的
            mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果
        }
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * 显示自定义的带进度条通知栏
     */
    private void showCustomProgressNotify(String status, int notifyId, int progress) {

        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notify_view_custom_progress);
        mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.drawable.icon);
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "今日头条");
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
        if (progress >= 100 || downloadThread == null) {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
        } else {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
        }
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(0))
                .setTicker("头条更新");
        Notification nitify = mBuilder.build();
        nitify.contentView = mRemoteViews;
        mNotificationManager.notify(notifyId, nitify);
    }

    /**
     * 开始下载
     */
    public void startDownloadNotify() {
        isPause = false;
        if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
        } else {
            downloadThread = new DownloadThread();
            downloadThread.start();
        }
    }

    /**
     * 暂停下载
     */
    public void pauseDownloadNotify(int progress, int notifyId) {
        isPause = true;
        if (!isCustom) {
            mBuilder.setContentTitle("已暂停");
            setNotify(progress, notifyId);
        } else {
            showCustomProgressNotify("已暂停", notifyId, progress);
        }
    }

    /**
     * 取消下载
     */
    public void stopDownloadNotify(int notifyId, int progress) {
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
        downloadThread = null;
        if (!isCustom) {
            mBuilder.setContentTitle("下载已取消").setProgress(0, 0, false);
            mNotificationManager.notify(notifyId, mBuilder.build());
        } else {
            showCustomProgressNotify("已暂停", notifyId, progress);
        }
    }

    /**
     * 设置下载进度
     */
    public void setNotify(int progress, int notifyId) {
        mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {

        @Override
        public void run() {
            int now_progress = 0;
            // Do the "lengthy" operation 20 times
            while (now_progress <= 100) {
                // Sets the progress indicator to a max value, the
                // current completion percentage, and "determinate"
                if (downloadThread == null) {
                    break;
                }
                if (!isPause) {
                    mProgress = now_progress;
                    if (!isCustom) {
                        mBuilder.setContentTitle("下载中");
                        if (!indeterminate) {
                            setNotify(mProgress, notifyId);
                        }
                    } else {
                        showCustomProgressNotify("下载中", notifyId, mProgress);
                    }
                    now_progress += 10;
                }
                try {
                    // Sleep for 1 seconds
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                }
            }
            // When the loop is finished, updates the notification
            if (downloadThread != null) {
                if (!isCustom) {
                    mBuilder.setContentText("下载完成")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    mNotificationManager.notify(notifyId, mBuilder.build());
                } else {
                    showCustomProgressNotify("下载完成", notifyId, mProgress);
                }
            }
        }
    }
}
