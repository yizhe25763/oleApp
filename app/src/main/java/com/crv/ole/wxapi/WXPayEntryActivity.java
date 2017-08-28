package com.crv.ole.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.crv.ole.R;
import com.crv.ole.pay.activity.PayStateActivity;
import com.crv.ole.pay.tools.PayResultEnum;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.pay.wechat.Constants;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0)//支付成功
            {
                ToastUtil.showToast(getString(R.string.pay_success_hint));
                Log.i("微信支付成功！！！");
                EventBus.getDefault().post(PayResultEnum.PAY_SUCCESS);
            } else if (resp.errCode == -1) {    //支付失败
                ToastUtil.showToast(R.string.pay_fail_hint);
                Log.i("微信支付失败！！！");
                EventBus.getDefault().post(PayResultEnum.PAY_FAIL);
            } else if (resp.errCode == -2) {    //取消支付
                ToastUtil.showToast(R.string.pay_cancel_hint);
                Log.i("微信取消支付！！！");
                EventBus.getDefault().post(PayResultEnum.PAY_CANCEL);
            }
            finish();
        }
    }
}