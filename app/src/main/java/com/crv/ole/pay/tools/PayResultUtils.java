package com.crv.ole.pay.tools;

import android.app.Activity;
import android.content.Intent;

import com.crv.ole.pay.activity.PayStateActivity;

/**
 * 作用描述：支付结果跳转工具类
 * 创建者： wj_wsf
 * 创建时间： 2017/8/11 16:07.
 */

public class PayResultUtils {
    private static class SingletonHolder {
        private static final PayResultUtils INSTANCE = new PayResultUtils();
    }

    private PayResultUtils() {
    }

    public static final PayResultUtils getInstance() {
        return PayResultUtils.SingletonHolder.INSTANCE;
    }

    public void forword(Activity activity, int result) {
        activity.startActivity(
                new Intent(activity, PayStateActivity.class)
                        .putExtra("state", result));
        activity.finish();
    }

}
