package com.crv.ole.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.model.GiftCardItemData;
import com.crv.ole.pay.model.OrderConfirmAllCardsData;
import com.crv.ole.pay.model.OrderConfirmGoodsData;
import com.crv.ole.pay.model.OrderConfirmInfo;
import com.crv.ole.pay.model.OrderConfirmInfoData;
import com.crv.ole.pay.requestmodel.RequestOrderConfirmInfoModel;
import com.crv.ole.pay.requestmodel.RequestOrderConfirmModel;
import com.crv.ole.pay.tools.PayPopupWindow;
import com.crv.ole.pay.tools.PayResultEnum;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.pay.unionpay.RSAUtil;
import com.crv.ole.personalcenter.activity.GoodsAddressActivity;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.personalcenter.tools.CollectionEvent;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.view.SwitchView;
import com.crv.sdk.utils.DateUtil;
import com.crv.sdk.utils.PreferencesHelper;
import com.crv.sdk.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单确认页面
 * Created by wj_wsf on 2017/6/29 11:11
 */

public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.confirm_order_scroll)
    ScrollView confirm_order_scroll;
    @BindView(R.id.confirm_order_bottom_layout)
    LinearLayout confirmOrderBottomLayout;
    @BindView(R.id.confirm_order_hj_layout)
    RelativeLayout confirmOrderHjLayout;
    @BindView(R.id.confirm_order_ic3)
    ImageView confirmOrderIc3;
    @BindView(R.id.confirm_order_amount)
    TextView confirmOrderAmount;
    @BindView(R.id.confirm_order_yh)
    TextView confirmOrderYh;
    @BindView(R.id.confirm_order_yf)
    TextView confirmOrderYf;
    @BindView(R.id.confirm_order_submit_btn)
    Button confirmOrderSubmitBtn;
    @BindView(R.id.confirm_order_address_layout)
    RelativeLayout confirmOrderAddressLayout;
    @BindView(R.id.confirm_order_ic1)
    ImageView confirmOrderIc1;
    @BindView(R.id.confirm_order_ic2)
    ImageView confirmOrderIc2;
    @BindView(R.id.confirm_order_address_name_tv)
    TextView addressNameTv;
    @BindView(R.id.confirm_order_address_mobile_tv)
    TextView addressMobileTv;
    @BindView(R.id.confirm_order_address_address_tv)
    TextView addressContentTv;
    @BindView(R.id.confirm_order_goods_layout)
    LinearLayout confirmOrderGoodsLayout;
    @BindView(R.id.confirm_order_discount_coupon_layout)
    RelativeLayout confirmOrderDiscountCouponLayout;
    @BindView(R.id.confirm_order_discount_coupon_amount_txt)
    TextView confirmOrderDiscountCouponAmountTxt;
    @BindView(R.id.confirm_order_jfdx_switch)
    SwitchView confirmOrderJfdxSwitch;
    @BindView(R.id.confirm_order_syjf_txt)
    TextView confirmOrderSyjfTxt;
    @BindView(R.id.confirm_order_syjf_amount_txt)
    TextView confirmOrderSyjfAmountTxt;
    @BindView(R.id.confirm_order_sylpk_layout)
    RelativeLayout confirmOrderSylpkLayout;
    @BindView(R.id.confirm_order_sylpk_txt)
    TextView confirmOrderSylpkTxt;
    @BindView(R.id.confirm_order_sylpk_amount_txt)
    TextView confirmOrderSylpkAmountTxt;
    @BindView(R.id.confirm_order_fpxx_layout)
    RelativeLayout confirmOrderFpxxLayout;
    @BindView(R.id.confirm_order_fpxx_txt)
    TextView confirmOrderFpxxTxt;
    @BindView(R.id.confirm_order_fpxx_switch)
    SwitchView confirmOrderFpxxSwitch;
    @BindView(R.id.confirm_order_dkxx_layout)
    LinearLayout confirmOrderDkxxLayout;
    @BindView(R.id.confirm_order_dkxx_yhq_layout)
    RelativeLayout confirmOrderDkxxYhqLayout;
    @BindView(R.id.confirm_order_dkxx_yhq_amount)
    TextView confirmOrderDkxxYhqAmount;
    @BindView(R.id.confirm_order_dkxx_jfdx_layout)
    RelativeLayout confirmOrderDkxxJfdxLayout;
    @BindView(R.id.confirm_order_dkxx_jfdx_amount)
    TextView confirmOrderDkxxJfdxAmount;
    @BindView(R.id.confirm_order_dkxx_lpkzf_layout)
    RelativeLayout confirmOrderDkxxLpkzfLayout;
    @BindView(R.id.confirm_order_dkxx_lpkzf_amount)
    TextView confirmOrderDkxxLpkzfAmount;
    @BindView(R.id.confirm_order_dkxx_hdyj_layout)
    RelativeLayout confirmOrderDkxxHdyjLayout;
    @BindView(R.id.confirm_order_dkxx_hdyj_amount)
    TextView confirmOrderDkxxHdyjAmount;
    @BindView(R.id.confirm_order_dkxx_yf_layout)
    RelativeLayout confirmOrderDkxxYfLayout;
    @BindView(R.id.confirm_order_dkxx_yf_amount)
    TextView confirmOrderDkxxYfAmount;
    @BindView(R.id.confirm_order_address_hint_tv)
    TextView confirmOrderAddressHintTV;
    @BindView(R.id.confirm_order_address_msg_layout)
    RelativeLayout confirm_order_address_msg_layout;
    @BindView(R.id.confirm_order_syjf_layout)
    RelativeLayout confirm_order_syjf_layout;
    @BindView(R.id.confirm_order_fpxx_et)
    EditText confirm_order_fpxx_et;
    @BindView(R.id.confirm_order_common_layout)
    LinearLayout confirm_order_common_layout;
    @BindView(R.id.confirm_order_advance_dj_layout)
    LinearLayout confirm_order_advance_dj_layout;
    @BindView(R.id.confirm_order_dj_amount)
    TextView confirm_order_dj_amount;
    @BindView(R.id.confirm_order_wk_amount)
    TextView confirm_order_wk_amount;
    @BindView(R.id.confirm_order_wk_time)
    TextView confirm_order_wk_time;
    @BindView(R.id.confirm_order_wk_phone)
    EditText confirm_order_wk_phone;
    @BindView(R.id.confirm_order_desc_et)
    EditText confirm_order_desc_et;

    @BindView(R.id.confirm_order_advance_wk_layout)
    LinearLayout confirm_order_advance_wk_layout;
    @BindView(R.id.confirm_order_advance_wk_dj_amount)
    TextView confirm_order_advance_wk_dj_amount;
    @BindView(R.id.confirm_order_advance_wk_wk_amount)
    TextView confirm_order_advance_wk_wk_amount;
    @BindView(R.id.confirm_order_advance_wk_wk_time)
    TextView confirm_order_advance_wk_wk_time;

    @BindView(R.id.confirm_order_advance_qefk_layout)
    LinearLayout confirm_order_advance_qefk_layout;
    @BindView(R.id.confirm_order_qefk_amount)
    TextView confirm_order_qefk_amount;

    // 选择地址请求CODE
    private int CHOOSE_ADDRESS_REQUEST_CODE = 123;
    // 选择优惠券请求CODE
    private int CHOOSE_DISCOUNT_COUPON_REQUEST_CODE = 124;
    // 选择礼品卡请求CODE
    private int CHOOSE_GIFT_CARD_REQUEST_CODE = 125;
    // 调用银联支付的requestCode
    private int GO_UNION_PAY_REQUEST_CODE = 10;

    private AddressListData.Address addressData;
    private Map<String, OrderConfirmAllCardsData> discountCouponDatas;
    //优惠券优惠金额
    private float discountCouponAmount = 0f;
    private ArrayList<GiftCardItemData> giftCardDatas;
    //礼品卡优惠金额
    private float giftCardAmount = 0f;

    //优惠金额
    private float discountAmount = 0f;
    //运费金额
    private float deliveryAmount = 0f;

    private OrderConfirmInfo orderConfirmInfo;
    //积分优惠金额
    private float integralPay = 0f;
    //需要的支付金额
    private float payAmount = 0f;

    //总尾款
    private float totalBalancePrice = 0f;
    //总定金
    private float totalDepositPrice = 0f;

    //common_buy:普通结算，advance_buy_dj:预售结算定金，advance_buy_wk:预售结算尾款，advance_buy_qe:预售结算全额付款
    private String from_tag = "common_buy";
    //common:普通订单，preSale:预售订单,不传默认普通订单
    private String orderType = "common";

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.confirm_order_title);

        from_tag = getIntent().getStringExtra("from_tag");

        confirmOrderJfdxSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
