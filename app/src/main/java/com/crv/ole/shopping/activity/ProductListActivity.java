package com.crv.ole.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.ProductListAdapter;
import com.crv.ole.shopping.model.Product;
import com.crv.ole.shopping.model.ProductResponseData;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.dialog.FragmentDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by lihongshi
 * 搜索结果列表
 */
public class ProductListActivity extends BaseAppCompatActivity implements ProductListAdapter.OnItemTouchListener<Product>, View.OnClickListener {
    private String keyword = "";//关键字
    private String mid = "";//商家ID, 如果不填, 则查询OLE下全部商店的商品
    private String orderBy = "default";
    private FragmentDialog mDialog;
    private PullToRefreshRecyclerView mPullToRefreshRecyclerView;
    private ProductListAdapter mAdapter;
    private int pageNum = 1;
    private EditText search_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyword = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        mid = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_1);
        mDialog = new FragmentDialog(mContext);
        initSearchView();
        initTab();
        initRecyclerView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        keyword = intent.getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        mid = intent.getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_1);
        search_et.setText(keyword);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_et: {
                Intent intent = new Intent(this, MarketSearchActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.search_cancel: {
                finish();
                break;
            }
        }
    }

    private void initSearchView() {
        search_et = (EditText) this.findViewById(R.id.search_et);
        search_et.setOnClickListener(this);
        search_et.setText(keyword);
        this.findViewById(R.id.search_cancel).setOnClickListener(this);
    }

    private void initTab() {
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("综合排序"));
        tabLayout.addTab(tabLayout.newTab().setText("销量排序"));
        tabLayout.addTab(tabLayout.newTab().setText("价格最低"));
        tabLayout.addTab(tabLayout.newTab().setText("价格最高"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        orderBy = "default";
                        break;
                    case 1:
                        orderBy = "saleCount";
                        break;
                    case 2:
                        orderBy = "priceLow";
                        break;
                    case 3:
                        orderBy = "priceHigh";
                        break;
                    default:
                        orderBy = "default";
                        break;
                }
                pageNum = 1;
                mDialog.showProgressDialog("加载中...", null);
                initData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRecyclerView() {
        mPullToRefreshRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.pull_to_refresh_recy);
        mPullToRefreshRecyclerView.setHasPullDownFriction(true);
        RecyclerView recyclerView = mPullToRefreshRecyclerView.getRefreshableView();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new ProductListAdapter(null);
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);
        mPullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                pageNum = 1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> pullToRefreshBase) {
                pageNum++;
                initData();
            }
        });
    }

    @Override
    public void onItemClick(Product item, int position) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra("productId", item.getProductId());
        startActivity(intent);
    }

    @Override
    public void onAddShoppingCart(Product item, int position) {
        addShoppingCart(item);
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);//商家ID, 如果不填, 则查询OLE下全部商店的商品
        map.put("mid", mid);
        map.put("pageNum", pageNum + "");//页码, 默认值为1
        map.put("limit", "20");//页面显示数量, 默认为20
        map.put("orderBy", orderBy);
        ServerApi.postRequest(mContext, OleConstants.KEYWORD_SEARCH, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Type type = new TypeToken<ProductResponseData>() {
                }.getType();
                ProductResponseData data = new Gson().fromJson(response.body(), type);
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) && data.getRETURN_DATA() != null) {
                    List list = data.getRETURN_DATA().getProducts();
                    if (pageNum == 1) {
                        mAdapter.setAllItem(list);
                    } else {
                        mAdapter.addAllItem(list);
                    }
                    mPullToRefreshRecyclerView.onRefreshComplete();
                    mDialog.dissmissDialog();
                }
            }
        });
    }

    /**
     * 添加到购物车
     *
     * @param product
     */
    private void addShoppingCart(Product product) {
        ServiceManger.getInstance().addToCart(product.getProductId(), "", "1", new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onSuccess(BaseResponseData data) {
                Toast.makeText(mContext, OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) ?
                        "添加购物车成功" : data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
            }
        });
//        ShoppingCartModel.addToCart(mContext, product.getProductId(), "", "1", new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                Type type = new TypeToken<BaseResponseData>() {
//                }.getType();
//                BaseResponseData data = new Gson().fromJson(response.body(), type);
//                Toast.makeText(mContext, OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) ?
//                        "添加购物车成功" : data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
