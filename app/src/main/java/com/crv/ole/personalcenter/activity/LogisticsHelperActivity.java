package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.adapter.LogisticsHelperAdapter;
import com.crv.ole.personalcenter.adapter.MsgListAdapter;
import com.crv.ole.personalcenter.model.LogisticsListData;
import com.crv.ole.personalcenter.model.MessageItemData;
import com.crv.ole.personalcenter.model.MessageListData;
import com.crv.ole.shopping.activity.ProductDetailActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.DateTimeUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - 消息中心页面 - 物流助手、购物助手页面
 */

public class LogisticsHelperActivity extends BaseActivity {
    @BindView(R.id.logistics_helper_list_layout)
    PullToRefreshLayout logistics_helper_list_layout;

    @BindView(R.id.logistics_helper_list)
    ListView logistics_helper_list;

    private LogisticsHelperAdapter adapter;
    private List<MessageItemData> dataList;

    private int pageNum = 0, pageCount = 10, totalPage = 0;
    private boolean isRefresh = false;

    private String messageType = "orderShipping";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_helper);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();

        messageType = getIntent().getStringExtra("message_type");
        if (TextUtils.equals(messageType, "orderShipping")) {
            setBarTitle(R.string.logistics_helper_title);
        } else if (TextUtils.equals(messageType, "shopping")) {
            setBarTitle(R.string.shopping_helper_title);
        }

        logistics_helper_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.equals(messageType, "orderShipping")) {
                    startActivityWithAnim(new Intent(mContext, LogisticsDetailsActivity.class)
                            .putExtra(OleConstants.BundleConstant.ARG_PARAMS_0,
                                    dataList.get(position).getTargetObjId()));
                } else if (TextUtils.equals(messageType, "shopping")) {
                    startActivity(new Intent(mContext, ProductDetailActivity.class)
                            .putExtra("productId", dataList.get(position).getTargetObjId()));
                }
                readMessage(dataList.get(position).getId());
            }
        });

        logistics_helper_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 0;
                isRefresh = true;
                logistics_helper_list_layout.setCanLoadMore(false);
                getData();
            }

            @Override
            public void loadMore() {
                getData();
            }
        });

        getData();
    }

    private void showData(List<MessageItemData> lists) {
        if (lists == null || lists.size() == 0) {
            ToastUtil.showToast("未获取到数据");
            return;
        }

        if (dataList == null || adapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(lists);
            adapter = new LogisticsHelperAdapter(this, dataList);
            logistics_helper_list.setAdapter(adapter);
        } else {
            if (isRefresh) {
                dataList.clear();
                logistics_helper_list_layout.finishRefresh();
            } else {
                logistics_helper_list_layout.finishLoadMore();
            }
            dataList.addAll(lists);
            adapter.notifyDataSetChanged();
        }

        if (pageNum <= totalPage)
            logistics_helper_list_layout.setCanLoadMore(true);
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("limit", pageCount + "");
        if (TextUtils.equals(messageType, "orderShipping")) {
            params.put("messageType", "orderShipping");
        } else if (TextUtils.equals(messageType, "shopping")) {
            params.put("messageType", "shopping");
        }
        params.put("page", (pageNum += 1) + "");
        ServiceManger.<MessageListData>getInstance().getMessageList(params,
                new BaseRequestCallback<MessageListData>() {
                    @Override
                    public void onSuccess(MessageListData data) {
                        if (TextUtils.equals(data.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)) {
                            mDialog.dissmissDialog();
                            totalPage = data.getRETURN_DATA().getPageCount();
                            showData(data.getRETURN_DATA().getList());
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mDialog.showProgressDialog("加载中……", null);
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        mDialog.dissmissDialog();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void readMessage(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        ServiceManger.<BaseResponseData>getInstance().messageNotifyRead(params,
                new BaseRequestCallback<BaseResponseData>() {
                    @Override
                    public void onSuccess(BaseResponseData data) {
                        if (TextUtils.equals(data.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)) {
                            Log.i("修改通知为已读状态成功");
                        } else {
                            Log.i("修改通知为已读状态失败," + data.getRETURN_DESC());
                        }

                    }

                });
    }
}
