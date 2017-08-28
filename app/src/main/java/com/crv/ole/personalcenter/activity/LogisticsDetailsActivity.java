package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.adapter.LogisticsDetailsListAdapter;
import com.crv.ole.personalcenter.model.TrackBeanResponseData;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * create by lihongshi 2017/07/22
 * 物流详情
 */
public class LogisticsDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView logistics_pic;
    private TextView logistics_status;
    private TextView logistics_company;
    private TextView logistics_waybill;
    private TextView logistics_tel;

    private LogisticsDetailsListAdapter adapter;

    private String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_details);
        initTitleViews();
        setBarTitle("物流详情");
        orderId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logistics_details_call: { //拨打电话
                call("10086");
                break;
            }
            case R.id.logistics_details_kefu: { //在线客服
                UnicornModel.openChat(mContext);
                break;
            }
            case R.id.title_back_btn: { //后退
                finish();
                break;
            }


        }
    }

    /**
     * 调用拨号界面(跳转到拨号界面，用户手动点击拨打）
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initView() {

        logistics_pic = (ImageView) this.findViewById(R.id.logistics_pic);
        logistics_status = (TextView) this.findViewById(R.id.logistics_status);
        logistics_company = (TextView) this.findViewById(R.id.logistics_company);
        logistics_waybill = (TextView) this.findViewById(R.id.logistics_waybill);
        logistics_tel = (TextView) this.findViewById(R.id.logistics_tel);

        RecyclerView logintics_detal_recy = (RecyclerView) this.findViewById(R.id.logintics_detal_recy);
        logintics_detal_recy.setNestedScrollingEnabled(false);
        logintics_detal_recy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogisticsDetailsListAdapter(new ArrayList<>());
        logintics_detal_recy.setAdapter(adapter);

        this.findViewById(R.id.logistics_details_call).setOnClickListener(this);
        this.findViewById(R.id.logistics_details_kefu).setOnClickListener(this);
        this.findViewById(R.id.title_back_btn).setOnClickListener(this);

    }

    private void updateView(TrackBeanResponseData.RETURN_DATA responseData) {
        if (responseData == null) {
            return;
        }
        LoadImageUtil.getInstance().loadImage(logistics_pic, responseData.getProductImg());
        logistics_status.setText(responseData.getState());
        logistics_company.setText(responseData.getName());
        logistics_waybill.setText(responseData.getBillNo());
        logistics_tel.setText(responseData.getTelephone());
        adapter.setAllItem(responseData.getTraces());
    }

    private void initData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_TRACK);
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        requestData.setREQUEST_DATA(map);

        ServerApi.request(true, requestData, TrackBeanResponseData.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TrackBeanResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TrackBeanResponseData trackBeanResponseData) {
                        if (trackBeanResponseData.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            updateView(trackBeanResponseData.getRETURN_DATA());
                        } else {
                            ToastUtil.showToast(trackBeanResponseData.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
