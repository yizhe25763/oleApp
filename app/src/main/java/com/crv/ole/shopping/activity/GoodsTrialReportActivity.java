package com.crv.ole.shopping.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.shopping.adapter.TrialReportListAdapter;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
import com.crv.ole.shopping.ui.ProductListView;
import com.crv.ole.trial.model.MobileContent;
import com.crv.ole.trial.model.TrialProductDetilData;
import com.crv.ole.trial.model.TrialProductDetilResult;
import com.crv.ole.utils.LoadImageUtil;
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

import butterknife.BindView;
import me.codeboy.android.aligntextview.AlignTextView;

/**
 * 作用描述：商品试用报告列表页面
 * 创建者： wj_wsf
 * 创建时间： 2017/8/24 17:11.
 */

public class GoodsTrialReportActivity extends BaseActivity {

    private TrialReportListAdapter adapter;
    private List<TrialReportInfoData.RETURNDATABean.ListBean> dataList;
    private String productId, product_content;
    private int pageNum = 1, pageTotal;
    private boolean isRefresh = false;

    private PullToRefreshLayout trialReportRefreshLayout;
    private LinearLayout trial_report_goods_info_layout;
    private NestedScrollView fragmentHomeScroll;
    private ImageView imProduct;
    private TextView txCount;//数量
    private TextView txName;//商品名称
    private TextView txPrice;//商品价格
    private AlignTextView txDesc;//商品描述
    private TextView txCurrentCount;
    private TextView txCurrentPeoper;
    private ProductListView trialReportLv;

    private void assignViews() {
        trialReportRefreshLayout = (PullToRefreshLayout) findViewById(R.id.trial_report_refresh_layout);
        trialReportRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 1;
                isRefresh = true;
                getTrialReport(1);
            }

            @Override
            public void loadMore() {
                pageNum += 1;
                getTrialReport(1);
            }
        });

        trial_report_goods_info_layout = (LinearLayout) findViewById(R.id.trial_report_goods_info_layout);
        fragmentHomeScroll = (NestedScrollView) findViewById(R.id.fragment_home_scroll);
        imProduct = (ImageView) findViewById(R.id.im_product);
        txCount = (TextView) findViewById(R.id.tx_count);
        txName = (TextView) findViewById(R.id.tx_name);
        txPrice = (TextView) findViewById(R.id.tx_price);
        txDesc = (AlignTextView) findViewById(R.id.tx_desc);
        txCurrentCount = (TextView) findViewById(R.id.tx_current_count);
        txCurrentPeoper = (TextView) findViewById(R.id.tx_current_peoper);
        trialReportLv = (ProductListView) findViewById(R.id.trial_report_lv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_report_layout);

        assignViews();
        productId = getIntent().getStringExtra("productId");
        product_content = getIntent().getStringExtra("content");

        initTitleViews();
        initBackButton();
        setBarTitle(R.string.trial_report);

        getProductDate();
    }

    private void showData(List<TrialReportInfoData.RETURNDATABean.ListBean> datas) {
        if (datas == null || datas.size() < 1) {
            if (isRefresh) {
                trialReportLv.setVisibility(View.GONE);
            } else {
                trialReportLv.setVisibility(View.VISIBLE);
            }
            return;
        }

        if (dataList == null || adapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(datas);
            adapter = new TrialReportListAdapter(mContext, dataList);
            adapter.setListener(new SpecialCommentActivity.ZanInterf() {
                @Override
                public void requestZannet(int type, int position) {
                    zanTrialReport(type, position);
                }
            });
            trialReportLv.setAdapter(adapter);
            trialReportLv.setVisibility(View.VISIBLE);
        } else {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
                trialReportRefreshLayout.finishRefresh();
            } else {
                trialReportRefreshLayout.finishLoadMore();
            }
            dataList.addAll(datas);
            adapter.notifyDataSetChanged();

        }

        if (pageNum >= pageTotal) {
            trialReportRefreshLayout.setCanLoadMore(false);
        } else {
            trialReportRefreshLayout.setCanLoadMore(true);
        }
    }

    /**
     * 获取试用报告
     *
     * @param type 0-普通刷新 1 -下拉加载
     */
    private void getTrialReport(int type) {
        if (type == 0) {
            mDialog.showProgressDialog("加载中...", null);
        }
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId", "p_2670631");
//        requestMap.put("productId", productId);
        requestMap.put("pageNum", pageNum + "");
        ServiceManger.getInstance().getTrialReport(requestMap,
                new BaseRequestCallback<TrialReportInfoData>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(TrialReportInfoData data) {
                        Log.i("试用报告详情：" + new Gson().toJson(data));
                        if (data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            if (data.getRETURN_DATA() != null) {
                                pageTotal = data.getRETURN_DATA().getNumOfPage();
                                showData(data.getRETURN_DATA().getList());
                            }
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.i("试用报告获取失败：" + msg);
                        ToastUtil.showToast("获取试用报告失败！");
                    }

                    @Override
                    public void onEnd() {
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void getProductDate() {
        Map<String, String> params = new HashMap<>();
//        params.put("productObjId", "tryOutManage_product_50000");
        params.put("productObjId", productId);
        ServiceManger.getInstance().getTryOutProductDetails(params, new BaseRequestCallback<TrialProductDetilResult>() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(R.string.waiting);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(TrialProductDetilResult data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    if (data.getRETURN_DATA() != null) {
                        //刷新数据
                        updateView(data.getRETURN_DATA());
                    }


                } else {
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }

    private void updateView(TrialProductDetilData detilData) {
        trial_report_goods_info_layout.setVisibility(View.VISIBLE);

        LoadImageUtil.getInstance().loadImage(imProduct, detilData.getProductImg().get(0).getUrl());
        txCount.setText("评测");
//        txCount.setBackgroundResource(R.drawable.ic_sqsypc);
        txCurrentCount.setText(String.format(getString(R.string.count), detilData.getSellNum()));
        txCurrentPeoper.setText(String.format(getString(R.string.peporer), detilData.getTotalRecords() + ""));
//        tx_count.setText(String.format(getString(R.string.num), product_count));
        txName.setText(detilData.getName());
        txPrice.setText(String.format(getString(R.string.price), detilData.getPrice()));
        txDesc.setText(product_content);

        getTrialReport(0);
    }

    /**
     * 试用报告点赞
     */
    private void zanTrialReport(int type, int position) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", dataList.get(position).getId());
        ServiceManger.getInstance().zanTrialReport(requestMap,
                new BaseRequestCallback<ZanBean>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(ZanBean data) {
                        Log.i("试用报告点赞详情：" + new Gson().toJson(data));
                        if (TextUtils.equals(data.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)) {
                            if (type == 0) {
                                dataList.get(position).getLikesInfo().setStatus(1);
                            } else if (type == 1) {
                                dataList.get(position).getLikesInfo().setStatus(0);
                            }
                            ToastUtil.showToast("点赞情况：" + data.getRETURN_DATA().getStatus());
                            dataList.get(position).getLikesInfo().setLikesCount(data.getRETURN_DATA().getLikesCount());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("试用报告点赞失败！");
                    }

                    @Override
                    public void onEnd() {
                    }
                });
    }
}
