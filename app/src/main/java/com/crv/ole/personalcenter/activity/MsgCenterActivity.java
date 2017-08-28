package com.crv.ole.personalcenter.activity;

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
import com.crv.ole.personalcenter.adapter.MsgListAdapter;
import com.crv.ole.personalcenter.model.MessageData;
import com.crv.ole.personalcenter.model.MessageItemData;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - 消息中心页面
 */

public class MsgCenterActivity extends BaseActivity {
    @BindView(R.id.msg_list_layout)
    PullToRefreshLayout msg_list_layout;

    @BindView(R.id.msg_list)
    ListView msg_list;

    List<MessageItemData> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_center);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.msg_title);
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setBackgroundResource(R.drawable.msg_service_btn_selector);
        title_iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornModel.openChat(mContext);
            }
        });

        msg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.equals(msgList.get(position).getMessageType(), "orderShipping")) {
                    startActivity(
                            new Intent(MsgCenterActivity.this, LogisticsHelperActivity.class)
                                    .putExtra("message_type","orderShipping"));
                } else if (TextUtils.equals(msgList.get(position).getMessageType(), "live")) {
                    startActivity(new Intent(MsgCenterActivity.this, LiveWarnActivity.class));
                } else {
                    startActivity(new Intent(MsgCenterActivity.this, LogisticsHelperActivity.class)
                            .putExtra("message_type","shopping"));
                }
            }
        });

        msg_list_layout.setCanLoadMore(false);
        msg_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                initData();
            }

            @Override
            public void loadMore() {
            }
        });

        initData();
    }

    private void initData() {
        ServiceManger.<String>getInstance().messageCenter(new HashMap<>(), new BaseRequestCallback<String>() {
            @Override
            public void onSuccess(String data) {
                mDialog.dissmissDialog();
                Log.i(data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (TextUtils.equals(jsonObject.optString("RETURN_CODE"), OleConstants.REQUES_SUCCESS)) {
                        JSONObject object = jsonObject.optJSONObject("RETURN_DATA");
                        if (object.has("order") && object.optJSONObject("order") != null) {
                            JSONObject orderObject = object.optJSONObject("order");
                            MessageItemData msgData = new MessageItemData();
                            msgData.setContent(orderObject.optString("content"));
                            msgData.setTitle(orderObject.optString("title"));
                            msgData.setMessageType(orderObject.optString("messageType"));
                            msgData.setSendTime(orderObject.optString("sendTime"));
                            msgData.setTargetObjId(orderObject.optString("targetObjId"));

                            msgList.add(msgData);
                        }

                        if (object.has("shopping") && object.optJSONObject("shopping") != null) {
                            JSONObject shoppingObject = object.optJSONObject("shopping");
                            MessageItemData msgData = new MessageItemData();
                            msgData.setContent(shoppingObject.optString("content"));
                            msgData.setTitle(shoppingObject.optString("title"));
                            msgData.setMessageType(shoppingObject.optString("messageType"));
                            msgData.setSendTime(shoppingObject.optString("sendTime"));
                            msgData.setTargetObjId(shoppingObject.optString("targetObjId"));

                            msgList.add(msgData);
                        }

                        if (object.has("live") && object.optJSONObject("live") != null) {
                            JSONObject liveObject = object.optJSONObject("live");
                            MessageItemData msgData = new MessageItemData();
                            msgData.setContent(liveObject.optString("content"));
                            msgData.setTitle(liveObject.optString("title"));
                            msgData.setMessageType(liveObject.optString("messageType"));
                            msgData.setSendTime(liveObject.optString("sendTime"));
                            msgData.setTargetObjId(liveObject.optString("targetObjId"));

                            msgList.add(msgData);
                        }

                        msg_list.setAdapter(new MsgListAdapter(MsgCenterActivity.this, msgList));
                    } else {
                        ToastUtil.showToast(jsonObject.optString("RETURN_DESC"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
