package com.crv.ole.home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;
import com.crv.sdk.fragment.BaseFragment;

/**
 * 商城分类频道
 */
public class MarketClassFragment extends BaseFragment {
    public static MarketClassFragment getInstance() {
        MarketClassFragment fragment = new MarketClassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_class_layout, container, false);
    }
}
