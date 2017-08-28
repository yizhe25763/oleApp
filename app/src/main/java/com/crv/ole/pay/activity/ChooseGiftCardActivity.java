package com.crv.ole.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.NullBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.adapter.GiftCardListAdapter;
import com.crv.ole.pay.model.GiftCardItemData;
import com.crv.ole.pay.model.GiftCardListData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.PreferencesHelper;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 使用礼品卡页面
 *
 * @author wj_wsf
 */
public class ChooseGiftCardActivity extends BaseActivity {
    @BindView(R.id.choose_gift_card_list_layout)
    PullToRefreshLayout choose_gift_card_list_layout;
    @BindView(R.id.choose_gift_card_list)
    ListView choose_gift_card_list;
    @BindView(R.id.choose_gift_card_amount)
    TextView choose_gift_card_amount;
    @BindView(R.id.choose_gift_card_confirm_btn)
    Button choose_gift_card_confirm_btn;

    private List<GiftCardItemData> dataList;
    private ArrayList<GiftCardItemData> selectedList;
    private GiftCardListAdapter mAdapter;
    private GiftCardListAdapter.OnConfirmAmountListener confirmAmountListener;
    private Map<Integer, GiftCardItemData> giftCardAmountMap;

    private boolean isRefresh = false;
    private float amount = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.choose_gift_card_title);
        initView();
        getData();
    }

    private void initView() {
        choose_gift_card_list_layout.setCanLoadMore(false);
        choose_gift_card_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isRefresh = true;
                getData();
            }

            @Override
            public void loadMore() {
                getData();
            }
        });

        choose_gift_card_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (giftCardAmountMap != null && giftCardAmountMap.size() > 0) {
                    if (selectedList == null) {
                        selectedList = new ArrayList<>();
                    }
                    for (GiftCardItemData value : giftCardAmountMap.values()) {
                        selectedList.add(value);
                    }
                }
                setResult(100,
                        new Intent().putParcelableArrayListExtra("gift_card_amount", selectedList));
                finish();
            }
        });

    }

    private void showAmount() {
        amount = 0;
        for (GiftCardItemData value : giftCardAmountMap.values()) {
            amount += value.getSelectedAmount();
        }
        choose_gift_card_amount.setText("合计使用：¥" + String.valueOf(amount));
    }

    private void showData(List<GiftCardItemData> lists) {
        if (lists == null) {
            return;
        }

        if (dataList == null || mAdapter == null) {
            dataList = new ArrayList<>();
            dataList.addAll(lists);
            mAdapter = new GiftCardListAdapter(this, dataList);
            confirmAmountListener = new GiftCardListAdapter.OnConfirmAmountListener() {
                @Override
                public void onConfirmAmount(int position, GiftCardItemData amount) {
                    if (giftCardAmountMap == null)
                        giftCardAmountMap = new HashMap<>();
                    giftCardAmountMap.put(position, amount);
                    showAmount();
                }
            };
            mAdapter.setOnConfirmAmountListener(confirmAmountListener);
            choose_gift_card_list.setAdapter(mAdapter);
        } else {
            if (isRefresh) {
                dataList.clear();
                choose_gift_card_list_layout.finishRefresh();
                isRefresh = false;
                choose_gift_card_amount.setText("合计使用：¥0");
                if (giftCardAmountMap != null)
                    giftCardAmountMap.clear();
                amount = 0;
            }
            dataList.addAll(lists);
            mAdapter.notifyDataSetChanged();
        }

    }

    private void getData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_GIFT_CARD_LIST_ID);
        requestData.setREQUEST_DATA(new NullBean());

        ServerApi.request(false, requestData, GiftCardListData.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftCardListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(GiftCardListData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            showData(response.getRETURN_DATA().getCards());
                        } else {
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("获取礼品卡列表失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取礼品卡列表完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

}