//                confirm_order_syjf_layout.setVisibility(View.VISIBLE);
                if (orderConfirmInfo != null) {
                    orderId = "";
                    integralPay = Float.valueOf(orderConfirmInfo.getIntegralBalance()) *
                            orderConfirmInfo.getIntegralMoneyRatio();

                    if (!showHJView()) {
                        view.toggleSwitch(false);
                        integralPay = 0;
                    }
                    confirmOrderSyjfAmountTxt.setText("-¥" + String.valueOf(integralPay));
                    confirmOrderDkxxJfdxAmount.setText("-¥" + String.valueOf(integralPay));
                }
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
//                confirm_order_syjf_layout.setVisibility(View.GONE);
                if (orderConfirmInfo != null) {
                    orderId = "";
                    integralPay = 0;
                    confirmOrderSyjfAmountTxt.setText("-¥" + String.valueOf(integralPay));
                    confirmOrderDkxxJfdxAmount.setText("-¥" + String.valueOf(integralPay));
                    showHJView();
                }
            }
        });

        confirmOrderFpxxSwitch.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
                orderId = "";
                confirm_order_fpxx_et.setVisibility(View.VISIBLE);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
                orderId = "";
                confirm_order_fpxx_et.setVisibility(View.GONE);
            }
        });

        confirm_order_desc_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                orderId = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (TextUtils.equals(from_tag, "advance_buy_dj")) {
            confirm_order_advance_dj_layout.setVisibility(View.VISIBLE);
            confirm_order_advance_wk_layout.setVisibility(View.GONE);
            confirm_order_advance_qefk_layout.setVisibility(View.GONE);
            confirm_order_common_layout.setVisibility(View.GONE);
            orderType = "preSale";
        } else if (TextUtils.equals(from_tag, "advance_buy_wk")) {
            confirm_order_advance_dj_layout.setVisibility(View.GONE);
            confirm_order_advance_wk_layout.setVisibility(View.VISIBLE);
            confirm_order_advance_qefk_layout.setVisibility(View.GONE);
            confirm_order_common_layout.setVisibility(View.VISIBLE);
            confirmOrderDkxxYhqLayout.setVisibility(View.GONE);
            confirmOrderDiscountCouponLayout.setVisibility(View.GONE);
            confirmOrderDkxxJfdxLayout.setVisibility(View.GONE);
            confirm_order_syjf_layout.setVisibility(View.GONE);
            orderType = "preSale";
        } else if (TextUtils.equals(from_tag, "advance_buy_qe")) {
            confirm_order_advance_dj_layout.setVisibility(View.GONE);
            confirm_order_advance_wk_layout.setVisibility(View.GONE);
            confirm_order_advance_qefk_layout.setVisibility(View.VISIBLE);
            confirm_order_common_layout.setVisibility(View.VISIBLE);
            orderType = "preSale";
        } else {
            confirm_order_advance_dj_layout.setVisibility(View.GONE);
            confirm_order_advance_wk_layout.setVisibility(View.GONE);
            confirm_order_advance_qefk_layout.setVisibility(View.GONE);
            confirm_order_common_layout.setVisibility(View.VISIBLE);
            orderType = "common";
        }

        getData();
    }

    /**
     * 获取确认订单时候需要的信息
     */
    private void getData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("orderType", orderType);
