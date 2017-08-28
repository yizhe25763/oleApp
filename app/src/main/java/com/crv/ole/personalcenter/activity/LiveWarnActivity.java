package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.personalcenter.adapter.LogisticsHelperAdapter;
import com.crv.ole.personalcenter.model.LogisticsListData;
import com.crv.ole.personalcenter.model.MessageItemData;
import com.crv.ole.personalcenter.model.MessageListData;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.DateTimeUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - 消息中心页面 - 直播提醒页面
 */

public class LiveWarnActivity extends BaseActivity {
    @BindView(R.id.logistics_helper_list_layout)
    PullToRefreshLayout logistics_helper_list_layout;

    @BindView(R.id.logistics_helper_list)
    ListView logistics_helper_list;

    private LogisticsHelperAdapter adapter;
    private List<MessageItemData> dataList;

    private int pageNum = 0, pageCount = 10, totalPage = 0;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_helper);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.live_warn_title);

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
        params.put("messageType", "live");
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

}
