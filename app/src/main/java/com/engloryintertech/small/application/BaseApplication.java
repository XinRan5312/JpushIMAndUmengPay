package com.engloryintertech.small.application;

import android.content.Context;

import com.engloryintertech.small.R;
import com.engloryintertech.small.datas.DatabaseLoader;
import com.engloryintertech.small.im.XinRanApp;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class BaseApplication extends XinRanApp {

    private static BaseApplication mBaseApplication;
    private static final int MAX_NORMAL_CACHE_SIZE = 16 * 1024 * 1024;//设置最大缓存
    private DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        DatabaseLoader.initHelper(this);
        initImageLoader(getApplicationContext());
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        long availableMemory = Runtime.getRuntime().maxMemory();
        int memoryCacheSize = (int) (availableMemory * (15 / 100f));
        if (memoryCacheSize > MAX_NORMAL_CACHE_SIZE) {
            memoryCacheSize = MAX_NORMAL_CACHE_SIZE;
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(60 * 1024 * 1024) // 60 Mb
                .writeDebugLogs()
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取Application
     */
    public static BaseApplication getApplication() {
        return mBaseApplication;
    }

    private NotifyDispatcher<DataType, Object> mNotifydispatcher = new NotifyDispatcher<DataType, Object>();

    public void registerDataListener(DataType type, NotifyDispatcher.IDataSourceListener<DataType, Object> listener) {
        mNotifydispatcher.registerDataListener(type, listener);
    }

    public void unRegisterDataListener(NotifyDispatcher.IDataSourceListener<DataType, Object> listener) {
        mNotifydispatcher.unRegisterDataListener(listener);
    }

    public enum DataType {
        Fail, Success,
        WXAuthFail,WXAuthSuccess,                   //微信授权
        WXGetUsetInfoFail,WXGetUserInfoSuccess,     //微信获取用户信息
        WXLoginAppFail,WXLoginAppSuccess,           //微信登录后登录App
        WXPayFail,WXPaySuccess,                     //微信支付
        WXPayOrderInforFail,WXPayOrderInforSuccess, //获取微信订单信息
        AliPayOrderInforFail,AliPayOrderInforSuccess, //获取支付宝订单信息
        ThirdLoginFail, ThirdLoginSuccess,          //第三方登录
        InitializeFail, InitializeSuccess,          //初始化
        RegisterFail, RegisterSuccess,              //注册
        GetVerifiFail, GetVerifiSuccess,            //获取验证码
        CheckveriFail, CheckveriSuccess,            //校验验证码
        LoginFail, LoginSuccess,                    //登录
        ResetFail, ResetSuccess,                    //重置密码
        SaveUserFail, SaveUserSuccess,              //保存用户
        GetUserInfoFail, GetUserInfoSuccess,        //获取用户所有信息
        ApproveFail, ApproveSuccess,                //获取用户认证状态
        HomeInfoFail, HomeInfoSuccess,              //获取首页信息接口
        AddLocationFail, AddLocationSuccess,        //添加地址
        GetUserLocationFail, GetUserLocationSuccess, //获取用户所有地址
        UpLoadImageFail,UpLoadImageSuccess          //上传图片
    }

    public void notifyDataChanged(DataType type, Object data) {
        mNotifydispatcher.notifyDataChanged(type, data);
    }

    public void notifyDataChanged(DataType type) {
        notifyDataChanged(type, null);
    }
}