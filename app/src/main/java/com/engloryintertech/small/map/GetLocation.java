package com.engloryintertech.small.map;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/10.
 * 获得定位信息
 */
public class GetLocation {

    private String TAG = getClass().getName();
    public static String LocationInfo;//地址信息
    private Context context;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源,如网络定位结果,详见定位类型表
                    double latitude = amapLocation.getLatitude();//获取纬度
                    double longitude = amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    String format = df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    String country = amapLocation.getCountry();//国家信息
                    String province = amapLocation.getProvince();//省信息
                    String city = amapLocation.getCity();//城市信息
                    String district = amapLocation.getDistrict();//城区信息
                    String street = amapLocation.getStreet();//街道信息
                    String streetNum = amapLocation.getStreetNum();//街道门牌号信息
                    String cityCode = amapLocation.getCityCode();//城市编码
                    String adCode = amapLocation.getAdCode();//地区编码
                    String aoiName = amapLocation.getAoiName();//获取当前定位点的AOI信息
                    Log.e(TAG, "纬度：" + latitude + "经度：" + longitude + "定位时间：" + format + "国家：" + country + "省信息：" + province
                            + "城市：" + city + "城区信息：" + district + "街道信息：" + street + "城市编码：" + cityCode + "地区编码：" + adCode +
                            "街道门牌号信息：" + streetNum + "当前定位点的AOI信息：" + aoiName);
                    LocationInfo = latitude + longitude + format + country + province + city + district + street + cityCode + adCode + streetNum + aoiName;
                    LocationInfo = "国家：" + country + "省信息：" + province + "城市：" + city + "城区信息：" + district + "街道信息：" + street;
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            } else {
                Log.e(TAG, "有错误");
            }
        }
    };

    public GetLocation(Context context) {
        this.context = context;
    }

    /**
     * 获取准确信息,调取该方法
     */
    public void getLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        configLocationParam();
    }

    /**
     * 配置定位参数
     */
    private void configLocationParam() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//        if (mLocationOption.isOnceLocation()) {
//            mLocationOption.setOnceLocation(true);
//            //isOnceLocation是布尔型参数，true表示启动单次定位，false表示使用默认的连续定位策略。
//        }
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 销毁定位
     */
    public void onDestroy() {
        //停止定位
        mLocationClient.stopLocation();
        //销毁客户端
        mLocationClient.onDestroy();
    }

}