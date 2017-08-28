package com.crv.ole.shopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fairy on 2017/8/8.
 */

public class BlackFragment extends com.crv.sdk.fragment.BaseFragment {
    private Unbinder unbinder;
    private static BlackFragment blackFragment;

    public static BlackFragment getInstance(){
        if(blackFragment == null) {
            blackFragment = new BlackFragment();
        }
        return blackFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_pictxtdetail_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }
}
