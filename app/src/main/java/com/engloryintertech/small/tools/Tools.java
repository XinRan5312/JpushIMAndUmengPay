package com.engloryintertech.small.tools;

import android.util.Log;

/**
 * Log工具类
 * 发布版本时 要将 DEBUGGER 设置为 false
 */
public class Tools {

    public static boolean DEBUGGER = true;

    public static void debugger(String tag, String msg) {
        if (DEBUGGER)
            Log.d(tag, msg);
    }

    public static void info(String tag, String msg) {
        if (DEBUGGER)
            Log.i(tag, msg);
    }

    public static void verbo(String tag, String msg) {
        if (DEBUGGER)
            Log.v(tag, msg);
    }

    public static void error(String tag, String msg) {
        if (DEBUGGER)
            Log.e(tag, msg);
    }

}