package com.crv.ole.shopping.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.TabEntity;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.activity.VipCardActivity;
import com.crv.ole.personalcenter.model.CollectionResultData;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.personalcenter.ui.ObservableScrollView;
import com.crv.ole.shopping.fragment.BlackFragment;
import com.crv.ole.shopping.fragment.BuyNowDialogFragment;
import com.crv.ole.shopping.fragment.EvaluateFragment;
import com.crv.ole.shopping.fragment.PicTxtDetailFragment;
import com.crv.ole.shopping.fragment.TrialReportFragment;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.shopping.model.ProductDetailsInfoData;
import com.crv.ole.shopping.model.ProductSaleInfoData;
import com.crv.ole.shopping.ui.ProductViewPager;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.crv.sdk.utils.CountDownTimerUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 */

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.title_content_layout)
    LinearLayout titleLayout;
    @BindView(R.id.title_content_tabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.viewPager)
    ProductViewPager mViewPager;
    @BindView(R.id.product_convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.product_nameTv)
    TextView nameTv;                //  商品名称
    @BindView(R.id.product_brandIv)
    ImageView brandTv;
    @BindView(R.id.product_priceTv)
    TextView priceTv;               //  商品正价
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
    View v1LineIv;                  //  v1虚线
    @BindView(R.id.product_v2Price_lineTv)
    View v2LineIv;                  //  v2虚线
    @BindView(R.id.product_v3Price_lineTv)
    View v3LineIv;                  //  v3虚线
    @BindView(R.id.product_preSaleMsgTv)
    TextView preSaleMsg;                        //  促销活动 - 活动预告
    @BindView(R.id.product_saleRemindTv)
    TextView preSaleRemindTv;                   //  促销活动 - 活动预告 - 提醒我
    @BindView(R.id.product_saleMsgTv)
    TextView saleMsg;                           //  促销活动 - 活动信息
    @BindView(R.id.setReminderTv)
    TextView setReminderTv;                     //  到货提醒文字

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles = new ArrayList<String>();
    private List<Integer> mIcons = new ArrayList<Integer>();
    private List<Fragment> mFragments = new ArrayList<>();
    private int currPage = 1, prePage = 1;      //  用户区分目前是上页面还是下页面

    private float price;
    private long currTime;

    private String productId = "";
    private ProductDetailsInfoData.RETURNDATABean returnData;
    private ProductSaleInfoData.RETURNDATABean saleData;
    private boolean ifLiked = false;            //  是否收藏
    private boolean ifShowReport;               //  是否有试用报告
    private boolean ifSetSaleReminder = false;  //  是否设置促销提醒
    private boolean ifSetStockReminder = false; //  是否设置到货提醒
    private static ArrayList<PhotoInfo> imgsList = new ArrayList<PhotoInfo>();  //  轮播图片信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("productId")) {
            productId = getIntent().getStringExtra("productId");
        }
        if (TextUtils.isEmpty(productId)) {
            finish();
            return;
        }
        initData();
        initListener();
        getProductDetails();
    }

    private void initData() {
        mTitles.clear();
        mIcons.clear();
        mFragments.clear();
        //  初始化顶部的tab切换条数据
        mTitles.add(getResources().getString(R.string.product_detail_tab_1));
        mTitles.add(getResources().getString(R.string.product_detail_tab_2));
        mIcons.add(R.drawable.ic_my_normal);
        mIcons.add(R.drawable.ic_my_normal);
        mTitles.add(getResources().getString(R.string.product_detail_tab_3));
        mIcons.add(R.drawable.ic_my_normal);
        mTitles.add(getResources().getString(R.string.product_detail_tab_4));
        mIcons.add(R.drawable.ic_my_normal);
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIcons.get(i), mIcons.get(i)));
        }

        //  初始化viewpager数据
        mFragments.add(BlackFragment.getInstance());
        mFragments.add(PicTxtDetailFragment.getInstance());
        mFragments.add(TrialReportFragment.getInstance());
        mFragments.add(EvaluateFragment.getInstance());
    }

    /**
     * 设置试用报告的显隐 默认显示
     * @param type
     */
    private void initView(int type){
        //  判断是否有试用报告
        if(type == 1){  // 获取商品详情成功后
            if(returnData != null && !TextUtils.isEmpty(String.valueOf(returnData.isHasTrialReports()))) {
                ifShowReport = returnData.isHasTrialReports();
            }else {
                ifShowReport = false;
            }
        }else{
            ifShowReport = false;
        }
        if(!ifShowReport){
            mTabEntities.remove(2);
            mFragments.remove(2);
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setCurrentTab(1);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments == null ? null : mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }
        });
        if(!ifShowReport){
            mViewPager.setOffscreenPageLimit(3);
        }else {
            mViewPager.setOffscreenPageLimit(4);
        }
        mViewPager.setCurrentItem(1);
    }

    private void initListener() {
        //  获得标题的切换动画
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currValue = (int) animation.getAnimatedValue();
                titleLayout.layout(titleLayout.getLeft(), currValue,
                        titleLayout.getLeft() + titleLayout.getWidth(),
                        currValue + titleLayout.getHeight());
                prePage = currPage;
            }
        };

        if (scrollView != null) {
            scrollView.setListener(new VipCardActivity.ScrollChangedListener() {
                @Override
                public void scrollChanged(int a, int b) {
                    int location[] = new int[2];
                    mViewPager.getLocationOnScreen(location);
                    int titleHeight = titleLayout.getHeight();
                    int start = -titleHeight / 2;
                    int end = 0;
                    if (location[1] > titleHeight) {
                        currPage = 1;
                        start = -titleHeight / 2;
                        end = 0;
                    } else {
                        currPage = 2;
                        start = 0;
                        end = -titleHeight / 2;
                    }
                    if (currPage != prePage) {
                        ValueAnimator animator = ValueAnimator.ofInt(start, end);
                        animator.setDuration(200);
                        animator.addUpdateListener(animatorUpdateListener);
                        animator.start();
                    }
                }
            });
        }

        //  点击tab时，控制viewPager的index（0-显示上页面 其他-下页面）
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    goTop();
                    commonTabLayout.setCurrentTab(1);
                } else {
                    mViewPager.setCurrentItem(position);
                    loadData(position);
                }
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
                if (position == 0) {
                    goTop();
                    commonTabLayout.setCurrentTab(1);
                    mViewPager.setCurrentItem(1);
                } else {
                    commonTabLayout.setCurrentTab(position);
                    loadData(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    /**
     * 获取信息成功后更新页面
     */
    private void updatePageInfo() {
        //  初始化轮播图片的操作
        if (returnData.getImages() != null && returnData.getImages().size() > 0) {
            imgsList.clear();
            for (ProductDetailsInfoData.RETURNDATABean.ImagesBean imgBean : returnData.getImages()) {
                String imgUrl = imgBean.getUrl();
                PhotoInfo info = new PhotoInfo();
                info.setSourcePath(imgUrl);
                imgsList.add(info);
            }
            //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
            mConvenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, imgsList)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.ic_sylb_normal, R.drawable.ic_sylb_pressed})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            //设置翻页的效果，不需要翻页效果可用不设
            //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
            mConvenientBanner.setManualPageable(true);//设置不能手动影响  默认是手指触摸 轮播图不能翻页
            if(imgsList.size() > 1) {
                mConvenientBanner.setCanLoop(true);  //默认true,设置轮播图是否轮播
            }else{
                mConvenientBanner.setCanLoop(false);
            }
            mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    Intent intent = new Intent(mContext, LookPicActivity.class);
                    intent.putExtra("EXTRA_IMAGE_LIST", imgsList);
                    intent.putExtra("EXTRA_CURRENT_IMG_POSITION", i);
                    mContext.startActivity(intent);
                }
            });
            goTop();
        }

        initView(1);

        //  判断是否缺货
        if (returnData.getSellableAmount() < 1) {
            findViewById(R.id.buyNow_tv).setVisibility(View.GONE);
            findViewById(R.id.addCar_tv).setVisibility(View.GONE);
            setReminderTv.setVisibility(View.VISIBLE);
            if (ifSetStockReminder) {
                setReminderTv.setText("已设置到货提醒");
            } else {
                setReminderTv.setText("到货提醒");
            }
        }

        //  设置是否收藏
        if (returnData.getKeepInfos() != null) {
            ifLiked = returnData.getKeepInfos().isIsKeep();
        }
        if (ifLiked) {
            findViewById(R.id.addLikes_Iv).setBackgroundResource(R.drawable.btn_dblsc_ysc);
        } else {
            findViewById(R.id.addLikes_Iv).setBackgroundResource(R.drawable.btn_spsc_wsc);
        }

        //  设置价格显示
        nameTv.setText(returnData.getName());
        if(!TextUtils.isEmpty(returnData.getCountry().getImgUrl())) {
            LoadImageUtil.getInstance().loadBackground(brandTv, returnData.getCountry().getImgUrl());
        }
        if(returnData.getSkuPrice().isHasSpecialPrice()){
            setPrice(1);
        }else {
            setPrice(0);
        }

        //  加载下一屏内容
        loadData(1);

        //  从其他页面的立即购买跳转
        if(getIntent().hasExtra("state") &&
                TextUtils.equals("buyNow", getIntent().getStringExtra("state"))){
            BuyNowDialogFragment.showDialog(this, price, returnData.getSellableAmount(),
                    productId, returnData.getSkuPrice().getCurPrice().getSkuId(), 0);
        }

    }

    /**
     * 设置页面价格
     * @param type 0-普通设置 1-促销设置 2-普通转促销 3-促销转普通
     */
    private void setPrice(int type){
        ProductDetailsInfoData.RETURNDATABean.SkuPriceBean sku = returnData.getSkuPrice();
        if(type == 0){      //  普通价格设置
            price = Float.parseFloat(sku.getCurPrice().getUnitPrice());
            priceTv.setText("￥" + price);
            if(!TextUtils.isEmpty(sku.getSpecialMsg())){
                for(ProductDetailsInfoData.RETURNDATABean.SkuPriceBean.MembersPricesBean pricesBean :
                        sku.getMembersPrices()){
                    switch (pricesBean.getMemberLv()) {
                        case 1:
                            findViewById(R.id.product_v1PriceLayout).setVisibility(View.VISIBLE);
                            v1PriceTv.setText("V1会员价 " + pricesBean.getPrice().getUnitPrice());
                            break;
                        case 2:
                            findViewById(R.id.product_v2PriceLayout).setVisibility(View.VISIBLE);
                            v2PriceTv.setText("V2会员价 " + pricesBean.getPrice().getUnitPrice());
                            break;
                        case 3:
                            findViewById(R.id.product_v3PriceLayout).setVisibility(View.VISIBLE);
                            v3PriceTv.setText("V3会员价 " + pricesBean.getPrice().getUnitPrice());
                            break;
                    }
                }
            }else if(TextUtils.isEmpty(sku.getSpecialMsg()) && sku.getMembersPrices().size() == 1){
                if(sku.getMembersPrices().get(0).getMemberLv() != sku.getCurPrice().getCurMemberLv()){
                    myPriceLayout.setVisibility(View.VISIBLE);
                    myPriceTv.setText(sku.getMembersPrices().get(0).getPrice().getUnitPrice());
                    switch (sku.getMembersPrices().get(0).getMemberLv()) {
                        case 1:
                            myPriceTagIv.setBackgroundResource(R.drawable.ic_v1);
                            break;
                        case 2:
                            myPriceTagIv.setBackgroundResource(R.drawable.ic_v2);
                            break;
                        case 3:
                            myPriceTagIv.setBackgroundResource(R.drawable.ic_v3);
                            break;
                    }
                }
            }
        }else if(type == 1){    //  促销价格设置
            price = Float.parseFloat(sku.getSpecialPirce().getUnitPrice());
            priceTv.setText("￥" + price);
            primePriceTv.setText("￥"+sku.getMarketPrice().getUnitPrice());
            primePriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            primePriceTv.setVisibility(View.VISIBLE);
            findViewById(R.id.product_v3_98Iv).setVisibility(View.GONE);
        }else if(type == 2){    //

        }else if(type == 3){    //  促销转普通
            saleMsg.setVisibility(View.GONE);
            price = Float.parseFloat(sku.getMarketPrice().getUnitPrice());
            priceTv.setText("￥" + price);
            primePriceTv.setVisibility(View.GONE);
        }
    }

    private void setSaleInfo() {
        //  是否设置促销提醒
        if (returnData != null && returnData.getPromotionNotify() != null) {
            ifSetSaleReminder = returnData.getPromotionNotify().isState();
        }
        if (ifSetSaleReminder) {
            preSaleRemindTv.setBackgroundResource(R.color.b_b5b5b5);
            preSaleRemindTv.setText("已提醒");
        } else {
            preSaleRemindTv.setBackgroundResource(R.color.c_d9be64);
            preSaleRemindTv.setText("提醒我");
        }

        //  有促销活动
        if (saleData != null && saleData.getRule() != null) {
            ProductSaleInfoData.RETURNDATABean.RuleBean rule = saleData.getRule();
            long beginTime = toMillions(rule.getBeginDate());
            long endTime = toMillions(rule.getEndDate());
            currTime = System.currentTimeMillis();
            Log.i("beginTime:" + beginTime + ", currTime:" + currTime);
            if (endTime < currTime) {                 //  已经结束了
            } else {
                if (beginTime < currTime) {         //  已经开始了
                    ToastUtil.showToast("已经开始了");
                    saleMsg.setVisibility(View.VISIBLE);
                    findViewById(R.id.product_preSaleLayout).setVisibility(View.GONE);
                    String timeInfo = "【" + rule.getName() + "】" + " 仅剩";
                    final CountDownTimerUtil myCountDownTimer = new CountDownTimerUtil(endTime - currTime,
                            1000, saleMsg, "PRODUCTSALE", timeInfo);
                    myCountDownTimer.setOnCountDownFinishListener(new CountDownTimerUtil.OnCountDownFinishListener() {
                        @Override
                        public void onFinish() {
                            setPrice(3);
                        }
                    });
                    myCountDownTimer.start();
                } else {                            //  还没有开始
                    ToastUtil.showToast("还没有开始");
                    findViewById(R.id.buyNow_tv).setEnabled(false);
                    findViewById(R.id.product_preSaleLayout).setVisibility(View.VISIBLE);
                    saleMsg.setVisibility(View.GONE);
                    preSaleMsg.setText("【" + rule.getName() + "】" + rule.getBeginDate());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                currTime = System.currentTimeMillis();
                                Log.i("currTime: "+currTime);
                                if(currTime >= beginTime){
                                    setSaleInfo();
                                }
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        }
    }


    private void loadData(int position) {
        if (position == 1) {
            //  设置详情页的参数信息
            PicTxtDetailFragment.getInstance().setReturnData(returnData);
            if (ifShowReport) {
                TrialReportFragment.getInstance().loadData(productId);
            } else {
                EvaluateFragment.getInstance().loadData(productId);
            }
        } else if (position == 2) {
            if (ifShowReport) {
                TrialReportFragment.getInstance().loadData(productId);
            }
            EvaluateFragment.getInstance().loadData(productId);
        } else if (position == 3) {
            EvaluateFragment.getInstance().loadData(productId);
        }
    }


    @OnClick({R.id.title_back_layout, R.id.share_layout, R.id.addLikes_layout, R.id.addCar_layout,
            R.id.buyNow_tv, R.id.addCar_tv, R.id.product_saleRemindTv, R.id.setReminderTv})
    public void onClick(View v) {
        if (!ToolUtils.isLoginStatus(mContext)) {
            if(v.getId() != R.id.addCar_tv && v.getId()!=R.id.title_back_layout) {
                startActivityWithAnim(LoginActivity.class);
                return;
            }
        }
        switch (v.getId()) {
            case R.id.title_back_layout:    //  tab中的返回按钮
                finish();
                break;
            case R.id.share_layout:         //  tab中的分享键
//                UmengUtils.shareUrl(mContext, sharetitle, shareContent, shareUrl, shareImage, R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
                UmengUtils.shareUrl(ProductDetailActivity.this, "胡歌真漂亮", "这是我自己分享的一段话",
                        "https://github.com/CymChad/BaseRecyclerViewAdapterHelper",
                        "https://b-ssl.duitang.com/uploads/item/201607/21/20160721003824_hmMzs.thumb.700_0.jpeg", R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
                            @Override
                            public void onStart(UmengUtils.ShareType type) {

                            }

                            @Override
                            public void onResult(UmengUtils.ShareType type) {
                                ToastUtil.showToastAt("分享成功", Gravity.CENTER);

                            }

                            @Override
                            public void onError(UmengUtils.ShareType type, Throwable throwable) {
                                ToastUtil.showToastAt("分享失败", Gravity.CENTER);

                            }

                            @Override
                            public void onCancel(UmengUtils.ShareType type) {
                                ToastUtil.showToastAt("分享取消", Gravity.CENTER);

                            }
                        });
                break;
            case R.id.buyNow_tv:                //  立即购买
                BuyNowDialogFragment.showDialog(this, price, returnData.getSellableAmount(),
                        productId, returnData.getSkuPrice().getCurPrice().getSkuId(), 0);
                break;
            case R.id.addLikes_layout:          //  弹出一个收藏框
                CollectionUtils utils = CollectionUtils.getInstance();
                if (ifLiked) {
                    removeGoodsCollection();
                } else {
                    utils.setOnCollectionListener(new CollectionUtils.OnCollectionListener() {
                        @Override
                        public void onCollection(int like, int collections) {
                            ToastUtil.showToastAt("收藏成功", Gravity.CENTER);
                            ifLiked = true;
                            findViewById(R.id.addLikes_Iv).setBackgroundResource(R.drawable.btn_dblsc_ysc);
                        }

                        @Override
                        public void onFailed(String msg) {
                            ToastUtil.showToastAt("收藏失败", Gravity.CENTER);
                        }
                    });
                    List<String> productList = new ArrayList<>();
                    productList.add(productId);
                    utils.showCollectionDialog(ProductDetailActivity.this,
                            CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION, productList);
                }
                break;
            case R.id.addCar_layout:    //  跳转到购物车
                startActivityWithAnim(ShoppingCartListActivity.class);
                break;
            case R.id.addCar_tv: {      //  加入购物车
//                ShoppingCartModel.addToCart(mContext, productId,
//                        returnData.getSkuPrice().getCurPrice().getSkuId(), "1", new StringCallback() {
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                Type type = new TypeToken<BaseResponseData>() {
//                                }.getType();
//                                BaseResponseData data = new Gson().fromJson(response.body(), type);
//                                Toast.makeText(mContext, OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) ?
//                                        "添加购物车成功" : data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
//                            }
//                        });

                ServiceManger.getInstance().addToCart(productId,
                        returnData.getSkuPrice().getCurPrice().getSkuId(), "1", new BaseRequestCallback<BaseResponseData>() {
                            @Override
                            public void onSuccess(BaseResponseData data) {
                                Toast.makeText(mContext, OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) ?
                                        "添加购物车成功" : data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
            }
            case R.id.product_saleRemindTv:     //  促销-提醒我
                if(ifSetSaleReminder){
                    cancelNotify(0);
                }else {
                    setReminder(0);
                }
                break;
            case R.id.setReminderTv:            //  缺货-提醒
                if(ifSetStockReminder){
                    cancelNotify(1);
                }else {
                    setReminder(1);
                }
                break;
        }
    }

    /**
     * 获取商品详情
     */
    private void getProductDetails() {
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        ServiceManger.getInstance().getProductDetails(requestMap,
                new BaseRequestCallback<ProductDetailsInfoData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始获取商品详情");
                    }

                    @Override
                    public void onSuccess(ProductDetailsInfoData data) {
                        Log.i("商品详情：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            //  轮播图片
                            if (data.getRETURN_DATA() != null) {
                                returnData = data.getRETURN_DATA();
                                //  测试库存不够（假装是这样的）
//                                returnData.setSellableAmount(0);
                                updatePageInfo();
                                //  测试有活动(假装是这样的)
//                                returnData.setHasPromotion(false);
                                if (returnData.isHasPromotion()) {
                                    ToastUtil.showToast("有促销活动");
                                    getProductSaleActivity();
                                } else {
                                    mDialog.dissmissDialog();
                                }
                            }
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                            initView(0);
                            mDialog.dissmissDialog();
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("获取商品详情失败！");
                        initView(0);
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 获取促销活动
     */
    private void getProductSaleActivity() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        ServiceManger.getInstance().getProductSaleActivity(requestMap,
                new BaseRequestCallback<ProductSaleInfoData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始获取促销活动");
                    }

                    @Override
                    public void onSuccess(ProductSaleInfoData data) {
                        Log.i("促销活动：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            saleData = data.getRETURN_DATA();
                            setSaleInfo();
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("获取促销活动失败！");
                    }

                    @Override
                    public void onEnd() {
                        Log.i("获取促销活动结束");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 取消商品收藏
     */
    private void removeGoodsCollection() {
        mDialog.showProgressDialog("加载中...", null);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("objId", productId);
        ServiceManger.getInstance().removeGoodsCollection(requestMap,
                new BaseRequestCallback<CollectionResultData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始取消商品收藏");
                    }

                    @Override
                    public void onSuccess(CollectionResultData data) {
                        Log.i("取消商品收藏：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            ToastUtil.showToastAt("取消收藏成功", Gravity.CENTER);
                            ifLiked = false;
                            findViewById(R.id.addLikes_Iv).setBackgroundResource(R.drawable.btn_spsc_wsc);
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("取消商品收藏失败！");
                    }

                    @Override
                    public void onEnd() {
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 设置促销活动/商品到货提醒 promotion-促销(0) arrival-到货通知(1)
     */
    private void setReminder(int type) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("skuId", returnData.getSkuPrice().getCurPrice().getSkuId());
        if (type == 0) {
            requestMap.put("type", "promotion");
            requestMap.put("objId", saleData.getRule().getRuleId());
        } else {
            requestMap.put("type", "arrival");
            requestMap.put("objId", productId);
        }
        ServiceManger.getInstance().addNotify(requestMap,
                new BaseRequestCallback<BaseResponseData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始设置提醒");
                    }

                    @Override
                    public void onSuccess(BaseResponseData data) {
                        Log.i("提醒成功：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            if (type == 0) {
                                ifSetSaleReminder = true;
                                preSaleRemindTv.setBackgroundResource(R.color.b_b5b5b5);
                                preSaleRemindTv.setText("已提醒");
                            } else {
                                ifSetStockReminder = true;
                                setReminderTv.setText("已设置到货提醒");
                            }
                            ToastUtil.showToastAt("设置提醒成功", Gravity.CENTER);
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        LogUtil.i("设置提醒失败: " + msg);
                        ToastUtil.showToast("设置提醒失败");
                    }

                    @Override
                    public void onEnd() {
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 取消促销活动/商品到货提醒 promotion-促销(0) arrival-到货通知(1)
     */
    private void cancelNotify(int type) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("skuId", returnData.getSkuPrice().getCurPrice().getSkuId());
        if (type == 0) {
            requestMap.put("type", "promotion");
            requestMap.put("objId", returnData.getPromotionNotify().getBindKey());
        } else {
            requestMap.put("type", "arrival");
            requestMap.put("objId", productId);
        }
        ServiceManger.getInstance().cancelNotify(requestMap,
                new BaseRequestCallback<BaseResponseData>() {
                    @Override
                    public void onStart() {
                        Log.i("开始取消提醒");
                    }

                    @Override
                    public void onSuccess(BaseResponseData data) {
                        Log.i("取消提醒成功：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            if (type == 0) {
                                ifSetSaleReminder = false;
                                preSaleRemindTv.setBackgroundResource(R.color.c_d9be64);
                                preSaleRemindTv.setText("提醒我");
                            } else {
                                ifSetStockReminder = false;
                                setReminderTv.setText("设置到货提醒");
                            }
                            ToastUtil.showToastAt("取消提醒成功", Gravity.CENTER);
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        LogUtil.i("取消提醒失败: " + msg);
                        ToastUtil.showToast("取消提醒失败");
                    }

                    @Override
                    public void onEnd() {
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 将日期转化成毫秒数
     */
    public long toMillions(String str) {
        long returnMill = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            returnMill = sdf.parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMill;
    }

    /**
     * 整个页面拉到最顶部
     */
    private void goTop() {
        scrollView.scrollTo(0, 0);
    }

    private class LocalImageHolderView implements Holder<PhotoInfo> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, PhotoInfo data) {
            LoadImageUtil.getInstance().loadBackground(imageView, data.getSourcePath());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置开始轮播以及轮播时间  建议在onResume方法中设置
        if(imgsList.size() > 0) {
            mConvenientBanner.startTurning(3000);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(imgsList.size() > 1) {
            mConvenientBanner.stopTurning();
        }
    }
}
