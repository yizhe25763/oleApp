package com.crv.ole.pay.unionpay;

import android.app.Activity;

import com.crv.ole.utils.Log;
import com.unionpay.UPPayAssistEx;

/**
 * 作用描述：发起银联支付
 * 创建者： wj_wsf
 * 创建时间： 2017/8/2 16:29.
 */

public class UnionPay {
    /**
     * @param activity
     * @param tn       后台生成的支付订单ID
     * @param mode     "00" - 启动银联正式环境 "01" - 连接银联测试环境
     */
    public void unionPay(Activity activity, String tn, String mode) {
        Log.v("在" + mode + "模式下调用银联支付参数：" + tn);
        UPPayAssistEx.startPay(activity, null, null, tn, mode);
    }
}
