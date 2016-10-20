package com.engloryintertech.small.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.engloryintertech.small.application.BaseApplication;

public class SharedPreferencesUtils {

    private static final String CACHE_FILE_NAME = "SMall";
    private static SharedPreferences mSharedPreferences;
    public static final String ScreenWidth = "ScreenWidth";
    public static final String ScreenHeight = "ScreenHeight";
    public static final String LoginType = "LoginType";

    public static void putBoolean(Context context, String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key, defValue);
    }

    public static Long getLongValue(Context context, String key, long defalut) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getLong(key, defalut);
    }

    public static void setLongValue(Context context, String key, long value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public static int getScreenWidth() {
        return getInt(BaseApplication.getApplication(), ScreenWidth, 0);
    }

    public static int getScreenHeight() {
        return getInt(BaseApplication.getApplication(), ScreenHeight, 0);
    }

    public static void saveListIdMin(int mainId, String key) {
        putInt(BaseApplication.getApplication(), key, mainId);
    }

    public static int getListIdMin(String key) {
        int minId = getInt(BaseApplication.getApplication(), key, 0);
        return minId;
    }

}
