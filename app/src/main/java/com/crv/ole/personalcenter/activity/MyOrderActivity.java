package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.adapter.MyOrderPagerAdapter;
import com.crv.ole.personalcenter.ui.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - 我的订单
 */

public class MyOrderActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.myOrder_pagerSlideTab)
    PagerSlidingTabStrip myOrder_pagerSlideTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private int[] mTitles = {R.string.myOrder_allOrder, R.string.myOrder_forPay, R.string.myOrder_forSend,
            R.string.myOrder_forGain, R.string.myOrder_forEval/*, R.string.myOrder_service*/};

    private int postion = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.myOrder_title);
        postion = getIntent().getIntExtra("position", -1);
        initView();
        initListener();
    }

    private void initView() {
        myOrder_pagerSlideTab.setTextColor(getResources().getColor(R.color.white));// 未选中状态下的字体颜色
        myOrder_pagerSlideTab.setTabBackground(R.color.transparent);// 点击时候的背景颜色
        myOrder_pagerSlideTab.setSelectedTextColorResource(R.color.c_222222);// 选中状态下的字体颜色
        myOrder_pagerSlideTab.setScanScroll(true);// 是否可以滚动
        myOrder_pagerSlideTab.setOnPageChangeListener(this);// 滚动监听
    }

    private void initListener() {
        mViewPager.setAdapter(new MyOrderPagerAdapter(mContext, getSupportFragmentManager(), mTitles));
        mViewPager.setOffscreenPageLimit(6);
        myOrder_pagerSlideTab.setViewPager(mViewPager); // 绑定适配器

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                myOrder_pagerSlideTab.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (postion != -1) {
            mViewPager.setCurrentItem(postion);
        }
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
