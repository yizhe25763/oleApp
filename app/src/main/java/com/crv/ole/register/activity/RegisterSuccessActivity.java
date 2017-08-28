package com.crv.ole.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.register.model.BindMemberInfoResultBean;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;

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
 * 登陆 - 注册成功页面
 */

public class RegisterSuccessActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.username_edt)
    EditText username_edt;
    @BindView(R.id.userident_edt)
    EditText userident_edt;
    @BindView(R.id.bindBtn)
    Button bindBtn;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle("注册成功");
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setText("跳过");

        mobile = getIntent().getStringExtra("mobile");
        initView();
    }

    private void initView(){
        username_edt.addTextChangedListener(this);
        userident_edt.addTextChangedListener(this);
        userident_edt.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputDigits)));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if(TextUtil.isEmpty(username_edt.getText().toString()) && TextUtil.isEmpty(userident_edt.getText().toString())){
            bindBtn.setEnabled(false);
            bindBtn.setBackground(getResources().getDrawable(R.drawable.btn_disable));
        }else{
            bindBtn.setEnabled(true);
            bindBtn.setBackground(getResources().getDrawable(R.drawable.btn_normal));
        }
    }

    @OnClick({R.id.title_iv_1, R.id.bindBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_iv_1:
                startActivity(new Intent(mContext, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bindBtn:
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
        bean.setGuestname(username_edt.getText().toString().trim());
        bean.setIdcard(userident_edt.getText().toString().trim());
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
                            ToastUtil.showToast("恭喜你，注册成功！\n即将进入首页");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            }, 2000);
                        }else if(desc.equals("绑定会员卡信息失败")){
                            ToastUtil.showToast(desc);
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
