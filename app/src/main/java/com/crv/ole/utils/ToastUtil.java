package com.crv.ole.utils;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.sdk.utils.TextUtil;


/**
 * 提示
 * Created by Administrator on 2016-06-04.
 */
public class ToastUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();

    public static void showToast(int resid) {
        showToast(resid, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 新增两个showToastAt, 设置显示位置
     *
     * @param msg
     * @param location
     */
    public static void showToastAt(String msg, int location) {
        showToastAt(msg, Toast.LENGTH_SHORT, location);
    }

    public static void showToastAt(int msg, int location) {
        showToastAt(msg, Toast.LENGTH_SHORT, location);
    }

    public static void showToast(final String msg, final int showtime) {
        if (TextUtil.isEmpty(msg)) {
            return;
        }
        handler.post(() -> {
            synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                if (toast != null) {
                    //toast.cancel();
                    toast.setText(msg);
                    toast.setDuration(showtime);
                } else {
                    toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), msg, showtime);
                }
                toast.show();
            }
        });
    }

    public static void showToast(final int resid, final int showtime) {
        if (resid == 0) {
            return;
        }
        handler.post(() -> {
            synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                if (toast != null) {
                    //toast.cancel();
                    toast.setText(resid);
                    toast.setDuration(showtime);
                } else {
                    toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), resid, showtime);
                }
                toast.show();
            }
        });
    }

    public static void showToastAt(final String msg, final int showtime, int location) {
        if (msg == null || msg.equals("")) {
            return;
        }
        handler.post(() -> {
            synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                if (toast != null) {
                    //toast.cancel();
                    toast.setText(msg);
                    toast.setDuration(showtime);
                } else {
                    toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), msg, showtime);
                }
                toast.setGravity(location, 0, 0);
                toast.show();
            }
        });
    }

    public static void showToastAt(final int resid, final int showtime, int location) {
        if (resid == 0) {
            return;
        }
        handler.post(() -> {
            synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                if (toast != null) {
                    //toast.cancel();
                    toast.setText(resid);
                    toast.setDuration(showtime);
                } else {
                    toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), resid, showtime);
                }
                toast.setGravity(location, 0, 0);
                toast.show();
            }
        });
    }
}