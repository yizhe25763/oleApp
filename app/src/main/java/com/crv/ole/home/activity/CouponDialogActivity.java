package com.crv.ole.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.home.model.BarCodeResponseData;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.crv.ole.utils.ToolUtils;

/**
 * 电子价签优惠卷对话框
 */
public class CouponDialogActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView tvTip;
    private ImageView imageView01;
    private TextView tvPrice, tvRule, tvType, tvDate;
    private Button btnReceive, btnLogin;
    private ImageView ivCancel;
    private String barCode;
    private BarCodeResponseData barCodeResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCode = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        String cachekey = "bar_code_detail_" + BaseApplication.getInstance().getUserId();
        barCodeResponseData = SerializableManager.readSerializable(mContext, cachekey);
        initView();
        updateView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReceive: {
                finish();
                break;
            }
            case R.id.btnLogin: {
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            }
            case R.id.ivCancel: {
                finish();
                break;
            }
        }
    }

    private void initView() {
        tvTip = (TextView) this.findViewById(R.id.tvTip);
        imageView01 = (ImageView) this.findViewById(R.id.imageView01);
        tvPrice = (TextView) this.findViewById(R.id.tvPrice);
        tvRule = (TextView) this.findViewById(R.id.tvRule);
        tvType = (TextView) this.findViewById(R.id.tvType);
        tvDate = (TextView) this.findViewById(R.id.tvDate);
        btnReceive = (Button) this.findViewById(R.id.btnReceive);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        ivCancel = (ImageView) this.findViewById(R.id.ivCancel);
        btnReceive.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
    }

    private void updateView() {
        if (ToolUtils.isLoginStatus(mContext)) {
            btnReceive.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        } else {
            btnReceive.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }
        BarCodeResponseData.ActivityObj activityObj = barCodeResponseData.getRETURN_DATA().getActivityObj();
        int cardNum = activityObj.getCardNum();
        tvTip.setText(String.format("你有%d张优惠卷领取", cardNum));
        imageView01.setImageResource(cardNum > 1 ? R.mipmap.bg_dzbj : R.mipmap.bg_yzbg);

        BarCodeResponseData.CardBatchShowObj cardBatchShowObj = activityObj.getCardBatchShowObj();
        if (cardBatchShowObj != null) {
            tvPrice.setText(cardBatchShowObj.getAmount());
            tvRule.setText(cardBatchShowObj.getOuterName());
            tvDate.setText(cardBatchShowObj.getEffectedEnd());
        }
    }


}
