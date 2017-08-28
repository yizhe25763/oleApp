package com.crv.ole.pay.tools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.activity.ConfirmOrderActivity;
import com.crv.ole.pay.activity.PayStateActivity;
import com.crv.ole.pay.model.PayMethodItemData;
import com.crv.ole.pay.model.PayMethodListData;
import com.crv.ole.pay.model.RealPayData;
import com.crv.ole.pay.requestmodel.RequestGetPayMethodModel;
import com.crv.ole.pay.requestmodel.RequestOrderConfirmModel;
import com.crv.ole.pay.unionpay.UnionPay;
import com.crv.ole.pay.wechat.WechatPay;
import com.crv.ole.pay.zfb.Pay;
import com.crv.ole.personalcenter.activity.CardIntroduceActivity;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.dialog.AlertDialog;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.utils.PreferencesHelper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import razerdp.basepopup.BasePopupWindow;

/**
 * 选择支付方式popupwindow
 * 注意接收支付结果的方式，银联需要在onActivityResult方法中接收，参考ConfirmOrderActivity
 */
public class PayPopupWindow extends BasePopupWindow implements View.OnClickListener {
    private Activity mContext;
    private ImageButton choosePayCancel;
    private TextView choosePayAmount;
    private Button choosePayConfirm;
    private RelativeLayout choose_pay_amount_layout, choose_pay_title_layout;
    private LinearLayout choose_pay_layout, choose_pay_method_layout;

    private List<PayMethodItemData> lists;
    private String orderId;
    private float orderAmount;
    //支付结果展示是否要跳转到支付结果页面
    private boolean isToResultActivity = true;

    private int selectPay = 0;
    private boolean confirmCancel = false;
    private IDialog mDialog;
    private CustomDiaglog cancelDiaglog;

    private void assignViews() {
        choosePayCancel = (ImageButton) findViewById(R.id.choose_pay_cancel);
        choosePayAmount = (TextView) findViewById(R.id.choose_pay_amount);
        choosePayConfirm = (Button) findViewById(R.id.choose_pay_confirm);
        choose_pay_amount_layout = (RelativeLayout) findViewById(R.id.choose_pay_amount_layout);
        choose_pay_title_layout = (RelativeLayout) findViewById(R.id.choose_pay_title_layout);
        choose_pay_layout = (LinearLayout) findViewById(R.id.choose_pay_layout);
        choose_pay_method_layout = (LinearLayout) findViewById(R.id.choose_pay_method_layout);

        choosePayAmount.setText("¥" + orderAmount);
    }

    /**
     * @param context     来源Activity
     * @param orderId     订单ID
     * @param orderAmount 订单支付金额
     */
    public PayPopupWindow(Activity context, String orderId, float orderAmount) {
        super(context);
        this.mContext = context;
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        mDialog = new FragmentDialog(context);
        setOnBeforeShowCallback(new OnBeforeShowCallback() {
            @Override
            public boolean onBeforeShow(View view, View view1, boolean b) {
                getPayMethod();
                return true;
            }
        });
        assignViews();
        setViewClickListener(this, choosePayCancel, choosePayConfirm,
                choose_pay_amount_layout, choose_pay_title_layout, choose_pay_layout);

    }

    /**
     * @param context            来源Activity
     * @param orderId            订单ID
     * @param orderAmount        订单支付金额
     * @param isToResultActivity 是否要跳转到支付结果页面,默认为true
     */
    public PayPopupWindow(Activity context, String orderId, float orderAmount, boolean isToResultActivity) {
        super(context);
        this.mContext = context;
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.isToResultActivity = isToResultActivity;
        mDialog = new FragmentDialog(context);
        setOnBeforeShowCallback(new OnBeforeShowCallback() {
            @Override
            public boolean onBeforeShow(View view, View view1, boolean b) {
                getPayMethod();
                return true;
            }
        });
        assignViews();
        setViewClickListener(this, choosePayCancel, choosePayConfirm,
                choose_pay_amount_layout, choose_pay_title_layout, choose_pay_layout);

    }

