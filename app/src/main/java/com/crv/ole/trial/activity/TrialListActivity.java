package com.crv.ole.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.pay.tools.PayResultEnum;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.personalcenter.activity.LogisticsDetailsActivity;
import com.crv.ole.personalcenter.activity.TrialReportActivity;
import com.crv.ole.trial.adapter.TrialListAdapter;
import com.crv.ole.trial.callback.OnItemButtonClickListener;
import com.crv.ole.trial.model.TrialItemData;
import com.crv.ole.trial.model.TrialItemResponse;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 申请试用记录界面
 * Created by zhangbo on 2017/8/17.
 */

public class TrialListActivity extends BaseActivity {

    @BindView(R.id.trial_list)
    PullToRefreshRecyclerView trial_list;

    private LayoutInflater inflater;

    private View footView;

    private TrialListAdapter adapter;

    private List<TrialItemData> list = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();

    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_list_layout);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initTitleViews();
        initUI();
        initParams();
        initEvent();
        pageNum = 1;
        initDate(true);
    }


    private void initUI() {
        footView = inflater.inflate(R.layout.activity_trial_list_footview_layout, null);
        adapter = new TrialListAdapter(this, list);
        trial_list.setMode(PullToRefreshBase.Mode.BOTH);
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        adapter.setFootView(footView);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看更多历史记录
                startActivityWithAnim(TrialOverdueListActivity.class);
            }
        });
        RecyclerView recyclerView = trial_list.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        title_name_tv.setText(getString(R.string.trial));
    }

    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        adapter.setOnItemButtonClickListener(new OnItemButtonClickListener() {
            @Override
            public void onTrialItemButtonClick(TrialItemData data, int position) {
                if ("TA001".equals(data.getOrderState())) {//支付超时
                    ToastUtil.showToast("支付时间已超时");
                } else if ("TA002".equals(data.getOrderState())) {//未支付，去支付
                    Intent intent = new Intent(mContext, TrialPayActivity.class);
                    intent.putExtra("payAmount", data.getPostageInfo().getIntegral());//积分
                    intent.putExtra("money", data.getPostageInfo().getCash());//现金
                    intent.putExtra("orderId", data.getAliasCode());//外部订单ID
                    startActivityWithAnim(intent);
                } else if ("TA004".equals(list.get(position).getOrderState())) {//试用报告未提交
                    Intent intent = new Intent(mContext, TrialReportActivity.class);
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, true);
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, data.getActivityId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_2, data.getProductId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_3, data.getOrderId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_4, data.getProductObjId());
                    startActivityWithAnim(intent);
                } else if ("TA003".equals(list.get(position).getOrderState())) {//试用报告已提交
                    Intent intent = new Intent(mContext, TrialReportActivity.class);
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, false);
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, data.getActivityId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_2, data.getProductId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_3, data.getOrderId());
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_4, data.getProductObjId());
                    startActivityWithAnim(intent);
                } else if ("TA005".equals(list.get(position).getOrderState())) {//试用报告提交超时
                    ToastUtil.showToast("填写试用报告已超时");
                } else if ("TP006".equals(list.get(position).getOrderState())) {//物流中
                    Intent intent = new Intent(mContext, LogisticsDetailsActivity.class);
                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, data.getOrderId());
                    startActivityWithAnim(intent);
                }
            }
        });
        trial_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum = 1;
                initDate(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum++;
                initDate(false);
            }
        });
    }


    private void initParams() {
        map.put("isHistory", "0");
        map.put("limit", "20");
        map.put("state", "");
    }


    //服务器获取数据
    private void initDate(boolean isRefresh) {
        map.put("pageNum", pageNum + "");
        ServiceManger.getInstance().appraiseList(map, new BaseRequestCallback<TrialItemResponse>() {
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
            public void onSuccess(TrialItemResponse data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    if (data.getRETURN_DATA() != null && data.getRETURN_DATA().getList() != null && data.getRETURN_DATA().getList().size() > 0) {
                        if (isRefresh) {
                            list.clear();
                        }
                        list.addAll(data.getRETURN_DATA().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        if (isRefresh) {
                            list.clear();
                        }
                        adapter.notifyDataSetChanged();
                    }
                    trial_list.onRefreshComplete();
                } else {
                    trial_list.onRefreshComplete();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
        }
    }

    @Override
    public void eventBus(String event) {
        super.eventBus(event);
        if (OleConstants.REFRESH_TRIAL_LIST.equals(event) || PayResultEnum.PAY_SUCCESS.equals(event)) {
            pageNum = 1;
            initDate(true);//重新获取数据
        }
    }


}
