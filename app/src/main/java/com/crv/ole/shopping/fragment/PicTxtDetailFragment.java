package com.crv.ole.shopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.shopping.model.ProductDetailsInfoData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fairy on 2017/7/26.
 * 商品详情页 - 详情页 - 图文详情
 */

public class PicTxtDetailFragment extends BaseFragment {
    @BindView(R.id.brand_tv)
    TextView brandTv;
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.sellUnit_tv)
    TextView sellUnitTv;
    @BindView(R.id.shelfLife_tv)
    TextView shelfLifeTv;
    @BindView(R.id.spec_tv)
    TextView specTv;
    @BindView(R.id.temperatureControl_tv)
    TextView temControlTv;
    @BindView(R.id.mobileContent_layout)
    LinearLayout imgLayout;
    private Unbinder unbinder;
    private static PicTxtDetailFragment picTxtDetailFragment;
    private ProductDetailsInfoData.RETURNDATABean returnData;

    public static PicTxtDetailFragment getInstance(){
        if(picTxtDetailFragment == null) {
            picTxtDetailFragment = new PicTxtDetailFragment();
        }
        return picTxtDetailFragment;
    }


    /**
     * 设置页面参数显示
     * @param returnData
     */
    public void setReturnData(ProductDetailsInfoData.RETURNDATABean returnData){
        if(this.returnData == null) {
            this.returnData = returnData;
            updatePageInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_pictxtdetail_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    /**
     * 设置商品参数以及图片展示
     */
    private void updatePageInfo(){
        brandTv.setText(returnData.getCountry().getCName());
        placeTv.setText(returnData.getOriginPlace());
        sellUnitTv.setText(returnData.getSellUnit());
        shelfLifeTv.setText(returnData.getShelfLife());
        specTv.setText(returnData.getSpec());
        temControlTv.setText(returnData.getTemperatureControl().getDes());
        if(returnData.getMobileContent() != null && returnData.getMobileContent().size() > 0){
            for(ProductDetailsInfoData.RETURNDATABean.ImagesBean imagesBean : returnData.getMobileContent()){
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(lp);
                LoadImageUtil.getInstance().loadBackground(imageView, imagesBean.getUrl());
                imgLayout.addView(imageView);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        returnData = null;
        imgLayout.removeAllViews();
    }

}
