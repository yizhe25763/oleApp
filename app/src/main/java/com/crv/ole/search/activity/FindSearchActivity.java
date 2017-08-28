package com.crv.ole.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.activity.SpecialDetailActivity;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.search.adapter.FindSearchResultAdapter;
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
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fairy on 2017/7/22.
 * 首页发现频道 - 搜索页面
 */

public class FindSearchActivity extends BaseActivity {
    @BindView(R.id.search_inputEt)
    EditText inputEt;
    @BindView(R.id.search_listView)
    ListView listView;
    private List<ListResult.RETURNDATABean.InformationBean> dataList;
    private FindSearchResultAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_search);
        ButterKnife.bind(this);

        dataList = new ArrayList<>();
        adapter = new FindSearchResultAdapter(mContext, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, SpecialDetailActivity.class)
                        .putExtra("id", dataList.get(position).getId()));
            }
        });
        inputEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    getResultList(inputEt.getText().toString());
                }
                return false;
            }
        });
    }

    @OnClick({R.id.search_img, R.id.search_cancel})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.search_img:
                getResultList(inputEt.getText().toString());
                break;
            case R.id.search_cancel:
                finish();
                break;
        }
    }

    /**
     * 获取咨询列表
     */
    public void getResultList(String msg) {
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_INFOLIST);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("title", msg);
        requestMap.put("pageNum", "1");
        requestMap.put("limit", "20");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, ListResult.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {}
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ListResult response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        dataList.clear();
                        if(response.getRETURN_DESC().equals("success") && response.getRETURN_DATA() != null){
                            if(response.getRETURN_DATA().getInformation() != null){
                                if(response.getRETURN_DATA().getInformation().size() > 0){
                                    dataList.addAll(response.getRETURN_DATA().getInformation());
                                    adapter.notifyDataSetChanged();
                                }else{
                                    ToastUtil.showToast("没有搜到结果，换个词试试吧！");
                                    inputEt.setText("");
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            Log.i("请求出错");
                            ToastUtil.showToast("请求出错，请稍后再试");
                            e.printStackTrace();
                        }

                    @Override
                    public void onComplete() {
                        dismissProgressDialog();
                    }
                });

    }
}
