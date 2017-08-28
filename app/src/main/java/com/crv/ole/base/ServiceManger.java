package com.crv.ole.base;

import com.crv.ole.net.RequestData;
import com.crv.ole.shopping.model.RefundDetailResponseData;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.RequestParamsUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class ServiceManger<T> {
    private volatile static ServiceManger mServiceManger;

    private IService service;

    private BaseRequestInterface requestInterface = new RequestInterfaceImpl();

    private ServiceManger() {
        service = new ServiceImpl();
    }

    public static ServiceManger getInstance() {
        if (mServiceManger == null) {
            synchronized (ServiceManger.class) {
                if (mServiceManger == null) {
                    mServiceManger = new ServiceManger();
                }
            }
        }
        return mServiceManger;
    }


    /**
     * 获取配置
     *
     * @param baseRequestCallback
     */
    public void getConfig(BaseRequestCallback<T> baseRequestCallback) {
        service.getConfig(baseRequestCallback);
    }

    /**
     * 登录
     *
     * @param map
     * @param baseRequestCallback
     */
    public void logIn(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.logIn(map, baseRequestCallback);
    }

    /**
     * 三方登录
     *
     * @param map
     * @param baseRequestCallback
     */
    public void logInOther(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.logInOther(map, baseRequestCallback);
    }

    /**
     * 自动登录
     *
     * @param token
     * @param baseRequestCallback
     */
    public void autoLogIn(String token, BaseRequestCallback<T> baseRequestCallback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        service.autoLogIn(map, baseRequestCallback);
    }

    /**
     * 验证验证码是否正确
     *
     * @param obj
     * @param baseRequestCallback
     */
    public void checkCode(Object obj, BaseRequestCallback<T> baseRequestCallback) {

        service.checkCode(obj, baseRequestCallback);
    }

    /**
     * 获取短信验证码
     *
     * @param map
     * @param baseRequestCallback
     */
    public void fetchSmsCheckCode(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.fetchSmsCheckCode(map, baseRequestCallback);
    }

    /**
     * 用户中心信息
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getUserCenterInfo(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getUserCenterInfo(map, baseRequestCallback);
    }

    /**
     * 获取个人资料
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getUserInfo(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getUserInfo(map, baseRequestCallback);
    }


    /**
     * 提交个人资料
     *
     * @param obj
     * @param baseRequestCallback
     */
    public void submitUserInfo(Object obj, BaseRequestCallback<T> baseRequestCallback) {
        service.submitUserInfo(obj, baseRequestCallback);
    }

    /**
     * 获取用户收货地址列表
     *
     * @param obj
     * @param baseRequestCallback
     */
    public void getUserInfoAddress(Object obj, BaseRequestCallback<T> baseRequestCallback) {
        service.getUserInfoAddress(obj, baseRequestCallback);
    }

    /**
     * 删除用户收货地址
     *
     * @param obj
     * @param baseRequestCallback
     */
    public void deleteAddress(Object obj, BaseRequestCallback<T> baseRequestCallback) {
        service.deleteAddress(obj, baseRequestCallback);
    }

    /**
     * 获取分类详情
     *
     * @param map                 map参数
     * @param baseRequestCallback 回调
     */
    public void getClassfiyDetil(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getClassfiyDetil(map, baseRequestCallback);
    }

    /**
     * 添加到购物车
     *
     * @param productId           商品ID
     * @param skuId               skuId，不传就将默认sku加入购物车
     * @param amount              购买数量，不传默认是购买1个
     * @param baseRequestCallback 回调
     */
    public void addToCart(String productId, String skuId, String amount, BaseRequestCallback<T> baseRequestCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("amount", amount);
        service.addToCart(map, baseRequestCallback);

        EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
    }

    /**
     * 上传图片
     *
     * @param files               文件列表
     * @param baseRequestCallback 回调
     */
    public void uploadImage(List<File> files, BaseRequestCallback<T> baseRequestCallback) {
        service.uploadImage(files, baseRequestCallback);
    }

    /**
     * 获取商品详情
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void getProductDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getProductDetails(map, baseRequestCallback);
    }

    /**
     * 获取商品促销活动
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void getProductSaleActivity(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getProductSaleActivity(map, baseRequestCallback);
    }

    /**
     * 消息订阅
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void addNotify(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.addNotify(map, baseRequestCallback);
    }

    /**
     * 取消消息订阅
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void cancelNotify(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.cancelNotify(map, baseRequestCallback);
    }

    /**
     * 取消商品收藏
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void removeGoodsCollection(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.removeGoodsCollection(map, baseRequestCallback);
    }

    /**
     * 立即购买
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void buyNow(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.buyNow(map, baseRequestCallback);
    }

    /**
     * 获取试用报告
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void getTrialReport(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getTrialReport(map, baseRequestCallback);
    }

    /**
     * 试用报告点赞
     *
     * @param map                 map 参数
     * @param baseRequestCallback 回调
     */
    public void zanTrialReport(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.zanTrialReport(map, baseRequestCallback);
    }

    /**
     * 短信找回密码
     *
     * @param map
     * @param baseRequestCallback
     */
    public void smsResetPwd(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.smsResetPwd(map, baseRequestCallback);
    }

    /**
     * 个人中心设置密码
     *
     * @param map
     * @param baseRequestCallback
     */
    public void resetPwd(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.resetPwd(map, baseRequestCallback);
    }

    /**
     * 检测是否会员
     *
     * @param map
     * @param baseRequestCallback
     */
    public void checkMemberByMobile(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.checkMemberByMobile(map, baseRequestCallback);
    }

    /**
     * 注册会员
     *
     * @param map
     * @param baseRequestCallback
     */
    public void registerMember(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.registerMember(map, baseRequestCallback);
    }

    /**
     * 更改收货地址
     *
     * @param obj
     * @param baseRequestCallback
     */
    public void editAddress(Object obj, BaseRequestCallback<T> baseRequestCallback) {
        service.editAddress(obj, baseRequestCallback);
    }

    public void searchEleGoodDetails(String barCode, BaseRequestCallback<T> baseRequestCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("barCode", barCode); //条形码
        service.searchEleGoodDetails(map, baseRequestCallback);
    }

    /**
     * 消息中心
     *
     * @param map
     * @param baseRequestCallback
     */
    public void messageCenter(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.messageCenter(map, baseRequestCallback);
    }

    public void articleDetail(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.articleDetail(map, baseRequestCallback);
    }

    /**
     * 修改通知为已读状态
     *
     * @param map
     * @param baseRequestCallback
     */
    public void messageNotifyRead(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.messageNotifyRead(map, baseRequestCallback);
    }

    /**
     * 获取消息列表（含已读)
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getMessageList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getMessageList(map, baseRequestCallback);
    }

    /**
     * 获取消息详情
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getMessageDetail(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getMessageDetail(map, baseRequestCallback);
    }

    //选中购物车商品
    public void checkItem(String cartId, boolean checked, String itemId, BaseRequestCallback baseRequestCallback) {
        service.checkItem(cartId, checked, itemId, baseRequestCallback);
    }


    /**
     * 全选购物车
     *
     * @param cartType common:普通购物车，preSale:预售购物车
     * @param check    T、t:代表全选，F或不传表示全不选
     */
    public void checkAll(String cartType, boolean check, BaseRequestCallback baseRequestCallback) {
        service.checkAll(cartType, check, baseRequestCallback);
    }

//    public void getOrderFrom(Map<String,String> map, BaseRequestCallback baseRequestCallback) {
//        service.getOrderFrom(map, baseRequestCallback);
//    }

    /**
     * 获取购物车数量
     *
     * @param cartType            common:普通购物车，preSale:预售购物车,不传取所有商品数量
     * @param merchantId          商家Id 可选，不传获取所有商家购物车商品数量
     * @param baseRequestCallback
     */
    public void getCartCount(String cartType, String merchantId, BaseRequestCallback baseRequestCallback) {
        service.getCartCount(cartType, merchantId, baseRequestCallback);
    }

    /***
     * 获取订单列表
     * @param map
     * @param baseRequestCallback
     */
    public void getOrderList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getOrderList(map, baseRequestCallback);
    }

    /**
     * 预售加入购物车
     *
     * @param map
     * @param baseRequestCallback
     */
    public void addPresaleToCart(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.addPresaleToCart(map, baseRequestCallback);
    }

    /**
     * 好物详情
     *
     * @param map
     * @param baseRequestCallback
     */
    public void hwProductDetail(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.hwProductDetail(map, baseRequestCallback);
    }

    /**
     * 热搜
     *
     * @param map
     * @param baseRequestCallback
     */
    public void hotValue(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.hotValue(map, baseRequestCallback);
    }

    /**
     * 批量添加商品
     *
     * @param map
     * @param baseRequestCallback
     */
    public void cartAddBatch(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.cartAddBatch(map, baseRequestCallback);
    }

    /**
     * activityId	活动id	string	必传参数
     * compareWords	与其他商品比较	string
     * feelingWords	该商品的XX感	string
     * fileIdList	试用图片fileID，	string	必传参数，组成字符串，用逗号分隔
     * freeWords	自由发言	string
     * moodWords	收到时的心情	string
     * orderId	订单id	string	必传参数
     * productId	商品id	string	必传参数
     * wordContent	试用报告文字	string	必传参数
     */
    public void addTrialReport(String activityId, String productId, String orderId,
                               String wordContent, String moodWords, String feelingWords,
                               String compareWords, String freeWords, String fileIdList,
                               BaseRequestCallback<T> baseRequestCallback) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        map.put("compareWords", compareWords);
        map.put("feelingWords", feelingWords);
        map.put("fileIdList", fileIdList);
        map.put("freeWords", freeWords);
        map.put("moodWords", moodWords);
        map.put("oneSentence", wordContent);
        map.put("orderId", orderId);
        map.put("productObjId", productId);
        service.addTrialReport(map, baseRequestCallback);
    }

    /**
     * 获取试用详情
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getTryOutDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getTryOutDetails(map, baseRequestCallback);
    }


    /**
     * 文章点赞
     *
     * @param obj
     * @param requestCallback
     */
    public void zanComment(Object obj, BaseRequestCallback requestCallback) {
        service.zanComment(obj, requestCallback);
    }

    /**
     * 获取试用商品详情
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getTryOutProductDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getTryOutProductDetails(map, baseRequestCallback);
    }

    /**
     * 申请试用
     *
     * @param map
     * @param baseRequestCallback
     */
    public void applyForTrial(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.applyForTrial(map, baseRequestCallback);
    }

    /**
     * 试用积分抵扣
     *
     * @param map
     * @param baseRequestCallback
     */
    public void integralPay(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.integralPay(map, baseRequestCallback);
    }

    /**
     * 取消订单
     *
     * @param map
     * @param baseRequestCallback
     */
    public void cancelOrder(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.cancelOrder(map, baseRequestCallback);
    }

    /**
     * 试用历史记录
     *
     * @param map
     * @param baseRequestCallback
     */
    public void appraiseList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.appraiseList(map, baseRequestCallback);
    }

    public void getRefundOrderDetails(String aliasCode, BaseRequestCallback baseRequestCallback) {
        service.getRefundOrderDetails(aliasCode, baseRequestCallback);
    }

    /**
     * 清除compositeDisposable
     */
    public void onDestory() {
        if (service != null) {
            service.clear();
        }
    }


    /**
     * h5获取数据的通用接口
     *
     * @param apiId
     * @param map
     * @param baseRequestCallback
     */
    public void fetchData(String apiId, Map<String, String> map, BaseRequestCallback<String> baseRequestCallback) {
        RequestData requestData = RequestParamsUtils.getInstance().getRequestData(map, apiId);
        requestInterface.request(requestData, String.class, baseRequestCallback, false);
    }

    /**
     * 获取优惠卷
     *
     * @param canceled            0:未使用，1：已使用和未使用已过期，2：未使用已过期
     *                            //used:使用过的券，expired:过期的券，unuse:未使用的券
     * @param start
     * @param limit
     * @param baseRequestCallback
     */
    public void voucherList(String canceled, int start, int limit, BaseRequestCallback baseRequestCallback) {
        service.voucherList(canceled, start, limit, baseRequestCallback);
    }

    /**
     * 获取大首页数据
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getOleMarketHome(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getOleMarketHome(map, baseRequestCallback);
    }

    /**
     * 获取退款退货单列表
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getRefundOrderList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getRefundOrderList(map, baseRequestCallback);
    }

    /**
     * 评价订单
     *
     * @param map
     * @param baseRequestCallback
     */
    public void orderAppraiseAdd(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.orderAppraiseAdd(map, baseRequestCallback);
    }

    /**
     * 评价商品
     *
     * @param map
     * @param baseRequestCallback
     */
    public void productAppraiseAdd(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.productAppraiseAdd(map, baseRequestCallback);
    }

    /**
     * 获取试用报告提示语
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getReportInput(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getReportInput(map, baseRequestCallback);

    }

    /**
     * 获取试用报告商品列表
     *
     * @param map
     * @param baseRequestCallback
     */
    public void getTrialReportGoodsList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback) {
        service.getTrialReportGoodsList(map, baseRequestCallback);

    }
}
