package com.crv.ole.shopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.pay.activity.ConfirmOrderActivity;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.shopping.adapter.ShoppingCartAdapter;
import com.crv.ole.shopping.model.CartCommonPromoRule;
import com.crv.ole.shopping.model.CartDivider;
import com.crv.ole.shopping.model.CartResponseData;
import com.crv.ole.shopping.model.CartSectionItem;
import com.crv.ole.shopping.model.ShoppingCartModel;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * create by lihongshi 2017/08/01
 * 购物车
 */
public class ShoppingCartListActivity extends BaseAppCompatActivity implements View.OnClickListener,
        SwipeItemClickListener, ShoppingCartAdapter.OnCartListener {
    private TextView titleTv;
    private TextView subTitleTv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;
    private ShoppingCartAdapter mAdapter;
    private RelativeLayout rlJieSuan, rlBianJi, rlGwcEmpty;
    private TextView tvShiFu;
    private TextView tvYiSheng;
    private Button btnJieSuan;
    private final String cacheKey = "cache_shoppingcart_" + BaseApplication.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBar();
        initView();
        updateView();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateNumByServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SerializableManager.saveSerializable(mContext, mAdapter.getAllItem(), cacheKey);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_cart_list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbarSubtitle: {
                edit();
                break;
            }
            case R.id.btnJieSuan: {//结算时,普通和预售商品不能同时结算
                if (isCanJieSuan()) {
                    goToJieSuanActivity();
                } else {
                    Toast.makeText(mContext, "亲,商品和预售不能同时结算哟!", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.btnDel: { //底部删除
                List<CartResponseData.BuyItems> produceIDs = new ArrayList<>();
                for (int i = 0; i < mAdapter.getAllItem().size(); i++) {
                    Object object = mAdapter.getAllItem().get(i);
                    if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                        CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                        if (buyItems.isChecked()) {
                            produceIDs.add(buyItems);
                        }
                    }
                }
                delete(produceIDs);
                break;
            }
            case R.id.btnCollection: { //转入心愿单
                List<String> list = new ArrayList<>();
                for (Object object : mAdapter.getAllItem()) {
                    if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                        CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                        if (buyItems.isChecked()) {
                            list.add(buyItems.getProductId());
                        }
                    }
                }
                collection(list);
                break;
            }
        }
    }

    @Override
    public void onUpdateView() {
        updateView();
    }

    @Override
    public void onItemCheck(Object object) {
        CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
        if (buyItems != null) {
            ServiceManger.getInstance().checkItem(buyItems.getCartId(),
                    buyItems.getChecked(), buyItems.getItemId(), new BaseRequestCallback<CartResponseData>() {
                        @Override
                        public void onSuccess(CartResponseData data) {

                        }
                    });
        }
    }

    @Override
    public void onSectionCheck(String cartType, boolean check) {
        ServiceManger.getInstance().checkAll(cartType, check, new BaseRequestCallback<CartResponseData>() {
            @Override
            public void onSuccess(CartResponseData data) {

            }
        });
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Object object = mAdapter.getAllItem().get(position);
        if (CartResponseData.BuyItems.class.equals(object.getClass())) {
            CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
            gotoProduceDetailActivity(buyItems.getProductId());
        }
    }

    @Override
    public void onRuleClick(Object object) {
        CartCommonPromoRule cartCommonPromoRule = (CartCommonPromoRule) object;
        if (cartCommonPromoRule != null && !TextUtil.isEmpty(cartCommonPromoRule.getPromotionLink())) { //跳转到好物详情
            Intent intent = new Intent(mContext, HwDetailActivity.class);
            intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_0, cartCommonPromoRule.getPromotionLink());
            startActivity(intent);
        }
    }


    private void initBar() {
        titleTv = getToolbarTitle();
        subTitleTv = getToolbarSubTitle();
        String title = String.format(getTitle().toString(), 0.00);
        titleTv.setText(title);

        subTitleTv.setOnClickListener(this);
        subTitleTv.setTag(false);
        subTitleTv.setText("编辑");
    }

    private void edit() {
        boolean isEditMode = (boolean) subTitleTv.getTag();
        isEditMode = !isEditMode;
        subTitleTv.setTag(isEditMode);
        subTitleTv.setText(isEditMode ? "完成" : "编辑");
        mAdapter.updateEditMode(isEditMode);
        updateView();

        if (!isEditMode) {
            updateNumByServer();
        }
    }

    /**
     * 普通商品和预售商品不能同时结算 common:普通购物车，preSale:预售购物车
     *
     * @return
     */
    private String preSaleType = "";
    private String lastCartType = "";

    private boolean isCanJieSuan() {
        List list = mAdapter.getAllItem();
        for (Object object : list) {
            if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                if (lastCartType != null && !lastCartType.equalsIgnoreCase(buyItems.getCartType())) {
                    return false;
                }
                lastCartType = buyItems.getObjType();
                preSaleType = buyItems.getPreSaleType();
            }
        }
        return true;
    }

    /**
     * 跳转到结算页面
     */
    private void goToJieSuanActivity() {
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        intent.putExtra("orderType", "common".equalsIgnoreCase(lastCartType) ? "common" : "preSale");
        intent.putExtra("from_tag", (preSaleType != null && preSaleType.startsWith("advance_buy")) ? preSaleType : "common_buy");
        startActivity(intent);
    }

    /**
     * 跳转到商品详情页
     */
    private void gotoProduceDetailActivity(String productId) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    private void initView() {
        rlGwcEmpty = (RelativeLayout) this.findViewById(R.id.rlGwcEmpty);
        rlJieSuan = (RelativeLayout) this.findViewById(R.id.rlJieSuan);
        btnJieSuan = (Button) this.findViewById(R.id.btnJieSuan);
        btnJieSuan.setOnClickListener(this);
        tvShiFu = (TextView) this.findViewById(R.id.tvShiFu);
        tvYiSheng = (TextView) this.findViewById(R.id.tvYiSheng);

        rlBianJi = (RelativeLayout) this.findViewById(R.id.rlBianJi);
        CheckBox rbQuanXuan = (CheckBox) this.findViewById(R.id.rbQuanXuan);
        rbQuanXuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAdapter.setAllCheck(b);
                updateView();
            }
        });
        Button btnDel = (Button) this.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        Button btnCollection = (Button) this.findViewById(R.id.btnCollection);
        btnCollection.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        mRecyclerView = (SwipeMenuRecyclerView) this.findViewById(R.id.swipeMenuRecyclerView);
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setSwipeItemClickListener(this);
        List list = SerializableManager.readObjectForList(mContext, cacheKey);
        mAdapter = new ShoppingCartAdapter(list);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        ShoppingCartModel.getCart(this, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Type type = new TypeToken<CartResponseData>() {
                }.getType();
                CartResponseData data = new Gson().fromJson(response.body(), type);
                List mList = parseData(data);
                mAdapter.setAllItem(mList);
                updateView();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private List parseData(CartResponseData data) {
        List mList = new ArrayList<>();
        if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())
                && data.getRETURN_DATA() != null && data.getRETURN_DATA().getCarts() != null) {
            List<CartResponseData.Carts> list = data.getRETURN_DATA().getCarts();
            yunfei = getYunFei(list);
            for (CartResponseData.Carts cart : list) {
                if (cart.getCartType().equalsIgnoreCase("common")) {
                    //添加普通header
                    CartSectionItem cartSectionItem = new CartSectionItem();
                    cartSectionItem.setCartId(cart.getCartId());
                    cartSectionItem.setCartType(cart.getCartType());
                    cartSectionItem.setText("商品");
                    mList.add(cartSectionItem);

                    //普通非促销活动的商品
                    List<CartResponseData.BuyItems> buyItemsList = cart.getBuyItems();
                    if (buyItemsList != null && buyItemsList.size() > 0) {
                        mList.addAll(cart.getBuyItems());

                    }

                    //普通促销活动商品
                    if (cart.getOrderRuleTargets() != null && cart.getOrderRuleTargets().size() > 0) {
                        //添加分割线
                        CartDivider cartDivider = new CartDivider();
                        mList.add(cartDivider);

                        List<CartResponseData.OrderRuleTargets> orderRuleTargetses = cart.getOrderRuleTargets();//有多个促销活动商品
                        for (CartResponseData.OrderRuleTargets orderRuleTargets : orderRuleTargetses) {
                            //促销规则
                            String rule = new StringBuilder().append("[")
                                    .append(orderRuleTargets.getActionType())
                                    .append("]")
                                    .append(" ")
                                    .append(orderRuleTargets.getRuleName())
                                    .toString();
                            CartCommonPromoRule cartCommonRule = new CartCommonPromoRule();
                            cartCommonRule.setText(rule);
                            cartCommonRule.setRuleId(orderRuleTargets.getRuleId());
                            cartCommonRule.setActionType(orderRuleTargets.getActionType());
                            cartCommonRule.setRuleName(orderRuleTargets.getRuleName());
                            cartCommonRule.setType(orderRuleTargets.getType());
                            cartCommonRule.setUserFriendlyMessage(orderRuleTargets.getUserFriendlyMessage());
                            cartCommonRule.setPromotionLink(orderRuleTargets.getPromotionLink());
                            mList.add(cartCommonRule);

                            //促销商品
                            mList.addAll(orderRuleTargets.getBuyItems());
                            //添加分割线
                            CartDivider cartDivider01 = new CartDivider();
                            mList.add(cartDivider01);
                        }
                    }
                } else if (cart.getCartType().equalsIgnoreCase("preSale")) {
                    //添加预售header
                    CartSectionItem cartSectionItem = new CartSectionItem();
                    cartSectionItem.setCartId(cart.getCartId());
                    cartSectionItem.setCartType(cart.getCartType());
                    cartSectionItem.setText("预售");
                    mList.add(cartSectionItem);
                    //预售商品
                    mList.addAll(cart.getBuyItems());
                }
            }
        }

        return mList;
    }

    private double getYunFei(List<CartResponseData.Carts> list) {
        double totalDeliveryFee = 0;
        for (CartResponseData.Carts cart : list) {
            double deliver = Double.parseDouble(cart.getTotalDeliveryFee());
            totalDeliveryFee = totalDeliveryFee + deliver;
        }
        return totalDeliveryFee;
    }

    private double yunfei = 0;

    /**
     * 获取以省价格，实付价格，购物车已选数量，商品件数,购物车总数量,
     */
    private double[] calculation() {
        double selectCount = 0;
        double yisheng = 0;
        double shifu = 0;
        double totolCount = 0;
        List list = mAdapter.getAllItem();
        for (Object object : list) {
            if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                if (buyItems.isChecked()) {
                    selectCount++;
                    totolCount = totolCount + Integer.parseInt(buyItems.getNumber());
                    int diffPrice = (int) Double.parseDouble(buyItems.getDiffPrice());
                    yisheng = yisheng + diffPrice;

                    double unitPrice = Double.parseDouble(buyItems.getUnitPrice());
                    int num = Integer.parseInt(buyItems.getNumber());
                    double price = unitPrice * num;
                    shifu = shifu + price;
                }
            }
        }
        return new double[]{yisheng, shifu, selectCount, totolCount};
    }

    private void updateView() {
        double[] calculationResult = calculation();
        String title = String.format(getTitle().toString(), calculationResult[3]);
        titleTv.setText(title);
        String yisheng = new StringBuilder().append(
                yunfei == 0 ? "不含运费" : ("运费金额" + yunfei))
                .append("  已省: ¥").append(calculationResult[0])
                .toString();
        tvYiSheng.setText(yisheng);
        tvShiFu.setText(String.format("¥%.2f", calculationResult[1]));
        btnJieSuan.setText(String.format("结算(%.0f)", calculationResult[2]));

        if (calculationResult[3] <= 0 && mAdapter.getItemCount() <= 0) {
            subTitleTv.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            rlJieSuan.setVisibility(View.INVISIBLE);
            rlBianJi.setVisibility(View.INVISIBLE);
            rlGwcEmpty.setVisibility(View.VISIBLE);
            subTitleTv.setVisibility(View.INVISIBLE);
            return;
        }
        subTitleTv.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        boolean isEditMode = (boolean) subTitleTv.getTag();
        rlJieSuan.setVisibility(isEditMode ? View.INVISIBLE : View.VISIBLE);
        rlBianJi.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        rlGwcEmpty.setVisibility(View.INVISIBLE);
    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            if (viewType == ShoppingCartAdapter.VIEW_TYPE_NORMAL) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_72);
                // 1. MATCH_PARENT 自适应高度，保持和Item一样高; 2. 指定具体的高，比如80; 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                            .setBackground(R.color.txt_f6f6f6)
                            .setImage(R.drawable.btn_jrsc)
                            .setText("移入收藏")
                            .setTextSize(12)
                            .setTextColor(getResources().getColor(R.color.c_666666))
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(addItem);// 添加菜单到右侧。

                    SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                            .setBackground(R.color.txt_f6f6f6)
                            .setImage(R.drawable.delete_button_selector)
                            .setText("删除")
                            .setTextSize(12)
                            .setTextColor(getResources().getColor(R.color.c_666666))
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem); // 添加菜单到右侧。
                }

            }

        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    List<String> list = new ArrayList();
                    CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) mAdapter.getAllItem().get(adapterPosition);
                    list.add(buyItems.getProductId());
                    collection(list);
                } else if (menuPosition == 1) {
                    List<CartResponseData.BuyItems> list = new ArrayList();
                    CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) mAdapter.getAllItem().get(adapterPosition);
                    list.add(buyItems);
                    delete(list);
                }
            }
        }
    };

    /**
     * 删除商品对话框
     *
     * @param products 需要删除的
     */
    private void delete(List<CartResponseData.BuyItems> products) {
        new CustomDiaglog(this, "确认删除此商品", "确认", "取消").setOnConfimClickListerner(new CustomDiaglog.OnConfimClickListerner() {
            @Override
            public void onCanleClick() {
                mAdapter.delItems(products);
                updateView();
                deleteByServer(products);
            }

            @Override
            public void OnConfimClick() {

            }
        }).show();
    }

    /**
     * 从服务器批量删除商品
     *
     * @param products
     */
    private void deleteByServer(List<CartResponseData.BuyItems> products) {
        ShoppingCartModel.removeCartItems(mContext, products, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e(TAG, "删除商品行:" + response.body());
                Type type = new TypeToken<CartResponseData>() {
                }.getType();
                CartResponseData data = new Gson().fromJson(response.body(), type);
                if (!OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    Toast.makeText(mContext, data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }

    /**
     * 批量移入收藏
     *
     * @param productId
     */
    private void collection(List<String> productId) {
        CollectionUtils.getInstance().showCollectionDialog(this,
                CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION, productId);
        CollectionUtils.getInstance().setOnCollectionListener(new CollectionUtils.OnCollectionListener() {
            @Override
            public void onCollection(int like, int collections) {
                ToastUtil.showToast("成功收藏到收藏夹");
            }

            @Override
            public void onFailed(String msg) {
                ToastUtil.showToast(msg);
            }
        });
    }

    /**
     * 批量修改数量
     */
    private void updateNumByServer() {
        List list = new ArrayList();
        for (Object object : mAdapter.getAllItem()) {
            if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                list.add(buyItems);
            }
        }
        ShoppingCartModel.updateCartNum(mContext, list, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Type type = new TypeToken<CartResponseData>() {
                }.getType();
                CartResponseData data = new Gson().fromJson(response.body(), type);
                if (!OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    Toast.makeText(mContext, data.getRETURN_DESC(), Toast.LENGTH_SHORT).show();
                } else {
                    List list1 = parseData(data);
                    mAdapter.setAllItem(list1);
                    updateView();
                }
            }
        });
    }

}
