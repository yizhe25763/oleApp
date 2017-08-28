package com.crv.ole.trial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.activity.CardIntroduceActivity;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
import com.crv.ole.trial.adapter.TrialInfoProductAdapter;
import com.crv.ole.trial.adapter.TrialInfoReportAdapter;
import com.crv.ole.trial.callback.OnProductItemClickListener;
import com.crv.ole.trial.callback.OnReportItemClickListener;
import com.crv.ole.trial.model.TrialInfoData;
import com.crv.ole.trial.model.TrialInfoResult;
import com.crv.ole.trial.model.TrialProduct;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.DateUtil;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codeboy.android.aligntextview.AlignTextView;

/**
 * 试用商品列表Activity
 * Created by zhangbo on 2017/8/12.
 */

public class TrialInfoActivity extends BaseActivity {

    private LayoutInflater inflater;

    private ImageView im_title;//大图

    private TextView tx_title;//标题

    private TextView tx_time;//日期

    private AlignTextView tx_content;//内容

    private RadioGroup rg_trial_info;//商品报告选择

    private RadioButton rb_trial_product;//商品

    private RadioButton rb_trial_report;//报告


    @BindView(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    private ViewGroup endview;

    private String url;//大图URL

    private String title;//标题

    private CustomDiaglog customDiaglog;

    private List<TrialProduct> productList = new ArrayList<>();//商品列表

    private List<TrialReportInfoData.RETURNDATABean.ListBean> reporctList = new ArrayList<>();//报告列表


    private String activeId;

    private View headView;
    private View footView;

    private ListView listView;

    private TrialInfoProductAdapter productAdapter;//商品


    private TrialInfoReportAdapter reportListAdapter;//报告

    private int pageNum = 1;

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_pdt_info_layout);
        ButterKnife.bind(this);
        initTitleViews();
        inflater = LayoutInflater.from(this);
        initUI();
        initData();
        initEvent();
        getTrialInfo();
    }

    private void initUI() {
        if (!TextUtil.isEmpty(getIntent().getStringExtra("from"))) {
            from = getIntent().getStringExtra("from");
        }
        endview = (ViewGroup) findViewById(R.id.endview);
        headView = inflater.inflate(R.layout.activity_trial_pdt_info_head_layout, null);
        footView = inflater.inflate(R.layout.the_end_layout, null);
        im_title = (ImageView) headView.findViewById(R.id.im_title);
        tx_title = (TextView) headView.findViewById(R.id.tx_title);
        tx_time = (TextView) headView.findViewById(R.id.tx_time);
        tx_content = (AlignTextView) headView.findViewById(R.id.tx_content);
        rg_trial_info = (RadioGroup) headView.findViewById(R.id.rg_trial_info);
        rb_trial_product = (RadioButton) headView.findViewById(R.id.rb_trial_product);
        rb_trial_report = (RadioButton) headView.findViewById(R.id.rb_trial_report);
        listView = pullToRefreshListView.getRefreshableView();
        listView.addHeaderView(headView);
        productAdapter = new TrialInfoProductAdapter(this, productList);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        pullToRefreshListView.setAdapter(productAdapter);
        reportListAdapter = new TrialInfoReportAdapter(this, reporctList);

        productAdapter.setOnProductItemClickListener(new OnProductItemClickListener() {
            @Override
            public void OnButtonItemClick(TrialProduct data, int position) {

                //TODO 申请试用
                if (!ToolUtils.isLoginStatus(TrialInfoActivity.this)) {
                    startActivityWithAnim(LoginActivity.class);
                    return;
                } else {
                    //只有会员才能试用
                    if (!"common".equals(BaseApplication.getInstance().getUserCenter().getMemberlevel())) {
                        Intent intent = new Intent(TrialInfoActivity.this, TrialProductDetilActivity.class);
                        intent.putExtra("url", data.getProductImage());
                        intent.putExtra("count", data.getSellNum() + "");
                        intent.putExtra("name", data.getName() + "");
                        intent.putExtra("price", data.getMemberPrice() + "");
                        intent.putExtra("content", data.getProductDescription());
                        intent.putExtra("productObjId", data.getProductObjId());
                        intent.putExtra("activeId", activeId);
                        startActivityWithAnim(intent);

                    } else {
                        //showDiaglog
                        if (customDiaglog == null) {
                            customDiaglog = new CustomDiaglog(TrialInfoActivity.this, getString(R.string.trial_dialog_title), getString(R.string.trial_dialog_cancle), getString(R.string.trial_dialog_confim));
                        }
                        customDiaglog.setOnConfimClickListerner(new CustomDiaglog.OnConfimClickListerner() {
                            @Override
                            public void onCanleClick() {
                                if (customDiaglog != null && customDiaglog.isShowing()) {
                                    customDiaglog.dismiss();
                                }
                            }

                            @Override
                            public void OnConfimClick() {
                                //绑定会员卡
                                startActivityWithAnim(new Intent(mContext, CardIntroduceActivity.class).putExtra("source", "bind"));
                            }
                        });
                        customDiaglog.show();
                    }
                }

            }
        });

        reportListAdapter.setOnReportItemClickListener(new OnReportItemClickListener() {
            @Override
            public void OnReportZanItemClick(TrialReportInfoData.RETURNDATABean.ListBean data, int position) {
                zanTrialReport(data, position);
            }
        });
    }

    //初始化数据
    private void initData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        activeId = getIntent().getStringExtra("activeId");
        //activeId = "60000";
        LoadImageUtil.getInstance().loadImage(im_title, url);
        tx_title.setText(title);
        title_name_tv.setText(getString(R.string.trial_title));
    }

    //初始化事件
    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        rg_trial_info.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_trial_product://试用商品
                        endview.setVisibility(View.GONE);
                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        pullToRefreshListView.setAdapter(productAdapter);
                        listView.removeFooterView(footView);
                        listView.addFooterView(footView);
                        productAdapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_trial_report://试用报告
                        listView.removeFooterView(footView);
                        pullToRefreshListView.setAdapter(reportListAdapter);
                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                            @Override
                            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                            }

                            @Override
                            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                listView.removeFooterView(footView);
                                pageNum++;
                                getTrialReport(false);
                            }
                        });
                        getTrialReport(true);
                        break;
                }
            }
        });
        rb_trial_product.setChecked(true);

        if (!TextUtil.isEmpty(from)) {
            if ("report".equals(from)) {//来自试用报告
                rb_trial_report.setChecked(true);
                listView.removeFooterView(footView);
                pullToRefreshListView.setAdapter(reportListAdapter);
                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                        listView.removeFooterView(footView);
                        pageNum++;
                        getTrialReport(false);
                    }
                });
                getTrialReport(true);
            }
        }
    }

    /**
     * 服务器试用详情
     */
    private void getTrialInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("activeId", activeId);
        ServiceManger.getInstance().getTryOutDetails(params, new BaseRequestCallback<TrialInfoResult>() {

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
            public void onSuccess(TrialInfoResult data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) && data != null) {
                    refreshProductView(data.getRETURN_DATA());
                }
            }
        });
    }


    //获取试用报告
    private void getTrialReport(boolean isRefresh) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("activityId", activeId);
        //requestMap.put("activityId", "test3");
        requestMap.put("examineStatus", "1");
        if (isRefresh) {
            requestMap.put("pageNum", 1 + "");
        } else {
            requestMap.put("pageNum", pageNum + "");
        }

        ServiceManger.getInstance().getTrialReport(requestMap, new BaseRequestCallback<TrialReportInfoData>() {

            @Override
            public void onStart() {
                super.onStart();
                if (isRefresh) {
                    showProgressDialog(R.string.waiting);
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                if (isRefresh) {
                    dismissProgressDialog();
                }
            }

            @Override
            public void onSuccess(TrialReportInfoData data) {
                if (data != null && data.getRETURN_DATA() != null && data.getRETURN_DATA().getList() != null && data.getRETURN_DATA().getList().size() > 0) {
                    if (isRefresh) {
                        listView.addFooterView(footView);
                        endview.setVisibility(View.GONE);
                        reporctList.clear();
                    }
                    reporctList.addAll(data.getRETURN_DATA().getList());
                    reportListAdapter.notifyDataSetChanged();
                } else {
                    if (!isRefresh) {
                        pullToRefreshListView.onRefreshComplete();
                        listView.addFooterView(footView);
                        endview.setVisibility(View.GONE);
                    } else {
                        ToastUtil.showToast(getString(R.string.no_trial_report));
                        endview.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 刷新商品数据
     *
     * @param data
     */
    private void refreshProductView(TrialInfoData data) {
        Date start = DateUtil.parse(data.getActiveObj().getBeginTime(), "yyyy-MM-dd");
        int startMonth = start.getMonth();
        int startDate = start.getDate();

        Date end = DateUtil.parse(data.getActiveObj().getEndTime(), "yyyy-MM-dd");
        int endMonth = end.getMonth();
        int endDate = end.getDate();

        tx_time.setText("申请日期: " + startMonth + "月" + startDate + "日——" + endMonth + "月" + endDate + "日");
        tx_content.setText(data.getActiveObj().getDescription());

        productList.clear();
        for (TrialProduct product : data.getProductList()) {
            productList.add(product);
        }
        productAdapter.notifyDataSetChanged();
    }


    /**
     * 试用报告点赞
     */
    private void zanTrialReport(TrialReportInfoData.RETURNDATABean.ListBean listBean, int position) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", reporctList.get(position).getId());
        ServiceManger.getInstance().zanTrialReport(requestMap,
                new BaseRequestCallback<ZanBean>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(ZanBean data) {
                        Log.i("试用报告点赞详情：" + new Gson().toJson(data));
                        if (TextUtils.equals(data.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)) {
                            listBean.getLikesInfo().setStatus(reporctList.get(position).getLikesInfo().getStatus() == 1 ? 0 : 1);
                            listBean.getLikesInfo().setLikesCount(data.getRETURN_DATA().getLikesCount());
                            reportListAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
        }
    }
}
