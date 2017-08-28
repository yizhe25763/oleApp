package com.crv.ole.personalcenter.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseFragment;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.databinding.FragmentCouponHistoryBinding;
import com.crv.ole.personalcenter.adapter.DiscountCouponListAdapter;
import com.crv.ole.personalcenter.model.CouponResponseData;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

/**
 * crate by lihongshi
 * 历史优惠卷
 */
public class CouponHistoryFragment extends BaseFragment {
    private FragmentCouponHistoryBinding mDataBinding;
    private int pageNum = 1;
    private final String cacheKey = "coupon_history_list_" + BaseApplication.getInstance().getUserId();
    private DiscountCouponListAdapter mAdapter;
    private String type = "1";

    public static CouponHistoryFragment getInstance(String params) {
        Bundle bundle = new Bundle();
        bundle.putString(OleConstants.BundleConstant.ARG_PARAMS_0, params);
        CouponHistoryFragment couponHistoryFragment = new CouponHistoryFragment();
        couponHistoryFragment.setArguments(bundle);
        return couponHistoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon_history, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = this.getArguments().getString(OleConstants.BundleConstant.ARG_PARAMS_0, "1");
        initView();
    }

    private void initView() {
        mDataBinding.pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum++;
                getData();
            }
        });

        mDataBinding.pullToRefreshRecyclerView.getRefreshableView().setLayoutManager(new LinearLayoutManager(mContext));
        List<CouponResponseData.VoucherList> lists = SerializableManager.readObjectForList(mContext, cacheKey);
        mAdapter = new DiscountCouponListAdapter(lists);
        mDataBinding.pullToRefreshRecyclerView.getRefreshableView().setAdapter(mAdapter);
    }

    private void getData() {
        ServiceManger.getInstance().voucherList(type, pageNum, 10, new BaseRequestCallback<CouponResponseData>() {
            @Override
            public void onSuccess(CouponResponseData data) {
                mDataBinding.pullToRefreshRecyclerView.onRefreshComplete();
                if (OleConstants.REQUES_SUCCESS.equalsIgnoreCase(data.getRETURN_CODE())
                        && data.getRETURN_DATA().getVoucherList() != null) {
                    if (pageNum == 1) {
                        SerializableManager.saveSerializable(mContext, data.getRETURN_DATA().getVoucherList(), cacheKey);
                        mAdapter.setAllItem(data.getRETURN_DATA().getVoucherList());
                    } else {
                        mAdapter.addAllItem(data.getRETURN_DATA().getVoucherList());
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                mDataBinding.pullToRefreshRecyclerView.onRefreshComplete();
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
