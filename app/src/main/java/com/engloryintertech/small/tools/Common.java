package com.engloryintertech.small.tools;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 公共类
 * String判空
 * 系统相关获取：语言、网络状态等
 * App版本号等等
 */
public class Common {

    /**
     * 判断字符串是否为空 不为空 false ；为空 true
     */
    public static boolean isStringNull(String str) {
        if (TextUtils.isEmpty(str) || str.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络状态
     */
    public static String getNetWorkSate() {
        if (isWifi()) {
            return Constants.NetworkState_Wifi;
        } else {
            return getCurrentNetType();
        }
    }

    public static String getCurrentNetType() {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = Constants.NetworkState_Wifi;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = Constants.NetworkState_Wifi;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = Constants.NetworkState_SecendG;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = Constants.NetworkState_ThirdG;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                // LTE是3g到4g的过渡，是3.9G的全球标准
                type = Constants.NetworkState_FouthG;
            }
        }
        return type;
    }

    /**
     * 网络状态是否是wifi
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String getUserAgentInfo() {
        String imei = getDeviceId();
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本
        String device_model = Build.MODEL; // 设备型号
        String infoString = getUserAgnet() + ";" + device_model + "/" + imei + ";"
                + Constants.Product_Name + "/" + getVersionName();
        return infoString;
    }

    /**
     * 设备唯一标号
     */
    public static String getDeviceId() {
        String DeviceId = null;
        if (ContextCompat.checkSelfPermission(BaseApplication.getApplication(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager TelephonyMgr = (TelephonyManager) BaseApplication.getApplication()
                    .getSystemService(BaseApplication.getApplication().TELEPHONY_SERVICE);
            DeviceId = TelephonyMgr.getDeviceId();// Requires READ_PHONE_STATE
        } else {
            DeviceId = SharedPreferencesUtils.getString(BaseApplication.getApplication(), Constants.RANDOMUUID, "");
            if (Common.isStringNull(DeviceId)) {
                DeviceId = genericDeviceId();
            }
        }
        return DeviceId;
    }

    public static String getUserAgnet() {
        if (!Common.isNetEnable()) {
            return getStaticAgent();
        }
        WebView webview = new WebView(BaseApplication.getApplication());
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String UserAgent = settings.getUserAgentString();
        if (isStringNull(UserAgent)) {
            UserAgent = getStaticAgent();
        }
        return UserAgent;
    }

    private static String getStaticAgent() {
        return "Mozilla/5.0 (Linux; Android 5.1.1; SM-J200G Build/LMY47X; wv) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.121 Mobile Safari/537.36;";
    }

    public static String genericDeviceId() {
        String Devicenum = SharedPreferencesUtils.getString(BaseApplication.getApplication(), Constants.RANDOMUUID, "");
        if (Common.isStringNull(Devicenum)) {
            Devicenum = UUID.randomUUID().toString();
            SharedPreferencesUtils.putString(BaseApplication.getApplication(), Constants.RANDOMUUID, Devicenum);
        }
        return Devicenum;
    }

    /**
     * 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
     */
    public static boolean isNetEnable() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            // 获取网络连接管理的对象
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 判断当前网络是否已经连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取应用versionName
     */
    public static String getVersionName() {
        try {
            PackageManager manager = BaseApplication.getApplication().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    BaseApplication.getApplication().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    /**
     * 获取系统版本号
     */
    public static String getSystemVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机设备型号
     */
    public static String getSystemDeviceModel() {
        return Build.MODEL;
    }

    public static String getID(Context context) {
        String szImei = getDeviceId();
        Tools.debugger("getDeviceId", "szImei : " + szImei);
        String m_szDevIDShort = "35"
                + // we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
                + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
                + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10; // 13 digits
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        BluetoothAdapter m_BluetoothAdapter = null;
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = m_BluetoothAdapter.getAddress();
        String m_szLongID = szImei + m_szDevIDShort
                + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF)
                m_szUniqueID += "0";
            m_szUniqueID += Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        Tools.debugger("getDeviceId", "m_szUniqueID : " + m_szUniqueID);
        return m_szUniqueID;
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getWindowsWidth(Activity activity) {
//        DisplayMetrics dm = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕的高度
     */
    public final static int getWindowsHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**电话号码 后4位变为星号*/
    public static String NumberChangeStar(String numberStr){
        if(!isStringNull(numberStr) && numberStr.length() > 7 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < numberStr.length(); i++) {
                char c = numberStr.charAt(i);
                if (i > 7) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            numberStr = sb.toString();
        }
        return numberStr;
    }
}