package com.crv.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 硬件设备工具类
 * 设备id,mac,ip,gps,手机屏幕宽度
 * Created by Administrator on 2016-06-04.
 */
public class DeviceUtil {


    /**
     * 获取设备id
     *
     * @param
     * @return String
     * @throws
     */
    public static String getDeviceId(Context context) {
        PreferencesHelper helper = new PreferencesHelper(context);
        String deviceId = helper.getString("deviceId");
        if (TextUtils.isEmpty(deviceId)) {
            TelephonyManager manager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = manager.getDeviceId();
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = manager.getSubscriberId();
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = getMacAddress(context);
                }
            }
            if (!TextUtils.isEmpty(deviceId)) {
                helper.put("deviceId", deviceId);
            }
        }

        return deviceId;
    }

    /**
     * 获取Mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    /**
     * 获取本机的ip地址
     *
     * @return
     */
    public static String getIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            Log.w("getLocalIpAddress", e);
        }
        return null;
    }


    /**
     * GPS是否打开
     *
     * @return
     */
    public static boolean isGps(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.widthPixels;
        }

        return 480;
    }
}
