package com.crv.ole.personalcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.crv.ole.R;
import com.crv.ole.base.BaseBottomSheetDialogFragment;
import com.crv.ole.home.model.RegionsBean;
import com.crv.ole.home.model.RegionsModel;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lihongshi 2017/08/09
 * 省市区选择底部对话框
 */
public class AfterBottomDialogFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {
    private String id;
    private OnConfirmClickListener mOnConfirmListener;
    private WheelPicker mFirstWheelView;
    List<String> provinces = Arrays.asList("商品质量问题", "商品错发/漏发", "退运费", "收到商品不符", "发票问题", "七天无理由退货", "未按预约时间发货");
    List<String> provincess = Arrays.asList("商品品质问题");

    public interface OnConfirmClickListener {
        void onClick(String province);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.after_order_bottom_dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = this.getArguments().getString(OleConstants.BundleConstant.ARG_PARAMS_0, "");
        initView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConfirmClickListener) {
            mOnConfirmListener = (OnConfirmClickListener) context;
        }
    }

    private static AfterBottomDialogFragment newInstance(String param1) {
        AfterBottomDialogFragment fragment = new AfterBottomDialogFragment();
        Bundle args = new Bundle();
        args.putString(OleConstants.BundleConstant.ARG_PARAMS_0, param1);
        fragment.setArguments(args);
        return fragment;
    }

    //防止重复弹出
    public static AfterBottomDialogFragment showDialog(FragmentActivity activity, String params, OnConfirmClickListener onConfirmClickListener) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        AfterBottomDialogFragment bottomDialogFragment = (AfterBottomDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (null == bottomDialogFragment) {
            bottomDialogFragment = AfterBottomDialogFragment.newInstance(params);
        }
        if (!activity.isFinishing() && null != bottomDialogFragment && !bottomDialogFragment.isAdded()) {
            fragmentManager.beginTransaction().add(bottomDialogFragment, TAG).commitAllowingStateLoss();
        }
        bottomDialogFragment.setOnConfirmListener(onConfirmClickListener);
        return bottomDialogFragment;
    }

    public void setOnConfirmListener(OnConfirmClickListener listener) {
        this.mOnConfirmListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnConfirmListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_ok: {
                callBack();
                dismiss();
                break;
            }
            case R.id.txt_cancel: {
                dismiss();
                break;
            }
        }
    }

    private void callBack() {
        if (mOnConfirmListener == null) {
            return;
        }
        StringBuilder areaBuilder = new StringBuilder();
        StringBuilder codeBuilder = new StringBuilder();
        mOnConfirmListener.onClick(provinces.get(mFirstWheelView.getCurrentItemPosition()));
    }

    private void initView(View view) {
        TextView mTxtOK = (TextView) view.findViewById(R.id.txt_ok);
        TextView mTxtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        mTxtOK.setOnClickListener(this);
        mTxtCancel.setOnClickListener(this);
        mFirstWheelView = (WheelPicker) view.findViewById(R.id.wheelPicker01);
        mFirstWheelView.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
            }
        });
        fillFirstData();
        //默认选中
        if (TextUtil.isEmpty(id)) {
            defSelect();
        }
    }

    /**
     * 默认选中
     */
    private void defSelect() {
        Map<String, RegionsBean> map = RegionsModel.getProviceAndCityAndDistrictbyId(id);
        RegionsBean province = map.get("province");
        RegionsBean city = map.get("city");
        RegionsBean district = map.get("district");
        if (province != null) {
            List<String> list = mFirstWheelView.getData();
            int pos = list.indexOf(province.getName());
            if (pos != -1) {
                mFirstWheelView.setSelectedItemPosition(pos);
            }
        }
    }


    private void fillFirstData() {
        mFirstWheelView.setData(provinces);
    }
}
