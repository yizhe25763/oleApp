package com.crv.ole.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.adapter.HWAdapter;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.activity.GoodsTrialReportActivity;
import com.crv.ole.shopping.activity.HwDetailActivity;
import com.crv.ole.trial.adapter.TrialReportGoodsListAdapter;
import com.crv.ole.trial.model.TrialReportGoodsItemData;
import com.crv.ole.trial.model.TrialReportGoodsListData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
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
 * 作用描述：具有试用报告的试用商品列表页面
 * 创建者： wj_wsf
 * 创建时间： 2017/8/24 17:11.
 */

public class TrialReportGoodsListActivity extends BaseActivity {

    private int pageNum = 0, pageTotal;
    private boolean isRefresh = false;

    private GridView trial_report_goods_list_gridview;
    private PullToRefreshLayout trial_report_goods_list_layout;
    private TrialReportGoodsListAdapter adapter;
    private List<TrialReportGoodsItemData> dataList;

    private void assignViews() {
        trial_report_goods_list_gridview = (GridView) findViewById(R.id.trial_report_goods_list_gridview);
        trial_report_goods_list_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, GoodsTrialReportActivity.class)
                        .putExtra("productId", dataList.get(position).getProductId()));
            }
        });

        trial_report_goods_list_layout = (PullToRefreshLayout) findViewById(R.id.trial_report_goods_list_layout);
        trial_report_goods_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 0;
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
        setContentView(R.layout.activity_trial_report_goods_list_layout);

        assignViews();
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.trial_report_product_list_title);

        getList();
    }

    private void showData(List<TrialReportGoodsItemData> datas) {
        if (datas == null || datas.size() < 1)
            return;
        if (dataList == null || adapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(datas);
            adapter = new TrialReportGoodsListAdapter(mContext, dataList);
            trial_report_goods_list_gridview.setAdapter(adapter);
        } else {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
                trial_report_goods_list_layout.finishRefresh();
            } else {
                trial_report_goods_list_layout.finishLoadMore();
            }
            dataList.addAll(datas);
            adapter.notifyDataSetChanged();

        }

        if (datas.size() < 10) {
            trial_report_goods_list_layout.setCanLoadMore(false);
        } else {
            trial_report_goods_list_layout.setCanLoadMore(true);
        }
    }

    /**
     * 获取好物列表
     */
    private void getList() {
        Map<String, String> map = new HashMap<>();
        map.put("limit", "10");
        map.put("start", "" + pageNum);
        ServiceManger.getInstance().getTrialReportGoodsList(map,
                new BaseRequestCallback<TrialReportGoodsListData>() {
                    @Override
                    public void onSuccess(TrialReportGoodsListData data) {
                        if (TextUtils.equals(OleConstants.REQUES_SUCCESS, data.getRETURN_CODE())) {
                            showData(data.getRETURN_DATA());
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }
                });
    }
}
