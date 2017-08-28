package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.model.ActiveHrtMemberData;
import com.crv.ole.personalcenter.model.ActiveHrtMemberResultData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

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
 * 个人中心 - 会员卡 - 华润通激活页面
 */

public class ActiveHRTActivity extends BaseActivity {
    @BindView(R.id.vipCard_active)
    Button activtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_hrt);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.vipCard_activeHRT);
//        title_back_btn.setBackground(getResources().getDrawable(R.drawable.back_button_selector));
    }

    @OnClick({R.id.vipCard_active})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.vipCard_active:
                getPhoneValidateCode();
                break;
        }
    }

    /**
     * 华润通激活 （？？？？？？？？？？？？？？请求数据待定）
     */
    private void getPhoneValidateCode(){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.ACTIVE_HRTMEMBER);
        ActiveHrtMemberData bean = new ActiveHrtMemberData();
        bean.setChannel("ole");
        bean.setMemberid("12345678");
        bean.setShopid("123");
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, ActiveHrtMemberResultData.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ActiveHrtMemberResultData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ActiveHrtMemberResultData response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        String desc = response.getRETURN_DESC();
                        if(TextUtils.equals("激活华润通信息成功", desc)){
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                            ToastUtil.showToast("华润通激活成功！");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(mContext, VipCardActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            .putExtra("result", "success"));
                                    finish();
                                }
                            },2000);
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

}
