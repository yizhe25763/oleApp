package com.crv.ole.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * create by lihongshi 2017/08/07
 */
public class BaseFragment extends Fragment {
    public final String TAG = BaseFragment.class.getSimpleName();
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

}
