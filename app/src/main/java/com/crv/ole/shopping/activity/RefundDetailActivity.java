package com.crv.ole.shopping.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.databinding.ActivityRefundDetailBinding;
import com.crv.ole.personalcenter.activity.AfterOrderWLActivity;
import com.crv.ole.personalcenter.activity.LogisticsDetailsActivity;
import com.crv.ole.personalcenter.activity.OrderInfoActivity;
import com.crv.ole.personalcenter.model.RefundDetailResult;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.shopping.model.RefundDetailResponseData;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.TwoTextView;
import com.crv.sdk.utils.StringUtils;
import com.crv.sdk.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by lihongshi 2017/08/23
 * 退款详情
 */
public class RefundDetailActivity extends BaseAppCompatActivity {

    private final String cachekey = "refund_detail_" + BaseApplication.getInstance().getUserId();
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbarSubtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tvState)
    TextView tvState;//退款类型
    @BindView(R.id.tvWuLiu)
    TextView tvWuLiu;
    @BindView(R.id.tvTklx)
    TwoTextView tvTklx;
    @BindView(R.id.tvTkje)
    TwoTextView tvTkje;
    @BindView(R.id.tvTkyy)
    TwoTextView tvTkyy;
    @BindView(R.id.tvTksm)
    TwoTextView tvTksm;
    @BindView(R.id.tvDdh)
    TwoTextView tvDdh;
    @BindView(R.id.tvTkdh)
    TwoTextView tvTkdh;
    @BindView(R.id.tvThydh)
    TwoTextView tvThydh;
    @BindView(R.id.tvSqsj)
    TwoTextView tvSqsj;
    @BindView(R.id.tvShtgsj)
    TwoTextView tvShtgsj;
    @BindView(R.id.tvYdhlrsj)
    TwoTextView tvYdhlrsj;
    @BindView(R.id.tvTkdscsj)
    TwoTextView tvTkdscsj;
    @BindView(R.id.tvTksj)
    TwoTextView tvTksj;
    @BindView(R.id.help_call)
    LinearLayout helpCall;
    @BindView(R.id.dividr)
    View dividr;
    @BindView(R.id.help_kefu)
    LinearLayout helpKefu;
    @BindView(R.id.txtkdh_tv)
    TextView txtkdhTv;
    @BindView(R.id.txtkdh_layout)
    LinearLayout txtkdhLayout;
    private ActivityRefundDetailBinding mDataBinding;
    private String aliasCode;//退款单号
    private String mType;//判断是否是填写订单按妞进入的

    private String mOrderID;//订单号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        aliasCode = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        mType = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_1);
        mDataBinding = (ActivityRefundDetailBinding) getViewDataBinding();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_detail;
    }


    /**
     * 调用拨号界面(跳转到拨号界面，用户手动点击拨打）
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void initData() {
        ServiceManger.getInstance().getRefundOrderDetails(aliasCode, new BaseRequestCallback<RefundDetailResult>() {
            @Override
            public void onSuccess(RefundDetailResult data) {
                if (OleConstants.REQUES_SUCCESS.equalsIgnoreCase(data.getRETURN_CODE())) {
                    updateView(data);
                    return;
                }
                Toast.makeText(mContext, data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView(RefundDetailResult data) {
        if (data == null) {
            return;
        }
        if (data.getRETURN_DATA() != null) {
            if (!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getOrderAliasCode())) {
                mOrderID = data.getRETURN_DATA().getOrderAliasCode();
            }
            if (!StringUtils.isNullOrEmpty(mType) && mType.equals("1")) {//列表点击填入运单号进入
                tvState.setText("退货退款-待退款");
                tvTklx.setRightTitle("退货退款");
                tvTkje.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getFMoney()) ? "￥" + data.getRETURN_DATA().getFMoney() : "");//退款金额
                tvTkyy.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getReason()) ? data.getRETURN_DATA().getReason() : "");//退款原因
                tvTksm.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getCusRemark()) ? data.getRETURN_DATA().getCusRemark() : "");//退款说明
                tvDdh.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getOrderAliasCode()) ? data.getRETURN_DATA().getOrderAliasCode() : "");//订单号
                tvTkdh.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getAliasCode()) ? data.getRETURN_DATA().getAliasCode() : "");//退款单号
                tvSqsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getFormatCreateTime()) ? data.getRETURN_DATA().getFormatCreateTime() : "");//申请时间
                //                tvShtgsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getStates().getApproveState().getLastModifyTime()) ? data.getRETURN_DATA().getStates().getApproveState().getLastModifyTime() : "");//审核通过时间
                tvTksj.setRightTitle("服务端暂未返回数据");//退款时间
                tvYdhlrsj.setRightTitle("服务端暂未返回数据");//运单号录入时间
                //                tvTkdscsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime()) ? data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime() : "");//退款单生成时间
                txtkdhLayout.setVisibility(View.VISIBLE);
                tvThydh.setVisibility(View.GONE);
            } else {
                if (data.getRETURN_DATA().getStates().getApproveState().getState().equals("state_0")) {//待审核
                    tvState.setText("退货退款-审核中");
                    tvTklx.setRightTitle("退货退款");
                    tvYdhlrsj.setRightTitle("服务端暂未返回数据");//运单号录入时间
                    tvThydh.setRightTitle("未填入");//退货运单号
                    tvTkdscsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime()) ? data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime() : "");//退款单生成时间
                } else if (data.getRETURN_DATA().getStates().getApproveState().getState().equals("state_3")) {//仅退款
                    tvState.setText("仅退款-退款中");
                    tvTklx.setRightTitle("仅退货");
                    tvYdhlrsj.setVisibility(View.GONE);
                    tvTkdscsj.setVisibility(View.GONE);
                } else if (data.getRETURN_DATA().getStates().getApproveState().getState().equals("state_2")) {//取消

                } else if (data.getRETURN_DATA().getStates().getApproveState().getState().equals("state_1")) {//同意
                    if (data.getRETURN_DATA().getDeliveryNo().length() > 0) {//有运单号 可以查看物流
                        tvWuLiu.setVisibility(View.VISIBLE);
                        tvState.setText("退货退款-退款中");
                        tvThydh.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getDeliveryNo()) ? data.getRETURN_DATA().getDeliveryNo() : "");
                    } else {//无运单号
                        tvState.setText("退货退款-审核中");
                        tvThydh.setRightTitle("未填入");//退货运单号
                        ToastUtil.showToast("您的申请已被通过请尽快填入运单号");
                    }
                    tvTklx.setRightTitle("退款退货");
                    tvYdhlrsj.setRightTitle("服务端暂未返回数据");//运单号录入时间
                    tvTkdscsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime()) ? data.getRETURN_DATA().getStates().getRefundOrderState().getLastModifyTime() : "");//退款单生成时间
                }
                tvTkje.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getFMoney()) ? "￥" + data.getRETURN_DATA().getFMoney() : "");//退款金额
                tvTkyy.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getReason()) ? data.getRETURN_DATA().getReason() : "");//退款原因
                tvTksm.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getCusRemark()) ? data.getRETURN_DATA().getCusRemark() : "");//退款说明
                tvDdh.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getOrderAliasCode()) ? data.getRETURN_DATA().getOrderAliasCode() : "");//订单号
                tvTkdh.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getAliasCode()) ? data.getRETURN_DATA().getAliasCode() : "");//退款单号
                tvSqsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getFormatCreateTime()) ? data.getRETURN_DATA().getFormatCreateTime() : "");//申请时间
                tvShtgsj.setRightTitle(!StringUtils.isNullOrEmpty(data.getRETURN_DATA().getStates().getApproveState().getLastModifyTime()) ? data.getRETURN_DATA().getStates().getApproveState().getLastModifyTime() : "");//审核通过时间
                tvTksj.setRightTitle("服务端暂未返回数据");//退款时间
            }


        }


    }

    @OnClick({R.id.help_call, R.id.help_kefu, R.id.tvWuLiu, R.id.txtkdh_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.help_call:
                call("10086");
                break;
            case R.id.help_kefu:
                UnicornModel.openChat(mContext);
                break;
            case R.id.tvWuLiu://跳转到物流页面
                if (!StringUtils.isNullOrEmpty(mOrderID)) {
                    startActivity(new Intent(RefundDetailActivity.this
                            , LogisticsDetailsActivity.class)
                            .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                                    mOrderID));
                }
                break;
            case R.id.txtkdh_tv://跳转到填写运单号页面
                if (!StringUtils.isNullOrEmpty(mOrderID)) {
                    startActivity(new Intent(RefundDetailActivity.this
                            , AfterOrderWLActivity.class)
                            .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                                    mOrderID));
                }
                break;


        }

    }

}
