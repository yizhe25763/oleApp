package com.crv.ole.shopping.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.NullBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.model.HotSearchBean;
import com.crv.ole.shopping.model.SearchHistoryData;
import com.crv.ole.shopping.model.SearchHistoryModel;
import com.crv.ole.shopping.model.SearchSection;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.HeadView.StickyGridHeadersBaseAdapter;
import com.crv.sdk.HeadView.StickyGridHeadersGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Yang on 2017/7/28.
 * 超市 - 搜索及分类页 - 搜索页
 */

public class MarketSearchActivity extends BaseActivity {

    @BindView(R.id.search_gridview)
    StickyGridHeadersGridView search_gridview;

    @BindView(R.id.search_et)
    EditText search_et;

    HotSearchBean searchBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketsearch);
        ButterKnife.bind(this);
        initLister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchHotSearchData();
    }

    private void initLister() {
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("acitonId:" + actionId);
                String text = search_et.getText().toString().trim();
                if (text.length() > 0) {
                    SearchHistoryModel.insertHistory(text);
                    hideSoftInput();
                    gotoProductListActivity(mContext, text);
                    finish();
                } else {
                    ToastUtil.showToast("请输入搜索信息");
                }
                return true;
            }
        });
    }

    private void fetchHotSearchData() {
        showProgressDialog("加载中...", null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.HOT_SERACH);
        requestData.setREQUEST_DATA(new NullBean());
        ServerApi.request(false, requestData, HotSearchBean.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotSearchBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HotSearchBean response) {
                        dismissProgressDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            searchBean = response;
                            packData();
                        } else {
                            ToastUtil.showToast("获取热所失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissProgressDialog();
                        ToastUtil.showToast("网络错误");
                    }

                    @Override
                    public void onComplete() {
                        dismissProgressDialog();
                    }
                });

    }

    private void packData() {
        ArrayList<SearchSection> mdata = new ArrayList<>();

        //1:热搜
        List<String> hotSearch = searchBean.getRETURN_DATA().getHotWord();
        SearchSection section1 = new SearchSection();
        section1.setSectionName("热搜");

        List<SearchHistoryData> list = new ArrayList<>();
        for (String hot : hotSearch) {
            SearchHistoryData item = new SearchHistoryData();
            item.setText(hot);
            list.add(item);
        }
        section1.setSearchList(list);
        mdata.add(section1);
        //2:历史搜索
        List<SearchHistoryData> history = SearchHistoryModel.queryAllSearchHistory();
        if (history.size() > 0) {
            SearchSection section2 = new SearchSection();
            section2.setSectionName("历史搜索");
            section2.setSearchList(history);
            mdata.add(section2);
        }

        search_gridview.setAdapter(new SearchAdapter(this, mdata));
    }

    @OnClick({R.id.search_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:
                finish();
                break;
        }
    }

    class SearchAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {

        public ArrayList<SearchSection> mItemList;

        public ArrayList<SearchHistoryData> totalItemList = new ArrayList<>();

        private Context cxt;

        public SearchAdapter(Context cxt, ArrayList<SearchSection> list) {
            this.cxt = cxt;
            this.mItemList = list;
            notifyDataChange();
        }

        public void notifyDataChange() {

            totalItemList.clear();

            for (SearchSection section : this.mItemList) {
                for (SearchHistoryData historyData : section.getSearchList()) {
                    totalItemList.add(historyData);
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            SearchHistoryData historyData = totalItemList.get(position);
            return historyData;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getNumHeaders() {

            return mItemList == null ? 0 : mItemList.size();
        }

        @Override
        public int getCountForHeader(int header) {

            SearchSection section = mItemList.get(header);

            return section.getSearchList().size();
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(cxt).inflate(R.layout.serach_head, null);
            }

            SearchSection section = mItemList.get(position);
            String head = section.getSectionName();
            TextView headView = (TextView) convertView.findViewById(R.id.headname_tv);
            headView.setText(head);

            ImageButton delete_btn = (ImageButton) convertView.findViewById(R.id.delete_btn);
            if (position == 1) {
                delete_btn.setVisibility(View.VISIBLE);
            } else {
                delete_btn.setVisibility(View.INVISIBLE);
            }
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchHistoryModel.deleteAll();
                    if (mItemList.size() > 1) {
                        mItemList.remove(1);
                        notifyDataChange();
                    }
                }
            });

            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(cxt).inflate(R.layout.serach_item, null);
            }

            TextView itemView = (TextView) convertView.findViewById(R.id.itemname_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoProductListActivity(cxt, totalItemList.get(position).getText());
                    finish();
                }
            });
            SearchHistoryData historyData = totalItemList.get(position);
            itemView.setText(historyData.getText());

            return convertView;
        }
    }


    private static void gotoProductListActivity(Context context, String text) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, text);
        context.startActivity(intent);
    }
}
