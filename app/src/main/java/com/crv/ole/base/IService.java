package com.crv.ole.base;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/8/5.
 */

public interface IService<T> extends BaseService {

    //1:获取配置信息
    void getConfig(BaseRequestCallback<T> baseRequestCallback);

    //2:登录
    void logIn(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //3:三方登录
    void logInOther(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //4:自动登录
    void autoLogIn(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //5:获取用户中心数据
    void getUserCenterInfo(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //6:个人资料
    void getUserInfo(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //7:修改个人资料
    void submitUserInfo(Object obj, BaseRequestCallback<T> baseRequestCallback);

    //8:获取用户收货地址列表
    void getUserInfoAddress(Object obj, BaseRequestCallback baseRequestCallback);

    //9:删除用户收货地址
    void deleteAddress(Object obj, BaseRequestCallback baseRequestCallback);

    //10:上传照片
    void uploadImage(List<File> files, BaseRequestCallback baseRequestCallback);

    //11:验证验证码是否正确
    void checkCode(Object obj, BaseRequestCallback baseRequestCallback);

    //12:短信修改密码
    void smsResetPwd(Map map, BaseRequestCallback baseRequestCallback);

    //13:设置页面修改密码
    void resetPwd(Map map, BaseRequestCallback baseRequestCallback);

    //14:检测是否会员
    void checkMemberByMobile(Map map, BaseRequestCallback baseRequestCallback);

    //15:注册会员
    void registerMember(Map map, BaseRequestCallback baseRequestCallback);

    //16:获取短信验证码
    void fetchSmsCheckCode(Map map, BaseRequestCallback baseRequestCallback);

    //17:更改收货地址
    void editAddress(Object obj, BaseRequestCallback baseRequestCallback);

    //18:文章点赞
    void zanComment(Object obj, BaseRequestCallback baseRequestCallback);


    //100:超市分类数据详情
    void getClassfiyDetil(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //101:添加到购物车
    void addToCart(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取商品详情
    void getProductDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取商品促销活动
    void getProductSaleActivity(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //取消商品收藏
    void removeGoodsCollection(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //消息订阅
    void addNotify(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //取消消息订阅
    void cancelNotify(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //立即购买
    void buyNow(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取试用报告
    void getTrialReport(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //试用报告点赞
    void zanTrialReport(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //消息中心
    void messageCenter(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取消息列表（含已读）
    void getMessageList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取消息详情
    void getMessageDetail(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //修改通知为已读状态
    void messageNotifyRead(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //电子价签商详查询接口
    void searchEleGoodDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //选中购物车商品
    void checkItem(String cartId, boolean checked, String itemId, BaseRequestCallback<T> baseRequestCallback);

    //全选购物车商品
    void checkAll(String cartType, boolean checked, BaseRequestCallback<T> baseRequestCallback);

    //获取购物车数量
    void getCartCount(String cartType, String merchantId, BaseRequestCallback<T> baseRequestCallback);

//    void getOrderFrom(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取订单列表
    void getOrderList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //预售加入购物车
    void addPresaleToCart(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //好物详情
    void hwProductDetail(Map map, BaseRequestCallback baseRequestCallback);

    //热搜
    void hotValue(Map map, BaseRequestCallback baseRequestCallback);

    //批量添加商品
    void cartAddBatch(Map map, BaseRequestCallback baseRequestCallback);

    //添加试用报告
    void addTrialReport(Map map, BaseRequestCallback baseRequestCallback);

    //试用详情
    void getTryOutDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //试用商品详情
    void getTryOutProductDetails(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //提交申请试用
    void applyForTrial(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //试用订单积分抵扣
    void integralPay(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //取消订单
    void cancelOrder(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取优惠卷
    void voucherList(String canceled, int start, int limit, BaseRequestCallback<T> baseRequestCallback);

    //试用历史列表
    void appraiseList(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取大首页数据
    void getOleMarketHome(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //文章详情
    void articleDetail(Map<String, String> map, BaseRequestCallback<T> baseRequestCallback);

    //获取退货退款单详情
    void getRefundOrderDetails(String aliasCode, BaseRequestCallback<T> baseRequestCallback);

    //获取退货退款单列表
    void getRefundOrderList(Map map, BaseRequestCallback<T> baseRequestCallback);

    //评价订单
    void orderAppraiseAdd(Map map, BaseRequestCallback<T> baseRequestCallback);

    //评价商品
    void productAppraiseAdd(Map map, BaseRequestCallback<T> baseRequestCallback);

    //获取试用报告提示语
    void getReportInput(Map map, BaseRequestCallback<T> baseRequestCallback);

    //获取试用报告商品列表
    void getTrialReportGoodsList(Map map, BaseRequestCallback<T> baseRequestCallback);

}
