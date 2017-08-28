package com.crv.sdk.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * 应用工具类
 * 应用，服务，app是否安装
 * 语言
 * 状态栏高度
 * Created by Administrator on 2016-06-04.
 */
public class AppUtil {
    /**
     * 判断指定包名的进程是否运行
     * @param context
     * @param packageName 指定包名
     * @return 是否运行
     */
    public static boolean isAppRunning(Context context, String packageName){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo rapi : infos){
            if(rapi.processName.equals(packageName))
                return true;
        }
        return false;
    }

    /**
     *判断当前应用程序处于前台还是后台
     * @param context context
     * @return    boolean
     */
    public static boolean isBackground(Context context)
    {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty())
        {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName()))
            {
                return true;
            }
        }
        return false;

    }



    /**
     * 判断service是否启动
     *
     * @return
     */
    public static boolean isServiceStart(Context context) {
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myServiceList = myManager
                .getRunningServices(50);
        for (ActivityManager.RunningServiceInfo info : myServiceList) {
            if (context.getPackageName().equals(info.service.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断service是否在运行
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context,String serviceName)  {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceName)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断activity是否启动
     *
     * @return
     */
    public static boolean isAcvitityStart(Context context) {
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> myActivityList = myManager
                .getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo info : myActivityList) {
            if (context.getPackageName().equals(
                    info.baseActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context,String packageName){
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    /**
     * 获取类名
     * @param name
     * @return
     */
    public static String getClassName(String name)
    {
        if (name.lastIndexOf(".") != -1)
        {
            return name.substring(name.lastIndexOf(".") + 1);
        }
        else
        {
            return name;
        }
    }

    /**
     * 判断当前context是否存在
     *
     * @return
     */
    public static boolean isActivity(Activity activity) {
        return activity == null;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int status_bar_height(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbar;
    }

    /**
     * 获取当前使用的语言,如zh-CN，zh-TW，zh-HK，en-US等
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    }

    /**
     * 判断当前设备系统是否使用中文
     * @return
     */
    public static boolean isChinese() {
        return Locale.getDefault().getLanguage().equals("zh");
    }

    /**
     * 拨打系统电话
     * @param tel
     */
    public static void callTel(Context context, String tel) {
        try {
            if (!TextUtils.isEmpty(tel)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.replaceAll("-|\\(|\\)", "")));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception ex) {
        }
    }
}
