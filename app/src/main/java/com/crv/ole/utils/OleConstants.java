package com.crv.ole.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by wj_wsf on 2017/6/29 14:06.
 */

public class OleConstants {
    public static String appToken;
    public static String encryptAppKey;
    public static String sign;

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    public final static String UNION_PAY_MODE = "01";

    //百度推送
    public final static String BAIDU_API_KEY = "g09eL5upQodDF0R40aq9MGXw";
    /**
     * 基础路径
     */
    public static String BASE_PATH = Environment.getExternalStorageDirectory() + File.separator + ".ole" + File.separator;


    /**
     * 临时文件夹
     */
    public static String TMP_PATH = BASE_PATH + "temp";

    public static final String KEY_IS_SHOW_TUTORIAL = "IS_SHOW_TUTORIAL";


    //---------evnetBus事件开始-----------

    public final static String USER_CENTER_RELOAD = "user_center_reload";

    public final static String WEB_SCROLL_TOP = "web_scrool_top";

    public final static String WEB_SCROLL_BOTTOM = "web_scrool_bottom";

    public final static String REFRESH_GWC_COUNT = "refresh_gwc_count";

    public final static String REFRESH_TRIAL_LIST = "refresh_trial_list";//刷新试用列表

    public final static String ORDER_APPRAISE_ADD_SUCCESS = "order_appraise_add_success";//评价成功


    //---------evnetBus事件结束------------

    public final static String KEY_LATELY_LOGIN_TIME = "lately_login_time";

    public static long AUTO_LOGIN_TIME = 1000 * 60 * 60;//1小时过期

    public static final int noticeJsLogInSuccess = 1000;


    //接口地址
    public static String request_url;

    //是否绑定百度推送
    public final static String BASE_HOST = "http://10.0.147.163";
    //获取config信息接口
    public final static String GET_CONFIG_URL = BASE_HOST + "/oleMobileApi/server/tools/appConfig.jsx?version=";
    //获取config信息解密key
    public final static String DES_DECODE_KEY = "MdBq5RlPdQ5DySDbx7aLmUPo2SyFp4XJ";
    //图片上传路径
    public final static String UPLOAD_IMAGE_URL = BASE_HOST + "/oleMobileApi/server/util/uploadImage.jsx";

    //请求相关-------
    public final static String REQUES_SUCCESS = "S0A00000";
    //登录
    public final static String LOGIN = "crv.ole.user.login";
    //三方登录
    public final static String OTHER_LOGIN = "crv.ole.user.openLogin";
    //短信重新设置密码
    public final static String SMS_RESET_PWD = "crv.ole.user.smsPwdReset";

    //用户信息
    public final static String USER_INFO = "crv.ole.user.getUserInfo";
    //会员自动登录
    public final static String AUTO_LOGIN = "crv.ole.user.autoLogin";
    //更新个人信息
    public final static String UPATE_USERINFO = "crv.ole.user.updateUserInfo";
    //获取收货地址列表
    public final static String ADDRESS_LIST = "crv.ole.address.getAllAddress";
    //保存收货地址
    public final static String ADDRESS_SAVE = "crv.ole.address.saveAddress";
    //删除收货地址
    public final static String ADDRESS_DELETE = "crv.ole.address.deleteAddress";
    //绑定手机
    public final static String BIND_PHONE = "crv.ole.user.bindTelephone";
    //绑定邮箱
    public final static String BIND_EMAIL = "crv.ole.email.bindEmail";
    //发送邮箱验证码
    public final static String SEND_EMAIL = "crv.ole.email.sendEmail";
    //获取用户中心
    public final static String USER_CENTER_INFO = "crv.ole.user.getUserCenter";

    //上传图片
    public final static String GET_REGIONS = "crv.ole.address.getRegions";
    //密码设定
    public final static String RESET_PASSWORD = "crv.ole.user.resetPassword";

