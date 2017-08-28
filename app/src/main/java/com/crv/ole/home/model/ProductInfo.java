package com.crv.ole.home.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


/**
 * Created by yanghongjun on 17/6/29.
 */

public class ProductInfo implements Serializable{



    public String spec;
    public String startTime;
    public String remainingTime;
    public String beginDateTime;
    public String endDateTime;
    public boolean flag;
    public boolean unSetChange;
    public boolean timeRunning;
    public String marketPrice;
    public String weight;
    public int stockWidth;
    public int sellableCount;
    public int stock;
    public String salesAmount;
    public String marketPriceString;
    public String id;
    public String realProductId;
    public String skuId;
    public List<SkusInfo> productSkus ;
    public String fileId;
    public String imgUrl;
    public String flagImgUrl;
    public String url;
    public String linkTo;
    public String tag;
    public boolean selected;
    public String name;
    public String description;
    public String memberPriceString;
    public String merchantId;
    public String merchantName;
    public String sellingPoint;
    public boolean isGlobal = false;
    public String memberPrice;
    public boolean showName = false;
    public String flagUrl_86X48;
    public String buyCount;
    public int star;
    public String desComment;
    public String salemarker; //商品角标Url
    public HashMap<String,String> productCanDelivery;
    public String flagUrl;//跨境表url
    public String countryName;//国家名称
    public String bonded;//保税描述
    public String channel_jx;//万家精选标签url
}
