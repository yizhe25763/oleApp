package com.crv.ole.shopping.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crv.ole.R;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.shopping.adapter.TrialReportListAdapter;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
import com.crv.ole.shopping.ui.ProductListView;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.fragment.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fairy on 2017/7/26.
 * 商品详情页 - 详情页 - 试用报告
 */

public class TrialReportFragment extends BaseFragment {
    @BindView(R.id.listView)
    ProductListView listView;
    private Unbinder unbinder;
    private static TrialReportFragment trialReportFragment;
    private TrialReportListAdapter adapter;
    private List<TrialReportInfoData.RETURNDATABean.ListBean> dataList;
    private String productId;
    private int pageNum = 1;


    public static TrialReportFragment getInstance(){
        if(trialReportFragment == null) {
            trialReportFragment = new TrialReportFragment();
        }
        return trialReportFragment;
    }

    /**
     * 设置页面参数显示
     * @param id
     */
    public void loadData(String id){
        if(TextUtils.isEmpty(productId)) {
            productId = id;
//            productId = "p_2670631";
            getTrialReport(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_trialreport_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            dataList = new ArrayList<>();
            adapter = new TrialReportListAdapter(mContext, dataList);
            adapter.setListener(new SpecialCommentActivity.ZanInterf() {
                @Override
                public void requestZannet(int type, int position) {
                    zanTrialReport(type, position);
                }
            });
            listView.setAdapter(adapter);
        }
        return view;
    }


    /**
     * 获取试用报告
     * @param type 0-普通刷新 1 -下拉加载
     */
    private void getTrialReport(int type){
        if(type == 1) {
            mDialog.showProgressDialog("加载中...", null);
        }
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId",productId);
        requestMap.put("pageNum", pageNum+"");
        ServiceManger.getInstance().getTrialReport(requestMap,
                new BaseRequestCallback<TrialReportInfoData>() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(TrialReportInfoData data) {
                        Log.i("试用报告详情："+new Gson().toJson(data));
                        if(data.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)){
                            if(data.getRETURN_DATA()!=null){
                                if(data.getRETURN_DATA().getList() != null){
                                    if(data.getRETURN_DATA().getList().size() > 0) {
                                        dataList.addAll(data.getRETURN_DATA().getList());
                                        adapter.notifyDataSetChanged();
                                    }else{
                                        ToastUtil.showToast("没有什么可看的了哦");
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.i("试用报告获取失败："+msg);
                        ToastUtil.showToast("获取试用报告失败！");
                    }

                    @Override
                    public void onEnd() {
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 试用报告点赞
     */
    private void zanTrialReport(int type, int position){
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id",dataList.get(position).getId());
        ServiceManger.getInstance().zanTrialReport(requestMap,
                new BaseRequestCallback<ZanBean>() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(ZanBean data) {
                        Log.i("试用报告点赞详情："+new Gson().toJson(data));
                        if(TextUtils.equals(data.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)){
                            if(type == 0){
                                dataList.get(position).getLikesInfo().setStatus(1);
                            }else if(type == 1){
                                dataList.get(position).getLikesInfo().setStatus(0);
                            }
                            ToastUtil.showToast("点赞情况："+data.getRETURN_DATA().getStatus());
                            dataList.get(position).getLikesInfo().setLikesCount(data.getRETURN_DATA().getLikesCount());
                            adapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast("试用报告点赞失败！");
                    }

                    @Override
                    public void onEnd() {}
                });
    }
}
