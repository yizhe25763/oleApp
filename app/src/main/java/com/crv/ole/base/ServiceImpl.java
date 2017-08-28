package com.crv.ole.base;

import com.crv.ole.classfiy.model.ProductClassfiyResult;
import com.crv.ole.home.model.BarCodeResponseData;
import com.crv.ole.home.model.ConfigResponse;
import com.crv.ole.home.model.ImageListData;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.model.SpecialDerailResult;
import com.crv.ole.login.model.LoginResultBean;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.personalcenter.model.CollectionResultData;
import com.crv.ole.personalcenter.model.CouponResponseData;
import com.crv.ole.personalcenter.model.MessageListData;
import com.crv.ole.personalcenter.model.OrderListResponse;
import com.crv.ole.personalcenter.model.RefundDetailResult;
import com.crv.ole.personalcenter.model.RefundListResponse;
import com.crv.ole.personalcenter.model.SubmitUserInfoResponse;
import com.crv.ole.personalcenter.model.UserInfoResultBean;
import com.crv.ole.register.model.CheckMemberByMobileInfoResultBean;
import com.crv.ole.register.model.RegisterMemberInfoResultBean;
import com.crv.ole.register.model.SendMsgCodeInfoResultBean;
import com.crv.ole.shopping.model.AddCartResponseData;
import com.crv.ole.shopping.model.AddPreToCartRespose;
import com.crv.ole.shopping.model.CartCountResponseData;
import com.crv.ole.shopping.model.CartResponseData;
import com.crv.ole.shopping.model.ProductDetailsInfoData;
import com.crv.ole.shopping.model.ProductSaleInfoData;
import com.crv.ole.shopping.model.RefundDetailResponseData;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.shopping.model.ZanBean;
import com.crv.ole.trial.model.TrialInfoResult;
import com.crv.ole.trial.model.TrialItemResponse;
import com.crv.ole.trial.model.TrialProductDetilResult;
import com.crv.ole.trial.model.TrialReportGoodsListData;
import com.crv.ole.trial.model.TrialReportInputResponse;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.crv.ole.utils.RequestParamsUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class ServiceImpl implements IService {

    private BaseRequestInterface requestInterface;

    public ServiceImpl() {
        requestInterface = new RequestInterfaceImpl();
    }

    //--------登录相关开始---------
    @Override
    public void logIn(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.LOGIN);
        requestInterface.request(requestData, LoginResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void logInOther(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.OTHER_LOGIN);
        requestInterface.request(requestData, LoginResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void autoLogIn(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.AUTO_LOGIN);
        requestInterface.request(requestData, LoginResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void checkCode(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.CHECK_PHONECODE);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void smsResetPwd(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.SMS_RESET_PWD);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);

    }

    @Override
    public void resetPwd(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.RESET_PASSWORD);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    //--------登录相关结束---------

    //--------会员相关开始---------
    @Override
    public void getUserCenterInfo(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.USER_CENTER_INFO);
        requestInterface.request(requestData, UserCenterData.class, baseRequestCallback, false);
    }

    @Override
    public void getUserInfo(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.USER_INFO);
        requestInterface.request(requestData, UserInfoResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void submitUserInfo(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.UPATE_USERINFO);
        requestInterface.request(requestData, SubmitUserInfoResponse.class, baseRequestCallback, false);
    }


    @Override
    public void getUserInfoAddress(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.ADDRESS_LIST);
        requestInterface.request(requestData, AddressListData.class, baseRequestCallback, false);
    }

    public void deleteAddress(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.ADDRESS_DELETE);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }


    @Override
    public void uploadImage(List list, BaseRequestCallback baseRequestCallback) {
        requestInterface.upload(OleConstants.UPLOAD_IMAGE_URL, list, ImageListData.class, baseRequestCallback);
    }

    @Override
    public void checkMemberByMobile(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CHECKMEMBERINFO_BYMOBILE);
        requestInterface.request(requestData, CheckMemberByMobileInfoResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void registerMember(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.REGISTER_MEMBER);
        requestInterface.request(requestData, RegisterMemberInfoResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void fetchSmsCheckCode(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.SEND_SMSVALIDATE);
        requestInterface.request(requestData, SendMsgCodeInfoResultBean.class, baseRequestCallback, false);
    }

    @Override
    public void editAddress(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.ADDRESS_SAVE);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }


    @Override
    public void getOrderList(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_ORDER_LIST_ID);
        requestInterface.request(requestData, OrderListResponse.class, baseRequestCallback, false);
    }

    //--------会员相关结束---------

    //--------商品分类开始---------
    @Override
    public void getClassfiyDetil(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.KEYWORD_SEARCH);
        requestInterface.request(requestData, ProductClassfiyResult.class, baseRequestCallback, false);
    }

    @Override
    public void addToCart(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CART_ADD);
        requestInterface.request(requestData, UserInfoResultBean.class, baseRequestCallback, false);
    }
    //--------商品分类结束---------

    //--------获取配置---------
    @Override
    public void getConfig(BaseRequestCallback baseRequestCallback) {
        requestInterface.get(OleConstants.GET_CONFIG_URL
                + PreferencesUtils.getInstance().getString(OleConstants.KEY.CONFIG_VERSION_KEY, ""), ConfigResponse.class, baseRequestCallback);
    }

    //--------------------商品详情相关开始------------------
    //--------获取商品详情--------
    @Override
    public void getProductDetails(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_PRODUCT_DETAILS_ID);
        requestInterface.request(requestData, ProductDetailsInfoData.class, baseRequestCallback, false);
        Log.i("商品详情请求信息：" + new Gson().toJson(requestData));
    }

    //--------获取商品促销信息--------
    @Override
    public void getProductSaleActivity(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_PRODUCT_SALEACTIVITY_ID);
        requestInterface.request(requestData, ProductSaleInfoData.class, baseRequestCallback, false);
        Log.i("商品促销信息请求信息：" + new Gson().toJson(requestData));
    }

    //--------取消商品收藏--------
    @Override
    public void removeGoodsCollection(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GOODS_COLLECTION_REMOVE_FROM_FOLDER_URL_ID);
        requestInterface.request(requestData, CollectionResultData.class, baseRequestCallback, false);
    }

    //--------消息订阅--------
    @Override
    public void addNotify(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_ADDNOTIFY_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    //--------取消消息订阅--------
    @Override
    public void cancelNotify(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_CANCELNOTIFY_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    //--------立即购买--------
    @Override
    public void buyNow(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_BUYNOW_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    //--------获取试用报告--------
    @Override
    public void getTrialReport(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_PRODUCT_TRIALREPORT_ID);
        requestInterface.request(requestData, TrialReportInfoData.class, baseRequestCallback, false);
        Log.i("试用报告请求参数：" + new Gson().toJson(requestData));
    }

    //--------试用报告点赞--------
    @Override
    public void zanTrialReport(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_TRIALREPORT_LIKE_ID);
        requestInterface.request(requestData, ZanBean.class, baseRequestCallback, false);
        Log.i("试用报告点赞请求参数：" + new Gson().toJson(requestData));
    }
    //--------------------商品详情相关结束------------------


    //---------态度相关-----------

    @Override
    public void zanComment(Object obj, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(obj, OleConstants.COMMENT_LIKE_ID);
        requestInterface.request(requestData, LikeBean.class, baseRequestCallback, false);
    }


    //--------------------


    //电子价签商详查询接口
    @Override
    public void messageCenter(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.MESSAGE_CENTER_ID);
        requestInterface.request(requestData, String.class, baseRequestCallback, false);
    }

    @Override
    public void getMessageList(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.MESSAGE_GET_LIST_ID);
        requestInterface.request(requestData, MessageListData.class, baseRequestCallback, false);
    }

    @Override
    public void getMessageDetail(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.MESSAGE_GET_DETAIL_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void messageNotifyRead(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.MESSAGE_NOTIFY_READ_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void searchEleGoodDetails(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_SEARCHELEGOODDETAILS);
        requestInterface.request(requestData, BarCodeResponseData.class, baseRequestCallback, false);
    }

    //选中购物车商品
    @Override
    public void checkItem(String cartId, boolean checked, String itemId, BaseRequestCallback baseRequestCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("cartId", cartId);
        map.put("itemId", itemId);
        map.put("checked", checked ? "T" : "F");
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CART_CHECK_ITEM);
        requestInterface.request(requestData, CartResponseData.class, baseRequestCallback, false);
    }

    //全选购物车
    @Override
    public void checkAll(String cartType, boolean checked, BaseRequestCallback baseRequestCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("cartType", cartType);
        map.put("checked", checked ? "T" : "F");
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CART_CHECK_ALL);
        requestInterface.request(requestData, CartResponseData.class, baseRequestCallback, false);
    }

    //获取购物车数量
    @Override
    public void getCartCount(String cartType, String merchantId, BaseRequestCallback baseRequestCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("cartType", cartType);
        map.put("merchantId", merchantId);
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CART_GET_COUNT);
        requestInterface.request(requestData, CartCountResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void addPresaleToCart(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CART_ADD_PRE);
        requestInterface.request(requestData, AddPreToCartRespose.class, baseRequestCallback, false);
    }

    @Override
    public void hwProductDetail(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_HW_GOODS_DETAILS);
        requestInterface.request(requestData, String.class, baseRequestCallback, false);
    }


    @Override
    public void hotValue(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_HW_GOODS_DETAILS);
        requestInterface.request(requestData, String.class, baseRequestCallback, false);
    }

    @Override
    public void cartAddBatch(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_HW_GOODS_DETAILS);
        requestInterface.request(requestData, AddCartResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void addTrialReport(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_TRIALREPORT_GET);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void cancelOrder(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.CANCLE_ORDER_ID);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);

    }

    @Override
    public void voucherList(String canceled, int start, int limit, BaseRequestCallback baseRequestCallback) {
        Map<Object, Object> map = new HashMap<>();
        map.put("canceled", canceled);
        map.put("start", start);
        map.put("limit", limit);
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.DISCOUNT_COUPON_LIST_ID);
        requestInterface.request(requestData, CouponResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void articleDetail(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.DETAILS);
        requestInterface.request(requestData, SpecialDerailResult.class, baseRequestCallback, false);
    }

    @Override
    public void integralPay(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.TRIAL_INTEGRAL_PAY);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);
    }

    @Override
    public void getTryOutDetails(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_TRY_OUT_DETIALS);
        requestInterface.request(requestData, TrialInfoResult.class, baseRequestCallback, false);
    }

    @Override
    public void getTryOutProductDetails(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_TRY_OUT_PRODUCT_DETIALS);
        requestInterface.request(requestData, TrialProductDetilResult.class, baseRequestCallback, false);
    }

    @Override
    public void applyForTrial(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.APPLAY_TRIAL);
        requestInterface.request(requestData, TrialProductDetilResult.class, baseRequestCallback, false);

    }

    @Override
    public void appraiseList(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.TRIAL_ORDER_APPRAISE_LIST);
        requestInterface.request(requestData, TrialItemResponse.class, baseRequestCallback, false);

    }

    @Override
    public void getOleMarketHome(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_OLE_MARKET_HOME_ID);
        requestInterface.request(requestData, String.class, baseRequestCallback, true);
    }

    @Override
    public void getRefundOrderDetails(String aliasCode, BaseRequestCallback baseRequestCallback) {
        Map<Object, Object> map = new HashMap<>();
        map.put("aliasCode", aliasCode);
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_REFUND_ORDER_DETAILS);
        requestInterface.request(requestData, RefundDetailResult.class, baseRequestCallback, false);

    }

    @Override
    public void getRefundOrderList(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_REFUND_ORDER_LIST);
        requestInterface.request(requestData, RefundListResponse.class, baseRequestCallback, false);

    }

    @Override
    public void orderAppraiseAdd(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.ORDER_APPRAISE_ADD);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);

    }


    @Override
    public void productAppraiseAdd(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.PRODUCT_APPRAISE_ADD);
        requestInterface.request(requestData, BaseResponseData.class, baseRequestCallback, false);

    }

    @Override
    public void getReportInput(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_REPORT_INPUT);
        requestInterface.request(requestData, TrialReportInputResponse.class, baseRequestCallback, false);

    }

    @Override
    public void getTrialReportGoodsList(Map map, BaseRequestCallback baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, OleConstants.GET_TRIAL_PRODUCT_LIST_ID);
        requestInterface.request(requestData, TrialReportGoodsListData.class, baseRequestCallback, false);
    }

    @Override
    public void clear() {
        if (requestInterface != null) {
            requestInterface.clear();
        }
    }

}
