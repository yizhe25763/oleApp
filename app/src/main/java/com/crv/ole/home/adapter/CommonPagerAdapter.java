package com.crv.ole.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 公共的滑动切换页面适配器
 */

public class CommonPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private int[] mTitles;
    private List<Fragment> mFragments;

    public CommonPagerAdapter(Context context, FragmentManager fm, int[] mTitles, List<Fragment> mFragments) {
        super(fm);
        this.context = context;
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(mTitles[position]);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