    //资讯收藏列表
    public final static String INFORMATION_COLLECTION_LIST_URL_ID = "crv.ole.article.getFavorArticleList";
    //资讯新建收藏夹
    public final static String INFORMATION_COLLECTION_ADD_FOLDER_URL = BASE_HOST + "/oleMobileApi/server/article/articleCollection.jsx";

    //热度数量
    public final static String PRODUCT_HOT_VALUE = "crv.ole.product.hotValue";

    //电子价签
    public final static String ELECTRIC_PRICE_COUPONS = "crv.ole.electricprice.getCouponsByActivity";


    //商品收藏文件夹列表
    public final static String GOODS_COLLECTION_FOLDER_LIST_URL_ID = "crv.ole.favorite.favoriteProductTypeList";
    //商品收藏列表
    public final static String GOODS_COLLECTION_LIST_URL_ID = "crv.ole.favorite.favoriteGoodsList";
    //商品收藏夹新增
    public final static String GOODS_COLLECTION_ADD_URL = BASE_HOST + "/oleMobileApi/server/favorite/favoriteProductTypeAdd.jsx";
    //商品收藏夹修改
    public final static String GOODS_COLLECTION_UPDATE_URL = BASE_HOST + "/oleMobileApi/server/favorite/favoriteProductTypeUpdate.jsx";
    //商品收藏夹删除
    public final static String GOODS_COLLECTION_DELETE_URL_ID = "crv.ole.favorite.favoriteProductTypeDelete";
    //添加商品到收藏夹
    public final static String GOODS_COLLECTION_ADD_TO_FOLDER_URL_ID = "crv.ole.favorite.favoriteGoodsAdd";
    //好物商品详情
    public final static String GET_HW_GOODS_DETAILS = "crv.ole.product.getGoodProDetails";

    public final static String GOODS_COLLECTION_REMOVE_FROM_FOLDER_URL_ID = "crv.ole.favorite.favoriteGoodsDelete";

    //获取优惠券列表接口ID
    public final static String DISCOUNT_COUPON_LIST_ID = "crv.ole.voucher.voucherList";
    //查询积分明细
    public final static String QUERY_INTEGRAINFO = "crv.ole.integration.queryIntegrationDetails";
    //查询总积分
    public final static String QUERY_USER_INTEGRATION = "crv.ole.integration.queryIntegration";

    public final static String GET_INFOLIST = "crv.ole.info.list";

    public final static String GET_TRACK = "crv.ole.order.track";//物流详情

    public final static String KEYWORD_SEARCH = "crv.ole.product.keyword.search";//商品搜索结果
    public final static String CART_ADD = "crv.ole.cart.addToCart";//添加商品到购物车
    public final static String CART_ADD_PRE = "crv.ole.cart.addPresaleToCart";//添加预售商品到购物车
    public final static String CART_ADD_BATCH = "crv.ole.cart.addCartBatch";//批量添加商品

    public final static String CART_GET = "crv.ole.cart.get";//获取购物车
    public final static String CART_REMOVE = "crv.ole.cart.removeItems";//批量删除购物车里的商品
    public final static String CART_UPDATE_NUM = "crv.ole.cart.changeAmount";//批量修改购物车里的商品数量
    public final static String CART_BUY_NOW = "crv.ole.cart.buyNow";//立即购买
    public final static String CART_GET_COUNT = "crv.ole.cart.getCount";//获取购物车商品数量
    public final static String CART_CHECK_ITEM = "crv.ole.cart.checkItem";//选中购物车商品
    public final static String CART_CHECK_ALL = "crv.ole.cart.checkAll";//全选购物车商品
    public final static String ORDER_INFO = "crv.ole.order.OrderList";//订单详情

    public final static String HOT_SERACH = "crv.ole.hotword.search";//热苏
    public final static String GET_OLEMARKETTEMPLATE_ID = "crv.ole.template.oleMarketTemplate";   //分类列表