//
//        ServiceManger.getInstance().getOrderFrom(map, new BaseRequestCallback() {
//            @Override
//            public void onSuccess(Object data) {
//                OrderConfirmInfoData response = (OrderConfirmInfoData) data;
//                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
////                            showData(response.getRETURN_DATA().getCards());
//                    orderConfirmInfo = response.getRETURN_DATA();
//                    showViewData();
//                } else {
//                    ToastUtil.showToast(response.getRETURN_DESC());
//                }
//            }
//        });

        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_ORDER_INFO_ID);
        requestData.setREQUEST_DATA(new RequestOrderConfirmInfoModel(orderType));
        ServerApi.request(false, requestData, OrderConfirmInfoData.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderConfirmInfoData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(OrderConfirmInfoData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
//                            showData(response.getRETURN_DATA().getCards());
                            orderConfirmInfo = response.getRETURN_DATA();
                            showViewData();
                        } else {
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("获取订单信息失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取订单信息完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void showViewData() {
        if (orderConfirmInfo != null && orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0) {

            confirm_order_scroll.setVisibility(View.VISIBLE);
            confirmOrderBottomLayout.setVisibility(View.VISIBLE);

            //显示默认地址信息
            if (orderConfirmInfo.getDeliveryAddress() != null) {
                confirm_order_address_msg_layout.setVisibility(View.VISIBLE);
                confirmOrderAddressHintTV.setVisibility(View.GONE);
                addressNameTv.setText(orderConfirmInfo.getDeliveryAddress().getUserName());
                addressMobileTv.setText(orderConfirmInfo.getDeliveryAddress().getMobile());
                addressContentTv.setText(orderConfirmInfo.getDeliveryAddress().getAddress());
            }

            //显示商品列表
            if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0
                    && orderConfirmInfo.getOcs().get(0).getBuyItems() != null
                    && orderConfirmInfo.getOcs().get(0).getBuyItems().size() > 0) {
                confirmOrderGoodsLayout.removeAllViews();
                for (OrderConfirmGoodsData goodsData : orderConfirmInfo.getOcs().get(0).getBuyItems()) {
                    View goodsView = LayoutInflater.from(this).inflate(R.layout.order_confirm_goods_item_layout, null);
                    LoadImageUtil.getInstance().loadImage((ImageView) goodsView.findViewById(R.id.order_confirm_goods_img), goodsData.getIcon());
                    ((TextView) goodsView.findViewById(R.id.order_confirm_goods_title)).setText(goodsData.getProductName());
                    ((TextView) goodsView.findViewById(R.id.order_confirm_goods_count)).setText(goodsData.getAmount() + "");
                    ((TextView) goodsView.findViewById(R.id.order_confirm_goods_price)).setText(goodsData.getUnitDealPrice());
                    confirmOrderGoodsLayout.addView(goodsView);
                }
            }

            //只有预售全额付款，购物车结算才显示积分和优惠券使用
            if (TextUtils.equals(from_tag, "advance_buy_qe") || TextUtils.equals(from_tag, "common_buy")) {

                integralPay = Float.valueOf(orderConfirmInfo.getIntegralBalance()) *
                        orderConfirmInfo.getIntegralMoneyRatio();

                confirmOrderSyjfAmountTxt.setText("-¥" + String.valueOf(integralPay));
                confirmOrderDkxxJfdxAmount.setText("-¥" + String.valueOf(integralPay));

                //显示积分信息
                confirmOrderSyjfTxt.setText("剩余积分：" + orderConfirmInfo.getIntegralBalance());
//                confirmOrderSyjfAmountTxt.setText("-" + orderConfirmInfo.getIntegralBalance());
                if (confirmOrderJfdxSwitch.isOpened()) {
                    integralPay = Float.valueOf(orderConfirmInfo.getIntegralBalance()) *
                            orderConfirmInfo.getIntegralMoneyRatio();
                    confirmOrderSyjfAmountTxt.setText("-¥" + String.valueOf(integralPay));
                    confirmOrderDkxxJfdxAmount.setText("-¥" + String.valueOf(integralPay));
                } else {
                    integralPay = 0;
                    confirmOrderSyjfAmountTxt.setText("-¥" + String.valueOf(integralPay));
                    confirmOrderDkxxJfdxAmount.setText("-¥0");
                }

                //判断是否有优惠券可用
                if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0
                        && orderConfirmInfo.getOcs().get(0).getAllCardBatchs() != null
                        && orderConfirmInfo.getOcs().get(0).getAllCardBatchs().size() > 0) {
                    confirmOrderDkxxYhqAmount.setText("-¥0");
                } else {
                    confirmOrderDiscountCouponAmountTxt.setText("无可用优惠券");
                    confirmOrderDkxxYhqAmount.setText("-¥0");

                }
            }

            if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0) {
                //显示运费信息
                deliveryAmount = orderConfirmInfo.getOcs().get(0).getTotalDeliveryFee();
                if (deliveryAmount == 0) {
                    confirmOrderYf.setText("已免运费");
                    confirmOrderDkxxYfAmount.setText("¥0");
                } else {
                    confirmOrderYf.setText("含运费" + deliveryAmount + "元");
                    confirmOrderDkxxYfAmount.setText("¥" + deliveryAmount);
                }

                if (TextUtils.equals(from_tag, "advance_buy_dj")) {
                    totalDepositPrice = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalDepositPrice());
                    totalBalancePrice = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalBalancePrice());
                    confirm_order_dj_amount.setText("¥" + totalDepositPrice);
                    confirm_order_wk_amount.setText("¥" + totalBalancePrice);

                    confirm_order_wk_time.setText("尾款支付：" +
                            DateUtil.format(
                                    DateUtil.parse(orderConfirmInfo.getOcs().get(0).getBalanceBeginTime(),
                                            "yyyy-MM-dd HH:mm:ss"),
                                    "MM月dd日 HH:mm")
                            + "-" +
                            DateUtil.format(
                                    DateUtil.parse(orderConfirmInfo.getOcs().get(0).getBalanceEndTime(),
                                            "yyyy-MM-dd HH:mm:ss"),
                                    "MM月dd日 HH:mm"));

                } else if (TextUtils.equals(from_tag, "advance_buy_wk")) {
                    totalDepositPrice = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalDepositPrice());
                    totalBalancePrice = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalBalancePrice());
                    confirm_order_advance_wk_dj_amount.setText("¥" + totalDepositPrice);
                    confirm_order_advance_wk_wk_amount.setText("¥" + totalBalancePrice);
