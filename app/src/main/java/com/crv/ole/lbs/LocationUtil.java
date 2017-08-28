package com.crv.ole.lbs;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度定位类
 * Created by wj_wsf on 2017/6/30 10:24.
 */

public class LocationUtil {
    private LocationClient mLocationClient = null;
    private BDLocationListener bdLocationListener;

    public LocationUtil(Context context) {
        mLocationClient = new LocationClient(context);
        setLocationOption();
    }

    public LocationClient getLocationClient(){
        return mLocationClient;
    }

    /**
     * 设置定位回调
     * @param bdLocationListener
     */
    public void setBDLocationListener(BDLocationListener bdLocationListener) {
        this.bdLocationListener = bdLocationListener;
    }

    //设置定位相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02  设置坐标类型
        option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);//打开gps
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        mLocationClient.setLocOption(option);
    }

    /**
     * 开始定位
     */
    public void startLBS() {
        if (this.bdLocationListener != null)
            mLocationClient.registerLocationListener(bdLocationListener);
        mLocationClient.start();
    }

    /**
     * 开始定位
     * @param listener 定位回调
     */
    public void startLBS(BDLocationListener listener) {
        if (listener != null)
            mLocationClient.registerLocationListener(listener);
        mLocationClient.start();
    }

    public void stopLBS() {
        mLocationClient.stop();
    }
}
