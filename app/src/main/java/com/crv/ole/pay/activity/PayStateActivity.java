package com.crv.ole.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.personalcenter.activity.MyOrderActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 支付成功或失败页面
 * Created by zhangbo on 2017/8/11.
 */

public class PayStateActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.im_pay_state)
    ImageView im_pay_state;//支付状态图标

    @BindView(R.id.tx_pay_state)
    TextView tx_pay_state;//支付状态文字


    @BindView(R.id.ll_bt_state_success)
    LinearLayout ll_bt_state_success;//支付成功按钮区域


    @BindView(R.id.ll_bt_state_failed)
    LinearLayout ll_bt_state_failed;//支付失败按钮区域


    @BindView(R.id.bt_back_to_shop)
    Button bt_back_to_shop;//返回商城


    @BindView(R.id.bt_back_to_home)
    Button bt_back_to_home;//返回首页


    @BindView(R.id.bt_back_to_order)
    Button bt_back_to_order;//返回订单页面

    private int type = -1;

    private boolean isClickable = false;//返回订单是否可点击

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_state_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initDate();
        initEvent();
    }

    private void initDate() {
        type = getIntent().getIntExtra("state", -1);
        switch (type) {
            case 0://支付成功
                title_name_tv.setText(getString(R.string.pay_success_hint));
                ll_bt_state_success.setVisibility(View.VISIBLE);
                ll_bt_state_failed.setVisibility(View.GONE);
                im_pay_state.setBackgroundResource(R.mipmap.img_pay_success);
                tx_pay_state.setText(getString(R.string.pay_state_success));
                break;
            case 1://支付失败
                title_name_tv.setText(getString(R.string.pay_fail_hint));
                ll_bt_state_success.setVisibility(View.GONE);
                ll_bt_state_failed.setVisibility(View.VISIBLE);
                im_pay_state.setBackgroundResource(R.mipmap.img_pay_failed);
                tx_pay_state.setText(getString(R.string.pay_state_failed));
                initTime();
                break;

        }
    }


    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        bt_back_to_shop.setOnClickListener(this);
        bt_back_to_home.setOnClickListener(this);
        bt_back_to_order.setOnClickListener(this);
    }

    //倒计时
    private void initTime() {
        final long count = 3;
        Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .take(count + 1).map(new Function<Long, Long>() {
            @Override
            public Long apply(@NonNull Long aLong) throws Exception {
                return count - aLong;
            }
        }).doOnSubscribe(disposable -> {
            isClickable = false;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e(TAG, "aLong" + aLong);
                bt_back_to_order.setText(String.format(getString(R.string.back_to_order), aLong + ""));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
                isClickable = true;
                bt_back_to_order.setText(getString(R.string.back_to_order_end));
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
            case R.id.bt_back_to_shop://返回商城
                //TODO 返回商城
                startActivityWithAnim(new Intent(this, MainActivity.class).putExtra("index", 2));
                break;
            case R.id.bt_back_to_home://返回首页
                startActivityWithAnim(new Intent(this, MainActivity.class).putExtra("index", 0));
                break;
            case R.id.bt_back_to_order://返回订单页面
                if (isClickable) {
                    //TODO 返回订单
                    startActivityWithAnim(new Intent(this, MyOrderActivity.class).putExtra("position", 1));
                    finish();
                }
                break;

        }


    }
}
