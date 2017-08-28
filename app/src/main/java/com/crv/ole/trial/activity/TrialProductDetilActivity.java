package com.crv.ole.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.activity.GoodsAddressActivity;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.trial.callback.OnTextClickListener;
import com.crv.ole.trial.callback.TextClick;
import com.crv.ole.trial.model.MobileContent;
import com.crv.ole.trial.model.TrialProductDetilData;
import com.crv.ole.trial.model.TrialProductDetilResult;
import com.crv.ole.trial.view.ScrollBottomScrollView;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.ScreenUtil;
import com.crv.sdk.utils.TextUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codeboy.android.aligntextview.AlignTextView;

/**
 * 试用商品详情界面
 * Created by zhangbo on 2017/8/15.
 */

public class TrialProductDetilActivity extends BaseActivity {

    @BindView(R.id.scrollView)
    ScrollBottomScrollView scrollView;

    @BindView(R.id.im_product)
    ImageView im_product;

    @BindView(R.id.tx_count)
    TextView tx_count;//数量


    @BindView(R.id.tx_finish_time)
    TextView tx_finish_time;//结束时间


    @BindView(R.id.tx_name)
    TextView tx_name;//商品名称


    @BindView(R.id.tx_price)
    TextView tx_price;//商品价格


    @BindView(R.id.tx_desc)
    AlignTextView tx_desc;//商品描述

    @BindView(R.id.info_number)
    TextView info_number;//销售单位


    @BindView(R.id.info_day)
    TextView info_day;//保质期


    @BindView(R.id.info_address)
    TextView info_address;//产地名称


    @BindView(R.id.info_wk)
    TextView info_wk;//温控


    @BindView(R.id.info_gg)
    TextView info_gg;//规格


    @BindView(R.id.info_place)
    TextView info_place;//品牌


    @BindView(R.id.ll_im_detil)
    LinearLayout ll_im_detil;//商品详情图列表


    @BindView(R.id.im_location)
    ImageView im_location;//定位图标

    @BindView(R.id.tx_tishi)
    TextView tx_tishi;//地址提示


    @BindView(R.id.ll_info)
    LinearLayout ll_info;//地址信息区域

    @BindView(R.id.tx_applayer_name)
    TextView tx_applayer_name;//申请人姓名


    @BindView(R.id.tx_applayer_mobile)
    TextView tx_applayer_mobile;//申请人手机


    @BindView(R.id.rl_select_addr)
    RelativeLayout rl_select_addr;//选择地址区域


    @BindView(R.id.tx_applayer_addr)
    TextView tx_applayer_addr;//申请人地址


    @BindView(R.id.cb_aggree)
    CheckBox cb_aggree;//同意服务


    @BindView(R.id.tx_rule)
    TextView tx_rule;//规则


    @BindView(R.id.bt_applay)
    Button bt_applay;//申请试用


    @BindView(R.id.bt_down)
    Button bt_down;//下拉

    @BindView(R.id.tx_current_count)
    TextView tx_current_count;//数量

    @BindView(R.id.tx_current_peoper)
    TextView tx_current_peoper;//人数

    private String product_url; //商品图片URL
    private String product_count;//商品数量
    private String product_name;//商品名称
    private String product_price;//商品价格
    private String product_content;//商品介绍
    private String activeId;//活动ID

    // 选择地址请求CODE
    private int CHOOSE_ADDRESS_REQUEST_CODE = 123;

    private AddressListData.Address addressData;

    private float startY;
    private float currentY;

    private boolean isdown = false;

    private String productObjId;

    private TrialProductDetilData data;

    private boolean isApplay = false;//是否已提交申请

