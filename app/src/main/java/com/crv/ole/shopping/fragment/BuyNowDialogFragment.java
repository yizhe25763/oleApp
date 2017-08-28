package com.crv.ole.shopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseBottomSheetDialogFragment;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.pay.activity.ConfirmOrderActivity;
import com.crv.ole.shopping.model.AddPreToCartRespose;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fairy on 2017/8/11.
 * 立即购买弹出框
 */

public class BuyNowDialogFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = BuyNowDialogFragment.class.getName();
    private static String productId;
    private static String skuId;
    private static float price;
    private static float totalStock;
    private static int buyType;
    private int amount = 1;
    private TextView amountTv;
    private EditText amountEt;
    private TextView totalPriceTv;

    @Override
    public int getLayoutResId() {
        return R.layout.buynow_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.product_close_layout).setOnClickListener(this);
        view.findViewById(R.id.product_substract_layout).setOnClickListener(this);
        view.findViewById(R.id.product_add_layout).setOnClickListener(this);
        view.findViewById(R.id.product_accountBtn).setOnClickListener(this);
        amountTv = (TextView) view.findViewById(R.id.product_numTv);
        amountTv.setOnClickListener(this);
        amountEt = (EditText) view.findViewById(R.id.product_numEt);
        amountEt.setSelection(amountEt.getText().toString().length());
        totalPriceTv = (TextView) view.findViewById(R.id.product_totalPriceTv);
        amountTv.setText(amount + "");
        totalPriceTv.setText("￥" + (amount * price));

        amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                amount = Integer.parseInt(amountEt.getText().toString());
                if (amount <= totalStock) {
                    amountTv.setText(amount + "");
                    totalPriceTv.setText("￥" + (amount * price));
                } else {
                    ToastUtil.showToast("购买数量不能大于库存数哦");
                    String amStr = amount + "";
                    amount = Integer.parseInt(amStr.substring(0, amStr.length()));
                    amountTv.setText(amount + "");
                    amountEt.setText(amount + "");
                    totalPriceTv.setText("￥" + (amount * price));
                }
            }
        });
    }


    /**
     * 弹出一个立即购买框
     *
     * @param activity
     * @param priceParams      结算单价
     * @param totalStockParams 总库存数
     * @param productIdParams  商品Id
     * @param skuIdParams      商品SkuId
     * @param buyTypeParams    0:普通结算，1:预售结算定金，2:预售结算尾款，3:预售结算全额付款
     * @return
     */
    public static BuyNowDialogFragment showDialog(FragmentActivity activity, float priceParams,
                                                  int totalStockParams, String productIdParams, String skuIdParams,
                                                  int buyTypeParams) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        BuyNowDialogFragment buyNowDialog = (BuyNowDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (null == buyNowDialog) {
            buyNowDialog = new BuyNowDialogFragment();
            price = TextUtils.isEmpty(priceParams + "") ? 1 : priceParams;
            totalStock = TextUtils.isEmpty(totalStock + "") ? 1 : totalStockParams;
            productId = TextUtils.isEmpty(productIdParams) ? "" : productIdParams;
            skuId = TextUtils.isEmpty(skuIdParams) ? "" : skuIdParams;
            buyType = TextUtils.isEmpty(buyTypeParams + "") ? 0 : buyTypeParams;
        }
        if (!activity.isFinishing() && null != buyNowDialog && !buyNowDialog.isAdded()) {
            fragmentManager.beginTransaction().add(buyNowDialog, TAG).commitAllowingStateLoss();
        }
        return buyNowDialog;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_close_layout:             //  关闭
                this.dismiss();
                break;
            case R.id.product_substract_layout:         //  ----------------
//                if(amountEt.getVisibility() == View.VISIBLE){
//                    amountEt.setVisibility(View.GONE);
//                    amountTv.setVisibility(View.VISIBLE);
//                    hideSoftInput(0);
//                }
                if (amount <= 1) {
                    ToastUtil.showToast("购买数量至少为1哦");
                    return;
                }
                amount--;
                amountTv.setText(amount + "");
                amountEt.setText(amount + "");
                totalPriceTv.setText("￥" + (amount * price));
                break;
            case R.id.product_add_layout:               //  ++++++++++++++++
//                if(amountEt.getVisibility() == View.VISIBLE){
//                    amountEt.setVisibility(View.GONE);
//                    amountTv.setVisibility(View.VISIBLE);
//                    hideSoftInput(0);
//                }
                if (amount >= totalStock) {
                    ToastUtil.showToast("购买数量不能大于库存数哦");
                    return;
                }
                amount++;
                amountTv.setText(amount + "");
                amountEt.setText(amount + "");
                totalPriceTv.setText("￥" + (amount * price));
                break;
            case R.id.product_numTv:                    //  显示编辑区域
//                if(amountEt.getVisibility() == View.GONE || amountEt.getVisibility() == View.INVISIBLE){
//                    amountEt.setVisibility(View.VISIBLE);
//                    amountTv.setVisibility(View.GONE);
//                    hideSoftInput(1);
//                }
                break;
            case R.id.product_accountBtn:               //  结算

                if (buyType == 1) {
                    advanceBuyNow();
                } else {
                    buyNow();
                }
                break;
        }
    }

    /**
     * 立即购买
     * common_buy:普通结算，advance_buy_dj:预售结算定金，advance_buy_wk:预售结算尾款，advance_buy_qe:预售结算全额付款
     *
     * @return
     */
    private void buyNow() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("amount", amount + "");
        requestMap.put("productId", productId);
        requestMap.put("skuId", skuId);
        ServiceManger.getInstance().buyNow(requestMap,
                new BaseRequestCallback<BaseResponseData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始立即购买");
                    }

                    @Override
                    public void onSuccess(BaseResponseData data) {
                        Log.i("立即购买：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            dismiss();
                            String buyTag = "";
                            if (buyType == 0) {
                                buyTag = "common_buy";
                            } else if (buyType == 1) {
                                buyTag = "advance_buy_dj";
                            } else if (buyType == 2) {
                                buyTag = "advance_buy_wk";
                            } else if (buyType == 3) {
                                buyTag = "advance_buy_qe";
                            }
                            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                            intent.putExtra("from_tag", buyTag);
                            startActivity(intent);
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("立即购买失败！");
                    }

                    @Override
                    public void onEnd() {
                        Log.i("立即购买成功");
                    }
                });
    }

    /**
     * 预售支付定金，立即购买
     * common_buy:普通结算，advance_buy_dj:预售结算定金，advance_buy_wk:预售结算尾款，advance_buy_qe:预售结算全额付款
     *
     * @return
     */
    private void advanceBuyNow() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount+"");
        map.put("buyNow", "Y");//是否立即购买,Y:是，N:否，不传默认是否
        ServiceManger.getInstance().addPresaleToCart(map, new BaseRequestCallback<AddPreToCartRespose>() {
            @Override
            public void onSuccess(AddPreToCartRespose data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);

                    dismiss();
                    String buyTag = "";
                    if (buyType == 0) {
                        buyTag = "common_buy";
                    } else if (buyType == 1) {
                        buyTag = "advance_buy_dj";
                    } else if (buyType == 2) {
                        buyTag = "advance_buy_wk";
                    } else if (buyType == 3) {
                        buyTag = "advance_buy_qe";
                    }
                    Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                    intent.putExtra("from_tag", buyTag);
                    startActivity(intent);
                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });

    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInput(int type) {
        View view = getActivity().getWindow().peekDecorView();
        InputMethodManager inputmanger = (InputMethodManager) getActivity().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && type == 0) {
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else if (view == null && type == 1) {
            amountEt.setFocusable(true);
            inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
