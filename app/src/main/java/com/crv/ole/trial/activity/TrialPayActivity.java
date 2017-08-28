package com.crv.ole.trial.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.pay.tools.PayPopupWindow;
import com.crv.ole.pay.tools.PayResultEnum;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 试用支付界面
 * Created by zhangbo on 2017/8/18.
 */

public class TrialPayActivity extends BaseActivity {


    @BindView(R.id.rg_pay_type)
    RadioGroup rg_pay_type;


    @BindView(R.id.rb_jifen)
    RadioButton rb_jifen;//积分


    @BindView(R.id.rb_online)
    RadioButton rb_online;//线上支付


    @BindView(R.id.rl_jifen_pay)
    RelativeLayout rl_jifen_pay;//积分支付区域

    @BindView(R.id.tx_jifen)
    TextView tx_jifen;//积分数量


    @BindView(R.id.jifen_pay_confirm)
    Button jifen_pay_confirm;//积分支付确认


    private double payAmount = 0;//积分

    private PayPopupWindow payPopupWindow;

    private String orderId;//订单ID

    private double money = 0;//现金

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_pay_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initUI();
        initEvent();
    }

    private void initUI() {
        title_name_tv.setText(getString(R.string.pay));
        payAmount = getIntent().getDoubleExtra("payAmount", 0);
        orderId = getIntent().getStringExtra("orderId");
        money = getIntent().getDoubleExtra("money", 0);
        tx_jifen.setText(String.format(getString(R.string.pay_jifen), payAmount + ""));
    }

    private void initEvent() {
        jifen_pay_confirm.setOnClickListener(this);
        title_back_layout.setOnClickListener(this);
        rg_pay_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_jifen://积分
                        rl_jifen_pay.setVisibility(View.VISIBLE);
                        if (payPopupWindow != null && payPopupWindow.isShowing()) {
                            payPopupWindow.dismiss();
                        }
                        break;
                    case R.id.rb_online://线上支付
                        rl_jifen_pay.setVisibility(View.GONE);
                        showPayPop();
                        break;
                }
            }
        });
        if (BaseApplication.getInstance().getUserCenter().getTotalIntegralValue() < payAmount) {//积分不足
            rb_jifen.setClickable(false);
            rb_jifen.setEnabled(false);
            rb_jifen.setTextColor(getResources().getColor(R.color.d_dbdbdb));
            rl_jifen_pay.setVisibility(View.GONE);
            rb_online.setChecked(true);
            rb_online.post(new Runnable() {
                @Override
                public void run() {
                    showPayPop();
                }
            });
        } else {
            rb_jifen.setChecked(true);
        }
    }

    private void showPayPop() {
        if (payPopupWindow == null) {
            payPopupWindow = new PayPopupWindow(TrialPayActivity.this, orderId, (float) money, false);
        }
        payPopupWindow.setOnAlterDialogClickListener(new PayPopupWindow.OnAlterDialogClickListener() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onConfim() {
                if (BaseApplication.getInstance().getUserCenter().getTotalIntegralValue() < payAmount) {
                    //积分不足时，直接退出界面
                    finish();
                }
            }
        });
        payPopupWindow.showPopupWindow();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
            case R.id.jifen_pay_confirm:
                //积分抵扣
                integralPay();
                break;
        }
    }


    private void integralPay() {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("payAmount", payAmount + "");
        ServiceManger.getInstance().integralPay(params, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onSuccess(BaseResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    ToastUtil.showToast(getString(R.string.pay_state_success));
                    //TODO 发送通知 刷新列表
                    EventBus.getDefault().post(OleConstants.REFRESH_TRIAL_LIST);
                    finish();
                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100) //在ui线程执行 优先级100
    public void onDataSynEvent(PayResultEnum event) {
        Log.d("event---->" + event);
        if (event == PayResultEnum.PAY_FAIL) {
            ToastUtil.showToast(getString(R.string.pay_state_failed));
        } else if (event == PayResultEnum.PAY_CANCEL) {
            ToastUtil.showToast(getString(R.string.pay_state_cancle));
        } else if (event == PayResultEnum.PAY_SUCCESS) {
            ToastUtil.showToast(getString(R.string.pay_state_success));
            //TODO 发送通知 刷新列表
            EventBus.getDefault().post(OleConstants.REFRESH_TRIAL_LIST);
            finish();
        }
    }
}
