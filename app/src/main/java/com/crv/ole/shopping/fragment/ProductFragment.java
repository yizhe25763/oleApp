package com.crv.ole.shopping.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.shopping.activity.LookPicActivity;
import com.crv.ole.shopping.image.WheelView;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.shopping.model.ProductDetailsInfoData;
import com.crv.ole.shopping.ui.VerticalScrollView;
import com.crv.sdk.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品详情页 - 商品图 - 1
 */

public class ProductFragment extends BaseFragment {
    @BindView(R.id.scrollView)
    VerticalScrollView scrollView;
    @BindView(R.id.product_images)
    WheelView mWheelAd;
    private Unbinder unbinder;
    @BindView(R.id.product_nameTv)
    TextView nameTv;                //  商品名称
    @BindView(R.id.product_priceTv)
    TextView priceTv;               //  商品正价
    @BindView(R.id.product_presellMsgTv)
    TextView presellMsgTv;          //  预售活动提醒
    @BindView(R.id.product_myPrice_layout)
    RelativeLayout myPriceLayout;   //  我的会员价格
    @BindView(R.id.product_myPriceTv)
    TextView myPriceTv;             //  我的会员价
    @BindView(R.id.product_myPriceTagIv)
    ImageView myPriceTagIv;         //  我的会员标签
    @BindView(R.id.product_primePriceTv)
    TextView primePriceTv;          //  原价
    @BindView(R.id.product_v1PriceTv)
    TextView v1PriceTv;             //  v1价格
    @BindView(R.id.product_v2PriceTv)
    TextView v2PriceTv;             //  v2价格
    @BindView(R.id.product_v3PriceTv)
    TextView v3PriceTv;             //  v3价格
    @BindView(R.id.product_v1Price_lineTv)
    View v1LineIv;             //  v1虚线
    @BindView(R.id.product_v2Price_lineTv)
    View v2LineIv;             //  v2虚线
    @BindView(R.id.product_v3Price_lineTv)
    View v3LineIv;             //  v3虚线


    private static ProductDetailsInfoData.RETURNDATABean dataBean;
    private static ArrayList<PhotoInfo> imgsList = new ArrayList<PhotoInfo>();
    private double price;


    public static ProductFragment getInstance(){
        return new ProductFragment();
    }
    public void setDataBean(ProductDetailsInfoData.RETURNDATABean returndataBean){
        dataBean = returndataBean;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_product_layout, container, false);
            unbinder = ButterKnife.bind(this, view);

            //设置删除线
//            oldTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if(dataBean != null){
                initData();
                initView();
                initWheel();
            }
        }
        return view;
    }

    private void initData(){
        nameTv.setText(dataBean.getName());
        if(dataBean.getSkuPrice().isHasSpecialPrice()){
            priceTv.setText("￥"+Double.parseDouble(dataBean.getSkuPrice().getSpecialPirce().getUnitPrice()));
        }

    }


    private void initView(){
        boolean ifpresellShow = false;
        String level = "v";

        if(ifpresellShow){  //  设置预售活动提醒
            presellMsgTv.setVisibility(View.VISIBLE);
        }
        if(level.equals("v")){
            view.findViewById(R.id.product_vipPrice_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.product_v3_98Iv).setVisibility(View.GONE);
            v1LineIv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            v2LineIv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            v3LineIv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }else{
            if(!TextUtils.isEmpty(level)){
                view.findViewById(R.id.product_myPrice_layout).setVisibility(View.VISIBLE);
                if (level.equals("v1")) {
                    view.findViewById(R.id.product_myPriceTagIv).setBackgroundResource(R.drawable.ic_v1);
                } else if (level.equals("v2")) {
                    view.findViewById(R.id.product_myPriceTagIv).setBackgroundResource(R.drawable.ic_v2);
                } else if (level.equals("v3")) {
                    view.findViewById(R.id.product_myPriceTagIv).setBackgroundResource(R.drawable.ic_v3);
                }
            }
        }

    }

    /**
     * 初始化轮播图片的操作
     */
    private void initWheel(){
        if(dataBean.getImages() != null && dataBean.getImages().size() > 0){
            if(mWheelAd != null) {
                mWheelAd.stop();
                mWheelAd.clear();
            }
            for (ProductDetailsInfoData.RETURNDATABean.ImagesBean imgBean : dataBean.getImages()){
                String imgUrl = imgBean.getUrl();
                mWheelAd.addWheel(new WheelView.WheelInfo(imgUrl));
                PhotoInfo info = new PhotoInfo();
                info.setSourcePath(imgUrl);
                imgsList.add(info);
            }
            mWheelAd.start();
        }
        if(mWheelAd != null) {
            RelativeLayout.LayoutParams mWheelAdLP = (RelativeLayout.LayoutParams) mWheelAd.getLayoutParams();
            mWheelAdLP.height = BaseApplication.getDeviceWidth();
            mWheelAd.setLayoutParams(mWheelAdLP);
            mWheelAd.setOnWheelClickListener(new WheelView.OnWheelClickListener() {
                @Override
                public void onWheelClick(int position, WheelView.WheelInfo info) {
                    // 图片点击后的事件
                    Intent intent = new Intent(mContext, LookPicActivity.class);
                    intent.putExtra("EXTRA_IMAGE_LIST", imgsList);
                    intent.putExtra("EXTRA_CURRENT_IMG_POSITION", position);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }
    }


    public void goTop() {
        scrollView.goTop();
    }



}
