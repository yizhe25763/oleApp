package com.crv.ole.pay.zfb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.crv.ole.pay.activity.PayStateActivity;
import com.crv.ole.pay.tools.PayResultEnum;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


public class Pay {

    private Activity mContext;
    private boolean isToResultActivity = true;
    public static final String RSA_PUBLIC = "";

    private static final int RQF_PAY = 1;

    //   private boolean isNative = false;


    public Pay(Activity context) {
        this.mContext = context;
    }

    public Pay(Activity context, boolean isToResultActivity) {
        this.mContext = context;
        this.isToResultActivity = isToResultActivity;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                Result result = new Result((String) msg.obj);
                switch (msg.what) {
                    case RQF_PAY: {
                        String resultStr = result.memo.replace("。", "");
                        boolean succ = false;
                        if (!TextUtils.isEmpty(result.resultStatus) && result.resultStatus.equals("9000") && !TextUtils.isEmpty(result.result)) {
                            String results = result.result;
                            if (results.substring(results.indexOf("success") + 9, results.indexOf("success") + 13).equals("true")) {
                                succ = true;
                            }
                        }
                        if (succ) {
                            Log.i("支付宝支付成功！！！");
                            if (isToResultActivity)
                                PayResultUtils.getInstance().forword(mContext, 0);
                            else {
                                EventBus.getDefault().post(PayResultEnum.PAY_SUCCESS);
                                ToastUtil.showToast(resultStr);
                            }
                        } else if (!TextUtils.isEmpty(resultStr)) {
                            if (result.resultStatus.equals("6001")) {
                                ToastUtil.showToast(resultStr);
                            } else {
                                ToastUtil.showToast(resultStr);
                            }
                            Log.i("支付宝支付失败！！！");
                            if (isToResultActivity) {
                                PayResultUtils.getInstance().forword(mContext, 1);
                            } else {
                                EventBus.getDefault().post(PayResultEnum.PAY_FAIL);
                            }
                        }
                        Log.d("pay result: " + result.toString());

                    }
                    break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("支付宝支付失败！！！");
                if (isToResultActivity)
                    PayResultUtils.getInstance().forword(mContext, 1);
                else {
                    EventBus.getDefault().post(PayResultEnum.PAY_FAIL);
                    ToastUtil.showToast("支付失败！！！");
                }
            }
        }

    };

    // 支付接口调用
    public void pay(final String orderInfo) {
        try {
            new Thread() {
                public void run() {

                    PayTask alipay = new PayTask(mContext);
                    String result = alipay.pay(orderInfo, true);
                    Log.i("支付宝支付结果：" + result);
                    Message msg = new Message();
                    msg.what = RQF_PAY;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("支付宝支付失败！！！");

            if (isToResultActivity)
                PayResultUtils.getInstance().forword(mContext, 1);
            else {
                EventBus.getDefault().post(PayResultEnum.PAY_FAIL);
                ToastUtil.showToast("支付失败！！！");
            }
        }
    }

    //原生支付
    public void nativePay(String platInfo, boolean isNative) {
        //  this.isNative = isNative;
        pay(parsePayString(platInfo));
    }

    // 支付接口调用
    public String parsePayString(String platInfo) {
        String orderInfo = getOrderInfo(platInfo);
        return orderInfo;
    }

    public static String getOrderInfo(String platInfo) {

        StringBuffer orderInfo = new StringBuffer();

        if (!TextUtils.isEmpty(platInfo)) {
            try {
                JSONObject json = new JSONObject(platInfo);
                if (json.has("partner")) {
                    orderInfo.append("partner=" + "\"" + json.getString("partner") + "\"");
                }
                if (json.has("out_trade_no")) {
                    orderInfo.append("&out_trade_no=" + "\"" + json.getString("out_trade_no") + "\"");
                }
                if (json.has("subject")) {
                    orderInfo.append("&subject=" + "\"" + json.getString("subject") + "\"");
                }
                if (json.has("body")) {
                    orderInfo.append("&body=" + "\"" + json.getString("body") + "\"");
                }
                if (json.has("total_fee")) {
                    orderInfo.append("&total_fee=" + "\"" + json.getString("total_fee") + "\"");
                }
                if (json.has("notify_url")) {
                    orderInfo.append("&notify_url=" + "\"" + json.getString("notify_url") + "\"");
                }
                if (json.has("service")) {
                    orderInfo.append("&service=" + "\"" + json.getString("service") + "\"");
                }
                // 字符集，默认utf-8
                if (json.has("_input_charset")) {
                    orderInfo.append("&_input_charset=\"" + json.getString("_input_charset") + "\"");
                }
                if (json.has("return_url")) {
                    orderInfo.append("&return_url=" + "\"" + json.getString("return_url") + "\"");
                }
                if (json.has("payment_type")) {
                    orderInfo.append("&payment_type=" + "\"" + json.getString("payment_type") + "\"");
                }
                if (json.has("seller_id")) {
                    orderInfo.append("&seller_id=" + "\"" + json.getString("seller_id") + "\"");
                }
                if (json.has("it_b_pay")) {
                    orderInfo.append("&it_b_pay=" + "\"" + json.getString("it_b_pay") + "\"");
                }
                if (json.has("sign")) {
                    orderInfo.append("&sign=" + "\"" + json.getString("sign") + "\"");
                }
                if (json.has("sign_type")) {
                    orderInfo.append("&sign_type=" + "\"" + json.getString("sign_type") + "\"");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return orderInfo.toString();
    }

}
