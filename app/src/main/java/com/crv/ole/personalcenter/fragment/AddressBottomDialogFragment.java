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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lihongshi 2017/08/09
 * 省市区选择底部对话框
 */
public class AddressBottomDialogFragment extends BaseBottomSheetDialogFragment implements View.OnClickListener {
    private String id;
    private OnConfirmClickListener mOnConfirmListener;
    private WheelPicker mFirstWheelView, mSecondWheelView, mThirdWheelView;
    private List<RegionsBean> provinces;
    private List<RegionsBean> cities;
    private List<RegionsBean> districtes;

    public interface OnConfirmClickListener {
        void onClick(Map<String, String> result);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_address_bottom_dialog;
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

    private static AddressBottomDialogFragment newInstance(String param1) {
        AddressBottomDialogFragment fragment = new AddressBottomDialogFragment();
        Bundle args = new Bundle();
        args.putString(OleConstants.BundleConstant.ARG_PARAMS_0, param1);
        fragment.setArguments(args);
        return fragment;
    }

    //防止重复弹出
    public static AddressBottomDialogFragment showDialog(FragmentActivity activity, String params, OnConfirmClickListener onConfirmClickListener) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        AddressBottomDialogFragment bottomDialogFragment = (AddressBottomDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (null == bottomDialogFragment) {
            bottomDialogFragment = AddressBottomDialogFragment.newInstance(params);
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
        RegionsBean province = provinces.get(mFirstWheelView.getCurrentItemPosition());
        areaBuilder.append(province.getName());
        codeBuilder.append(province.getId());
        if (mSecondWheelView.getVisibility() != View.INVISIBLE) {
            RegionsBean city = cities.get(mSecondWheelView.getCurrentItemPosition());
            areaBuilder.append(city.getName());
            codeBuilder.append("/")
                    .append(city.getId());
        }
        if (mThirdWheelView.getVisibility() != View.INVISIBLE) {
            RegionsBean distri = districtes.get(mThirdWheelView.getCurrentItemPosition());
            areaBuilder.append(distri.getName());
            codeBuilder.append("/")
                    .append(distri.getId());
        }
        Map<String, String> map = new HashMap<>();
        map.put("area", areaBuilder.toString());
        map.put("code", codeBuilder.toString());
        mOnConfirmListener.onClick(map);
    }

    private void initView(View view) {
        TextView mTxtOK = (TextView) view.findViewById(R.id.txt_ok);
        TextView mTxtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        mTxtOK.setOnClickListener(this);
        mTxtCancel.setOnClickListener(this);
        mFirstWheelView = (WheelPicker) view.findViewById(R.id.wheelPicker01);
        mSecondWheelView = (WheelPicker) view.findViewById(R.id.wheelPicker02);
        mThirdWheelView = (WheelPicker) view.findViewById(R.id.wheelPicker03);
        mFirstWheelView.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                Log.e(TAG, "proinceName:" + provinces.get(i).getName());
                fillSecondData(i);
                fillNpThird(mSecondWheelView.getSelectedItemPosition());
            }
        });
        mSecondWheelView.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                fillNpThird(i);
            }
        });

        fillFirstData();
        fillSecondData(mFirstWheelView.getCurrentItemPosition());
        fillNpThird(mSecondWheelView.getCurrentItemPosition());
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
                fillSecondData(pos);
            }
        }
        if (city != null) {
            List<String> list = mSecondWheelView.getData();
            int pos = list.indexOf(city.getName());
            if (pos != -1) {
                mSecondWheelView.setSelectedItemPosition(pos);
                fillNpThird(pos);
            }
        }
        if (district != null) {
            List<String> list = mThirdWheelView.getData();
            int pos = list.indexOf(district.getName());
            if (pos != -1) {
                mThirdWheelView.setSelectedItemPosition(pos);
            }
        }
    }

    private List<String> getDisplayedValues(List<RegionsBean> list) {
        List<String> strings = new ArrayList<>();
        for (RegionsBean regionsBean : list) {
            strings.add(regionsBean.getName());
        }
        return strings;
    }

    private void fillFirstData() {
        provinces = RegionsModel.getAllProvince();
        mFirstWheelView.setData(getDisplayedValues(provinces));
    }

    private void fillSecondData(int index) {
        if (index < provinces.size()) {
            String provinceId = provinces.get(index).getId();
            cities = RegionsModel.getCities(provinceId);
            Log.e(TAG, "fillSecondData:" + cities.size());
            if (cities.isEmpty()) {
                mSecondWheelView.setVisibility(View.INVISIBLE);
                mThirdWheelView.setVisibility(View.INVISIBLE);
            } else {
                mSecondWheelView.setVisibility(View.VISIBLE);
                mSecondWheelView.setData(getDisplayedValues(cities));
            }
        }
    }

    private void fillNpThird(int index) {
        if (cities.isEmpty()) {
            mThirdWheelView.setVisibility(View.INVISIBLE);
            return;
        }
        RegionsBean city = cities.get(index);
        districtes = RegionsModel.getDistrictes(city.getId());
        if (districtes.isEmpty()) {
            mThirdWheelView.setVisibility(View.INVISIBLE);
        } else {
            mThirdWheelView.setVisibility(View.VISIBLE);
            mThirdWheelView.setData(getDisplayedValues(districtes));
        }
    }

    private List<RegionsBean> options1Items = new ArrayList<>();
    private List<ArrayList<RegionsBean>> options2Items = new ArrayList<>();
    private List<ArrayList<ArrayList<RegionsBean>>> options3Items = new ArrayList<>();

    private void initRegionData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 写子线程中的操作,解析省市区数据
                options1Items = RegionsModel.getAllProvince();

                for (int i = 0; i < options1Items.size(); i++) { //遍历省份
                    ArrayList<RegionsBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<RegionsBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

                    RegionsBean provice = options1Items.get(i);

                    List<RegionsBean> cities = RegionsModel.getCities(provice.getId());
                    for (int c = 0; c < cities.size(); c++) { //遍历该省份的所有城市
                        RegionsBean city = cities.get(c);
                        CityList.add(city); //添加城市

                        ArrayList<RegionsBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                        List<RegionsBean> districtes = RegionsModel.getDistrictes(city.getId());
                        for (int d = 0; d < districtes.size(); d++) { //该城市对应地区所有数据
                            RegionsBean districte = districtes.get(d);
                            City_AreaList.add(districte);//添加该城市所有地区数据
                        }
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                    }


                    /**
                     * 添加城市数据
                     */
                    options2Items.add(CityList);

                    /**
                     * 添加地区数据
                     */
                    options3Items.add(Province_AreaList);
                }


            }
        });
        thread.start();
    }


}
