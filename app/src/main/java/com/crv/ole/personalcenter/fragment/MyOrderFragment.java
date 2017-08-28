package com.crv.ole.personalcenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.pay.tools.PayPopupWindow;
import com.crv.ole.personalcenter.activity.CardIntroduceActivity;
import com.crv.ole.personalcenter.activity.EvaluationOrderActivity;
import com.crv.ole.personalcenter.activity.OrderInfoActivity;
import com.crv.ole.personalcenter.adapter.MyOrderListAdapter;
import com.crv.ole.personalcenter.model.OrderData;
import com.crv.ole.personalcenter.model.OrderItem;
import com.crv.ole.personalcenter.model.OrderListResponse;
import com.crv.ole.personalcenter.model.UserInfoResultBean;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.fragment.BaseFragment;
import com.crv.sdk.utils.TextUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 个人中心 - 我的订单 - 所有订单
 */

public class MyOrderFragment extends BaseFragment implements MyOrderListAdapter.OnOrderClickListener {
    private Unbinder unbinder;
    @BindView(R.id.myOrder_refreshLayout)
    PullToRefreshLayout myOrder_refreshLayout;
    @BindView(R.id.myOrder_listView)
    ListView myOrder_listView;
    private ViewGroup rl_empter;
    private List<OrderData> dataList = new ArrayList<>();
    private MyOrderListAdapter orderListAdapter;
    private int mPosition;
    private boolean isVisible = false;
    private Map<String, String> params = new HashMap<>();
    private int start = 0;

