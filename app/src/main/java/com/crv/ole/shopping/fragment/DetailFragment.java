package com.crv.ole.shopping.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;
import com.crv.ole.shopping.callback.PageChangeInter;
import com.crv.sdk.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 商品详情页 - 详情页 - 2
 */

public class DetailFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private Unbinder unbinder;
    private List<Fragment> mFragments = new ArrayList<>();
    private PageChangeInter listener;
    private boolean ifShowReport;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_detail_layout, container, false);
            unbinder = ButterKnife.bind(this, view);

            initView();
            initListener();
        }
        return view;
    }

    private void initView(){
        //  初始化viewpager
        mFragments.add(PicTxtDetailFragment.getInstance());
        mFragments.add(PicTxtDetailFragment.getInstance());
        if(ifShowReport) {
            mFragments.add(TrialReportFragment.getInstance());
        }
        mFragments.add(EvaluateFragment.getInstance());
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments == null ? null : mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 :  mFragments.size();
            }
        });
        mViewPager.setCurrentItem(1);
    }

    private void initListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                //  告诉主页面去切换tab
                if(listener != null){
                    listener.updateTitle(position);
                }
                //  如果当前页是0，则变为1
                if(position == 0){
                    mViewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    /**
     * 设置当前的page页面
     * @param currentPage
     */
    public void setCurrentPage(int currentPage){
        if(currentPage > 0 && currentPage < mFragments.size()) {
            mViewPager.setCurrentItem(currentPage);
        }
    }

    /**
     * 是否显示试用报告页面
     * @param ifShowReport
     */
    public void ifShowReport(Boolean ifShowReport){
        this.ifShowReport = ifShowReport;
    }

    public void setPageChangeListener(PageChangeInter listener){
        this.listener = listener;
    }


    public void goTop() {
//        listview.goTop();
    }


    public interface BottomListener{
        void loadData();
    }
}
