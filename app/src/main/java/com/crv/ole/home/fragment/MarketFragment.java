package com.crv.ole.home.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.crv.ole.R;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.activity.MarketClassifyActivity;
import com.crv.ole.home.model.TabEntity;
import com.crv.ole.lbs.LocationUtil;
import com.crv.ole.shopping.activity.ShoppingCartListActivity;
import com.crv.ole.shopping.model.CartCountResponseData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * 商城主页tab
 */
public class MarketFragment extends BaseFragment {
    @BindView(R.id.fragment_market_tab_title_search_layout)
    RelativeLayout fragment_market_tab_title_search_layout;
    @BindView(R.id.title_name_tv)
    TextView title_name_tv;
    @BindView(R.id.fragment_market_tab_title_layout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.gwc_count_iv)
    ImageView gwc_count_iv;

    Unbinder unbinder;
    private int[] mTitles = {R.string.home_market_tab_1, R.string.home_market_tab_2};
    private int[] mIconUnselectIds = {R.drawable.ic_home_normal, R.drawable.ic_shoppingcart_normal};
    private int[] mIconSelectIds = {R.drawable.ic_home_pressed, R.drawable.ic_shoppingcart_pressed};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private LocationUtil locationUtil;
    private MarketTrialFragment marketTrialFragment;
    private MarketHWFragment marketHWFragment;

    public static MarketFragment getInstance() {
        MarketFragment fragment = new MarketFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(getString(mTitles[i]), mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_market_layout, container, false);
            unbinder = ButterKnife.bind(this, view);

            title_name_tv.setText(R.string.tab_market_page);
            fragment_market_tab_title_search_layout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(mContext, MarketClassifyActivity.class));
                        }
                    }
            );

            view.findViewById(R.id.fragment_market_tab_title_cart_btn).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), ShoppingCartListActivity.class);
                            startActivity(intent);
                        }
                    }
            );

            marketTrialFragment = MarketTrialFragment.getInstance();
            marketHWFragment = MarketHWFragment.getInstance();
            mFragments.add(marketTrialFragment);
            mFragments.add(marketHWFragment);

            initChildViewPager();

            commonTabLayout.setCurrentTab(0);
            mViewPager.setCurrentItem(0);
        }

        locationUtil = new LocationUtil(mContext);
        new RxPermissions(activity).request(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            locationUtil.startLBS(new BDLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation bdLocation) {
                                    if (locationUtil.getLocationClient() != null) {
                                        locationUtil.stopLBS();//end定位
                                    }

                                    Log.i("定位到的城市：" + bdLocation.getCity());
                                    Log.i("定位到的城市编码：" + bdLocation.getCityCode());
                                    Log.i("定位到的地址：" + bdLocation.getAddrStr());
                                    Log.i("定位到的纬度：" + bdLocation.getLatitude());
                                    Log.i("定位到的经度：" + bdLocation.getLongitude());
                                    Log.i("定位到位置语义化结果：" + bdLocation.getLocationDescribe());
                                    Log.i("定位返回错误码：" + bdLocation.getLocType());
                                    Log.i("定位返回错误码2：" + bdLocation.getLocTypeDescription());
                                }

                                @Override
                                public void onConnectHotSpotMessage(String s, int i) {
                                    Log.e("用户没有给权限！！！");
                                }
                            });
                        } else {
                            // At least one permission is denied
                        }
                    }

                });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCartCount();
    }

    private void initChildViewPager() {
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));

        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取购物车数量
     */
    private void getCartCount() {
        ServiceManger.getInstance().getCartCount("", "", new BaseRequestCallback<CartCountResponseData>() {
            @Override
            public void onSuccess(CartCountResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    int count = com.crv.sdk.utils.StringUtils.stringToInt(data.getRETURN_DATA().getCount());
                    gwc_count_iv.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
                }
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
            return getString(mTitles[position]);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
