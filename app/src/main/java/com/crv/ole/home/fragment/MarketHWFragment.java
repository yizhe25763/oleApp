package com.crv.ole.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.home.adapter.HWAdapter;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.activity.HwDetailActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

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
 * 商城好物频道
 */
public class MarketHWFragment extends BaseFragment {

    private HWAdapter pullToRefreshAdapter;
    private View mViews;

    private List<HWBean.RETURNDATABean> RETURN_DATA;
    private ListView listView;
    private PullToRefreshLayout fragment_market_special_layout;


    public static MarketHWFragment getInstance() {
        MarketHWFragment fragment = new MarketHWFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViews = inflater.inflate(R.layout.fragment_market_selection_layout, container, false);
        listView = (ListView) mViews.findViewById(R.id.list);
        fragment_market_special_layout = (PullToRefreshLayout) mViews.findViewById(R.id.fragment_market_special_layout);
        getList();
        initListener();
        return mViews;
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
                            RETURN_DATA = response.getRETURN_DATA();
                            pullToRefreshAdapter = new HWAdapter(getActivity(), RETURN_DATA);
                            listView.setAdapter(pullToRefreshAdapter);
                        }
                        fragment_market_special_layout.finishRefresh();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        fragment_market_special_layout.finishRefresh();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();
                        fragment_market_special_layout.finishRefresh();

                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            Log.i("MarketSelectionFragment显示");
            //            isVisible = true;
        } else {
            Log.i("MarketSelectionFragment隐藏");
            //            isVisible = false;
        }
    }


    private void initListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), HwDetailActivity.class);
                intent.putExtra("imgLinkTo", RETURN_DATA.get(position).getImgLinkTo());
                startActivity(intent);
            }
        });
        fragment_market_special_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getList();
            }
            @Override
            public void loadMore() {
                fragment_market_special_layout.finishLoadMore();
            }
        });

    }
}