    private CustomDiaglog customDiaglog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        mPosition = (bundle != null) ? bundle.getInt("position") : 0;
    }

    public static MyOrderFragment getInstance(Bundle bundle) {
        MyOrderFragment orderFragment = new MyOrderFragment();
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_order, container, false);
        rl_empter = (ViewGroup) view.findViewById(R.id.rl_empter);
        unbinder = ButterKnife.bind(this, view);
        TAG = "MyOrderFragment";
        initParams();
        initView();
        initListener();
        onVisible();
        return view;
    }

    private void initParams() {
        params.put("limit", "20");
        params.put("start", "0");
        params.put("serviceCode", "getOrders");
        params.put("keyword", "");
    }

    private void initView() {
        orderListAdapter = new MyOrderListAdapter(mContext, dataList);
        orderListAdapter.setOrderClickListener(this);
        myOrder_listView.setAdapter(orderListAdapter);
    }

    private void initListener() {
        myOrder_refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                initData(0, true);
            }

            @Override
            public void loadMore() {
                start++;
                initData(start, false);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//当可见的时候执行操作
            isVisible = true;
            onVisible();
        } else {//不可见时执行相应的操作
            isVisible = false;

        }
    }

    private void onVisible() {
        if (isVisible && view != null) {
            initData(0, true);
        }
    }

    //加载数据
    private void initData(int start, boolean isRefresh) {
        params.put("start", start + "");
        switch (mPosition) {
            case 0://全部
                params.put("buyerReviewState", "");
                params.put("payState", "");
                params.put("processState", "");
                getDate(isRefresh);
                break;
            case 1://待付款
                params.put("buyerReviewState", "");
                params.put("payState", "p200");
                params.put("processState", "");
                getDate(isRefresh);
                break;
            case 2://待发货
                params.put("buyerReviewState", "");
                params.put("payState", "");
                params.put("processState", "p101");
                getDate(isRefresh);
                break;
            case 3://待签收
                params.put("buyerReviewState", "");
                params.put("payState", "");
                params.put("processState", "p102");
                getDate(isRefresh);
                break;
            case 4://待评价
                params.put("buyerReviewState", "br100");
                params.put("payState", "");
                params.put("processState", "p112");
                getDate(isRefresh);
                break;
        }
    }

    private void getDate(boolean isRefresh) {
        ServiceManger.getInstance().getOrderList(params, new BaseRequestCallback<OrderListResponse>() {
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
            public void onSuccess(OrderListResponse result) {
                if (result != null && result.getRETURN_DATA() != null
                        && result.getRETURN_DATA().getOrderList() != null
                        && result.getRETURN_DATA().getOrderList().size() > 0) {
                    if (isRefresh) {
                        dataList.clear();
                        myOrder_refreshLayout.finishRefresh();
                    } else {
                        myOrder_refreshLayout.finishLoadMore();
                    }
                    for (OrderData data : result.getRETURN_DATA().getOrderList()) {
                        dataList.add(data);
                    }
                    isEmpty(false);
                    orderListAdapter.notifyDataSetChanged();
                } else {
                    if (isRefresh) {
                        dataList.clear();
                        myOrder_refreshLayout.finishRefresh();
                        isEmpty(true);
                    } else {
                        myOrder_refreshLayout.finishLoadMore();
                    }
                    orderListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void isEmpty(boolean isEmpty) {
        if (isEmpty) {
            rl_empter.setVisibility(View.VISIBLE);
            myOrder_refreshLayout.setVisibility(View.GONE);
        } else {
            rl_empter.setVisibility(View.GONE);
            myOrder_refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    /**
     * 展示diaLog
     *
     * @param resId
     */
    protected void showProgressDialog(int resId) {
        if (mDialog != null && !mDialog.isShow())
            mDialog.showProgressDialog(resId);
    }

    /**
     * 关闭dialog
     */
    public void dismissProgressDialog() {
        if (mDialog != null)
            mDialog.dissmissDialog();
    }

    @Override
    public void onRepeatClick(OrderData data) {
        final int size = data.getItems().size();
        for (int i = 0; i < size; i++) {
            int a = i;
            OrderItem item = data.getItems().get(i);
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

    @Override
    public void onCancleClick(OrderData data) {
        //取消订单
        showAlterDiaglo(data);
    }

    @Override
    public void onPayClick(OrderData data) {
        String orderId = data.getOrderAliasCode();
        float amount = 0;
        if ("common".equals(data.getOrderTypeInfo().getOrderType())) {//普通订单
            amount = Float.valueOf(data.getfTotalOrderPrice());
        } else if ("preSale".equals(data.getOrderTypeInfo().getOrderType())) {//预售订单
            //【 定金预售：1】,【定金预售 ，尾款不确定：2】
            if ("1".equals(data.getPreSaleInfo().getPreSaleType()) || "2".equals(data.getPreSaleInfo().getPreSaleType())) {
                if ("0".equals(data.getPreSaleInfo().getPreSalePayState())) {//定金未支付
                    amount = Float.valueOf(data.getPreSaleInfo().getDeposit());//定金
                }
                if ("1".equals(data.getPreSaleInfo().getPreSalePayState())) {//尾款未支付
                    amount = Float.valueOf(data.getPreSaleInfo().getBalance());//尾款
                }
            } else {//全款
                amount = Float.valueOf(data.getfTotalOrderPrice());
            }
        }
        new PayPopupWindow(getActivity(),
                orderId, amount, false).showPopupWindow();
    }

    @Override
    public void onShoHouClick(OrderData data) {
        //发起售后流程
        ToastUtil.showToast("售后功能暂未开发");
    }

    @Override
    public void onEvaluateClick(OrderData data) {
        //评价弹框
        startActivity(new Intent(getActivity(), EvaluationOrderActivity.class).putExtra("orderData", data).putExtra("isEditMode", TextUtil.isEmpty(data.getBuyerReviewInfo().getState()) || "br100".equals(data.getBuyerReviewInfo().getState())));
    }

    @Override
    public void onOrderNumClick(OrderData data) {
        //进入订单详情
        Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
        intent.putExtra("id", data.getOrderAliasCode());
        if ("p111".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//交易已关闭
            intent.putExtra("status", "7");
        } else if ("p200".equalsIgnoreCase(data.getPayStateInfo().getState())) {//未支付
            intent.putExtra("status", "1");
        } else {
            if ("p101".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//待出库
                intent.putExtra("status", "5");
            } else if ("p102".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//已出库待签收
                intent.putExtra("status", "6");
            } else if ("p112".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//已签收
                if (data.getBuyerReviewInfo() != null) {
                    if ("br102".equals(data.getBuyerReviewInfo().getState())) {//已评价
                        intent.putExtra("status", "3");
                    } else {//未评价
                        intent.putExtra("status", "4");
                    }
                } else {//未评价
                    intent.putExtra("status", "4");
                }
            } else {
                intent.putExtra("status", "2");//退款
            }
        }
        startActivity(intent);
    }

    @Override
    public void onItemClick(OrderData data) {
        //进入订单详情
        Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
        intent.putExtra("id", data.getOrderAliasCode());
        if ("p111".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//交易已关闭
            intent.putExtra("status", "7");
        } else if ("p200".equalsIgnoreCase(data.getPayStateInfo().getState())) {//未支付
            intent.putExtra("status", "1");
        } else {
            if ("p101".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//待出库
                intent.putExtra("status", "5");
            } else if ("p102".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//已出库待签收
                intent.putExtra("status", "6");
            } else if ("p112".equalsIgnoreCase(data.getProcessStateInfo().getState())) {//已签收
                if (data.getBuyerReviewInfo() != null) {
                    if ("br102".equals(data.getBuyerReviewInfo().getState())) {//已评价
                        intent.putExtra("status", "3");
                    } else {//未评价
                        intent.putExtra("status", "4");
                    }
                } else {//未评价
                    intent.putExtra("status", "4");
                }
            } else {
                intent.putExtra("status", "2");//退款
            }
        }
        startActivity(intent);
    }

    //退款
    @Override
    public void onReimburse(OrderData data) {
        //申请退款
        ToastUtil.showToast("申请退款功能暂未开发");
    }


    private void showAlterDiaglo(OrderData data) {
        //showDiaglog
        if (customDiaglog == null) {
            customDiaglog = new CustomDiaglog(getActivity(), getString(R.string.cancle_order_quest), getString(R.string.cancel), getString(R.string.confirm));
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
                params.put("orderId", data.getOrderAliasCode());
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
                            initData(0, true);
                            ToastUtil.showToast(R.string.cancle_order_success);
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);//刷新用户界面
                        } else {
                            ToastUtil.showToast(R.string.cancle_order_failed);
                        }
                    }
                });
            }
        });
        customDiaglog.show();
    }

    @Override
    public void eventBus(String event) {
        super.eventBus(event);
        if (event.equals(OleConstants.ORDER_APPRAISE_ADD_SUCCESS)) {
            initData(0, true);
            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);//刷新用户界面
        }
    }
}
