package com.crv.ole.pay.activity;

import android.os.Bundle;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 订单支付页面（收银台）
 * Created by wj_wsf on 2017/6/29 11:11
 */

public class PayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle("专题详情");
        title_iv_2.setVisibility(View.GONE);
    }
}
