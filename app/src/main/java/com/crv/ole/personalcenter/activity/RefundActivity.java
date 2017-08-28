package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.personalcenter.adapter.RefundListAdapter;
import com.crv.ole.personalcenter.model.RefundListData;
import com.crv.ole.personalcenter.model.RefundListResponse;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.shopping.activity.RefundDetailActivity;
import com.crv.ole.utils.OleConstants;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 退款退货Activity
 * Created by zhangbo on 2017/8/22.
 */

public class RefundActivity extends BaseActivity implements RefundListAdapter.OnRefundClickListener {

    @BindView(R.id.myOrder_refreshLayout)
    PullToRefreshLayout myOrder_refreshLayout;
    @BindView(R.id.myOrder_listView)
    ListView myOrder_listView;

    private RefundListAdapter adapter;

    private List<RefundListData> dataList = new ArrayList<>();

    private int page = 1;

    private Map<String, String> params = new HashMap<>();

    private boolean isInputWL = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initUI();
        initParams();
        initListener();
        initEvent();
        page = 1;
        initData(true);
    }


    private void initParams() {
        params.put("limit", "20");
    }


    //加载数据
    private void initData(boolean isRefresh) {
        params.put("page", page + "");
        ServiceManger.getInstance().getRefundOrderList(params, new BaseRequestCallback<RefundListResponse>() {

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
            public void onSuccess(RefundListResponse data) {
                if (data != null && data.getRETURN_DATA().getRecordList() != null && data.getRETURN_DATA().getRecordList().size() > 0) {
                    if (isRefresh) {
                        dataList.clear();
                        myOrder_refreshLayout.finishRefresh();
                    } else {
                        myOrder_refreshLayout.finishLoadMore();
                    }
                    dataList.addAll(data.getRETURN_DATA().getRecordList());
                    adapter.notifyDataSetChanged();
                } else {
                    if (isRefresh) {
                        dataList.clear();
                        myOrder_refreshLayout.finishRefresh();
                    } else {
                        myOrder_refreshLayout.finishLoadMore();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initListener() {
        myOrder_refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                initData(true);
            }

            @Override
            public void loadMore() {
                page++;
                initData(false);
            }
        });
    }


    private void initUI() {
        title_name_tv.setText("退款/退货");
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setBackground(getResources().getDrawable(R.drawable.order_kf_button_selector));
        adapter = new RefundListAdapter(mContext, dataList);
        adapter.setOnRefundClickListener(this);
        myOrder_listView.setAdapter(adapter);
    }

    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        title_iv_1.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout://返回
                finish();
                break;
            case R.id.title_iv_1: //联系客服
                UnicornModel.openChat(mContext);
                break;
        }
    }

    @Override
    public void onDetailClick(RefundListData data) {//查看详情
        Intent intent = new Intent(this, RefundDetailActivity.class);
        intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, data.getAliasCode());
        if ("1".equals(data.getProcessState().getState())) {
            isInputWL = true;
        }
        intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, isInputWL ? "1" : "0");
        startActivity(intent);
    }

    @Override
    public void onInputWL(RefundListData data) {
        // 填写运单
        startActivityWithAnim(new Intent(this
                , AfterOrderWLActivity.class)
                .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                        data.getOrderAliasCode()));
    }
}
