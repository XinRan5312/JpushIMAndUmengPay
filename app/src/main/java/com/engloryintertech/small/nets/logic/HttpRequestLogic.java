package com.engloryintertech.small.nets.logic;

import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.nets.callback.Callback;
import com.engloryintertech.small.nets.utils.OkHttpUtils;
import com.engloryintertech.small.tools.Common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 辅助层
 * 网络层的请求参数设置
 */
public class HttpRequestLogic {

    public static HttpRequestLogic instance = null;
    private static String mTAG;

    public synchronized static HttpRequestLogic sharedInstance(String TAG) {
        if (null == instance) {
            instance = new HttpRequestLogic();
        }
        mTAG = TAG;
        return instance;
    }

    /**
     * 请求头
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("NETWORKSTATE", Common.getNetWorkSate());
        headers.put("User-Agent", Common.getUserAgentInfo());
        String cookie = "u="
                + UserDaoHelper.getInstance().getUserId() + ";"
                + "n=" + Common.getID(BaseApplication.getApplication()) + ";"
                + "k=" + UserDaoHelper.getInstance().getUserToken();
        headers.put("cookie", cookie);
        headers.put("Market", Constants.Market_SMall);
        return headers;
    }

    public void getWebServiceNoheader(String url, String requestTag, Callback callback) {
        OkHttpUtils.get(mTAG).url(url).tag(requestTag).build().execute(callback);
    }

    /**
     * get 请求 无参数
     */
    public void getWebService(String url, String requestTag, Callback callback) {
        OkHttpUtils.get(mTAG).url(url).tag(requestTag)
                .headers(getHeaders()).build().execute(callback);
    }

    /**
     * get 请求  需参数
     */
    public void getWebServiceWithParams(String url, Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.get(mTAG).url(url).params(params).tag(requestTag)
                .headers(getHeaders())
                .build().execute(callback);
    }

    /**
     * post 请求 无参数
     */
    public void postWebService(String url, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .build()
                .execute(callback);
    }

    /**
     * post 请求 单个数
     */
    public void postWebServiceWithParam(String url, String key, String value, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .addParams(key, value).build()
                .execute(callback);
    }

    /**
     * post 请求 多个参数
     */
    public void postWebServiceWithParams(String url, Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).build()
                .execute(callback);
    }

    /**
     * 上传文件的 无参数
     */
    public void upLoadFile(String url, String fileKey, Map<String, File> files, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .files(fileKey, files).build()
                .execute(callback);
    }

    /**
     * 上传文件
     */
    public void upLoadFileWithParams(String url, String fileKey, Map<String, File> files,
                                     Map<String, Object> params, String requestTag, Callback callback) {
        OkHttpUtils.post(mTAG).url(url).
                tag(requestTag).headers(getHeaders())
                .params(params).files(fileKey, files).build()
                .execute(callback);
    }
}