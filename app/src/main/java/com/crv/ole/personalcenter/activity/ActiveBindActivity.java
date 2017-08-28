package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.model.UnbindMemberInfoResultBean;
import com.crv.ole.register.activity.VipRegistActivity;
import com.crv.ole.register.model.CheckMemberByMobileInfoResultBean;
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

import net.arnx.jsonic.JSON;

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
 * 个人中心 - 会员卡 - 开卡和绑定页面
 */

public class ActiveBindActivity extends BaseActivity {
    @BindView(R.id.activeBind_phone)
    EditText phoneEt;
    @BindView(R.id.activeBind_codeTxt)
    TextView codeTxt;
    @BindView(R.id.activeBind_code)
    EditText codeEt;
    @BindView(R.id.activeBind_next)
    Button nextBtn;
    /**
     *  跳转来源
     *  bind - 普通用户开通绑定会员卡
     *  unbind - 会员用户解绑会员卡
     */
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_bind);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        initBackButton();
        source = getIntent().getStringExtra("source");
        if(source.equals("unbind")) {
            setBarTitle("解绑");
        }else {
            setBarTitle(R.string.vipCard_activeBind);
        }

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(TelCheckUtil.isMobileNO(phoneEt.getText().toString())){
                    codeTxt.setEnabled(true);
                }else{
                    codeTxt.setEnabled(false);
                }
                setNextBg();
            }
        });
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                setNextBg();
            }
        });
    }

    /**
     * 设置下一步的背景色
     */
    public void setNextBg() {
        if(TelCheckUtil.isMobileNO(phoneEt.getText().toString()) && !TextUtils.isEmpty(codeEt.getText())){
            nextBtn.setEnabled(true);
        }else{
            nextBtn.setEnabled(false);
        }
    }


    @OnClick({R.id.activeBind_codeTxt, R.id.activeBind_next})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.activeBind_codeTxt:
                String mPhone = phoneEt.getText().toString().trim();
                final CountDownTimerUtil myCountDownTimer = new CountDownTimerUtil(60000, 1000, codeTxt, "LOGIN", "点击获取验证码");
                myCountDownTimer.start();
                getPhoneValidateCode(mPhone);
                break;
            case R.id.activeBind_next:   //  下一步
                checkPhoneCode(phoneEt.getText().toString().trim(), codeEt.getText().toString().trim());
                break;
        }
    }


    /**
     * 获取短信验证码
     */
    private void getPhoneValidateCode(String mPhone){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.SEND_SMSVALIDATE);
        SendMsgCodeInfoResultBean.SendMsgCodeInfo2 infoBean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo2();
        infoBean.setSendPhone(mPhone);
        infoBean.setSendType("unbindVipCard");

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
    private void checkPhoneCode(String mobile, String validateCode){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CHECK_PHONECODE);

        CheckPhoneCodeBean bean = new CheckPhoneCodeBean();
        bean.setPhoneNumber(mobile);
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
                        String desc = response.getRETURN_DESC();
                        if(TextUtils.equals("操作成功", desc)){
                            if(source.equals("bind")) {
                                checkMemberInfoByMobile(mobile);
                            }else if(source.equals("unbind")){
                                unbindMemberInfo();
                            }
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
     * 根据手机号校验是否注册会员(？？？？？？？？？数据待定)
     */
    private void checkMemberInfoByMobile(String mobile){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CHECKMEMBERINFO_BYMOBILE);
        CheckMemberByMobileInfoResultBean.CheckMemberByMobileInfo bean = new CheckMemberByMobileInfoResultBean().new CheckMemberByMobileInfo();
        bean.setChannel("ole");
        bean.setMobile(mobile);
        bean.setFocusshopid("123");
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, CheckMemberByMobileInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckMemberByMobileInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CheckMemberByMobileInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        if(response.getRETURN_DATA()!=null && response.getRETURN_DATA().getFlag()!=null){
                            if(response.getRETURN_DATA().getFlag().equals("1")){    // 存在去绑卡
                                startActivity(new Intent(mContext, ActiveBindChooseActivity.class)
                                .putExtra("mobile", mobile));
                            }else if(response.getRETURN_DATA().getFlag().equals("0")){  // 不存在去开卡
                                startActivity(new Intent(mContext, VipRegistActivity.class)
                                        .putExtra("mobile", mobile)
                                        .putExtra("source","ActiveBindActivity"));
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


    /**
     * 会员解除绑定(？？？？？？？？？数据待定)
     */
    private void unbindMemberInfo(){
        UserCenterData.UserCenter userCenter = BaseApplication.getInstance().getUserCenter();
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.UNBINDING_MEMBERINFO);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("channel", "ole");
        requestMap.put("thirdid", "u_123");
        requestMap.put("memberid", userCenter == null ? "" : userCenter.getMemberid());
        requestMap.put("shopid", "123");
        requestMap.put("cardno", "12345");
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
                        if(desc.equals("会员卡解除绑定成功")){
                            ToastUtil.showToast(desc);
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
