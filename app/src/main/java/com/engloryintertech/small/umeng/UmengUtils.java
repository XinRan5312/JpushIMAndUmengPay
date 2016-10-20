package com.engloryintertech.small.umeng;

import android.app.Activity;
import android.content.Context;

import com.engloryintertech.small.application.BaseApplication;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

/**
 * appKey:58083af7e0f55aacae000c42
 * Created by qixinh on 16/10/20.
 * 指南：
 * http://dev.umeng.com/analytics/android-doc/integration?spm=0.0.0.0.AH7j2z
 */
public class UmengUtils {

    /**
     * EScenarioType. E_UM_NORMAL　　普通统计场景类型
     * <p/>
     * EScenarioType. E_UM_GAME     　　游戏场景类型
     * <p/>
     * EScenarioType. E_UM_ANALYTICS_OEM  统计盒子场景类型
     * <p/>
     * EScenarioType. E_UM_GAME_OEM      　 游戏盒子场景类型
     *
     * @param etype
     */
    public static void setScenarioType(MobclickAgent.EScenarioType etype) {
        MobclickAgent.setScenarioType(BaseApplication.getApplication(), etype);
    }

    /**
     * 什么是集成测试？  集成测试是通过收集和展示已注册测试设备发送的日志，来检验SDK集成有效性和完整性的一个服务。
     * 所有由注册设备发送的应用日志将实时地进行展示，您可以方便地查看包括应用版本、渠道名称、自定义事件、页面访问情况等数据，提升集成与调试的工作效率。
     http://mobile.umeng.com/test_devices?ticket=ST-1450072700rPWK4t4q46xw-qpBXzk
     注意： 使用集成测试之后，所有测试数据不会进入应用正式的统计后台，只能在“管理--集成测试--实时日志”里查看，您不必再担心因为测试而导致的数据污染问题，让数据更加真实有效的反应用户使用情况。
     * @param isDebug
     */
    //使用普通测试流程，请先在程序入口添加以下代码打开调试模式：
    public static void setDebugMode(boolean isDebug) {
        MobclickAgent.setDebugMode(isDebug);
    }
    /**
     * 在代码中配置Appkey和Channel
     * <p/>
     * UMAnalyticsConfig(Context context, String appkey, String channelId)
     * <p/>
     * UMAnalyticsConfig(Context context, String appkey, String channelId, EScenarioType eType)
     * <p/>
     * UMAnalyticsConfig(Context context, String appkey, String channelId, EScenarioType eType,Boolean isCrashEnable)
     * <p/>
     * 构造意义：
     * String appkey:官方申请的Appkey
     * String channel: 渠道号
     * EScenarioType eType: 场景模式，包含统计、游戏、统计盒子、游戏盒子
     * Boolean isCrashEnable: 可选初始化. 是否开启crash模式
     *
     * @param config
     */
    public static void startWithConfig(MobclickAgent.UMAnalyticsConfig config) {
        MobclickAgent.startWithConfigure(config);
    }

    public static String getChannel(Context context) {
        return AnalyticsConfig.getChannel(context);
    }

    /**
     * session的统计：是作用在activity的统计时长时候用的（一般不使用下面这两个方法，用专门统计页面的那几个方法就好）
     * <p/>
     * 下面这两个方法一般在BaseActivity的onResum和onPause方法中分别调用
     * 确保在所有的Activity中都调用 MobclickAgent.onResume() 和MobclickAgent.onPause()方法，这两个调用将不会阻塞应用程序的主线程，也不会影响应用程序的性能。
     * 注意 如果您的Activity之间有继承或者控制关系请不要同时在父和子Activity中重复添加onPause和onResume方法，否则会造成重复统计，导致启动次数异常增高。
     * (eg.使用TabHost、TabActivity、ActivityGroup时)。
     * <p/>
     * 当应用在后台运行超过30秒（默认）再回到前端，将被认为是两个独立的session(启动)，例如用户回到home，或进入其他程序，经过一段时间后再返回之前的应用。
     * 可通过接口：MobclickAgent.setSessionContinueMillis(long interval) 来自定义这个间隔（参数单位为毫秒）。
     * 如果开发者调用Process.kill或者System.exit之类的方法杀死进程，请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，用来保存统计数据。
     *
     * @param context
     */
    public static void onStartSession(Activity context) {
        MobclickAgent.onResume(context);
    }

    public static void onPauseSession(Activity context) {
        MobclickAgent.onPause(context);
    }

    /**
     * 账号统计
     */
    //用于自用账号登陆
    public static void onProfileSignIn(String userId) {
        MobclickAgent.onProfileSignIn(userId);
    }
    //三方账号登陆

    /**
     * @param provider
     * @param userId   当用户使用第三方账号（如新浪微博）登录时，可以这样统计：
     *                 MobclickAgent.onProfileSignIn("WB"，"userID");
     */
    public static void onProfileSignIn(String provider, String userId) {
        MobclickAgent.onProfileSignIn(provider, userId);
    }

