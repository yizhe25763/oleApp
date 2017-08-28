package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crv.ole.personalcenter.fragment.MyOrderFragment;

/**
 * 我的订单下方碎片适配器
 */

public class MyOrderPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private int[] mTitles;

    public MyOrderPagerAdapter(Context context, FragmentManager fm, int[] mTitles) {
        super(fm);
        this.context = context;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(mTitles[position]);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        MyOrderFragment fragment = MyOrderFragment.getInstance(bundle);
        return fragment;
    }

}
