package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.databinding.ActivityCouponHistoryBinding;
import com.crv.ole.personalcenter.fragment.CouponHistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史优惠卷
 */
public class CouponHistoryActivity extends BaseAppCompatActivity {
    private ActivityCouponHistoryBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = (ActivityCouponHistoryBinding) getViewDataBinding();
        initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_history;
    }

    private void initView() {
        mDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton01:
                        mDataBinding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.radioButton02:
                        mDataBinding.viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CouponHistoryFragment.getInstance("1"));
        fragments.add(CouponHistoryFragment.getInstance("2"));
        mDataBinding.viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        mDataBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDataBinding.radioGroup.check(position == 0 ? R.id.radioButton01 : R.id.radioButton02);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


}
