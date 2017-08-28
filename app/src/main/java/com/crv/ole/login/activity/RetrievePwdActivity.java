package com.crv.ole.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.register.activity.PasswordActivity;
import com.crv.ole.register.model.SendMsgCodeInfoResultBean;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.CountDownTimerUtil;
import com.crv.sdk.utils.StringUtils;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;
import com.vondear.rxtools.view.RxCaptcha;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vondear.rxtools.view.RxCaptcha.TYPE.CHARS;


/**
 * 找回密码
 */
public class RetrievePwdActivity extends BaseActivity {

    @BindView(R.id.auth_mobile_edt)
    EditText phoneEdt;

    @BindView(R.id.tx_code_edt)
    EditText txCodeEdt;

    @BindView(R.id.tx_layout)
    RelativeLayout tx_layout;

    @BindView(R.id.line2_iv)
    ImageView line2_iv;

    @BindView(R.id.iv_code)
    ImageView ivCode;

    @BindView(R.id.sms_code_edt)
    EditText smsCodeEdt;

    @BindView(R.id.auth_code_txt)
    TextView authCodeTxt;

    @BindView(R.id.next_btn)
    Button nextBtn;

    @BindView(R.id.error_txt)
    TextView errorTxt;

    private String mPhone;//手机号码
    private String mIvCode;//图形验证码

    private boolean ifShowCode = false;     //  是否显示图形验证码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pwd);
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("找回密码");
        initCodeView();
        initLister();
    }

    private void initCodeView(){
        long currTime = System.currentTimeMillis();
        long saveTime = mPreferencesHelper.getLong(OleConstants.GET_CODE_START_TIME);
        if(saveTime == 0){
            mPreferencesHelper.put(OleConstants.GET_CODE_START_TIME, currTime);
            mPreferencesHelper.put(OleConstants.GET_CODE_TIMES, 0);
            tx_layout.setVisibility(View.GONE);
            line2_iv.setVisibility(View.GONE);
            ifShowCode = false;
        }else {
            //  24小时以内
            if (currTime - saveTime < (1000L * 60 * 60 * 24)) {
                int times = mPreferencesHelper.getInt(OleConstants.GET_CODE_TIMES);
                if(times <= 3){
                    tx_layout.setVisibility(View.GONE);
                    line2_iv.setVisibility(View.GONE);
                    ifShowCode = false;
                }else{
                    tx_layout.setVisibility(View.VISIBLE);
                    line2_iv.setVisibility(View.VISIBLE);
                    ifShowCode = true;
                }
            } else {
                mPreferencesHelper.put(OleConstants.GET_CODE_START_TIME, currTime);
                mPreferencesHelper.put(OleConstants.GET_CODE_TIMES, 0);
                tx_layout.setVisibility(View.GONE);
                line2_iv.setVisibility(View.GONE);
                ifShowCode = false;
            }
        }
        if(ifShowCode){
            tx_layout.setVisibility(View.VISIBLE);
            line2_iv.setVisibility(View.VISIBLE);
        }else{
            tx_layout.setVisibility(View.GONE);
            line2_iv.setVisibility(View.GONE);
        }
        getCode();
    }

    private void initLister() {

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mPhone = phoneEdt.getText().toString().trim();
                if (StringUtils.isMobile(mPhone)){
                    authCodeTxt.setEnabled(true);
                    authCodeTxt.setTextColor(getResources().getColor(R.color.d_d9be64));
                }else{
                    authCodeTxt.setEnabled(false);
                    authCodeTxt.setTextColor(Color.LTGRAY);
                }
                if ( StringUtils.isMobile(mPhone) && !TextUtil.isEmpty(smsCodeEdt.getText().toString())) {
                    if (ifShowCode){
                        if (TextUtils.equals(mIvCode.toLowerCase(), txCodeEdt.getText().toString().toLowerCase())){
                            nextBtn.setEnabled(true);
                            errorTxt.setVisibility(View.GONE);
                        }else{
                            nextBtn.setEnabled(false);
                            errorTxt.setVisibility(View.VISIBLE);
                        }
                    }else{
                        nextBtn.setEnabled(true);
                    }

                } else {
                    nextBtn.setEnabled(false);
                }
            }
        };

        phoneEdt.addTextChangedListener(watcher);

        txCodeEdt.addTextChangedListener(watcher);

        smsCodeEdt.addTextChangedListener(watcher);
    }


    @OnClick({R.id.title_back_layout, R.id.auth_code_txt, R.id.next_btn, R.id.iv_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_code://切换图形验证码
                getCode();
                break;
            case R.id.title_back_layout://返回
                finish();
                break;
            case R.id.auth_code_txt://获取验证码
                final CountDownTimerUtil myCountDownTimer = new CountDownTimerUtil(61000, 1000, authCodeTxt, "LOGIN", "点击获取验证码");
                myCountDownTimer.start();
                sendMsgValidateCode(mPhone);
                break;
            case R.id.next_btn://下一步
                mPhone = phoneEdt.getText().toString().trim();
                String smsCode = smsCodeEdt.getText().toString().trim();
                checkCode(mPhone,smsCode);
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

    private void sendMsgValidateCode(String mobile) {
        SendMsgCodeInfoResultBean.SendMsgCodeInfo2 infoBean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo2();
        infoBean.setSendPhone(mobile);
        infoBean.setSendType("forgotPwd");
        SendMsgCodeInfoResultBean.SendMsgCodeInfo1 bean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo1();
        String iv = ToolUtils.getRandomString(8);
        String key = mPreferencesHelper.getString(OleConstants.KEY.ENCRYPT_APP_KEY);
        HashMap<String,String> map = new HashMap<>();
        map.put("iv",iv);
        map.put("sendParam",DESEncryptUtil.encSign(key, iv, new Gson().toJson(infoBean)));
        ServiceManger.getInstance().fetchSmsCheckCode(map, new BaseRequestCallback<SendMsgCodeInfoResultBean>() {
            @Override
            public void onStart() {
                showProgressDialog("短信验证码发送中...", null);
            }

            @Override
            public void onSuccess(SendMsgCodeInfoResultBean response) {
                dismissProgressDialog();
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
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
                ToastUtil.showToast("请求出错，请稍后再试");
            }
        });
    }

    private void checkCode(String phone,String code) {
        HashMap<String,String> map = new HashMap<>();
        map.put("phoneValidatingCode",code);
        map.put("phoneNumber",phone);
        ServiceManger.getInstance().checkCode(map, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onStart() {
                showProgressDialog("验证中...", null);
            }

            @Override
            public void onSuccess(BaseResponseData response) {
                String desc = response.getRETURN_DESC();
                if(TextUtils.equals("操作成功", desc)){
                    Intent intents = new Intent();
                    intents.putExtra("validateCode", code);
                    intents.putExtra("mobilePhone", phone);
                    intents.putExtra("type", "findPwd");
                    intents.setClass(RetrievePwdActivity.this, PasswordActivity.class);
                    startActivity(intents);
                }else {
                    ToastUtil.showToast(desc);
                }
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
               dismissProgressDialog();
            }
        });

    }
}
