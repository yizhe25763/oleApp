package com.crv.sdk.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * activity管理工具类
 * 控制activity，方便退出应用清除所有的activity
 */
public class ActivityUtils {
    private static final LinkedList<Activity> sActivityList = new LinkedList<Activity>();

    /***
     * 在每个Activity的onCreate中调用，用来记录打开了的activity
     */
    public static void addActivity(Activity act) {
        sActivityList.add(act);
    }

    /***
     * 在每个Activity的onDestroy中调用
     */
    public static void removeActivity(Activity act) {
        sActivityList.remove(act);
    }

    /***
     * 结束所有的activity，并关闭程序的进程
     */
    public static void exit() {
        finishAll();
        System.exit(0);
    }

    /***
     * 结束所有的activity，但不会关闭程序的进程
     */
    public static void finishAll() {
        for (Activity act : sActivityList) {
            act.finish();
        }
        sActivityList.clear();
    }

    public static int getActivitycount() {

        if (sActivityList == null) {
            return 0;
        } else {
            return sActivityList.size();
        }
    }

}
