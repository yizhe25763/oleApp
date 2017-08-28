package com.crv.ole.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.adapter.SYAdapter;
import com.crv.ole.home.model.DataBean;
import com.crv.ole.home.model.SYBean;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.adapter.TrialReportListAdapter;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作用描述：试用活动列表页面
 * 创建者： wj_wsf
 * 创建时间： 2017/8/24 17:11.
 */

public class TrialActiveListActivity extends BaseActivity {

    private List<DataBean> titleList = new ArrayList<>();
    private SYAdapter adapter;
    private int pageNum = 1, pageTotal;
    private boolean isRefresh = false;

    private ListView trial_active_list_lv;
    private PullToRefreshLayout trial_active_list_refresh_layout;

    private void assignViews() {
        trial_active_list_lv = (ListView) findViewById(R.id.trial_active_list_lv);
        trial_active_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrialActiveListActivity.this, TrialInfoActivity.class);
                intent.putExtra("url", titleList.get(position).getImageUrl());
                intent.putExtra("title", titleList.get(position).getName());
                intent.putExtra("activeId", titleList.get(position).getLinkTo());
                startActivity(intent);
            }
        });

        trial_active_list_refresh_layout = (PullToRefreshLayout) findViewById(R.id.trial_active_list_refresh_layout);
        trial_active_list_refresh_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 1;
                isRefresh = true;
                getList();
            }

            @Override
            public void loadMore() {
                pageNum += 1;
                getList();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_active_list_layout);

        assignViews();
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.home_market_tab_1);

        getList();
    }

    private void showData() {
        if (adapter == null) {
            adapter = new SYAdapter(mContext, titleList);
            trial_active_list_lv.setAdapter(adapter);
        } else {
            if (isRefresh) {
                isRefresh = false;
                trial_active_list_refresh_layout.finishRefresh();
            } else {
                trial_active_list_refresh_layout.finishLoadMore();
            }
            adapter.notifyDataSetChanged();

        }

        if (pageNum >= pageTotal) {
            trial_active_list_refresh_layout.setCanLoadMore(false);
        } else {
            trial_active_list_refresh_layout.setCanLoadMore(true);
        }
    }

    /**
     * 获取试用列表
     */
    private void getList() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_MARKET_TEMPLATE);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageId", "tryOut3");
        requestMap.put("rappId", "oleMarketTemplate");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, SYBean.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SYBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SYBean response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            DataBean beans = null;
                            if (isRefresh) {
                                titleList.clear();
                            }
                            for (int i = 0; i < response.getRETURN_DATA().size(); i++) {
                                for (int j = 0; j < response.getRETURN_DATA().get(i).getOleFloors_i().size(); j++) {
                                    if (j == 0) {
                                        beans = new DataBean();
                                        beans.setUnitName(response.getRETURN_DATA().get(i).getOleFloors_h());
                                        beans.setName("");
                                        titleList.add(beans);
                                    }
                                    beans = new DataBean();
                                    beans.setUnitName(response.getRETURN_DATA().get(i).getOleFloors_h());
                                    beans.setName(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getParh());
                                    beans.setImageUrl(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getImgUrl());
                                    beans.setParp(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getParp());
                                    beans.setId(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getId());
                                    beans.setLinkTo(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getLinkTo());
                                    titleList.add(beans);
                                }
                            }
                            showData();
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
