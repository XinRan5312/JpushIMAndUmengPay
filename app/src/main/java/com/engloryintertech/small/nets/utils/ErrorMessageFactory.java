package com.engloryintertech.small.nets.utils;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.tools.CustomToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 错误信息提示
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    public static String createMessage(Exception e) {
        if (e instanceof SocketTimeoutException) {
            CustomToast.showToast("网络连接失败,请检查网络");//change
            return BaseApplication.getApplication().getString(R.string.network_connection_failed);
        } else if (e instanceof ConnectException) {
            return BaseApplication.getApplication().getString(R.string.network_not_connected);
        }
        return BaseApplication.getApplication().getString(R.string.operation_failed);
    }
}
