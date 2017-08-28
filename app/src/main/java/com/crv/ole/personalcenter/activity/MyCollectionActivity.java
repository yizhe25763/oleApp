package com.crv.ole.personalcenter.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.fragment.CollectionGoodsFragment;
import com.crv.ole.personalcenter.fragment.CollectionInformationFragment;
import com.crv.ole.personalcenter.tools.CollectionEvent;
import com.crv.ole.utils.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 - 我的收藏页面
 */

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.collection_tab1_txt)
    TextView collection_tab1_txt;
    @BindView(R.id.collection_tab2_txt)
    TextView collection_tab2_txt;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private CollectionInformationFragment informationFragment;
    private CollectionGoodsFragment goodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.myColl_title);

        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setBackgroundResource(R.color.transparent);
        title_iv_1.setText(R.string.collection_edit_txt);
        title_iv_1.setTextColor(Color.parseColor("#666666"));
        title_iv_1.setTextSize(15);

        informationFragment = CollectionInformationFragment.getInstance();
        goodsFragment = CollectionGoodsFragment.getInstance();

        mFragments.add(informationFragment);
        mFragments.add(goodsFragment);
        initChildViewPager();
        collection_tab1_txt.setSelected(true);
        mViewPager.setCurrentItem(0);

      //  throw new RuntimeException("我的收藏崩溃了………………");
    }

    @OnClick({R.id.collection_tab1_txt, R.id.collection_tab2_txt, R.id.title_iv_1})
    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_1:
                if (mViewPager.getCurrentItem() == 1) {
                    if (goodsFragment.getIsEdit())
                        title_iv_1.setText(R.string.collection_edit_txt);
                    else
                        title_iv_1.setText(R.string.finish);

                    goodsFragment.edit();
                } else {
                    if (informationFragment.getIsEdit())
                        title_iv_1.setText(R.string.collection_edit_txt);
                    else
                        title_iv_1.setText(R.string.finish);

                    informationFragment.edit();
                }
                break;
            case R.id.collection_tab1_txt:
                if (mViewPager.getCurrentItem() == 1) {
                    collection_tab1_txt.setSelected(true);
                    collection_tab2_txt.setSelected(false);
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.collection_tab2_txt:
                if (mViewPager.getCurrentItem() == 0) {
                    collection_tab1_txt.setSelected(false);
                    collection_tab2_txt.setSelected(true);
                    mViewPager.setCurrentItem(1);
                }
                break;
        }
    }

    /**
     * 初始化tab和pager的滑动点击事件
     */
    private void initChildViewPager() {
        mViewPager.setAdapter(new MyCollectionActivity.MyPagerAdapter(getSupportFragmentManager()));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    collection_tab1_txt.setSelected(true);
                    collection_tab2_txt.setSelected(false);

                    if (informationFragment.getIsEdit()) {
                        title_iv_1.setText(R.string.finish);
                    } else
                        title_iv_1.setText(R.string.collection_edit_txt);

                    informationFragment.showThisPage();
                } else {
                    collection_tab1_txt.setSelected(false);
                    collection_tab2_txt.setSelected(true);

                    if (goodsFragment.getIsEdit()) {
                        title_iv_1.setText(R.string.finish);
                    } else
                        title_iv_1.setText(R.string.collection_edit_txt);

                    goodsFragment.showThisPage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100) //在ui线程执行 优先级100
    public void onDataSynEvent(CollectionEvent event) {
        Log.d("event---->" + event);

        if (event == CollectionEvent.INFORMATION_CREATE_FOLDER ||
                event == CollectionEvent.INFORMATION_DELETE_FOLDER ||
                event == CollectionEvent.INFORMATION_UPDATE_FOLDER ||
                event == CollectionEvent.GOODS_UPDATE_FOLDER ||
                event == CollectionEvent.GOODS_UPDATE_FOLDER ||
                event == CollectionEvent.GOODS_UPDATE_FOLDER) {
            title_iv_1.setText(R.string.collection_edit_txt);
            if (mViewPager.getCurrentItem() == 1) {
                if (goodsFragment.getIsEdit())
                    goodsFragment.edit();
            } else {
                if (informationFragment.getIsEdit())
                    informationFragment.edit();
            }
        }
    }

}
