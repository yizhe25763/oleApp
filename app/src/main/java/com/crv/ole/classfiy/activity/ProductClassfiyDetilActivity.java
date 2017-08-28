package com.crv.ole.classfiy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.classfiy.adapter.ProductClassfiyDetilAdapter;
import com.crv.ole.classfiy.adapter.SortFilterAdapter;
import com.crv.ole.classfiy.callback.OnItemImgClickListener;
import com.crv.ole.classfiy.model.ProductClassfiyResult;
import com.crv.ole.classfiy.view.DropDownMenu;
import com.crv.ole.home.model.ClassifyInfoResult;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.shopping.activity.ProductDetailActivity;
import com.crv.ole.shopping.model.Product;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.NetWorkUtil;
import com.crv.sdk.utils.TextUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分类详情界面
 * Created by zhangbo on 2017/8/4.
 */

public class ProductClassfiyDetilActivity extends BaseActivity implements View.OnClickListener, OnItemImgClickListener<Product> {

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private List<View> popupViews = new ArrayList<>();
    private String headers[] = BaseApplication.getInstance().getResources().getStringArray(R.array.sort_head);

    PullToRefreshRecyclerView product_list;

    private ImageView imageView3;

    private String[] sort_titles;

    private List<Product> list = new ArrayList<>();

    private String imgUrl;

    private ProductClassfiyDetilAdapter adapter;

    private ArrayList<ClassifyInfoResult.RETURNDATABean.SortListBean> arrayList;

    private List<String> classfiys;

    //页数
    private int pageNum = 1;

    //关键字
    private String keyword = "";

    //排序
    private String orderBy = "default";

    //基本分类ID
    private String baseColumnId = "";

    //分类ID
    private String columnId = "";

    private boolean isRefresh = true;

    private ListView listView1;

    private ListView listView2;

    private SortFilterAdapter sortFilterAdapter;

    private SortFilterAdapter calssfiiyFilterAdapter;

    private LayoutInflater inflater;

    private View recycleView;

