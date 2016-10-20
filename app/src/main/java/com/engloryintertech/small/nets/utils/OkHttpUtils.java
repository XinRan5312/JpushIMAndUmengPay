package com.engloryintertech.small.nets.utils;

import android.util.Log;

import com.engloryintertech.small.nets.builder.GetBuilder;
import com.engloryintertech.small.nets.builder.PostBuilder;
import com.engloryintertech.small.nets.callback.Callback;
import com.engloryintertech.small.nets.request.RequestCall;
import com.engloryintertech.small.tools.Tools;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpUtils {

    private final String TAG = "OkHttpUtils";
    public static final long DEFAULT_MILLISECONDS = 10000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private static String mLogTAG;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
//            mOkHttpClient = new OkHttpClient();注释掉jg
            mOkHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS).build();//我写的
            Tools.error(TAG, "connectTimeout" + mOkHttpClient.connectTimeoutMillis());
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get(String LogTAG) {
        mLogTAG = LogTAG;
        return new GetBuilder();
    }

    public static PostBuilder post(String LogTAG) {
        mLogTAG = LogTAG;
        return new PostBuilder();
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();
        Tools.debugger(mLogTAG, mLogTAG + ",requestCall : " + requestCall.getRequest().toString());
        Tools.debugger(mLogTAG, mLogTAG + ",headers : " + requestCall.getRequest().headers().toString());
        Tools.debugger(mLogTAG, mLogTAG + ",params : " + requestCall.toString());
        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (call.isCanceled()) {
                    sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                    return;
                }

                if (!finalCallback.validateReponse(response, id)) {
                    sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                    return;
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                    return;
                }
            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        Tools.debugger(TAG, "sendFailResultCallback");
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                Tools.debugger(mLogTAG, mLogTAG + ",Fail : " + e.getLocalizedMessage());
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        Tools.debugger(TAG, "sendSuccessResultCallback");
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                Tools.debugger(mLogTAG, mLogTAG + ",Success : " + object.toString());
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }
    
    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

}

