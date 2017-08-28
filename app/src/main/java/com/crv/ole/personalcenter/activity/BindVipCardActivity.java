package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.register.model.BindMemberInfoResultBean;
import com.crv.ole.utils.FinishUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.TextUtil;
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
 * 个人中心 - 开卡和绑定(绑定会员卡或重新注册) - 绑定会员卡页面
 */

public class BindVipCardActivity extends BaseActivity implements TextWatcher{
    @BindView(R.id.bindCard_phoneTv)
    TextView phoneTv;
    @BindView(R.id.bindCard_name)
    EditText nameEt;
    @BindView(R.id.bindCard_card)
    EditText cardEt;
    @BindView(R.id.bindCard_bind)
    Button bindBtn;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindvipcard);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.vipCard_bindVip);

        init();
    }

    private void init(){
        nameEt.addTextChangedListener(this);
        cardEt.addTextChangedListener(this);
        cardEt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputDigits)));

        mobile = getIntent().getStringExtra("mobile");
        if(mobile!=null && mobile.length()==11){
            phoneTv.setText(mobile.substring(0,3)+"****"+mobile.substring(7));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtil.isEmpty(nameEt.getText().toString()) && TextUtil.isEmpty(cardEt.getText().toString())) {
            bindBtn.setEnabled(false);
        } else {
            bindBtn.setEnabled(true);
        }
    }


    @OnClick({R.id.bindCard_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bindCard_bind:
                bindMemberInfo();
                break;
        }
    }


    /**
     * 绑定会员卡(???????????????????待定)
     */
    private void bindMemberInfo(){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.BIND_MEMBERINFO);
        BindMemberInfoResultBean.BindMemberInfo bean = new BindMemberInfoResultBean().new BindMemberInfo();
        bean.setMobile(mobile);
        bean.setGuestname(nameEt.getText().toString().trim());
        bean.setIdcard(cardEt.getText().toString().trim());
        bean.setCardno("");
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, BindMemberInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BindMemberInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BindMemberInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        String desc = response.getRETURN_DESC();
                        if(desc.equals("绑定会员卡信息成功")){
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                            ToastUtil.showToast("恭喜你，会员卡绑定成功！");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(mContext, VipCardActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

}
