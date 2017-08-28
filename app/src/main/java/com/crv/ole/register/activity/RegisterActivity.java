package com.crv.ole.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.register.model.CheckPhoneCodeBean;
import com.crv.ole.register.model.SendMsgCodeInfoResultBean;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.FinishUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.TelCheckUtil;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.CountDownTimerUtil;
import com.google.gson.Gson;
import com.vondear.rxtools.view.RxCaptcha;

import net.arnx.jsonic.JSON;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.vondear.rxtools.view.RxCaptcha.TYPE.CHARS;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.phone_edt)
    EditText phoneEdt;
    @BindView(R.id.identifying_code_edt)
    EditText identifyingCodeEdt;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.auth_code_edt)
    EditText authCodeEdt;
    @BindView(R.id.auth_code_txt)
    TextView authCodeTxt;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.error_txt)
    TextView errorTxt;
    @BindView(R.id.fwxy)
    TextView fwxy;
    @BindView(R.id.agree_iv)
    ImageView agreeIv;
    private String mPhone;  //手机号码
    private String mIvCode; //图形验证码
    private boolean ifAgree = true;    // 是否同意服务协议
    private boolean ifShowCode = false;     //  是否显示图形验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initTitleViews();
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        setBarTitle("注册");

        initCodeView();
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(TelCheckUtil.isMobileNO(phoneEdt.getText().toString())){
                    authCodeTxt.setEnabled(true);
                }else{
                    authCodeTxt.setEnabled(false);
                }
                setNextBtnBg();
            }
        });
        identifyingCodeEdt.addTextChangedListener(this);
        authCodeEdt.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
        setNextBtnBg();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    /**
     * 设置下一步按钮的背景颜色
     */
    private void setNextBtnBg(){
        if(ifShowCode){
            if(TelCheckUtil.isMobileNO(phoneEdt.getText().toString()) &&
                    !TextUtils.isEmpty(identifyingCodeEdt.getText()) &&
                    !TextUtils.isEmpty(authCodeEdt.getText()) && ifAgree){
                nextBtn.setEnabled(true);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.btn_dlan_normal));
            }else{
                nextBtn.setEnabled(false);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.btn_disable));
            }
        }else{
            if(TelCheckUtil.isMobileNO(phoneEdt.getText().toString()) &&
                    !TextUtils.isEmpty(authCodeEdt.getText()) && ifAgree){
                nextBtn.setEnabled(true);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.btn_dlan_normal));
            }else{
                nextBtn.setEnabled(false);
                nextBtn.setBackground(getResources().getDrawable(R.drawable.btn_disable));
            }
        }
    }

    /**
     * 设置图形验证码的显隐
     */
    private void initCodeView(){
        long currTime = System.currentTimeMillis();
        long saveTime = mPreferencesHelper.getLong(OleConstants.GET_CODE_START_TIME);
        if(saveTime == 0){
            mPreferencesHelper.put(OleConstants.GET_CODE_START_TIME, currTime);
            mPreferencesHelper.put(OleConstants.GET_CODE_TIMES, 0);
            findViewById(R.id.identifying_code_layout).setVisibility(View.GONE);
            ifShowCode = false;
        }else {
            //  24小时以内
            if (currTime - saveTime < (1000L * 60 * 60 * 24)) {
                int times = mPreferencesHelper.getInt(OleConstants.GET_CODE_TIMES);
                if(times <= 3){
                    findViewById(R.id.identifying_code_layout).setVisibility(View.GONE);
                    ifShowCode = false;
                }else{
                    findViewById(R.id.identifying_code_layout).setVisibility(View.VISIBLE);
                    ifShowCode = true;
                }
            } else {
                mPreferencesHelper.put(OleConstants.GET_CODE_START_TIME, currTime);
                mPreferencesHelper.put(OleConstants.GET_CODE_TIMES, 0);
                findViewById(R.id.identifying_code_layout).setVisibility(View.GONE);
                ifShowCode = false;
            }
        }
        if(ifShowCode){
            getCode();
        }
    }

    @OnClick({R.id.title_back_layout, R.id.auth_code_txt, R.id.next_btn, R.id.fwxy, R.id.iv_code, R.id.agree_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_code://切换图形验证码
                getCode();
                break;
            case R.id.fwxy://服务协议
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, FwxyActivity.class);
                startActivity(intent);
                break;
            case R.id.title_back_layout://返回
                finish();
                break;
            case R.id.auth_code_txt://获取验证码
                mPhone = phoneEdt.getText().toString().trim();
                final CountDownTimerUtil myCountDownTimer = new CountDownTimerUtil(60000, 1000, authCodeTxt, "LOGIN", "点击获取验证码");
                myCountDownTimer.start();
                getPhoneValidateCode(mPhone);
                break;
            case R.id.agree_layout:     // 切换同意背景
                if(ifAgree){
                    ifAgree = false;
                    agreeIv.setBackground(getResources().getDrawable(R.drawable.btn_xz_normal_small));
                }else{
                    ifAgree = true;
                    agreeIv.setBackground(getResources().getDrawable(R.drawable.btn_xz_pressed_small));
                }
                setNextBtnBg();
                break;
            case R.id.next_btn://下一步
                if(ifShowCode &&
                        !TextUtils.equals(mIvCode.toLowerCase(), identifyingCodeEdt.getText().toString().toLowerCase())){
                    ToastUtil.showToast("请输入正确的图形验证码");
                    return;
                }
                mPhone = phoneEdt.getText().toString().trim();
                String validateCode = authCodeEdt.getText().toString().trim();
                checkPhoneCode(mPhone, validateCode);
                break;
        }
    }


    private void getCode() {
        RxCaptcha.build()
                .backColor(0xffffff)
                .codeLength(4)
                .fontSize(60)
                .lineNumber(0)
                .size(200, 70)
                .type(CHARS)
                .into(ivCode);
        mIvCode = RxCaptcha.build().getCode();
    }

    /**
     * 获取短信验证码
     */
    private void getPhoneValidateCode(String phone){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.SEND_SMSVALIDATE);
        SendMsgCodeInfoResultBean.SendMsgCodeInfo2 infoBean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo2();
        infoBean.setSendPhone(phone);
        infoBean.setSendType("register");
        if(ifShowCode) {
            infoBean.setValidateCode(mIvCode);
        }

        SendMsgCodeInfoResultBean.SendMsgCodeInfo1 bean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo1();
        String iv = ToolUtils.getRandomString(8);
        String key = mPreferencesHelper.getString(OleConstants.KEY.ENCRYPT_APP_KEY);
        bean.setIv(iv);
        bean.setSendParam(DESEncryptUtil.encSign(key, iv, JSON.encode(infoBean)));
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, SendMsgCodeInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendMsgCodeInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SendMsgCodeInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        String desc = response.getRETURN_DESC();
                        if(TextUtils.equals("操作成功", desc)){
                            ToastUtil.showToast("验证码已发送");
                            int times = mPreferencesHelper.getInt(OleConstants.GET_CODE_TIMES)+1;
                            mPreferencesHelper.put(OleConstants.GET_CODE_TIMES, times);
                            initCodeView();
                        }else {
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
     * 验证短信验证码
     */
    private void checkPhoneCode(String phone, String validateCode){
        errorTxt.setVisibility(View.GONE);
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CHECK_PHONECODE);
        CheckPhoneCodeBean bean = new CheckPhoneCodeBean();
        bean.setPhoneNumber(phone);
        bean.setPhoneValidatingCode(validateCode);
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, BaseResponseData.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseResponseData response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        if(TextUtils.equals("操作成功", response.getRETURN_DESC())){
                            errorTxt.setVisibility(View.GONE);
                            Intent intents = new Intent();
                            intents.setClass(RegisterActivity.this, PasswordActivity.class);
                            intents.putExtra("mobilePhone", phone).
                                    putExtra("validateCode", validateCode);
                            intents.putExtra("type", "register");
                            startActivity(intents);
                        }else if(TextUtils.equals("验证码不一致", response.getRETURN_DESC())){
                            errorTxt.setVisibility(View.VISIBLE);
                        }else{
                            ToastUtil.showToast(response.getRETURN_DESC());
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
