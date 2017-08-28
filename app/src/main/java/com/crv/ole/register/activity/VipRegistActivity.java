package com.crv.ole.register.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.activity.VipCardActivity;
import com.crv.ole.personalcenter.fragment.AddressBottomDialogFragment;
import com.crv.ole.personalcenter.model.UnbindMemberInfoResultBean;
import com.crv.ole.register.model.ShopInfoResultBean;
import com.crv.ole.utils.FinishUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.StringUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

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
 * 登陆 - 注册成功 - 会员注册页面 (开卡流程)
 */

public class VipRegistActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.vipRegist_agree_tv)
    TextView vipRegist_agree_tv;
    @BindView(R.id.vipRegist_sir_btn)
    Button sirBtn;
    @BindView(R.id.vipRegist_mr_btn)
    Button mrBtn;
    @BindView(R.id.vipRegist_name_et)
    EditText nameEt;
    @BindView(R.id.vipRegist_card_et)
    EditText cardEt;
    @BindView(R.id.vipRegist_area_tv)
    TextView areaTv;
    @BindView(R.id.vipRegist_shop_tv)
    TextView shopTv;
    @BindView(R.id.vipRegist_address_Et)
    EditText addressEt;
    @BindView(R.id.vipRegist_regist_btn)
    Button registBtn;
    private boolean ifAgree = true;
    /**
     * 跳转来源
     * ActiveBindActivity 个人中心-开卡流程
     * RegistSuccessActivity 注册登录 - 开卡流程
     */
    private String source;
    private String mobile;  // 电话
    private int guestsex = 1;    // 性别 1-男 2- 女
    private String provinceId, cityId, districtId, shopId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_regist);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.regist_title);
        source = getIntent().getStringExtra("source");
        mobile = getIntent().getStringExtra("mobile");
        nameEt.addTextChangedListener(this);
        areaTv.addTextChangedListener(this);
        shopTv.addTextChangedListener(this);
    }


    @OnClick({R.id.vipRegist_sir_btn, R.id.vipRegist_mr_btn, R.id.vipRegist_area_layout, R.id.vipRegist_shop_layout, R.id.vipRegist_agree_layout, R.id.vipRegist_regist_btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.vipRegist_sir_btn:
                guestsex = 1;
                sirBtn.setBackground(getResources().getDrawable(R.drawable.line_xuanzhongk));
                mrBtn.setBackground(getResources().getDrawable(R.drawable.line_weixuanzhong));
                break;
            case R.id.vipRegist_mr_btn:
                guestsex = 2;
                sirBtn.setBackground(getResources().getDrawable(R.drawable.line_weixuanzhong));
                mrBtn.setBackground(getResources().getDrawable(R.drawable.line_xuanzhongk));
                break;
            case R.id.vipRegist_area_layout:
                showRegionDialog();
                break;
            case R.id.vipRegist_shop_layout:
                if(TextUtils.isEmpty(areaTv.getText().toString())){
                    ToastUtil.showToast("请先完善所在地区信息");
                    return;
                }
                Intent intent = new Intent(mContext, ShopListActivity.class);
                intent.putExtra("provincecode", provinceId);
                intent.putExtra("citycode", cityId);
                startActivityForResult(intent, 6);
                break;
            case R.id.vipRegist_agree_layout:
                Drawable agreeImg = getResources().getDrawable(R.drawable.btn_xz_pressed_small);
                Drawable disagreeImg = getResources().getDrawable(R.drawable.btn_xz_normal_small);
                if(ifAgree){
                    ifAgree = false;
                    disagreeImg.setBounds(0, 0, disagreeImg.getMinimumWidth(), disagreeImg.getMinimumHeight());
                    vipRegist_agree_tv.setCompoundDrawables(disagreeImg, null, null, null);
                }else{
                    ifAgree = true;
                    agreeImg.setBounds(0, 0, agreeImg.getMinimumWidth(), agreeImg.getMinimumHeight());
                    vipRegist_agree_tv.setCompoundDrawables(agreeImg, null, null, null);
                }
                changeBg();
                break;
            case R.id.vipRegist_regist_btn:
                String idCard = cardEt.getText().toString();
                if(!TextUtils.isEmpty(cardEt.getText().toString())) {
                    if (StringUtils.isCardId(idCard)) {
                        openMemberCard();
                    }else{
                        ToastUtil.showToast("请输入正确的身份证号！");
                        return;
                    }
                }
                openMemberCard();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        changeBg();
    }

    private void changeBg(){
        if(!TextUtils.isEmpty(nameEt.getText()) && !TextUtils.isEmpty(areaTv.getText()) &&
                !TextUtils.isEmpty(shopTv.getText()) && ifAgree){
            registBtn.setEnabled(true);
        }else{
            registBtn.setEnabled(false);
        }
    }



    /**
     * 会员注册开卡(？？？？？？？？？数据待定)
     */
    private void openMemberCard(){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.OPEN_MEMBERCARD);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("channel", "ole");
        requestMap.put("thirdid", "u_123");
        requestMap.put("idcard", cardEt.getText().toString());
        requestMap.put("shopid", shopId);
        requestMap.put("guestname", nameEt.getText().toString().trim());
        requestMap.put("guestsex", guestsex+"");
        requestMap.put("mobile", mobile);
        requestMap.put("province", provinceId);
        requestMap.put("city", cityId);
        requestMap.put("district", districtId);
        requestMap.put("address", addressEt.getText().toString().trim());
        requestMap.put("exchsvflag", "1");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, UnbindMemberInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UnbindMemberInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(UnbindMemberInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        String desc = response.getRETURN_DESC();
                        if(desc.equals("会员开卡成功")){
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                            ToastUtil.showToast(desc);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(source.equals("ActiveBindActivity")){
                                        startActivity(new Intent(mContext, VipCardActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    }else if(source.equals("RegistSuccessActivity")){
                                        startActivity(new Intent(mContext, MainActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    }
                                    FinishUtils.getInstance().finishActivityList();
                                }
                            }, 2000);
                        }else{
                            ToastUtil.showToast(desc);
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

    /**
     * 区域选择
     */
    private void showRegionDialog() {
        AddressBottomDialogFragment.showDialog(this, "", new AddressBottomDialogFragment.OnConfirmClickListener() {
            @Override
            public void onClick(Map<String, String> map) {
                areaTv.setText(map.get("area"));
                String[] codeArr = map.get("code").split("/");
                provinceId = codeArr.length>0 ? codeArr[0] : "";
                cityId = codeArr.length>1 ? codeArr[1] : "";
                districtId = codeArr.length>2 ? codeArr[2] : "";
                shopTv.setText("");
                shopId = "";
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && resultCode == 100){
            ShopInfoResultBean.RETURNDATABean shopInfo = (ShopInfoResultBean.RETURNDATABean)
                    data.getSerializableExtra("result");
            if(shopInfo != null){
                shopTv.setText(shopInfo.getShopname());
                shopId = shopInfo.getShopid();
            }
        }
    }
}
