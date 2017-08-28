package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.databinding.ActivityCouponBinding;
import com.crv.ole.personalcenter.adapter.DiscountCouponListAdapter;
import com.crv.ole.personalcenter.model.CouponResponseData;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

/**
 * 个人中心 - 优惠券页面
 *
 * @author lihongshi
 */
public class CouponActivity extends BaseAppCompatActivity {

    private ActivityCouponBinding mDataBinding;
    private DiscountCouponListAdapter mAdapter;
    private int pageNum = 1;
    private final String cacheKey = "coupon_list_" + BaseApplication.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = (ActivityCouponBinding) getViewDataBinding();
        initView();
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
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

        mDataBinding.discountCouponMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CouponHistoryActivity.class));
            }
        });

    }

    private void getData() {
        ServiceManger.getInstance().voucherList("0", pageNum, 10, new BaseRequestCallback<CouponResponseData>() {
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
