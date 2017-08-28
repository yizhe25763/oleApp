package com.crv.ole.shopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.fragment.BuyNowDialogFragment;
import com.crv.ole.shopping.image.WheelView;
import com.crv.ole.shopping.model.AddPreToCartRespose;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.shopping.model.PreSaleResult;
import com.crv.ole.shopping.ui.CustomProgressBar;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.CountDownTimerUtil;
import com.crv.sdk.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 预售页面
 */
public class PreSaleActivity extends BaseActivity {
    @BindView(R.id.title_back_layout)
    RelativeLayout titleBackLayout;
    @BindView(R.id.share_layout)
    RelativeLayout shareLayout;
    @BindView(R.id.sc_imagebtn)
    ImageButton scImagebtn;
    @BindView(R.id.gwc_imagebtn)
    ImageButton gwcImagebtn;

    @BindView(R.id.tx_gwc_count)
    TextView tx_gwc_count;

    @BindView(R.id.product_images)
    WheelView productImages;
    @BindView(R.id.product_presellMsgTv)
    TextView productPresellMsgTv;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.place)
    ImageView place;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.cpb_progresbar)
    CustomProgressBar cpbProgresbar;
    @BindView(R.id.sy_txt)
    TextView syTxt;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.fh_txt)
    TextView fhTxt;
    @BindView(R.id.time1)
    TextView ydTime;
    @BindView(R.id.time2)
    TextView wkTime;
    @BindView(R.id.dj_txt)
    TextView dj;
    @BindView(R.id.info_place)
    TextView infoPlace;
    @BindView(R.id.info_address)
    TextView infoAddress;
    @BindView(R.id.info_number)
    TextView infoNumber;
    @BindView(R.id.info_day)
    TextView infoDay;
    @BindView(R.id.info_gg)
    TextView infoGg;
    @BindView(R.id.info_wk)
    TextView infoWk;
    @BindView(R.id.fdj_txt)
    TextView fdjTxt;
    @BindView(R.id.zfwk_txt)
    TextView zfwkTxt;
    @BindView(R.id.ysztx_txt)
    TextView ysztxTxt;
    @BindView(R.id.gwc_txt)
    TextView gwcTxt;
    @BindView(R.id.kstx_txr)
    TextView kstxTxr;
    @BindView(R.id.zfqk_txt)
    TextView zfqkTxt;
    @BindView(R.id.ysjs_txt)
    TextView ysjsTxt;

    @BindView(R.id.ll_end_pay)
    LinearLayout ll_end_pay;//尾款支付时间区域

    @BindView(R.id.ll_fh_time)
    LinearLayout ll_fh_time;//发货时间区域

    @BindView(R.id.time3)
    TextView time3;//发货时

    @BindView(R.id.tx_price_type)
    TextView tx_price_type;//支付类型:定金。全款

    private ArrayList<PhotoInfo> imageList = new ArrayList<>();
    private static ArrayList<PhotoInfo> selectedImgs = new ArrayList<PhotoInfo>();

    private CountDownTimerUtil timer1;
    private CountDownTimerUtil timer2;

    private String productId;
    private String skuId;
    private int sellableAmount;//可卖数量
    private float orderPrice;

    private int count = 0;

    @BindView(R.id.rl_gwc)
    RelativeLayout rl_gwc;//购物车区域

    @NonNull
    private void getUserInfo(String id) {
        productId = id;
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID("crv.ole.product.getProductDetails");
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId", id);
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, PreSaleResult.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PreSaleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(PreSaleResult response) {
                        if (response.getRETURN_DESC().equals("操作成功")) {
                            if (response.getRETURN_DATA() != null) {
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getName())) {//标题
                                    title.setText(response.getRETURN_DATA().getName());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getTotalPrice())) {//价格
                                    price.setText("预售价  ￥" + response.getRETURN_DATA().getPreSaleAttr().getJRule().getTotalPrice());
                                }
                                if (response.getRETURN_DATA().getPreSaleAttr().getJRule().getDisplayAmount() > 0 && !StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getBookAmount())) {//剩余数量显示
                                    int total = response.getRETURN_DATA().getPreSaleAttr().getJRule().getDisplayAmount();
                                    int bookNumber = Integer.parseInt(response.getRETURN_DATA().getPreSaleAttr().getBookAmount());
                                    String sy = Integer.toString(total - bookNumber);
                                    syTxt.setText("限量" + response.getRETURN_DATA().getPreSaleAttr().getJRule().getDisplayAmount() + "份，还剩" + sy + "份");
                                    cpbProgresbar.setCurProgress((total - bookNumber) / total);//设置进度条显示

                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getTag())) {//标签
                                    tag.setText(response.getRETURN_DATA().getTag());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getDeposit())) {//定金
                                    dj.setText("￥ " + response.getRETURN_DATA().getPreSaleAttr().getJRule().getDeposit());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getDepositBeginTime()) && !StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getDepositEndTime())) {//预售下定
                                    ydTime.setText(response.getRETURN_DATA().getPreSaleAttr().getJRule().getDepositBeginTime() + "—" + response.getRETURN_DATA().getPreSaleAttr().getJRule().getDepositEndTime());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getBeginTime()) && !StringUtils.isNullOrEmpty(response.getRETURN_DATA().getPreSaleAttr().getJRule().getEndTime())) {//尾款支付
                                    wkTime.setText(response.getRETURN_DATA().getPreSaleAttr().getJRule().getBeginTime() + "—" + response.getRETURN_DATA().getPreSaleAttr().getJRule().getEndTime());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getCountry().getImgUrl())) {//商品信息
                                    LoadImageUtil.getInstance().loadImage(place, response.getRETURN_DATA().getCountry().getImgUrl(), false);//
                                }
                                if (response.getRETURN_DATA().getImages() != null && response.getRETURN_DATA().getImages().size() > 0) {//商品图片
                                    PhotoInfo beans = null;
                                    for (int i = 0; i < response.getRETURN_DATA().getImages().size(); i++) {
                                        beans = new PhotoInfo();
                                        beans.setSourcePath(response.getRETURN_DATA().getImages().get(i).getUrl());
                                        imageList.add(beans);
                                    }
                                    initWheel();
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getCountry().getName())) {//pinpaiguo
                                    infoPlace.setText(response.getRETURN_DATA().getCountry().getName());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getCountry().getCName())) {//chandi
                                    infoAddress.setText(response.getRETURN_DATA().getCountry().getCName());
                                }

                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getSellUnit())) {//
                                    infoNumber.setText(response.getRETURN_DATA().getSellUnit());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getShelfLife())) {//标签
                                    infoDay.setText(response.getRETURN_DATA().getShelfLife());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getSpec())) {//标签
                                    infoGg.setText(response.getRETURN_DATA().getSpec());
                                }
                                if (!StringUtils.isNullOrEmpty(response.getRETURN_DATA().getTemperatureControl().getDes())) {//标签
                                    infoWk.setText(response.getRETURN_DATA().getTemperatureControl().getDes());
                                }

                                resetRule(response.getRETURN_DATA());
                                skuId = response.getRETURN_DATA().getSkuPrice().getCurPrice().getSkuId();
                                sellableAmount = response.getRETURN_DATA().getSellableAmount();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("请求出错");
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();            //请求失败
                    }

                    @Override
                    public void onComplete() {
                        Log.i("请求完成");
                        dismissProgressDialog();

                    }
                });
    }

    private void initData() {
        cpbProgresbar.setMaxProgress(100);
        cpbProgresbar.setProgressColor(Color.parseColor("#d9be64"));
        //        String id = "p_2000001";
        String id = "p_180547";

        getUserInfo(id);
    }

    /**
     * 初始化轮播图片的操作
     */
    private void initWheel() {
        if (imageList != null && imageList.size() > 0) {
            selectedImgs.clear();
            if (productImages != null) {
                productImages.stop();
                productImages.clear();
            }
            for (int i = 0; i < imageList.size(); i++) {
                String url = imageList.get(i).getSourcePath();
                productImages.addWheel(new WheelView.WheelInfo(url));
                PhotoInfo item = new PhotoInfo();
                item.setSourcePath(url);
                item.setNetResource(true);
                selectedImgs.add(item);
            }
            productImages.start();

        }

        if (productImages != null) {
            RelativeLayout.LayoutParams mWheelAdLP = (RelativeLayout.LayoutParams) productImages.getLayoutParams();
            mWheelAdLP.height = BaseApplication.getDeviceWidth();
            productImages.setLayoutParams(mWheelAdLP);
            productImages.setOnWheelClickListener(new WheelView.OnWheelClickListener() {
                @Override
                public void onWheelClick(int position, WheelView.WheelInfo info) {
                    // 图片点击后的事件
                    Intent intent = new Intent(mContext, LookPicActivity.class);
                    intent.putExtra("EXTRA_IMAGE_LIST", selectedImgs);
                    intent.putExtra("EXTRA_CURRENT_IMG_POSITION", position);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }
    }

    @OnClick({R.id.fdj_txt, R.id.zfqk_txt, R.id.title_back_layout, R.id.zfwk_txt, R.id.kstx_txr, R.id.gwc_txt, R.id.rl_gwc, R.id.gwc_imagebtn})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.fdj_txt://付定金
                BuyNowDialogFragment.showDialog(this, orderPrice, sellableAmount,
                        productId, skuId, 1);
                break;
            case R.id.zfqk_txt://支付全款
                BuyNowDialogFragment.showDialog(this, orderPrice, sellableAmount,
                        productId, skuId, 3);
                break;
            case R.id.zfwk_txt://支付尾款
                BuyNowDialogFragment.showDialog(this, orderPrice, sellableAmount,
                        productId, skuId, 2);
                break;
            case R.id.kstx_txr://设置提醒
                addNotify();
                break;
            case R.id.gwc_txt://加入购物车
                addToCart();
                break;
            case R.id.title_back_layout://返回
                finish();
            case R.id.gwc_imagebtn:
            case R.id.rl_gwc://跳转到购物车
                startActivityWithAnim(ShoppingCartListActivity.class);
                break;
        }
    }


    /**
     * 设置规则
     */
    private void resetRule(PreSaleResult.RETURNDATABean bean) {
        //先全部隐藏
        setVisableOrGone(false, false, false, false, false, false, false);
        String code = bean.getPreSaleAttr().getState().getCode();
        code = "1";
        String type = bean.getPreSaleAttr().getJRule().getType();
        type = "1";
        switch (code) {
            case "0"://预售未开始
                if (bean.getPreSaleAttr().getBindState().isState()) {
                    ysztxTxt.setVisibility(View.VISIBLE);//已设置提醒
                } else {
                    kstxTxr.setVisibility(View.VISIBLE);//可设置提醒
                }
                productPresellMsgTv.setText(bean.getPreSaleAttr().getJRule().getDepositBeginTime() + "开始预售");
                gwcTxt.setVisibility(View.VISIBLE);//加入购物车
                break;
            case "1"://订金支付时间段
                switch (type) {
                    case "1"://先付定金,固定尾款
                        if (!StringUtils.isNullOrEmpty(bean.getPreSaleAttr().getJRule().getDeposit())) {//定金
                            dj.setText("￥ " + bean.getPreSaleAttr().getJRule().getDeposit());
                            orderPrice = Float.valueOf(bean.getPreSaleAttr().getJRule().getDeposit());
                        }
                        fdjTxt.setVisibility(View.VISIBLE);
                        tx_price_type.setText("支付定金");
                        break;
                    case "3"://一次性付清全款
                        if (!StringUtils.isNullOrEmpty(bean.getPreSaleAttr().getJRule().getTotalPrice())) {//全款
                            dj.setText("￥ " + bean.getPreSaleAttr().getJRule().getTotalPrice());
                            orderPrice = Float.valueOf(bean.getPreSaleAttr().getJRule().getTotalPrice());
                        }
                        ll_end_pay.setVisibility(View.VISIBLE);
                        ll_fh_time.setVisibility(View.GONE);
                        fhTxt.setVisibility(View.GONE);
                        //TODO 设置发货时间
                        //time3.setText();
                        zfqkTxt.setVisibility(View.VISIBLE);
                        tx_price_type.setText("支付全款");
                        break;
                }
                timer1 = new CountDownTimerUtil(Long.valueOf(bean.getPreSaleAttr().getJRule().getDepositEndLongTime()) - System.currentTimeMillis(), 1000, productPresellMsgTv, "PreSalaFinish");
                timer1.start();
                gwcTxt.setVisibility(View.VISIBLE);//加入购物车
                break;
            case "2"://尾款支付时间段
                if (!StringUtils.isNullOrEmpty(bean.getPreSaleAttr().getJRule().getBalance())) {//定金
                    dj.setText("￥ " + bean.getPreSaleAttr().getJRule().getBalance());
                    orderPrice = Float.valueOf(bean.getPreSaleAttr().getJRule().getBalance());
                }
                zfwkTxt.setVisibility(View.VISIBLE);//支付尾款
                if (timer1 != null) {
                    timer1.cancel();
                }
                timer2 = new CountDownTimerUtil(Long.valueOf(bean.getPreSaleAttr().getJRule().getEndLongTime()) - System.currentTimeMillis(), 1000, productPresellMsgTv, "PreSalaFinish");
                timer2.start();
                tx_price_type.setText("支付尾款");

                break;
            case "3"://预售活动结束
                productPresellMsgTv.setVisibility(View.GONE);//隐藏倒计时
                ysjsTxt.setVisibility(View.VISIBLE);//活动已结束
                break;
        }

        if (bean.getKeepInfos().isIsKeep()) {//已收藏
            scImagebtn.setBackgroundResource(R.drawable.btn_spsc_ysc);
        } else {
            scImagebtn.setBackgroundResource(R.drawable.btn_spsc_wsc);
        }
    }


    /**
     * 设置按钮显示隐藏
     *
     * @param fdj    立即付定金
     * @param zfwktx 立即支付尾款
     * @param ysztx  已设置提醒
     * @param gwc    加入购物车
     * @param kstx   开售提醒
     * @param zfqk   立即付全款
     * @param ysjs   预售已结束
     */
    private void setVisableOrGone(boolean fdj, boolean zfwktx, boolean ysztx, boolean gwc, boolean kstx, boolean zfqk, boolean ysjs) {
        fdjTxt.setVisibility(fdj ? View.VISIBLE : View.GONE);
        zfwkTxt.setVisibility(zfwktx ? View.VISIBLE : View.GONE);
        ysztxTxt.setVisibility(ysztx ? View.VISIBLE : View.GONE);
        gwcTxt.setVisibility(gwc ? View.VISIBLE : View.GONE);
        kstxTxr.setVisibility(kstx ? View.VISIBLE : View.GONE);
        zfqkTxt.setVisibility(zfqk ? View.VISIBLE : View.GONE);
        ysjsTxt.setVisibility(ysjs ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sale);
        ButterKnife.bind(this);
        initData();
        tx_gwc_count.setText(count + "");
    }

    private void addToCart() {
        //TODO 加入购物车
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", "1");
        map.put("buyNow", "N");//是否立即购买,Y:是，N:否，不传默认是否
        ServiceManger.getInstance().addPresaleToCart(map, new BaseRequestCallback<AddPreToCartRespose>() {
            @Override
            public void onSuccess(AddPreToCartRespose data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    //count++;
                    tx_gwc_count.setVisibility(View.VISIBLE);
                    tx_gwc_count.setText(data.getRETURN_DATA().getCount() + "");
                    EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }

    /**
     * 预售提醒
     */
    private void addNotify() {
        Map<String, String> params = new HashMap<>();
        params.put("objId", productId);
        params.put("skuId", skuId);
        params.put("type", "preSale");//promotion:促销，live:直播，preSale:预售，arrival:到货通知
        ServiceManger.getInstance().addNotify(params, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onSuccess(BaseResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    ToastUtil.showToast("设置提醒成功");
                    ysztxTxt.setVisibility(View.VISIBLE);//已设置提醒
                    kstxTxr.setVisibility(View.GONE);//可设置提醒
                } else {
                    ToastUtil.showToast("设置提醒失败");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
        }
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
    }
}
