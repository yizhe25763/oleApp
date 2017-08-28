package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.adapter.HWAdapter;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.information.adapter.SpecialCommentListAdapter;
import com.crv.ole.information.model.SpecialCommentInfoResultBean;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.tools.PayPopupWindow;
import com.crv.ole.personalcenter.adapter.OrderInfoListAdapter;
import com.crv.ole.personalcenter.model.OrderData;
import com.crv.ole.personalcenter.model.OrderInfoReslt;
import com.crv.ole.personalcenter.model.OrderItem;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.personalcenter.model.UserInfoResultBean;
import com.crv.ole.personalcenter.ui.MyListView;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.CountDownTimerUtil;
import com.crv.sdk.utils.StringUtils;
import com.crv.sdk.utils.TextUtil;

import java.io.IOException;
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

import org.greenrobot.eventbus.EventBus;


/**
 * 订单详情  1、待付款 2、退款 3、已完成 4、待评价 5、待发货 6、待签收 7、交易关闭
 *
 * @author Fanhao Yi
 */
public class OrderInfoActivity extends BaseActivity {

    @BindView(R.id.title_back_layout)
    RelativeLayout titleBackLayout;
    @BindView(R.id.kf_layout)
    RelativeLayout kfLayout;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.status_icon)
    ImageView statusIcon;
    @BindView(R.id.status_conetent)
    TextView statusConetent;
    @BindView(R.id.status_time)
    TextView statusTime;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.user_icon)
    ImageView userIcon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.user_info)
    RelativeLayout userInfo;
    @BindView(R.id.physical_icon)
    ImageView physicalIcon;
    @BindView(R.id.physical_name)
    TextView physicalName;
    @BindView(R.id.physical_info)
    ImageButton physicalInfo;
    @BindView(R.id.physical_brief)
    TextView physicalBrief;
    @BindView(R.id.physical_time)
    TextView physicalTime;
    @BindView(R.id.physical_info_layout)
    RelativeLayout physicalInfoLayout;
    @BindView(R.id.info_head)
    ImageView infoHead;
    @BindView(R.id.info_list)
    MyListView infoList;
    @BindView(R.id.info_bottom)
    ImageView infoBottom;
    @BindView(R.id.ty_content)
    TextView tyContent;
    @BindView(R.id.tk_layout)
    LinearLayout tkLayout;
    @BindView(R.id.dd_content)
    TextView ddContent;
    @BindView(R.id.dd_layout)
    LinearLayout ddLayout;
    @BindView(R.id.yhq_content)
    TextView yhqContent;
    @BindView(R.id.yhq_layout)
    LinearLayout yhqLayout;
    @BindView(R.id.jf_content)
    TextView jfContent;
    @BindView(R.id.jf_layout)
    LinearLayout jfLayout;
    @BindView(R.id.ysk_content)
    TextView yskContent;
    @BindView(R.id.ysk_layout)
    LinearLayout yskLayout;
    @BindView(R.id.sp_content)
    TextView spContent;
    @BindView(R.id.sp_layout)
    LinearLayout spLayout;
    @BindView(R.id.yf_content)
    TextView yfContent;
    @BindView(R.id.yf_layout)
    LinearLayout yfLayout;
    @BindView(R.id.sfk_content)
    TextView sfkContent;
    @BindView(R.id.xdsj_content)
    TextView xdsjContent;
    @BindView(R.id.zfsj_content)
    TextView zfsjContent;
    @BindView(R.id.zfsj_layout)
    LinearLayout zfsjLayout;
    @BindView(R.id.bz_content)
    TextView bzContent;
    @BindView(R.id.bz_layout)
    LinearLayout bzLayout;
    @BindView(R.id.fqsh_btn)
    Button fqshBtn;
    @BindView(R.id.yjfg_btn)
    Button yjfgBtn;
    @BindView(R.id.pjdd_btn)
    Button pjddBtn;
    @BindView(R.id.bddh_btn)
    Button bddhBtn;
    @BindView(R.id.qxdd_btn)
    Button qxddBtn;
    @BindView(R.id.zfwk_btn)
    Button zfwkBtn;
    @BindView(R.id.tkxx_title)
    ImageView tkxxTitle;
    @BindView(R.id.tkxx_layout)
    TextView tkxxLayout;

    private String mOrderID;//订单id
    private String statusID;//订单状态id
    private CountDownTimerUtil timer;
    private OrderInfoListAdapter mAdapter;
    private List<OrderInfoReslt.RETURNDATABean.ItemsBean> dataList;
    private CustomDiaglog customDiaglog;
    private OrderInfoReslt beans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        ButterKnife.bind(this);
        mOrderID = getIntent().getStringExtra("id");
        statusID = getIntent().getStringExtra("status");
        dataList = new ArrayList<OrderInfoReslt.RETURNDATABean.ItemsBean>();
        if (!StringUtils.isNullOrEmpty(mOrderID) && !StringUtils.isNullOrEmpty(statusID)) {
            getInfo(mOrderID);
        }


    }

    /**
     * 获取订单详情
     */
    private void getInfo(String mOrderID) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.ORDER_INFO);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("aliasCode", mOrderID);
        requestMap.put("serviceCode", "getOrderDetails");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, OrderInfoReslt.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderInfoReslt>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(OrderInfoReslt response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            initView(statusID);
                            beans = response;
                            if (statusID.equals("2")) {
                                statusTime.setText("申请时间：暂无接口数据支持");
                            } else if (statusID.equals("1")) {
                                timer = new CountDownTimerUtil(Long.valueOf(response.getRETURN_DATA().getCreateTime()) - System.currentTimeMillis(), 1000, statusTime, "DZF");
                                timer.start();
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getDeliveryInfo().getUserName())) {//用户名
                                userName.setText(response.getRETURN_DATA().getDeliveryInfo().getUserName());
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getDeliveryInfo().getMobile())) {//手机
                                userPhone.setText(response.getRETURN_DATA().getDeliveryInfo().getMobile());
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getDeliveryInfo().getAddress())) {//地址
                                userAddress.setText(response.getRETURN_DATA().getDeliveryInfo().getAddress());
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getOrderAliasCode())) {//订单号
                                orderId.setText("订单号：" + response.getRETURN_DATA().getOrderAliasCode());
                            }

                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getFTotalOrderPrice())) {//订单总价
                                ddContent.setText("￥" + response.getRETURN_DATA().getFTotalOrderPrice());
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getTicketPayPrice())) {//优惠券折扣
                                yhqContent.setText("-￥" + response.getRETURN_DATA().getTicketPayPrice());
                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getFIntegralPayPrice())) {//积分抵扣
                                jfContent.setText("-￥" + response.getRETURN_DATA().getFIntegralPayPrice());

                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getCardPayPrice())) {//预售卡抵扣
                                yskContent.setText("-￥" + response.getRETURN_DATA().getCardPayPrice());

                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getFTotalDeliveryPrice())) {//邮费
                                yfContent.setText("￥" + response.getRETURN_DATA().getFTotalDeliveryPrice());

                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getFTotalOrderRealPrice())) {//实付款
                                sfkContent.setText("￥" + response.getRETURN_DATA().getFTotalOrderRealPrice());

                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getFormatCreateTime())) {//下单时间
                                xdsjContent.setText(response.getRETURN_DATA().getFormatCreateTime());

                            }
                            if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getDeliveryInfoExt().getDescription())) {//备注
                                bzContent.setText(response.getRETURN_DATA().getDeliveryInfoExt().getDescription());

                            }
                            dataList.clear();
                            if (response.getRETURN_DATA().getItems().size() > 0) {
                                dataList = response.getRETURN_DATA().getItems();

                            }
                            mAdapter = new OrderInfoListAdapter(OrderInfoActivity.this, dataList);
                            infoList.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();

                    }
                });

    }

    /**
     * 根据订单状态显示页面相应元素
     *
     * @param statusID 订单状态ID
     */
    private void initView(String statusID) {
        // 1、待付款 2、退款 3、已完成 4、待评价 5、待发货 6、待签收 7、交易关闭
        switch (statusID) {
            case "1":
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_dfkzt));
                statusConetent.setText("待付款");
                statusTime.setVisibility(View.VISIBLE);
                physicalInfoLayout.setVisibility(View.GONE);
                qxddBtn.setVisibility(View.VISIBLE);
                zfwkBtn.setVisibility(View.VISIBLE);
                zfsjLayout.setVisibility(View.GONE);
                statusConetent.setText("待付款");

                break;
            case "2":
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_tkzzt));
                statusConetent.setText("退款中");
                tkLayout.setVisibility(View.VISIBLE);
                statusTime.setVisibility(View.VISIBLE);
                bddhBtn.setVisibility(View.VISIBLE);
                physicalInfoLayout.setVisibility(View.GONE);
                userInfo.setVisibility(View.GONE);
                tkxxTitle.setVisibility(View.VISIBLE);
                tkxxLayout.setVisibility(View.VISIBLE);
                break;
            case "3":
                fqshBtn.setVisibility(View.VISIBLE);
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_ddywc));
                statusConetent.setText("已完成");
                break;
            case "4":
                fqshBtn.setVisibility(View.VISIBLE);
                pjddBtn.setVisibility(View.VISIBLE);
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_dpjzt));
                statusConetent.setText("待评价");
                break;
            case "5":
                qxddBtn.setVisibility(View.VISIBLE);
                physicalInfoLayout.setVisibility(View.GONE);
                zfsjLayout.setVisibility(View.GONE);
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_dfhzt));
                statusConetent.setText("待发货");

                break;
            case "6":
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_dqszt));
                statusConetent.setText("待签收");

                break;
            case "7":
                statusIcon.setBackground(getResources().getDrawable(R.drawable.ic_gb));
                statusConetent.setText("交易关闭");
                statusTime.setVisibility(View.VISIBLE);
                physicalInfoLayout.setVisibility(View.GONE);
                zfsjLayout.setVisibility(View.GONE);
                qxddBtn.setVisibility(View.GONE);
                zfwkBtn.setVisibility(View.GONE);
                statusTime.setText("逾时未支付，自动取消");
                break;
        }

    }

    @OnClick({R.id.title_back_layout, R.id.kf_layout, R.id.physical_info, R.id.fqsh_btn, R.id.yjfg_btn, R.id.pjdd_btn, R.id.bddh_btn, R.id.qxdd_btn, R.id.zfwk_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
            case R.id.kf_layout://客服
                UnicornModel.openChat(this);
                break;
            case R.id.physical_info://订单追踪
                startActivityWithAnim(new Intent(OrderInfoActivity.this
                        , LogisticsDetailsActivity.class)
                        .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                                mOrderID));
                break;
            case R.id.fqsh_btn://发起售后
                startActivityWithAnim(new Intent(OrderInfoActivity.this
                        , AfterSaleActivity.class)
                        .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                                mOrderID));
                break;
            case R.id.yjfg_btn://一件复购
                onRepeatClick(beans);
                break;
            case R.id.pjdd_btn://评论订单
                if (beans.getRETURN_DATA().getBuyerReviewInfo().getState() != null) {
                    startActivity(new Intent(OrderInfoActivity.this, EvaluationOrderActivity.class).putExtra("orderData", beans).putExtra("isEditMode", TextUtil.isEmpty(beans.getRETURN_DATA().getBuyerReviewInfo().getState()) || "br100".equals(beans.getRETURN_DATA().getBuyerReviewInfo().getState())));
                } else {
                    ToastUtil.showToast("返回数据不全");

                }
                break;
            case R.id.bddh_btn://拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                startActivity(intent);
                break;
            case R.id.qxdd_btn://取消订单
                showAlterDiaglo(mOrderID);
                break;
            case R.id.zfwk_btn://支付尾款
                onPayClick(beans);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 支付订单
     *
     * @param data
     */
    public void onPayClick(OrderInfoReslt data) {
        String orderId = data.getRETURN_DATA().getOrderAliasCode();
        float amount = 0;
        if ("common".equals(data.getRETURN_DATA().getOrderTypeInfo().getOrderType())) {//普通订单
            amount = Float.valueOf(data.getRETURN_DATA().getFTotalOrderPrice());
        } else if ("preSale".equals(data.getRETURN_DATA().getOrderTypeInfo().getOrderType())) {//预售订单
            //【 定金预售：1】,【定金预售 ，尾款不确定：2】
            if ("1".equals(data.getRETURN_DATA().getPreSaleInfo().getPreSaleType()) || "2".equals(data.getRETURN_DATA().getPreSaleInfo().getPreSaleType())) {
                if ("0".equals(data.getRETURN_DATA().getPreSaleInfo().getPreSalePayState())) {//定金未支付
                    amount = Float.valueOf(data.getRETURN_DATA().getPreSaleInfo().getDeposit());//定金
                }
                if ("1".equals(data.getRETURN_DATA().getPreSaleInfo().getPreSalePayState())) {//尾款未支付
                    amount = Float.valueOf(data.getRETURN_DATA().getPreSaleInfo().getBalance());//尾款
                }
            } else {//全款
                amount = Float.valueOf(data.getRETURN_DATA().getFTotalOrderPrice());
            }
        }
        new PayPopupWindow(OrderInfoActivity.this,
                orderId, amount, false).showPopupWindow();
    }


    /**
     * 取消订单
     *
     * @param mOrderID
     */
    private void showAlterDiaglo(String mOrderID) {
        //showDiaglog
        if (customDiaglog == null) {
            customDiaglog = new CustomDiaglog(OrderInfoActivity.this, getString(R.string.cancle_order_quest), getString(R.string.cancel), getString(R.string.confirm));
        }
        customDiaglog.setOnConfimClickListerner(new CustomDiaglog.OnConfimClickListerner() {
            @Override
            public void onCanleClick() {
                if (customDiaglog != null && customDiaglog.isShowing()) {
                    customDiaglog.dismiss();
                }
            }

            @Override
            public void OnConfimClick() {
                //取消订单
                Map<String, String> params = new HashMap<>();
                params.put("orderId", mOrderID);
                ServiceManger.getInstance().cancelOrder(params, new BaseRequestCallback<BaseResponseData>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showProgressDialog(R.string.waiting);
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onSuccess(BaseResponseData data) {
                        if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                            statusID = "7";
                            getInfo(mOrderID);
                            ToastUtil.showToast(R.string.cancle_order_success);
                        } else {
                            ToastUtil.showToast(R.string.cancle_order_failed);
                        }
                    }
                });
            }
        });
        customDiaglog.show();
    }


    /**
     * 一键复购
     *
     * @param data
     */
    public void onRepeatClick(OrderInfoReslt data) {
        final int size = data.getRETURN_DATA().getItems().size();
        for (int i = 0; i < size; i++) {
            int a = i;
            OrderInfoReslt.RETURNDATABean.ItemsBean item = data.getRETURN_DATA().getItems().get(i);
            ServiceManger.getInstance().addToCart(item.getProductId(), item.getSkuId(), item.getAmount() + "", new BaseRequestCallback<UserInfoResultBean>() {
                @Override
                public void onSuccess(UserInfoResultBean data) {
                    if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                        if (a == size - 1) {
                            ToastUtil.showToast("一键加入购物车成功");
                        }
                        EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                    } else {
                        ToastUtil.showToast(data.getRETURN_DESC());
                    }
                }
            });
        }
    }
}