    private String applicationState = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_pdt_detil_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initUI();
        initEvent();
        getDate();
    }


    private void initUI() {
        product_url = getIntent().getStringExtra("url");
        product_count = getIntent().getStringExtra("count");
        product_name = getIntent().getStringExtra("name");
        product_price = getIntent().getStringExtra("price");
        product_content = getIntent().getStringExtra("content");
        productObjId = getIntent().getStringExtra("productObjId");
        activeId = getIntent().getStringExtra("activeId");
        title_name_tv.setText(getString(R.string.applay_trail_title));


        LoadImageUtil.getInstance().loadImage(im_product, product_url);
        tx_current_count.setText(String.format(getString(R.string.count), product_count));
        tx_count.setText(String.format(getString(R.string.num), product_count));
        tx_name.setText(product_name);
        tx_price.setText(String.format(getString(R.string.price), product_price));
        tx_desc.setText(product_content);

        String clickText = getString(R.string.trial_applay_rule_tishi);

        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(clickText);
        spannable.setSpan(new TextClick(new OnTextClickListener() {
                    @Override
                    public void onTextClickListen() {
                        //试用规则
                        startActivityWithAnim(TrialRuleActivity.class);
                    }
                }), 9, 13
                , Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tx_rule.setText(spannable);
        tx_rule.setMovementMethod(LinkMovementMethod.getInstance());

      /*  scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isdown) {
                        startY = event.getY();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    currentY = event.getY();
                    if (isdown && currentY - startY > 0 && Math.abs(currentY - startY) > bt_applay.getHeight()) {
                        bt_down.setVisibility(View.VISIBLE);
                        isdown = false;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    startY = event.getY();
                }
                return false;
            }
        });*/
        //滑动到底部
        scrollView.onScrollViewScrollToBottom(new ScrollBottomScrollView.OnScrollBottomListener() {
            @Override
            public void scrollToBottom() {
                isdown = true;
                bt_down.setVisibility(View.GONE);
            }
        });
        scrollView.onScrollNotInBottom(new ScrollBottomScrollView.OnScrollNotInBottomListener() {
            @Override
            public void ScrollNotInBottom() {
                if (!(bt_down.getVisibility() == View.VISIBLE)) {
                    isdown = false;
                    bt_down.setVisibility(View.VISIBLE);
                }
            }
        });
        checkIsAppaly();
    }

    private void initEvent() {
        bt_applay.setOnClickListener(this);
        bt_down.setOnClickListener(this);
        title_back_layout.setOnClickListener(this);
        rl_select_addr.setOnClickListener(this);
        tx_finish_time.setOnClickListener(this);
    }


    private void getDate() {
        //TODO 服务器获取数据
        Map<String, String> params = new HashMap<>();
        params.put("productObjId", productObjId);
        ServiceManger.getInstance().getTryOutProductDetails(params, new BaseRequestCallback<TrialProductDetilResult>() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(R.string.waiting);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(TrialProductDetilResult data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    if (data.getRETURN_DATA() != null) {
                        //刷新数据
                        updateView(data.getRETURN_DATA());
                    }


                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }

    private void updateView(TrialProductDetilData detilData) {
        this.data = detilData;
        this.applicationState = detilData.getApplicationState();
        info_number.setText(detilData.getSellUnit() + "");
        info_day.setText(detilData.getShelfLife() + "");
        info_address.setText(detilData.getCountry() == null ? detilData.getOriginPlace() : detilData.getCountry().getCName());
        info_wk.setText(detilData.getTemperatureControl() == null ? "常温" : detilData.getTemperatureControl().getDes());
        info_gg.setText(detilData.getSpec());
        info_place.setText(detilData.getBand() == null ? "未知" : detilData.getBand().getName());
        tx_current_peoper.setText(String.format(getString(R.string.peporer), detilData.getTotalRecords() + ""));

        ll_im_detil.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (MobileContent mobileContent : detilData.getMobileContent()) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(mobileContent.getUrl()).into(new GlideDrawableImageViewTarget(imageView) {

                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);
                    imageView.setBackground(resource);
                    ll_im_detil.addView(imageView, layoutParams);
                }
            });
        }
        this.isApplay = data.isApplication();
        checkIsAppaly();
        //刷新地址信息
        if (data.isApplication()) {
            ll_info.setVisibility(View.VISIBLE);
            tx_tishi.setVisibility(View.GONE);
            tx_applayer_name.setText(data.getAddressInfo().getUserName());
            tx_applayer_mobile.setText(data.getAddressInfo().getPhone());
            tx_applayer_addr.setText(data.getAddressInfo().getAddress());
            im_location.setBackgroundResource(R.drawable.position_selected);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;

            case R.id.bt_applay:
                //申请试用
                if (!isApplay) {
                    if (!cb_aggree.isChecked()) {
                        ToastUtil.showToast(getString(R.string.please_see_rule));
                        return;
                    }
                    if (TextUtil.isEmpty(tx_applayer_name.getText().toString())) {
                        ToastUtil.showToast(getString(R.string.trial_input_info));
                        return;
                    }
                    applyForTrial();
                }
                break;

            case R.id.bt_down:
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);//滑动到底部
                bt_down.setVisibility(View.GONE);
                isdown = true;
                break;

            case R.id.rl_select_addr:
                startActivityForResult(
                        new Intent(this, GoodsAddressActivity.class)
                                .putExtra("from_page", "confirm_order"),
                        CHOOSE_ADDRESS_REQUEST_CODE);
                break;

            case R.id.tx_finish_time://审核通过进入试用列表
                if (isApplay && "1".equals(applicationState)) {
                    startActivityWithAnim(TrialListActivity.class);
                }
                break;
        }
    }

    //申请试用
    private void applyForTrial() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", data.getProductId());
        map.put("activeId", activeId);
        ServiceManger.getInstance().applyForTrial(map, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(R.string.waiting);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(BaseResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {//申请试用成功
                    isApplay = true;
                    checkIsAppaly();
                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }

    /**
     * 检查是否申请过
     */
    private void checkIsAppaly() {
        if (isApplay) {
            tx_finish_time.setVisibility(View.VISIBLE);
            bt_applay.setBackgroundColor(getResources().getColor(R.color.d_dbdbdb));
            tx_finish_time.setBackgroundResource(R.mipmap.bg_timebg_ysq);
            if ("0".equals(applicationState)) {//审核未通过
                tx_finish_time.setText(getString(R.string.trial_un_pass));
                bt_applay.setText(getString(R.string.trial_un_pass));
            } else if ("1".equals(applicationState)) {
                tx_finish_time.setText(getString(R.string.trial_pass));
                bt_applay.setText(getString(R.string.trial_pass));
            } else {
                bt_applay.setText(getString(R.string.applayed));
                tx_finish_time.setText(getString(R.string.applayed2));
            }
        } else {
            tx_finish_time.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_ADDRESS_REQUEST_CODE && resultCode == 100) {
            addressData = (AddressListData.Address) data.getSerializableExtra("address_data");
            if (addressData != null) {
                ll_info.setVisibility(View.VISIBLE);
                tx_tishi.setVisibility(View.GONE);
                tx_applayer_name.setText(addressData.getUserName());
                tx_applayer_mobile.setText(addressData.getMobile());
                tx_applayer_addr.setText(addressData.getAddress());
                im_location.setBackgroundResource(R.drawable.position_selected);
            }
        }
    }
}
