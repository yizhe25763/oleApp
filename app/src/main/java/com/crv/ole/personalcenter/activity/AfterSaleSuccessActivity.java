package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.fragment.MyOrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanhaoyi on 2017/8/23.
 */

public class AfterSaleSuccessActivity extends BaseActivity {

    @BindView(R.id.versionTv)
    TextView versionTv;

    Handler handler = new Handler();
    private int recLen = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_success);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle("发起售后");
        handler.postDelayed(runnable, 1000);
    }


    @OnClick(R.id.versionTv)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(this, MyOrderActivity.class);
        startActivity(intent);
        finish();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            versionTv.setText("等待" + recLen + "秒自动返回订单列表");
            if (recLen == 0) {
                Intent intent = new Intent();
                intent.setClass(AfterSaleSuccessActivity.this, MyOrderActivity.class);
                startActivity(intent);
                finish();
            }
            handler.postDelayed(this, 1000);
        }
    };
}
