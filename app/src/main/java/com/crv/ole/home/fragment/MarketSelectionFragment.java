package com.crv.ole.home.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;
import com.crv.ole.utils.Log;
import com.crv.sdk.fragment.BaseFragment;

/**
 * 商城精选频道
 */
public class MarketSelectionFragment extends BaseFragment {
    public static MarketSelectionFragment getInstance() {
        MarketSelectionFragment fragment = new MarketSelectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_selection_layout, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            Log.i("MarketSelectionFragment显示");
//            isVisible = true;
        } else {
            Log.i("MarketSelectionFragment隐藏");
//            isVisible = false;
        }
    }
}
