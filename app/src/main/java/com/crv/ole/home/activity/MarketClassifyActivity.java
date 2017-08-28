package com.crv.ole.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.classfiy.activity.ProductClassfiyDetilActivity;
import com.crv.ole.home.adapter.MarketClassifyAdapter;
import com.crv.ole.home.model.ClassifyInfoResult;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.activity.MarketSearchActivity;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Fairy on 2017/7/28.
 * 超市 - 搜索及分类页
 */

public class MarketClassifyActivity extends BaseActivity {
    @BindView(R.id.classify_listView)
    ListView listView;

    private List<ClassifyInfoResult.RETURNDATABean> dataList;
    private MarketClassifyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketclassify);
        ButterKnife.bind(this);

        dataList = new ArrayList<>();
        adapter = new MarketClassifyAdapter(mContext, dataList, BaseApplication.getDeviceWidth());
        listView.setAdapter(adapter);

        View endLayout = LayoutInflater.from(mContext).inflate(R.layout.the_end_layout, null);
        listView.addFooterView(endLayout);

        getClassifyList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == dataList.size()) {//footView
                    return;
                }
                ClassifyInfoResult.RETURNDATABean bean = dataList.get(position);
                List<ClassifyInfoResult.RETURNDATABean.SortListBean> sortList = bean.getSort_list();
                Intent intent = new Intent(MarketClassifyActivity.this, ProductClassfiyDetilActivity.class);
                ArrayList<ClassifyInfoResult.RETURNDATABean.SortListBean> arrayList = new ArrayList<ClassifyInfoResult.RETURNDATABean.SortListBean>();
                arrayList.addAll(sortList);
                intent.putExtra("columnId", bean.getSort_i().getImgLinkTo());
                intent.putExtra("imgUrl", bean.getSort_i().getImgUrl());
                intent.putExtra("sortList", arrayList);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.classify_cancel, R.id.classify_inputTv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classify_cancel:
                finish();
                break;
            case R.id.classify_inputTv:
                startActivityWithAnim(MarketSearchActivity.class);
                break;
        }
    }

    /**
     * 获取分类列表
     */
    public void getClassifyList() {
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_OLEMARKETTEMPLATE_ID);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageId", "sort");
        requestMap.put("rappId", "oleMarketTemplate");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(true, requestData, ClassifyInfoResult.class, OleConstants.sign)
                .subscribe(new Observer<ClassifyInfoResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("请求数据：" + new Gson().toJson(requestData));
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ClassifyInfoResult response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        if (response.getRETURN_DATA() != null && response.getRETURN_DATA().size() > 0) {
                            dataList.clear();
                            dataList.addAll(response.getRETURN_DATA());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissProgressDialog();
                    }
                });

    }
}