    /**
     * “saleCount”按销量
     * “priceHigh”价格从高到低
     * “priceLow”价格从低到高
     * “publishTime”上架时间
     * “default”或””按照是否有货和上架时间排序
     */
    //默认
    private static final String SORT_TYPE_DEFAULT = "default";
    //价格从高到底
    private static final String SORT_TYPE_HIGHT = "priceHigh";
    //价格从低到高
    private static final String SORT_TYPE_LOW = "priceLow";
    //销量排序
    private static final String SORT_TYPE_SELLCOUNT = "saleCount";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_classfiy_detil_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initResource();
        initEvent();
        initRecycleView();
        initPop();
        isRefresh = true;
        showProgressDialog();
        getData();
    }

    private void initPop() {
        //综合排序
        listView1 = new ListView(this);
        listView1.setDividerHeight(0);
        sortFilterAdapter = new SortFilterAdapter(ProductClassfiyDetilActivity.this, Arrays.asList(sort_titles), 0);
        sortFilterAdapter.setBaseItemTouchListener(new BaseItemTouchListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                mDropDownMenu.closeMenu();
                switch (position) {
                    case 0://综合排序
                        orderBy = SORT_TYPE_DEFAULT;
                        break;
                    case 1://销量
                        orderBy = SORT_TYPE_SELLCOUNT;
                        break;
                    case 2://价格最低
                        orderBy = SORT_TYPE_LOW;
                        break;
                    case 3://价格最高
                        orderBy = SORT_TYPE_HIGHT;
                        break;

                }
                isRefresh = true;
                pageNum = 1;
                showProgressDialog();
                getData();
            }
        });
        listView1.setAdapter(sortFilterAdapter);

        //选择分类
        listView2 = new ListView(this);
        listView2.setDividerHeight(0);
        calssfiiyFilterAdapter = new SortFilterAdapter(ProductClassfiyDetilActivity.this, classfiys, 1);
        calssfiiyFilterAdapter.setBaseItemTouchListener(new BaseItemTouchListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                mDropDownMenu.closeMenu();
                title_name_tv.setText(item);
                if (position == 0) {
                    columnId = baseColumnId;
                } else {
                    columnId = arrayList.get(position - 1).getLinkTo();
                }
                isRefresh = true;
                pageNum = 1;
                showProgressDialog();
                getData();
            }
        });
        listView2.setAdapter(calssfiiyFilterAdapter);

        listView1.setVerticalScrollBarEnabled(false);
        listView2.setVerticalScrollBarEnabled(false);
        popupViews.clear();
        popupViews.add(listView1);
        popupViews.add(listView2);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, recycleView);
    }

    //初始化资源
    private void initResource() {
        inflater = LayoutInflater.from(this);
        sort_titles = getResources().getStringArray(R.array.sort_title);
        arrayList = (ArrayList<ClassifyInfoResult.RETURNDATABean.SortListBean>) getIntent().getSerializableExtra("sortList");
        imgUrl = getIntent().getStringExtra("imgUrl");
        baseColumnId = getIntent().getStringExtra("columnId");
        columnId = getIntent().getStringExtra("columnId");
        classfiys = new ArrayList<>();
        classfiys.add(0, "选择分类");
        for (ClassifyInfoResult.RETURNDATABean.SortListBean bean : arrayList) {
            classfiys.add(bean.getContent());
        }
        title_name_tv.setText(getString(R.string.goods_list));
    }

    //初始化事件
    private void initEvent() {
        title_back_btn.setOnClickListener(this);
    }


    private void initRecycleView() {
        recycleView = inflater.inflate(R.layout.activity_product_classfiy_recycleview_layout, null);
        product_list = (PullToRefreshRecyclerView) recycleView.findViewById(R.id.product_list);
        View view = inflater.inflate(R.layout.product_classfiy_detil_head_layout, null);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        adapter = new ProductClassfiyDetilAdapter();
        adapter.setListener(this);
        product_list.setMode(PullToRefreshBase.Mode.BOTH);
        RecyclerView recyclerView = product_list.getRefreshableView();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setHeaderView(view);
        if (!TextUtil.isEmpty(imgUrl)) {
            LoadImageUtil.getInstance().loadImage(imageView3, imgUrl);
        }
        product_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                if (!NetWorkUtil.isNetworkAvailable(ProductClassfiyDetilActivity.this)) {
                    ToastUtil.showToast(R.string.network_error);
                    product_list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            product_list.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }
                pageNum = 1;
                isRefresh = true;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                if (!NetWorkUtil.isNetworkAvailable(ProductClassfiyDetilActivity.this)) {
                    ToastUtil.showToast(R.string.network_error);
                    product_list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            product_list.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }
                pageNum++;
                isRefresh = false;
                getData();
            }
        });
    }


    /**
     * 获取数据 1.
     * “saleCount”按销量
     * “priceHigh”价格从高到低
     * “priceLow”价格从低到高
     * “publishTime”上架时间
     * “default”或””按照是否有货和上架时间排序
     */
    private void getData() {
        if (!NetWorkUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(R.string.network_error);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("mid", "");//商家ID, 如果不填, 则查询OLE下全部商店的商品
        map.put("columnId", columnId);//商品分类ID, 如果不填, 则查询全部分类商品
        map.put("pageNum", pageNum + "");//页码, 默认值为1
        map.put("limit", "20");//页面显示数量, 默认为20
        map.put("orderBy", orderBy);
        ServiceManger.getInstance().getClassfiyDetil(map, new BaseRequestCallback<ProductClassfiyResult>() {
            @Override
            public void onSuccess(ProductClassfiyResult result) {
                if (result != null && result.getRETURN_DATA() != null) {
                    if (isRefresh) {
                        list.clear();
                        if (result.getRETURN_DATA().getTotal() == 0) {
                            ToastUtil.showToast("暂无数据");
                        } else {
                            adapter.setDate(result.getRETURN_DATA().getProducts());
                        }
                    } else {
                        if (result.getRETURN_DATA().getTotal() != 0) {
                            adapter.addDate(result.getRETURN_DATA().getProducts());
                        }
                    }

                    product_list.onRefreshComplete();
                }
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_btn:
                finish();
                break;
        }
    }

    private void showProgressDialog() {
        showProgressDialog(getString(R.string.waiting), null);
    }

    @Override
    public void onItemClick(Product item, int position) {
        //TODO  进入商品详情
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", item.getProductId());
        startActivityWithAnim(intent);
    }

    @Override
    public void OnAddToShoppingCar(Product item, int position) {
        if (!ToolUtils.isLoginStatus(this)) {//先登录
            startActivityWithAnim(LoginActivity.class);
            return;
        }
        ServiceManger.getInstance().addToCart(item.getProductId(), "", "1", new BaseRequestCallback() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog();
            }

            @Override
            public void onSuccess(Object data) {
                ToastUtil.showToast(R.string.add_cart_success);
                EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                ToastUtil.showToast(R.string.add_cart_failed);
                dismissProgressDialog();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }
        });
    }
}
