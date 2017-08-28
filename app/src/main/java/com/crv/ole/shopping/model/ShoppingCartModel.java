package com.crv.ole.shopping.model;

import android.content.Context;

import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.OleConstants;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by lihongshi on 2017/7/31.
 * 购物车的相关逻辑
 */
public class ShoppingCartModel {

    /**
     * 添加商品到购物车
     *
     * @param productId 商品ID
     * @param skuId     skuId，不传就将默认sku加入购物车
     * @param amount    购买数量，不传默认是购买1个
     * @param callback
     */
    public static void addToCart(Context context, String productId, String skuId, String amount, StringCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount);
        ServerApi.postRequest(context, OleConstants.CART_ADD, map, callback);
        EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
    }


    /**
     * 添加商品到购物车
     *
     * @param productId 商品ID
     * @param skuId     skuId，不传就将默认sku加入购物车
     * @param amount    购买数量，不传默认是购买1个
     */
    public static Observable<AddCartResponseData> addToCart(Context context, String productId, String skuId, String amount) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount);

//        RequestData requestData = new RequestData();
//        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CART_ADD);
//        requestData.setREQUEST_DATA(map);
//        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
//        return ServerApi.request(false, requestData, AddCartResponseData.class, sign);

        return ServerApi.request(context, OleConstants.CART_ADD, map, AddCartResponseData.class);
    }

    /**
     * 批量商品加入购物车
     * {
     * "products":[
     * {
     * "productId":"p_880015",
     * "skuId":"",
     * "amount":1
     * }
     * ]
     * }
     *
     * @param context
     * @param list
     */
    public static void addToCartBatch(Context context, List<CartResponseData.BuyItems> list, StringCallback callback) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : list) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("productId", buyItem.getCartId());
                jsonObject01.putOpt("skuId", buyItem.getItemId());
                jsonObject01.putOpt("amount", buyItem.getItemId());
                jsonArray.put(jsonObject01);
            }
            paramMap.put("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerApi.postRequest(context, OleConstants.CART_ADD_BATCH, paramMap, callback);
    }

    /**
     * 批量商品加入购物车
     * {
     * "products":[
     * {
     * "productId":"p_880015",
     * "skuId":"",
     * "amount":1
     * }
     * ]
     * }
     *
     * @param context
     */
    public static Observable<AddCartResponseData> addToCartBatch(Context context, List<CartResponseData.BuyItems> list) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : list) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("productId", buyItem.getCartId());
                jsonObject01.putOpt("skuId", buyItem.getItemId());
                jsonObject01.putOpt("amount", buyItem.getItemId());
                jsonArray.put(jsonObject01);
            }
            paramMap.put("products", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        RequestData requestData = new RequestData();
//        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CART_ADD_BATCH);
//        requestData.setREQUEST_DATA(paramMap);
//        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
//        return ServerApi.request(false, requestData, AddCartResponseData.class, sign);
        return ServerApi.request(context, OleConstants.CART_ADD_BATCH, paramMap, AddCartResponseData.class);
    }

    /**
     * crv.ole.cart.get
     * 获取购物车
     */
    public static void getCart(Context context, StringCallback callback) {
        ServerApi.postRequest(context, OleConstants.CART_GET, null, callback);
    }

    /**
     * 获取购物车
     *
     * @param context
     * @return
     */
    public static Observable<CartResponseData> getCart(Context context) {
//        RequestData requestData = new RequestData();
//        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CART_GET);
//        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
//         return ServerApi.request(false, requestData, CartResponseData.class, sign);
        return ServerApi.request(context, OleConstants.CART_GET, null, CartResponseData.class);
    }

    /**
     * 批量删除购物车商品
     *
     * @param context
     * @param products
     * @param callback
     */
    public static void removeCartItems(Context context, List<CartResponseData.BuyItems> products, StringCallback callback) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : products) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("cartId", buyItem.getCartId());
                jsonObject01.putOpt("itemId", buyItem.getItemId());
                jsonArray.put(jsonObject01);
            }
            map.put("items", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerApi.postRequest(context, OleConstants.CART_REMOVE, map, callback);
    }

    /**
     * 批量删除购物车商品
     *
     * @param context
     * @param products
     */
    public static Observable<CartResponseData> removeCartItems(Context context, List<CartResponseData.BuyItems> products) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : products) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("cartId", buyItem.getCartId());
                jsonObject01.putOpt("itemId", buyItem.getItemId());
                jsonArray.put(jsonObject01);
            }
            map.put("items", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        RequestData requestData = new RequestData();
//        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CART_REMOVE);
//        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
//        return ServerApi.request(false, requestData, CartResponseData.class, sign);
        return ServerApi.request(context, OleConstants.CART_REMOVE, map, CartResponseData.class);
    }

    /**
     * 批量修改商品数量
     *
     * @param context
     * @param products
     * @param callback
     */
    public static void updateCartNum(Context context, List<CartResponseData.BuyItems> products, StringCallback callback) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : products) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("cartId", buyItem.getCartId());
                jsonObject01.putOpt("itemId", buyItem.getItemId());
                jsonObject01.putOpt("toNumber", buyItem.getNumber());
                jsonArray.put(jsonObject01);
            }
            map.put("items", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServerApi.postRequest(context, OleConstants.CART_UPDATE_NUM, map, callback);
    }

    /**
     * 批量修改商品数量
     *
     * @param context
     * @param products
     */
    public static Observable<CartResponseData> updateCartNum(Context context, List<CartResponseData.BuyItems> products) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray();
            for (CartResponseData.BuyItems buyItem : products) {
                JSONObject jsonObject01 = new JSONObject();
                jsonObject01.putOpt("cartId", buyItem.getCartId());
                jsonObject01.putOpt("itemId", buyItem.getItemId());
                jsonObject01.putOpt("toNumber", buyItem.getNumber());
                jsonArray.put(jsonObject01);
            }
            map.put("items", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ServerApi.request(context, OleConstants.CART_UPDATE_NUM, map, CartResponseData.class);
        //  ServerApi.postRequest(context, OleConstants.CART_UPDATE_NUM, map, callback);
    }

    /**
     * 立即购买
     *
     * @param context
     * @param productId
     * @param skuId
     * @param amount
     * @param callback
     */
    public static void cartBuyNow(Context context, String productId, String skuId, String amount, StringCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount);
        ServerApi.postRequest(context, OleConstants.CART_BUY_NOW, map, callback);
    }

    /**
     * 立即购买
     *
     * @param context
     * @param productId
     * @param skuId
     * @param amount
     */
    public static Observable<BaseResponseData> cartBuyNow(Context context, String productId, String skuId, String amount) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount);
        return ServerApi.request(context, OleConstants.CART_BUY_NOW, map, BaseResponseData.class);
    }

    /**
     * @param context
     * @param cartType   购物车类型 可选，common:普通购物车，preSale:预售购物车,不传取所有商品数量
     * @param merchantId 商家Id		可选，不传获取所有商家购物车商品数量
     * @param callback
     */
    public static void getCartCount(Context context, String cartType, String merchantId, StringCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("cartType", cartType);
        map.put("merchantId", merchantId);
        ServerApi.postRequest(context, OleConstants.CART_GET_COUNT, map, callback);
    }

    /**
     * @param context
     * @param cartType   购物车类型 可选，common:普通购物车，preSale:预售购物车,不传取所有商品数量
     * @param merchantId 商家Id		可选，不传获取所有商家购物车商品数量
     */
    public static Observable<CartCountResponseData> getCartCount(Context context, String cartType, String merchantId) {
        Map<String, String> map = new HashMap<>();
        map.put("cartType", cartType);
        map.put("merchantId", merchantId);
        return ServerApi.request(context, OleConstants.CART_GET_COUNT, map, CartCountResponseData.class);
    }

    /**
     * @param context
     * @param cartId
     * @param checked
     * @param itemId
     */
    public static void checkItem(Context context, String cartId, boolean checked, String itemId) {


    }
}
