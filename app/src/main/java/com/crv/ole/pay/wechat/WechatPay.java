package com.crv.ole.pay.wechat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.crv.ole.utils.Log;
import com.crv.ole.utils.ToolUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechatPay {

    private static final String TAG = "WechatPay";

    private IWXAPI api;

    private Context mContext;

    private String payReqJson;

    public WechatPay(Context context) {
        this.mContext = context;

    }

    public void pay(String orderInfo) {
        api = WXAPIFactory.createWXAPI(mContext, null);
        if (ToolUtils.isWXAppInstalledAndSupported(mContext, api)) {
            payReqJson = orderInfo;
            sendPayReq(payReqJson);
        }
        Log.d("orderInfo: " + orderInfo);
    }


    private void sendPayReq(String requestStr) {
        PayReq req = new PayReq();

        if (requestStr != null) {
            try {
                JSONObject json = new JSONObject(requestStr);
                if (json.has("appid")) {
                    req.appId = json.getString("appid");
                }
                if (json.has("partnerid")) {
                    req.partnerId = json.getString("partnerid");
                }
                if (json.has("prepayid")) {
                    req.prepayId = json.getString("prepayid");
                }
                if (json.has("noncestr")) {
                    req.nonceStr = json.getString("noncestr");
                }
                if (json.has("package")) {
                    req.packageValue = json.getString("package");
                }

                if (json.has("timestamp")) {
                    req.timeStamp = json.getString("timestamp");
                }
                if (json.has("sign")) {
                    req.sign = json.getString("sign");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        api.registerApp(Constants.APP_ID);
        api.sendReq(req);
    }
}