    private void getPayMethod() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_PAY_METHOD_ID);
        requestData.setREQUEST_DATA(new RequestGetPayMethodModel(orderId));

        ServerApi.request(false, requestData, PayMethodListData.class,
                new PreferencesHelper(mContext).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayMethodListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(PayMethodListData response) {
                        mDialog.dissmissDialog();
                        try {
                            if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                orderAmount = Float.valueOf(response.getRETURN_DATA().getRealPayRecs().get(0).getTotal());
                                choosePayAmount.setText("¥" + orderAmount);
                                lists = response.getRETURN_DATA().getRealPayRecs().get(0).getPayments();
                                showItemView();
                            } else {
                                confirmCancel = true;
                                dismiss();
                                ToastUtil.showToast(response.getRETURN_DESC());
                                Log.i("获取支付方式失败！！！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        confirmCancel = true;
                        dismiss();
                        ToastUtil.showToast("获取支付方式列表失败");
                        Log.i("获取支付方式失败！！！");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取支付方式列表完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void showItemView() {
        if (lists != null) {
            choose_pay_method_layout.removeAllViews();
            for (int i = 0; i < lists.size(); i++) {
                final int index = i;
                PayMethodItemData itemData = lists.get(i);
                if (TextUtils.equals("Y", itemData.getIsMobile())) {
                    View itemView = LayoutInflater.from(mContext).inflate(R.layout.pay_method_item_layout, null);
                    TextView choose_pay_item_name = (TextView) itemView.findViewById(R.id.choose_pay_item_name);
                    ImageView choose_pay_item_ic = (ImageView) itemView.findViewById(R.id.choose_pay_item_ic);

                    if (index == 0)
                        choose_pay_item_ic.setImageResource(R.drawable.btn_spxz_xz);
                    else
                        choose_pay_item_ic.setImageResource(R.drawable.btn_spxz_wxz);

                    choose_pay_item_name.setText(itemData.getPaymentName());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (selectPay != index)
                                changeSelectPayView(index);
                        }
                    });
                    choose_pay_method_layout.addView(itemView);
                }
            }
        }
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_pay_cancel:
                showCancelDialog();
                break;
            case R.id.choose_pay_confirm:
                getPaySign();
                break;

            default:
                break;
        }
    }

    /**
     * 获取支付签名数据
     */
    private void getPaySign() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_PAY_SIGN_ID);
        Map<String, String> params = new HashMap<>();
        params.put("orderIds", orderId);
        params.put("payId", lists.get(selectPay).getPayInterfaceId());
        requestData.setREQUEST_DATA(params);

        ServerApi.request(false, requestData, String.class,
                new PreferencesHelper(mContext).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
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

                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        parsePaySign(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("获取支付信息失败");
                        Log.i("获取支付信息失败！！！");
                        if (isToResultActivity)
                            PayResultUtils.getInstance().forword(mContext, 1);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取支付信息完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 解析支付签名数据，调起支付组件
     *
     * @param paySign
     */
    private void parsePaySign(String paySign) {
        try {
            JSONObject jsonObject = new JSONObject(paySign);
            if (TextUtils.equals(OleConstants.REQUES_SUCCESS, jsonObject.optString("RETURN_CODE"))) {
                if (TextUtils.equals("payi_132", lists.get(selectPay).getPayInterfaceId())) {
                    //支付宝
                    new Pay(mContext, isToResultActivity).nativePay(
                            jsonObject.optJSONObject("RETURN_DATA").optJSONObject("paySign").toString(),
                            true);

                    confirmCancel = true;
                    PayPopupWindow.this.dismiss();
                } else if (TextUtils.equals("payi_129", lists.get(selectPay).getPayInterfaceId())) {
                    //微信
                    new WechatPay(mContext).pay(
                            jsonObject.optJSONObject("RETURN_DATA").optJSONObject("paySign").toString());

                    confirmCancel = true;
                    PayPopupWindow.this.dismiss();
                } else if (TextUtils.equals("payi_235", lists.get(selectPay).getPayInterfaceId())) {
                    //银联
//                    getUnionPayInfo();
                    Message msg = mHandler.obtainMessage();
                    msg.obj = jsonObject.optJSONObject("RETURN_DATA").optString("androidPayInfo");
                    mHandler.sendMessage(msg);

                    confirmCancel = true;
                    PayPopupWindow.this.dismiss();
                } else {
                    ToastUtil.showToast("目前不支持此支付方式，请重新选择。");
                }
            } else {
                ToastUtil.showToast(jsonObject.optString("RETURN_DESC"));
                Log.i("用户支付失败！！！");
                if (isToResultActivity)
                    PayResultUtils.getInstance().forword(mContext, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("用户支付失败！！！");
            if (isToResultActivity)
                PayResultUtils.getInstance().forword(mContext, 1);
        }
    }

    @Override
    public boolean onBeforeDismiss() {
        if (!confirmCancel)
            showCancelDialog();

        return confirmCancel;
    }

    private void showCancelDialog() {
        if (cancelDiaglog == null) {
            cancelDiaglog = new CustomDiaglog(mContext, mContext.getString(R.string.choose_pay_cancel_msg), mContext.getString(R.string.dialog_cancel), mContext.getString(R.string.dialog_confirm));

            cancelDiaglog.setOnConfimClickListerner(new CustomDiaglog.OnConfimClickListerner() {
                @Override
                public void onCanleClick() {
                    if (cancelDiaglog != null && cancelDiaglog.isShowing()) {
                        cancelDiaglog.dismiss();
                    }
                    confirmCancel = false;
                    if (onAlterDialogClickListener != null) {
                        onAlterDialogClickListener.onCancle();
                    }
                }

                @Override
                public void OnConfimClick() {
                    confirmCancel = true;
                    PayPopupWindow.this.dismiss();

                    if (onAlterDialogClickListener != null) {
                        onAlterDialogClickListener.onConfim();
                    }
                    Log.i("取消支付！！！");
                    if (isToResultActivity)
                        PayResultUtils.getInstance().forword(mContext, 1);
                }
            });
        }
        cancelDiaglog.show();
    }

    private void changeSelectPayView(int newIndex) {
        ((ImageView) choose_pay_method_layout.getChildAt(newIndex)
                .findViewById(R.id.choose_pay_item_ic)).setImageResource(R.drawable.btn_spxz_xz);
        ((ImageView) choose_pay_method_layout.getChildAt(selectPay)
                .findViewById(R.id.choose_pay_item_ic)).setImageResource(R.drawable.btn_spxz_wxz);
        selectPay = newIndex;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.pop_choose_pay_layout);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.choose_pay_layout);
    }

    private void getUnionPayInfo() {
        /*************************************************
         * 步骤1：从网络开始,获取交易流水号即TN
         ************************************************/
        mDialog.showProgressDialog("加载中……", null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tn = null;
                InputStream is;
                try {

                    String url = "http://101.231.204.84:8091/sim/getacptn";

                    URL myURL = new URL(url);
                    URLConnection ucon = myURL.openConnection();
                    ucon.setConnectTimeout(120000);
                    is = ucon.getInputStream();
                    int i = -1;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((i = is.read()) != -1) {
                        baos.write(i);
                    }

                    tn = baos.toString();
                    is.close();
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message msg = mHandler.obtainMessage();
                msg.obj = tn;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mDialog.isShow())
                mDialog.dissmissDialog();
            String tn = "";
            if (msg.obj == null || ((String) msg.obj).length() == 0) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setTitle("错误提示");
                builder.setMessage("网络连接失败,请重试!");
                builder.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Log.i("银联用户支付失败！！！");
                                if (isToResultActivity)
                                    PayResultUtils.getInstance().forword(mContext, 1);
                            }
                        });
                builder.create().show();
            } else {
                tn = (String) msg.obj;
                /*************************************************
                 * 步骤2：通过银联工具类启动支付插件
                 ************************************************/
                new UnionPay().unionPay(mContext, tn, OleConstants.UNION_PAY_MODE);
            }
        }
    };

    /**
     * 确认取消dialog的回调
     */
    public interface OnAlterDialogClickListener {
        void onCancle();

        void onConfim();
    }

    public OnAlterDialogClickListener onAlterDialogClickListener;

    public void setOnAlterDialogClickListener(OnAlterDialogClickListener onAlterDialogClickListener) {
        this.onAlterDialogClickListener = onAlterDialogClickListener;
    }
}
