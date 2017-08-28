package com.crv.ole.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.pay.adapter.ChooseDiscountCouponListAdapter;
import com.crv.ole.pay.model.OrderConfirmAllCardsData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用优惠券页面
 *
 * @author wj_wsf
 */
public class ChooseDiscountCouponActivity extends BaseActivity {
    @BindView(R.id.choose_discount_coupon_list)
    ListView choose_discount_coupon_list;

    private ArrayList<OrderConfirmAllCardsData> dataList;
    private ChooseDiscountCouponListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_discount_coupon);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.choose_discount_coupon_title);

        dataList = getIntent().getParcelableArrayListExtra("discount_coupon_list");
        initView();
    }

    private void initView() {
        ListView discount_coupon_list = (ListView) this.findViewById(R.id.choose_discount_coupon_list);
        discount_coupon_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setResult(100,
                        new Intent().putExtra("discount_coupon_data", dataList.get(i)));
                finish();
            }
        });

        mAdapter = new ChooseDiscountCouponListAdapter(dataList);
        discount_coupon_list.setAdapter(mAdapter);

    }

}
