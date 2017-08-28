package com.crv.ole.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.crv.ole.home.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeBankCobrandCard;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeChannel;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeDataTemplete;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeGoodsCategory;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeGoodsDetail;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeGoodsShop;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeOShopHome;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypePointExchange;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDK360;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDKCrBank;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDKCrTravel;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDKCrVanguard;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDKNotSupport;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSDKXiaoAn;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeSearchResult;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeUrl;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeZXQYDetail;
import static com.crv.ole.utils.HrtLinkToBean.HrtLinkType.HrtLinkToTypeZXQYList;


/**
 * Created by yanghongjun on 17/6/27.
 */

public class HrtLinkToBean {

    public enum HrtLinkType {
        HrtLinkToTypeUnkown, //unkown.
        HrtLinkToTypeUrl, //网页url.
        HrtLinkToTypeGoodsDetail, //商品详情.
        HrtLinkToTypeDataTemplete, //模版数据url.
        HrtLinkToTypeSearchResult, //搜索结果页.
        HrtLinkToTypeChannel, //频道数据.

        //特殊地址.
        HrtLinkToTypeOShopHome, //电商首页.
        HrtLinkToTypeZXQYList, //尊享权益列表页.
        HrtLinkToTypeGoodsCategory, //商品分类.
        HrtLinkToTypeBankCobrandCard, //银行联名卡.
        HrtLinkToTypePointExchange, //积分兑换.

        HrtLinkToTypeZXQYDetail, //尊享权益详情页.
        HrtLinkToTypeGoodsShop,

        //第三方sdk.
        HrtLinkToTypeSDKNotSupport, //第三方不支持.
        HrtLinkToTypeSDK360, //360借贷.
        HrtLinkToTypeSDKXiaoAn, //小安贷款.
        HrtLinkToTypeSDKCrBank, //华润银行.
        HrtLinkToTypeSDKCrVanguard, //华润万家送.
        HrtLinkToTypeSDKCrTravel, //华润出行.

    }

    private com.crv.ole.utils.HrtLinkToBean.HrtLinkType type;//跳转类型.
    private String data = "";//数据
    private String originData;//原始数据
    private boolean isPointPrice;//是否积分价出售(update by nick)
    private String key = "";
    private static String KEY_CRBANk_JUMP = "jumpUrl";


    public static HrtLinkToBean loadDataFromString(String str) {
        return loadDataFromString(str, null);
    }

    public static HrtLinkToBean loadDataFromString(String str, String priceType) {
        HrtLinkToBean linkToBean = new HrtLinkToBean();
        if (linkToBean != null) {
            linkToBean.convertLinkAttributeFromString(str);
            //价格类型.
            if (priceType != null && priceType.equals("point")) {
                linkToBean.isPointPrice = true;
            }
        }
        return linkToBean;
    }