//                    confirm_order_advance_wk_wk_time.setText("尾款支付：" +
//                            orderConfirmInfo.getOcs().get(0).getBalanceBeginTime() + "-" +
//                            orderConfirmInfo.getOcs().get(0).getBalanceEndTime());
                } else if (TextUtils.equals(from_tag, "advance_buy_qe")) {
                    confirm_order_qefk_amount.setText("¥" + orderConfirmInfo.getOcs().get(0).getTotalPayPrice());
                }

                payAmount = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalPayPrice());
                confirmOrderAmount.setText("¥" + payAmount);
                discountAmount = Float.valueOf(orderConfirmInfo.getOcs().get(0).getTotalDiscountPrice());
                confirmOrderDkxxHdyjAmount.setText("-¥" + discountAmount);
            } else {
                confirmOrderYf.setText("已免运费");
                confirmOrderDkxxYfAmount.setText("¥0");
            }

            showHJView();
        } else {
            ToastUtil.showToast("没有获取到商品信息");
        }

        confirm_order_scroll.scrollTo(0, 0);
    }

    /**
     * 提交订单
     */
    private void submitOrder() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CONFIRM_ORDER_ID);
        RequestOrderConfirmModel requestOrderConfirmModel = new RequestOrderConfirmModel();

        if (TextUtils.equals(from_tag, "advance_buy_dj") ||
                TextUtils.equals(from_tag, "advance_buy_wk") ||
                TextUtils.equals(from_tag, "advance_buy_qe")) {
            requestOrderConfirmModel.setOrderType("preSale");
            requestOrderConfirmModel.setPreSaleMobile(confirm_order_wk_phone.getText().toString());
        } else {
            requestOrderConfirmModel.setOrderType("common");
        }

        //如果是定金支付，没有预付卡信息
        if (!TextUtils.equals(from_tag, "advance_buy_dj")) {
            //添加预付卡参数
            if (giftCardDatas != null && giftCardDatas.size() > 0) {
                List<RequestOrderConfirmModel.PrepayCard> prepayCards = new ArrayList<>();
                for (GiftCardItemData itemData : giftCardDatas) {
                    RequestOrderConfirmModel.PrepayCard prepayCard = new RequestOrderConfirmModel.PrepayCard();
                    prepayCard.setCardNo(itemData.getCardNo());
                    prepayCard.setUsedAmount(itemData.getSelectedAmount() + "");
                    prepayCards.add(prepayCard);
                }
                requestOrderConfirmModel.setPrepayCards(prepayCards);
            }
        }

        //只有预售全额付款，购物车结算才显示积分和优惠券使用
        if (TextUtils.equals(from_tag, "advance_buy_qe") || TextUtils.equals(from_tag, "common_buy")) {
            //添加优惠券参数
            if (discountCouponDatas != null && discountCouponDatas.size() > 0) {
                List<RequestOrderConfirmModel.DisscountCouponInfo> discountDatas = new ArrayList<>();
                for (OrderConfirmAllCardsData itemData : discountCouponDatas.values()) {
                    RequestOrderConfirmModel.DisscountCouponInfo disscountCouponInfo = new RequestOrderConfirmModel.DisscountCouponInfo();
                    disscountCouponInfo.setCardBatchId(itemData.getId());
                    disscountCouponInfo.setSelectedNumber("1");
                    discountDatas.add(disscountCouponInfo);
                }
                requestOrderConfirmModel.setSelectedCardBatchAmounts(discountDatas);
            }

            requestOrderConfirmModel.setIntegralPay(integralPay);
        }

        requestOrderConfirmModel.setBuyingDevice(android.os.Build.MODEL);

        //如果是定金支付，没有发票信息和备注信息
        if (!TextUtils.equals(from_tag, "advance_buy_dj")) {
            requestOrderConfirmModel.setMemo(confirm_order_desc_et.getText().toString());
            if (confirmOrderFpxxSwitch.isOpened())
                requestOrderConfirmModel.setInvoiceTitle(confirm_order_fpxx_et.getText().toString());
        }

        requestData.setREQUEST_DATA(requestOrderConfirmModel);

        ServerApi.request(false, requestData, String.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (TextUtils.equals(jsonObject.optString("RETURN_CODE"), OleConstants.REQUES_SUCCESS)) {
//                            showData(response.getRETURN_DATA().getCards());
                                orderId = jsonObject.optJSONObject("RETURN_DATA")
                                        .optString("orderIds");
                                payAmount = Float.valueOf(jsonObject.optJSONObject("RETURN_DATA")
                                        .optString("totalNeedPayPrice"));
                                new PayPopupWindow(ConfirmOrderActivity.this,
                                        orderId, payAmount, true).showPopupWindow();
                            } else {
                                ToastUtil.showToast(jsonObject.optString("RETURN_DESC"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("提交订单失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("提交订单完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void showAddressViewData() {
        if (addressData == null) {
            confirm_order_address_msg_layout.setVisibility(View.GONE);
            confirmOrderAddressHintTV.setVisibility(View.VISIBLE);
        } else {
            confirm_order_address_msg_layout.setVisibility(View.VISIBLE);
            confirmOrderAddressHintTV.setVisibility(View.GONE);
            addressNameTv.setText(addressData.getUserName());
            addressMobileTv.setText(addressData.getPhone());
            addressContentTv.setText(addressData.getAddress());
        }
    }

    private void showDiscountCouponViewData() {
        orderId = "";
        if (discountCouponDatas != null) {
            discountCouponAmount = 0;
            for (OrderConfirmAllCardsData data : discountCouponDatas.values()) {
                discountCouponAmount += data.getFaceValue();
            }

            if (!showHJView()) {
                discountCouponAmount = 0;
                discountCouponDatas.clear();
            }
            confirmOrderDiscountCouponAmountTxt.setText("-¥" + discountCouponAmount);
            confirmOrderDkxxYhqAmount.setText("-¥" + discountCouponAmount);
        }

    }

    //显示计算底部合计部分
    private boolean showHJView() {
        Log.d("优惠券优惠金额：" + discountCouponAmount);
        Log.d("积分优惠金额：" + integralPay);
        Log.d("礼品卡优惠金额：" + giftCardAmount);
        Log.d("活动优惠金额：" + discountAmount);

        float discountAllAmount = discountCouponAmount + integralPay + giftCardAmount + discountAmount;
        if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0) {
            Log.d("优惠总金额：" + discountAllAmount);
            Log.d("商品总金额：" + payAmount);
            if (discountAllAmount > payAmount) {
                ToastUtil.showToast("优惠总金额不能大于商品总金额");
                return false;
            } else {
                confirmOrderYh.setText("已优惠：¥" + discountAllAmount * 10000 / 10000);

                confirmOrderAmount.setText("¥" + (payAmount * 10000 - discountAllAmount * 10000) / 10000);
                return true;
            }
        } else {
            ToastUtil.showToast("没有获取到商品信息");
            return false;
        }
    }

    @OnClick({R.id.confirm_order_address_layout, R.id.confirm_order_discount_coupon_layout,
            R.id.confirm_order_sylpk_layout, R.id.confirm_order_submit_btn, R.id.confirm_order_hj_layout})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order_address_layout:
                startActivityForResult(
                        new Intent(this, GoodsAddressActivity.class)
                                .putExtra("from_page", "confirm_order"),
                        CHOOSE_ADDRESS_REQUEST_CODE);
                break;
            case R.id.confirm_order_discount_coupon_layout:
                if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0
                        && orderConfirmInfo.getOcs().get(0).getAllCardBatchs() != null
                        && orderConfirmInfo.getOcs().get(0).getAllCardBatchs().size() > 0) {
                    startActivityForResult(
                            new Intent(this, ChooseDiscountCouponActivity.class)
                                    .putParcelableArrayListExtra("discount_coupon_list",
                                            orderConfirmInfo.getOcs().get(0).getAllCardBatchs()),
                            CHOOSE_DISCOUNT_COUPON_REQUEST_CODE);
                }
                break;
            case R.id.confirm_order_sylpk_layout:
                startActivityForResult(
                        new Intent(this, ChooseGiftCardActivity.class),
                        CHOOSE_GIFT_CARD_REQUEST_CODE);
                break;
            case R.id.confirm_order_submit_btn:
//                new PayPopupWindow(this, "2017010515324535005", 100).showPopupWindow();
                if (TextUtils.equals(from_tag, "advance_buy_dj")) {
                    if (TextUtils.isEmpty(confirm_order_wk_phone.getText().toString())) {
                        ToastUtil.showToast("请输入尾款支付的手机号");
                        break;
                    } else {
                        if (!StringUtils.isMobile(confirm_order_wk_phone.getText().toString())) {
                            ToastUtil.showToast("请输入正确的尾款支付的手机号");
                            break;
                        }
                    }
                } else {
                    if (confirmOrderFpxxSwitch.isOpened()) {
                        if (TextUtils.isEmpty(confirm_order_fpxx_et.getText().toString())) {
                            ToastUtil.showToast("请输入发票抬头");
                            break;
                        }
                    }
                }

                if (!ToolUtils.isLoginStatus(ConfirmOrderActivity.this)) {
                    Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (orderConfirmInfo.getOcs() != null && orderConfirmInfo.getOcs().size() > 0
                            && orderConfirmInfo.getOcs().get(0).getBuyItems() != null
                            && orderConfirmInfo.getOcs().get(0).getBuyItems().size() > 0) {
                        //如果刚才提交过订单，有订单号，直接支付，不用再提交订单
                        if (TextUtils.isEmpty(orderId)) {
                            submitOrder();
                        } else {
                            new PayPopupWindow(ConfirmOrderActivity.this,
                                    orderId, payAmount).showPopupWindow();
                        }
                    }
                }
                break;
            case R.id.confirm_order_hj_layout:
                if (!TextUtils.equals(from_tag, "advance_buy_dj")) {
                    if (confirmOrderDkxxLayout.getVisibility() == View.VISIBLE) {
                        confirmOrderDkxxLayout.setVisibility(View.GONE);
                        confirmOrderIc3.setImageResource(R.drawable.btn_jran_normal);
                    } else {
                        confirmOrderDkxxLayout.setVisibility(View.VISIBLE);
                        confirmOrderIc3.setImageResource(R.drawable.btn_jran_pressed);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode:" + requestCode);
        Log.d("resultCode:" + resultCode);
        if (requestCode == CHOOSE_ADDRESS_REQUEST_CODE && resultCode == 100) {
            addressData = (AddressListData.Address) data.getSerializableExtra("address_data");
            showAddressViewData();
        } else if (requestCode == CHOOSE_DISCOUNT_COUPON_REQUEST_CODE && resultCode == 100) {
            if (discountCouponDatas == null)
                discountCouponDatas = new HashMap<>();
            OrderConfirmAllCardsData cardsData = data.getParcelableExtra("discount_coupon_data");
            if (!discountCouponDatas.containsKey(cardsData.getId()))
                discountCouponDatas.put(cardsData.getId(), cardsData);
            showDiscountCouponViewData();
        } else if (requestCode == CHOOSE_GIFT_CARD_REQUEST_CODE && resultCode == 100) {
            orderId = "";
            giftCardDatas = data.getParcelableArrayListExtra("gift_card_amount");
            if (giftCardDatas != null) {
                giftCardAmount = 0;
                for (GiftCardItemData giftCardItemData : giftCardDatas) {
                    giftCardAmount += giftCardItemData.getSelectedAmount();
                }
            }
            Log.e("giftCardAmount：" + giftCardAmount);
            if (!showHJView()) {
                giftCardAmount = 0;
                giftCardDatas.clear();
            }
            confirmOrderSylpkAmountTxt.setText("-¥" + giftCardAmount);
            confirmOrderDkxxLpkzfAmount.setText("-¥" + giftCardAmount);

        } else if (requestCode == GO_UNION_PAY_REQUEST_CODE) {
            /*************************************************
             * 步骤3：处理银联手机支付控件返回的支付结果
             ************************************************/
            if (data == null) {
                return;
            }

            String msg = "";
            /*
             * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
             */
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {

                // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
                // result_data结构见c）result_data参数说明
                if (data.hasExtra("result_data")) {
                    String result = data.getExtras().getString("result_data");
                    Log.v("银联支付回调数据：" + result);
                    try {
                        JSONObject resultJson = new JSONObject(result);
                        String sign = resultJson.getString("sign");
                        String dataOrg = resultJson.getString("data");
                        // 此处的verify建议送去商户后台做验签
                        // 如要放在手机端验，则代码必须支持更新证书
                        boolean ret = RSAUtil.verify(dataOrg, sign, OleConstants.UNION_PAY_MODE);
                        if (ret) {
                            // 验签成功，显示支付结果
                            msg = "支付成功！";
                            Log.i("银联用户支付成功！！！");
                            startActivity(
                                    new Intent(this, PayStateActivity.class)
                                            .putExtra("state", 0));
                            finish();
                            return;
                        } else {
                            // 验签失败
                            msg = "支付失败！";
                            Log.i("银联用户支付失败！！！");
                            startActivity(
                                    new Intent(this, PayStateActivity.class)
                                            .putExtra("state", 1));
                            finish();
                            return;
                        }
                    } catch (JSONException e) {
                    }
                } else {
                    startActivity(
                            new Intent(this, PayStateActivity.class)
                                    .putExtra("state", 1));
                    finish();
                    return;
                }
                // 结果result_data为成功时，去商户后台查询一下再展示成功
//                msg = "支付成功！";
            } else if (str.equalsIgnoreCase("fail")) {
                msg = "支付失败！";
                Log.i("银联支付失败！！！");
                startActivity(
                        new Intent(this, PayStateActivity.class)
                                .putExtra("state", 1));
                finish();
                return;
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = "用户取消了支付";
                Log.i("银联用户取消了支付！！！");
                startActivity(
                        new Intent(this, PayStateActivity.class)
                                .putExtra("state", 1));
                finish();
                return;
            }

//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("支付结果通知");
//            builder.setMessage(msg);
//            builder.setInverseBackgroundForced(true);
//            // builder.setCustomTitle();
//            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100) //在ui线程执行 优先级100
    public void onDataSynEvent(PayResultEnum event) {
        Log.d("event---->" + event);

        if (event == PayResultEnum.PAY_CANCEL || event == PayResultEnum.PAY_FAIL) {
            PayResultUtils.getInstance().forword(this, 1);
        } else if (event == PayResultEnum.PAY_SUCCESS) {
            PayResultUtils.getInstance().forword(this, 0);
        }
    }

}