    /**
     * 账号退出时调用
     */
    public static void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
    }

    /**
     * ##############################################
     * 页面统计
     * <p/>
     * <p/>
     * 包含Activity、Fragment或View的应用
     * 统计应用中包含Fragment的情况比较复杂，首先要明确一些概念。
     * <p/>
     * 1. MobclickAgent.onResume()  和MobclickAgent.onPause()  方法是用来统计应用时长的(也就是Session时长,当然还包括一些其他功能)
     * <p/>
     * 2.MobclickAgent.onPageStart() 和MobclickAgent.onPageEnd() 方法是用来统计页面跳转的
     * <p/>
     * 在仅有Activity的应用中，SDK 自动帮助开发者调用了 2  中的方法，并把Activity 类名作为页面名称统计。但是在包含fragment的程序中我们希望统计更详细的页面，所以需要自己调用方法做更详细的统计。
     * <p/>
     * 首先，需要在程序入口处，调用 MobclickAgent.openActivityDurationTrack(false)  禁止默认的页面统计方式，这样将不会再自动统计Activity。
     * <p/>
     * 然后需要手动添加以下代码：
     * <p/>
     * 1. 使用 MobclickAgent.onResume 和 MobclickAgent.onPause 方法统计时长, 这和基本统计中的情况一样(针对Activity)
     * <p/>
     * 2. 使用 MobclickAgent.onPageStart 和 MobclickAgent.onPageEnd 方法统计页面(针对页面,页面可能是Activity 也可能是Fragment或View)
     */

    //如果页面是直接由Activity实现的，统计代码大约是如下这样在BaseActivity中：
    public static void onResumeOnlyActivity(Activity context) {
        //"SplashScreen"这个名字可以在BaseActivity的OnStart中动态的得到
        MobclickAgent.onPageStart("SplashScreen"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(context);          //统计时长
    }

    public static void onPauseOnlyActivity(Activity context) {

        MobclickAgent.onPageEnd("SplashScreen"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(context);
    }

    // 如果页面是使用FragmentActivity + Fragment实现的，需要在 FragmentActivity 中统计时长,
    //并在其包含的 Fragment 中统计页面,如下面的四个方法：

    public static void onResumeActivityWithFragments(Activity context) {

        MobclickAgent.onResume(context);          //统计时长
    }

    public static void onPauseActivityWithFragments(Activity context) {

        MobclickAgent.onPause(context);
    }

    public static void onResumeFragment() {
        //"SplashScreen"这个名字可以在BaseActivity的OnStart中动态的得到
        MobclickAgent.onPageStart("FragmentOne"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)

    }

    public static void onPauseFragment() {

        MobclickAgent.onPageEnd("FragmentOne"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义

    }

    /**
     * 日志上传加密策略
     * 如果enable为true，SDK会对日志进行加密。加密模式可以有效防止网络攻击，提高数据安全性。
     * 如果enable为false，SDK将按照非加密的方式来传输日志。
     * 如果您没有设置加密模式，SDK的加密模式为false（不加密）。
     * <p/>
     * 日志发送策略在官网上配置就好：http://dev.umeng.com/analytics/android-doc/integration?spm=0.0.0.0.AH7j2z
     */
    public static void enableEncrypt(boolean enable) {
        MobclickAgent.enableEncrypt(enable);
    }
    /**
     * 自定义事件：
     * 示例：统计微博应用中"转发"事件发生的次数，那么在转发的函数里调用
     * 设置详情请看官网：http://dev.umeng.com/analytics/android-doc/integration?spm=0.0.0.0.AH7j2z
     */

    /**
     * 错误统计
     Android统计SDK从V4.6版本开始内建错误统计，不需要开发者再手动集成。

     SDK通过Thread.UncaughtExceptionHandler  捕获程序崩溃日志，并在程序下次启动时发送到服务器。 如不需要错误统计功能，可通过此方法关闭

     MobclickAgent.setCatchUncaughtExceptions(false);

     如果开发者自己捕获了错误，需要上传到友盟服务器可以调用下面方法：

     public static void reportError(Context context, String error)
     //或
     public static void reportError(Context context, Throwable e)

     */

    /**
     * 社交统计
     * 向第三方分享后就可以调用下面的代码
     */
    public static void onSocialEvent(Activity context, UMPlatformData... platform) {
        MobclickAgent.onSocialEvent(context, platform);
    }

    /**
     * @param media 三方类型 比如新浪微博 腾讯微博 QQ  微信朋友圈等详情看枚举类UMPlatformData.UMedia
     * @param userId 本app userId
     * @param name name
     * @param thirdId 使用的三方userid
     * @param gender 性别
     * @return
     */
    public static UMPlatformData buildThirdUMPlatformData(UMPlatformData.UMedia media,String userId,String name,String thirdId,UMPlatformData.GENDER gender){
        UMPlatformData platform = new UMPlatformData(media, "user_id");
        platform.setName(name);
        platform.setGender(gender);
        platform.setWeiboId(thirdId);
        return platform;
    }

}
