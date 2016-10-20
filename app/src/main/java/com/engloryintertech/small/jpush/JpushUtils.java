package com.engloryintertech.small.jpush;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by qixinh on 16/10/19.
 */
public class JpushUtils {
    public static final String TAG = JpushUtils.class.getSimpleName();
    public static final String KEY_APP_KEY = "JPUSH_APPKEY";

    public static void initJpush() {
        JPushInterface.init(BaseApplication.getApplication());
    }

    public static void stopJpush() {
        JPushInterface.stopPush(BaseApplication.getApplication());
    }

    public static void resumeJpush() {
        JPushInterface.resumePush(BaseApplication.getApplication());
    }

    public static String getRegistrationID() {
        return JPushInterface.getRegistrationID(BaseApplication.getApplication());
    }
    /**
     * 标签和别名：
     * 为什么需要别名与标签
     * <p>
     * 推送消息时，要指定推送的对象：全部，某一个人，或者某一群人。
     * ############################################################
     * http://docs.jiguang.cn/jpush/client/Android/android_senior/
     * http://docs.jiguang.cn/jpush/client/Android/android_api/#api_1
     * #######################################################
     * <p>
     * <p>
     * alias：
     * null 此次调用不设置此值。（注：不是指的字符串"null"）
     * "" （空字符串）表示取消之前的设置。
     * 每次调用设置有效的别名，覆盖之前的设置。
     * 有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     * 限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     * <p>
     * <p>
     * tags：
     * null 此次调用不设置此值。（注：不是指的字符串"null"）
     * 空数组或列表表示取消之前的设置。
     * 每次调用至少设置一个 tag，覆盖之前的设置，不是新增。
     * 有效的标签组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|￥。
     * 限制：每个 tag 命名长度限制为 40 字节，最多支持设置 1000 个 tag，但总长度不得超过7K字节。（判断长度需采用UTF-8编码）
     * callback
     * 在 TagAliasCallback 的 gotResult 方法，返回对应的参数 alias, tags。并返回对应的状态码：0为成功，其他返回码请参考错误码定义。
     */
    public static void setTag(String tag) {

        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            Log.e("jpush_tag", "标签不能为空");
            return;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!JpushUtils.isValidTagAndAlias(sTagItme)) {
                Log.e("jpush_tag", "标签无效");
                return;
            }
            tagSet.add(sTagItme);
        }

        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), null, tagSet, mTagsCallback);
    }

    public static void setAlias(String alias) {
        if (TextUtils.isEmpty(alias)) {
            Log.e("jpush_Alias", "别名不能为空");
            return;
        }
        if (!JpushUtils.isValidTagAndAlias(alias)) {
            Log.e("jpush_Alias", "别名无效");
            return;
        }
        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), alias, null, mAliasCallback);
    }

    public static void setTagsAndAlias(String tag, String alias) {
        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            Log.e("jpush_tag", "标签不能为空");
            return;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!JpushUtils.isValidTagAndAlias(sTagItme)) {
                Log.e("jpush_tag", "标签无效");
                return;
            }
            tagSet.add(sTagItme);
        }
        if (TextUtils.isEmpty(alias)) {
            Log.e("jpush_Alias", "别名不能为空");
            return;
        }
        if (!JpushUtils.isValidTagAndAlias(alias)) {
            Log.e("jpush_Alias", "别名无效");
            return;
        }
        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), alias, tagSet, mTagsAndAliasCallback);
    }

    private static final TagAliasCallback mAliasCallback = new TagAliasCallback() {


        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JpushUtils.isConnected(BaseApplication.getApplication())) {
                        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), alias, null, mAliasCallback);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            JpushUtils.showToast(logs, BaseApplication.getApplication());
        }

    };
    private static final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JpushUtils.isConnected(BaseApplication.getApplication())) {
                        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), null, tags, mTagsCallback);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            JpushUtils.showToast(logs, BaseApplication.getApplication());
        }

    };
    private static final TagAliasCallback mTagsAndAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JpushUtils.isConnected(BaseApplication.getApplication())) {
                        JPushInterface.setAliasAndTags(BaseApplication.getApplication(), alias, tags, mTagsAndAliasCallback);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            JpushUtils.showToast(logs, BaseApplication.getApplication());
        }
    };
    //##########################标签和别名设置结束

    /**
     * Jpush通知栏的设置，如果不调用下面的方法，系统会有默认的
     */
    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setPushNotificationStyleBasic(Activity activity, @IdRes int drawableId) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(activity);
        builder.statusBarDrawable = drawableId;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStylePushNotificationCustom(Activity activity, @IdRes int drawableId, @LayoutRes int layoutId) {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(activity, layoutId, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = drawableId;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
    }

    //##########################通知栏设置结束
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    // 取得AppKey
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return appKey;
    }

    // 取得版本号
    public static String GetVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    public static String getDeviceId(Context context) {
        String deviceId = JPushInterface.getUdid(context);

        return deviceId;
    }

    public static void showToast(final String toast, final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(JpushUtils.class.getSimpleName(), e.getMessage());
        }
        return imei;
    }

}
