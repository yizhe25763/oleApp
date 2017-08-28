package com.crv.ole.register.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.register.adapter.ShopListAdapter;
import com.crv.ole.register.model.ShopInfoResultBean;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fairy on 2017/7/25.
 * 门店列表
 */

public class ShopListActivity extends BaseActivity{
    @BindView(R.id.shop_listView)
    ListView listView;

    private ShopListAdapter adapter;
    private List<ShopInfoResultBean.RETURNDATABean> dataList;
    private String provincecode;    //省编码
    private String citycode;        //市编码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle("门店");

        initData();
        initListener();
        getShopInfo();
    }

    private void initData(){
        provincecode = TextUtils.isEmpty(getIntent().getStringExtra("provincecode")) ? "" :
                getIntent().getStringExtra("provincecode");
        citycode = TextUtils.isEmpty(getIntent().getStringExtra("citycode")) ? "" :
                getIntent().getStringExtra("citycode");
        if(TextUtils.isEmpty(citycode)){
            citycode = provincecode;
        }

        dataList = new ArrayList<>();
        adapter = new ShopListAdapter(mContext, dataList);
        listView.setAdapter(adapter);
    }

    private void initListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(100,getIntent().putExtra("result", dataList.get(position)));
                finish();
            }
        });

    }

    /**
     * 查询门店信息
     */
    private void getShopInfo(){
        if(TextUtils.isEmpty(provincecode) || TextUtils.isEmpty(citycode)){
            ToastUtil.showToast("省市信息传递失败");
            finish();
        }
        showProgressDialog("请求中。。。", null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_SHOPINFO);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("provincecode", provincecode);
        requestMap.put("citycode", citycode);
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, ShopInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ShopInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        Log.i("门店请求数据：" + new Gson().toJson(requestData));
                        String desc = response.getRETURN_DESC();
                        if(TextUtils.equals(response.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)){
                            if(response.getRETURN_DATA() != null){
                                if(response.getRETURN_DATA() != null &&
                                        response.getRETURN_DATA().size() > 0){
                                    dataList.addAll(response.getRETURN_DATA());
                                    adapter.notifyDataSetChanged();
                                }else{
                                    ToastUtil.showToast("没有查询到门店信息！");
                                }
                            }
                        }else{
                            ToastUtil.showToast(desc);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("请求出错"+e.getMessage());
                        ToastUtil.showToast("门店信息查询失败");
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