    // 获取礼品卡、预付卡列表接口ID
    public final static String GET_GIFT_CARD_LIST_ID = "crv.ole.order.getPrepayCardList";
    // 验证礼品卡、预付卡密码
    public final static String CHECK_GIFT_CARD_PASSWORD_ID = "crv.ole.order.getPrepayCardInfo";
    // 订单确认页获取信息接口ID
    public final static String GET_ORDER_INFO_ID = "crv.ole.order.orderForm";
    // 订单确认页接口ID
    public final static String CONFIRM_ORDER_ID = "crv.ole.order.addOrder";
    // 获取支付方式列表接口ID
    public final static String GET_PAY_METHOD_ID = "crv.ole.order.getPayments";
    // 获取支付签名
    public final static String GET_PAY_SIGN_ID = "crv.ole.order.getPaySign";

    //获取订单列表
    public final static String GET_ORDER_LIST_ID = "crv.ole.order.OrderList";
    //取消订单
    public final static String CANCLE_ORDER_ID = "crv.ole.order.cancel";

    //评价订单
    public final static String ORDER_APPRAISE_ADD = "crv.ole.order.appraise.add";
    //评价商品
    public final static String PRODUCT_APPRAISE_ADD = "crv.ole.product.comment.add";

    //获取试用报告提示语
    public final static String GET_REPORT_INPUT = "crv.ole.trialReport.getReportInput";

    //获取退款退货单列表
    public final static String GET_REFUND_ORDER_LIST = "crv.ole.aftersale.getRefundOrderList";


    //  -------------------------------会员注册相关 - start-----------------------------------
    //获取短信验证码
    public final static String SEND_SMSVALIDATE = "crv.ole.sms.sendSmsValidate";
    //验证短信验证码
    public final static String CHECK_PHONECODE = "crv.ole.sms_checkPhoneCode";
    //会员注册
    public final static String REGISTER_MEMBER = "crv.ole.user.register";
    //根据手机号校验是否注册会员
    public final static String CHECKMEMBERINFO_BYMOBILE = "crv.ole.member.checkMemberInfoByMobile";
    //获得门店信息
    public final static String GET_SHOPINFO = "crv.ole.member.getShopInfo";
    //绑定会员卡
    public final static String BIND_MEMBERINFO = "crv.ole.member.bindingMemberInfo";
    //会员解除绑定
    public final static String UNBINDING_MEMBERINFO = "crv.ole.member.unbindingMemberInfo";
    //会员注册开卡
    public final static String OPEN_MEMBERCARD = "crv.ole.member.openMemberCard";
    //激活华润通会员
    public final static String ACTIVE_HRTMEMBER = "crv.ole.member.activeHrtMemberInfo";
    //  -------------------------------会员注册相关 - end-----------------------------------

    //  -------------------------------态度相关 - start-----------------------------------
    //获取文章评论
    public final static String GET_ARTICLEINFO = "crv.ole.article.getArticleInfor";

    //文章评论点赞文收藏接口ID
    public final static String COMMENT_LIKE_ID = "crv.ole.article.addCommentOrLike";


    //资讯列表
    public final static String LIST = "crv.ole.info.list";
    //资讯详情
    public final static String DETAILS = "crv.ole.info.details";
    //资讯栏目
    public final static String COLUMN = "crv.ole.info.column";
    //  -------------------------------态度相关 - end-----------------------------------


    //  -------------------------------商品相关 - start-----------------------------------
    //好物试用列表
    public final static String GET_MARKET_TEMPLATE = "crv.ole.template.oleMarketTemplate";
    //获取商品详情
    public final static String GET_PRODUCT_DETAILS_ID = "crv.ole.product.getProductDetails";
    //获取商品促销活动
    public final static String GET_PRODUCT_SALEACTIVITY_ID = "crv.ole.product.sale.activity";
    //立即购买
    public final static String PRODUCT_BUYNOW_ID = "crv.ole.cart.buyNow";
    //消息订阅
    public final static String PRODUCT_ADDNOTIFY_ID = "crv.ole.notify.add";
    //取消消息订阅
    public final static String PRODUCT_CANCELNOTIFY_ID = "crv.ole.notify.cancel";
    //获取试用报告
    public final static String GET_PRODUCT_TRIALREPORT_ID = "crv.ole.trialReport.get";
    //试用报告点赞
    public final static String PRODUCT_TRIALREPORT_LIKE_ID = "crv.ole.trialReport.like";
    //试用报告新增
    public final static String PRODUCT_TRIALREPORT_GET = "crv.ole.trialReport.add";


