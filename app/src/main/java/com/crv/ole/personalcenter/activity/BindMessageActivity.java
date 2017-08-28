package com.crv.ole.personalcenter.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.model.BindMessage;
import com.crv.ole.register.model.SendMsgCodeInfoResultBean;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yanghongjun on 17/7/10.
 */

public class BindMessageActivity extends BaseActivity {

    private final static String TITLE = "title";

    @BindView(R.id.message_et)
    public EditText message_et;

    @BindView(R.id.checkcode_et)
    public EditText checkcode_et;

    @BindView(R.id.checkcode_bt)
    public Button checkcode_bt;

    @BindView(R.id.finish_btn)
    public Button finish_bt;

    String type;//区分绑定手机/邮箱
    Timer timer;
    int count = 60;
    boolean startTimer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_message);
        initView();
    }

    private void initView() {
        title_name_tv = (TextView) findViewById(R.id.title_name_tv);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(TITLE);
        if (!TextUtils.isEmpty(title)) {
            setBarTitle(title);
        }
        type = getIntent().getStringExtra("type");

        if (type.equals("mobile")) {
            message_et.setHint("请输入手机号码");
            message_et.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (type.equals("email")) {
            message_et.setHint("请输入邮箱");
            message_et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChanged");
                textChange();
            }
        };
        message_et.addTextChangedListener(watcher);
        checkcode_et.addTextChangedListener(watcher);

    }


    /**
     * 监听点击
     *
     * @param view
     */
    @OnClick({R.id.title_back_layout, R.id.checkcode_bt, R.id.finish_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.checkcode_bt) {
            startTimer();
        } else if (id == R.id.finish_btn) {
            bindMessage();
        }
    }

    /**
     * 开始倒计时
     */
    private void startTimer() {

        if (startTimer == true) return;
        String messsage = message_et.getText().toString().trim();
        if (type.equals("mobile")) {//发送短信验证码
            sendMsgValidateCode(messsage);
        } else {//发送邮件验证码
            sendEmailValidateCode(messsage);
        }
        startTimer = true;
        timer = new Timer();
        timer.purge();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        count--;
                        if (count > 0) {
                            e.onNext(count);
                        } else {
                            e.onComplete();
                        }
                    }
                }, 0, 1000);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("开始了");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                checkcode_bt.setText(integer + "s");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                timer.cancel();
                count = 60;
                startTimer = false;
                checkcode_bt.setText("获取验证码");
            }
        });

    }

    /**
     * 监听文本改变
     */
    private void textChange() {
        String message = StringUtils.getStringNoBlank(message_et.getText().toString());
        boolean isLegalMessage = false;
        if (type.equals("mobile")) {
            checkcode_bt.setEnabled(StringUtils.isMobile(message));
            checkcode_bt.setTextColor(StringUtils.isMobile(message) ? 0xFFd9be64 : Color.LTGRAY);
            isLegalMessage = StringUtils.isMobile(message);
        } else if (type.equals("email")) {
            checkcode_bt.setEnabled(StringUtils.isEmail(message));
            checkcode_bt.setTextColor(StringUtils.isEmail(message) ? 0xFFd9be64 : Color.LTGRAY);
            isLegalMessage = StringUtils.isEmail(message);
        }

        if ((checkcode_et.getText().toString().length() > 0) && isLegalMessage) {
            finish_bt.setEnabled(true);
        } else {
            finish_bt.setEnabled(false);
        }
    }

    /**
     * 发送短信验证码
     */
    private void sendMsgValidateCode(String mobile) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.SEND_SMSVALIDATE);
        SendMsgCodeInfoResultBean.SendMsgCodeInfo2 infoBean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo2();
        infoBean.setSendPhone(mobile);
        infoBean.setSendType("bindMobile");

        SendMsgCodeInfoResultBean.SendMsgCodeInfo1 bean = new SendMsgCodeInfoResultBean().new SendMsgCodeInfo1();
        String iv = ToolUtils.getRandomString(8);
        String key = mPreferencesHelper.getString(OleConstants.KEY.ENCRYPT_APP_KEY);
        bean.setIv(iv);
        bean.setSendParam(DESEncryptUtil.encSign(key, iv, new Gson().toJson(infoBean)));
        requestData.setREQUEST_DATA(bean);
        Log.i("加密后的:" + bean.toString());

        ServerApi.request(false, requestData, SendMsgCodeInfoResultBean.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
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
                        String desc = response.getRETURN_DESC();
                        if (OleConstants.REQUES_SUCCESS.equals(response.getRETURN_CODE())) {
                            ToastUtil.showToast("验证码已发送");
                        } else {
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
     * 发送邮箱验证码
     */
    private void sendEmailValidateCode(String email) {
        showProgressDialog("邮件发送中...", null);
        RequestData<HashMap<String, String>> requestData = new RequestData<>();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.SEND_EMAIL);

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        requestData.setREQUEST_DATA(map);
        ServerApi.request(false, requestData, BaseResponseData.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
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
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            ToastUtil.showToast("邮件发送成功");
                        } else {
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissProgressDialog();
                        Log.i("请求出错");
                        ToastUtil.showToast("请求出错，请稍后再试");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("请求完成");
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 绑定
     */
    private void bindMessage() {

        BindMessage bindMessage = new BindMessage();
        RequestData requestData = new RequestData();

        if (type.equals("mobile")) {
            bindMessage.setPhoneNumber(message_et.getText().toString().trim());
            bindMessage.setPhoneValidatingCode(checkcode_et.getText().toString().trim());
            requestData.getRequestAttrsInstance().setApi_ID(OleConstants.BIND_PHONE);
        } else if (type.equals("email")) {
            bindMessage.setEmail(message_et.getText().toString().trim());
            bindMessage.setValidateCode(checkcode_et.getText().toString().trim());
            requestData.getRequestAttrsInstance().setApi_ID(OleConstants.BIND_EMAIL);
        } else {
            return;
        }

        showProgressDialog("绑定中...", null);
        requestData.setREQUEST_DATA(bindMessage);
        ServerApi.request(false, requestData, BaseResponseData.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseResponseData response) {
                        dismissProgressDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            String json = new Gson().toJson(response);
                            ToastUtil.showToast(response.getRETURN_DESC());
                            finish();
                        } else {
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissProgressDialog();
                        ToastUtil.showToast("绑定失败,请稍后再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

}
