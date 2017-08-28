package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.databinding.ActivityPointBinding;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.adapter.PointListAdapter;
import com.crv.ole.personalcenter.model.PointInfoResultBean;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心 - 积分
 * Created by lihongshi on 2017/7/12.
 */
public class PointActivity extends BaseAppCompatActivity {
    private TextView mNumTv;
    private PointListAdapter mAdapter;
    private int pageIndex = 1;
    private UserCenterData.UserCenter userCenter;
    private ActivityPointBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = (ActivityPointBinding) getViewDataBinding();
        userCenter = BaseApplication.getInstance().getUserCenter();
        initRecyclerView();
        queryIntegInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_point;
    }

    private void initRecyclerView() {
        dataBinding.pointRecyclerView.setHasPullUpFriction(false); // 设置没有上拉阻力
        RecyclerView recyclerView = dataBinding.pointRecyclerView.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PointListAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        setHeader(recyclerView);

        dataBinding.pointRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                pageIndex = 0;
                queryIntegInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                pageIndex++;
                queryIntegInfo();
            }
        });

    }

    private void setHeader(RecyclerView view) {
        View headerView = LayoutInflater.from(this).inflate(R.layout.point_header, view, false);
        mNumTv = (TextView) headerView.findViewById(R.id.point_numTv);
        mNumTv.setText(userCenter == null ? ""
                : String.valueOf(userCenter.getTotalIntegralValue()));
        mAdapter.setHeaderView(headerView);
    }

    /**
     * 获取积分详情
     */
    private void queryIntegInfo() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("memberid", userCenter == null ? "" : userCenter.getMemberid());
        String enddate = DateUtil.getNow(DateUtil.FORMAT_SHORT);
        requestMap.put("begindate", DateUtil.reduceDate(enddate, 90));
        requestMap.put("enddate", enddate);
        requestMap.put("pageindex", pageIndex + "");
        requestMap.put("pagesize", "10");
        ServerApi.postRequest(this, OleConstants.QUERY_INTEGRAINFO + "0", OleConstants.QUERY_INTEGRAINFO, requestMap, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                updateRecyView(response);
            }

            @Override
            public void onCacheSuccess(Response<String> response) {
                Type type = new TypeToken<PointInfoResultBean>() {
                }.getType();
                PointInfoResultBean data = new Gson().fromJson(response.body(), type);
                if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)
                        && data.getRETURN_DATA() != null
                        && data.getRETURN_DATA().getItems() != null) {
                    mAdapter.setAllItem(data.getRETURN_DATA().getItems());
                }
            }
        });
    }

    private void updateRecyView(Response<String> response) {
        Type type = new TypeToken<PointInfoResultBean>() {
        }.getType();
        PointInfoResultBean data = new Gson().fromJson(response.body(), type);
        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS) && data.getRETURN_DATA() != null) {
            if (pageIndex == 0) {
                mAdapter.clearAllItem();
            } else {
                mAdapter.addAllItem(data.getRETURN_DATA().getItems());
            }
        }
        dataBinding.pointRecyclerView.onRefreshComplete();
    }

}
