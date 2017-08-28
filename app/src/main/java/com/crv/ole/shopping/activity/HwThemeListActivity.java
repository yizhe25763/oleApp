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
import com.crv.ole.home.adapter.HWAdapter;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.adapter.TrialReportListAdapter;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
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
 * 作用描述：好物主题列表页面
 * 创建者： wj_wsf
 * 创建时间： 2017/8/24 17:11.
 */

public class HwThemeListActivity extends BaseActivity {

    private int pageNum = 1, pageTotal;
    private boolean isRefresh = false;

    private ListView hw_theme_lv;
    private PullToRefreshLayout hw_theme_refresh_layout;
    private HWAdapter adapter;
    private List<HWBean.RETURNDATABean> dataList;

    private void assignViews() {
        hw_theme_lv = (ListView) findViewById(R.id.hw_theme_lv);
        hw_theme_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HwThemeListActivity.this, HwDetailActivity.class);
                intent.putExtra("imgLinkTo",dataList.get(position).getImgLinkTo());
                startActivity(intent);
            }
        });

        hw_theme_refresh_layout = (PullToRefreshLayout) findViewById(R.id.hw_theme_refresh_layout);
        hw_theme_refresh_layout.setRefreshListener(new BaseRefreshListener() {
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
        setContentView(R.layout.activity_hw_theme_layout);

        assignViews();
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.home_market_tab_2);

        getList();
    }

    private void showData(List<HWBean.RETURNDATABean> datas) {
        if (datas == null || datas.size() < 1)
            return;

        if (dataList == null || adapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(datas);
            adapter = new HWAdapter(mContext, dataList);
            hw_theme_lv.setAdapter(adapter);
        } else {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
                hw_theme_refresh_layout.finishRefresh();
            } else {
                hw_theme_refresh_layout.finishLoadMore();
            }
            dataList.addAll(datas);
            adapter.notifyDataSetChanged();

        }

        if (pageNum >= pageTotal) {
            hw_theme_refresh_layout.setCanLoadMore(false);
        } else {
            hw_theme_refresh_layout.setCanLoadMore(true);
        }
    }

    /**
     * 获取好物列表
     */
    private void getList() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_MARKET_TEMPLATE);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageId", "goodProduct");
        requestMap.put("rappId", "oleMarketTemplate");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, HWBean.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HWBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HWBean response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            showData(response.getRETURN_DATA());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();

                    }
                });

    }
}