    //获取商品评论
    public final static String GET_PRODUCT_APPRAISE_ID = "crv.ole.product.getProductAppraise";

    //商品评论点赞
    public final static String PRODUCT_COMMENT_LIKE_ID = "crv.ole.product.comment.like";
    //商品评论点赞
    public final static String PRODUCT_SEARCHELEGOODDETAILS = "crv.ole.electricprice.searchEleGoodDetails";

    //获取试用详情
    public final static String GET_TRY_OUT_DETIALS = "crv.ole.product.getTryOutDetails";
    //获取试用商品详情
    public final static String GET_TRY_OUT_PRODUCT_DETIALS = "crv.ole.product.getTryOutProductDetails";
    //申请试用
    public final static String APPLAY_TRIAL = "crv.ole.product.trial.apply";
    //试用积分抵扣
    public final static String TRIAL_INTEGRAL_PAY = "crv.ole.order.integralPay";
    //试用历史记录
    public final static String TRIAL_ORDER_APPRAISE_LIST = "crv.ole.product.trial.getApply";

    //获取退货退款单详情
    public final static String GET_REFUND_ORDER_DETAILS = "crv.ole.aftersale.getRefundOrderDetails";
    //  -------------------------------商品相关 - end-----------------------------------

    //  -------------------------------消息中心相关 - start-----------------------------------
    //修改通知为已读状态
    public final static String MESSAGE_NOTIFY_READ_ID = "crv.ole.notify.read";
    //消息中心
    public final static String MESSAGE_CENTER_ID = "crv.ole.notify.msgCenter";
    //获取消息列表（含已读）
    public final static String MESSAGE_GET_LIST_ID = "crv.ole.notify.getNotifyList";
    //获取消息详情
    public final static String MESSAGE_GET_DETAIL_ID = "crv.ole.notify.getNotifyDetail";
    //  -------------------------------消息中心相关 - end-----------------------------------

    //大首页接口id
    public final static String GET_OLE_MARKET_HOME_ID = "crv.ole.template.oleMarketTemplate";

    //获取试用报告商品列表接口id
    public final static String GET_TRIAL_PRODUCT_LIST_ID = "crv.ole.trialReport.getTrialProductList";

    public static class KEY {
        public final static String OKGO_CACHE_KEY = "ole_okgo_cache_key";
        public final static String CONFIG_VERSION_KEY = "config_version_key";
        public final static String APP_TOKEN_KEY = "app_token_key";
        public final static String ENCRYPT_APP_KEY = "encrypt_app_key";
        public final static String REQUEST_URL_KEY = "request_url_key";
        public final static String REQUEST_SIGN_KEY = "request_sign_key";
        public final static String ACCOUNT_KEY = "account_key";     //  用户名
        public final static String PWD_KEY = "pwd_key";     //  用户密码
        public final static String REQUES_TOKEN = "token";
        public final static String USER_ID = "userId";      //  会员id
        public final static String LOGIN_ID = "loginId";    //  会员登录id
        public final static String LOGIN_STATUS = "isLogIn";

    }


    public static class BundleConstant {
        public final static String ARG_PARAMS_0 = "param0";
        public final static String ARG_PARAMS_1 = "param1";
        public final static String ARG_PARAMS_2 = "param2";
        public final static String ARG_PARAMS_3 = "param3";
        public final static String ARG_PARAMS_4 = "param4";
    }


    public final static String GET_CODE_TIMES = "get_code_times";     //  注册获取验证码次数
    public final static String GET_CODE_START_TIME = "get_code_start_time";    //  注册获取验证码时间


}