    /**
     * 解析str
     *
     * @param str
     */
    private void convertLinkAttributeFromString(String str) {
        HrtLinkToBean m = this;

        if (m != null) {
            m.originData = str;

            HashMap<String, String> map = new HashMap<>();

            if (str.startsWith("#type=")) {
                str = str.substring(1);
                String[] tmpArr = str.split("&");
                //  NSMutableArray<NSString*> *strArr = [@[] mutableCopy];

                ArrayList<String> strArr = new ArrayList<>();
                if (tmpArr.length >= 2) {
                    String typeStr = tmpArr[0];
                    String dataStr = tmpArr[1];
                    for (int i = 2; i < tmpArr.length; i++) {
                        String s = tmpArr[i];
                        dataStr += "&";
                        dataStr += s;
                    }
                    strArr.add(typeStr);
                    strArr.add(dataStr);
                }

                for (String oneS : strArr) {
                    String[] keyValueArr = str.split("=");
                    if (keyValueArr.length == 2) {
                        String key = keyValueArr[0];
                        String value = keyValueArr[1];
                        map.put(key, value);

                    } else if (keyValueArr.length > 2) {
                        String key = keyValueArr[0];
                        String value = keyValueArr[1];
                        for (int i = 2; i < keyValueArr.length; i++) {

                            value += "=";
                            value += keyValueArr[i];
                        }
                        map.put(key, value);
                    }

                }

            }

            String type = map.get("type");
            if (type != null) {

                if (type.equals("url")) {
                    m.type = HrtLinkToTypeUrl;
                    m.data = map.get("type");

                } else if (type.equals("good")) {
                    m.type = HrtLinkToTypeGoodsDetail;
                    m.data = map.get("goodId");
                } else if (type.equals("dataTemplet")) {
                    m.type = HrtLinkToTypeDataTemplete;
                    m.data = map.get("url");
                } else if (type.equals("search")) {
                    m.type = HrtLinkToTypeSearchResult;
                    m.data = map.get("keyword");
                } else if (type.equals("channel")) {
                    m.type = HrtLinkToTypeChannel;
                    if (TextUtils.isEmpty(map.get("cmId"))) {
                        m.data = map.get("cmId");

                    } else if (TextUtils.isEmpty(map.get("cycleId"))) {
                        m.data = map.get("cycleId");
                    }
                } else if (type.equals("businessHome")) {
                    String url = map.get("url");

                    if (url.equals("OLE_HOME")) {
                        m.type = HrtLinkToTypeOShopHome;
                        m.data = "";
                    } else if (url.equals("ZXQY")) {
                        m.type = HrtLinkToTypeZXQYList; //尊享权益.
                        m.data = "";
                    } else if (url.equals("CATEGORY")) {
                        m.type = HrtLinkToTypeGoodsCategory; //商品分类.
                        m.data = "";
                    } else if (url.equals("COBRAND_CARD")) {
                        m.type = HrtLinkToTypeBankCobrandCard; ////银行联名卡.
                        m.data = "";
                    } else if (url.equals("POINT_EXCHANGE")) {
                        m.type = HrtLinkToTypePointExchange; //积分兑换.
                        m.data = "";
                    } else {
                        Log.e("unsolved LinkToString:" + str);
                    }
                } else if (type.equals("ZXQY")) {
                    m.type = HrtLinkToTypeZXQYDetail;
                    m.data = map.get("id");
                } else if (type.equals("shop")) {
                    m.type = HrtLinkToTypeGoodsShop;
                    m.data = map.get("shopId");
                } else if (type.equals("thirdSDK")) {
                    String target = map.get("target");
                    String allStr = "target=" + target;

                    String[] strArr = allStr.split("&");

                    HashMap<String, String> targetDict = new HashMap<>();

                    for (String oneS : strArr) {

                        String[] keyValueArr = oneS.split("=");

                        if (keyValueArr.length == 2) {

                            String key = keyValueArr[0];
                            String value = keyValueArr[1];

                            targetDict.put(key, value);
                        } else if (keyValueArr.length > 2) {
                            String key = keyValueArr[0];
                            String value = keyValueArr[1];

                            for (int i = 2; i < keyValueArr.length; i++) {
                                value += "=";
                                value += keyValueArr[i];
                            }
                            targetDict.put(key, value);
                        }
                    }

                    target = targetDict.get("target");

                    if (target.equals("360_BORROW")) {
                        m.type = HrtLinkToTypeSDK360;
                        m.data = "";
                    } else if (target.equals("AN_BORROW")) {
                        m.type = HrtLinkToTypeSDKXiaoAn;
                        m.data = "";
                    } else if (target.equals("CR_BANK")) {
                        m.type = HrtLinkToTypeSDKCrBank;
                        m.data = "";
                        if (TextUtils.isEmpty(targetDict.get(KEY_CRBANk_JUMP))) {
                            m.data = KEY_CRBANk_JUMP + "=" + targetDict.get(KEY_CRBANk_JUMP);
                        }
                    } else if (target.equals("CR_WANGUARD") || target.equals("CR_VANGUARD")) {
                        m.type = HrtLinkToTypeSDKCrVanguard; //.
                        m.data = "";
                    } else if (target.equals("CR_TRAVEL") || target.equals("CR_VANGUARD")) {
                        m.type = HrtLinkToTypeSDKCrTravel; //.
                        m.data = "";
                    } else {
                        m.type = HrtLinkToTypeSDKNotSupport;
                        m.data = "";
                        Log.e("unsolved LinkToString:" + str);
                    }
                    m.key = m.data;
                }
            } else {
                Log.e("unsolved LinkToString:" + str);
            }
        }

    }

    /**
     *
     * @param context
     * @param imageUrl
     * @param title
     */
    public void jumpToNewAcWithOldAc(Context context, String imageUrl, String title) {
        title = title != null ? title : "";
        if (this.data != null && this.data.equals("#")) {
            return;
        }
        if (context == null) {
            return;
        }

        switch (this.type) {
            case HrtLinkToTypeUrl: {
                // html5
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }
            case HrtLinkToTypeDataTemplete: {
                // html5
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }
            case HrtLinkToTypeGoodsDetail: {
                //jump 商品详情.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeChannel: {
                //jump 频道页.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeOShopHome: {
                //jump 电商首页.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }

                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeZXQYList: {
                //jump 尊享权益.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeZXQYDetail: {
                //jump 权益详情.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeGoodsShop: {
                //jump 商品店铺.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeSearchResult: {
                //jump 搜索结果页.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeGoodsCategory: {
                //jump 商品分类页.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeBankCobrandCard: {
                //jump 银行联名卡.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypePointExchange: {
                //jump 积分兑换.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeSDK360: {
                //360借贷.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeSDKXiaoAn: {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeSDKCrBank: {
                //jump 华润银行.
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }

            case HrtLinkToTypeSDKCrVanguard: {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
            }
            break;
            case HrtLinkToTypeSDKNotSupport: {
                // 第三方不支持类型
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }
            case HrtLinkToTypeSDKCrTravel: {
                //华润出行。
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", title);
                if (!TextUtils.isEmpty(imageUrl)){
                    intent.putExtra("imageUrl", imageUrl);
                }
                context.startActivity(intent);
                break;
            }
            default: {

                Log.e("warning: unresolved linkTo type:" + this.type);
            }
            break;

        }

    }

    public void jumpToNewAcWithOldAc(Context context, String title){
        jumpToNewAcWithOldAc(context,null,title);
    }




}
