package com.crv.ole.shopping.model;

import java.io.Serializable;

/**
 * Created by lihongshi on 2017/7/31.
 */

public class Product implements Serializable {
    private String productImage; //商品图片
    private String sellAbleCount; //可卖数量
    private String marketPrice;//市场价
    private ProductCanDelivery productCanDelivery;//是否有货
    private String name;//商品名称
    private String memberPrice;//会员价
    private String sellingPoint;//商品特色信息
    private String productId;//商品ID

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getSellAbleCount() {
        return sellAbleCount;
    }

    public void setSellAbleCount(String sellAbleCount) {
        this.sellAbleCount = sellAbleCount;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public ProductCanDelivery getProductCanDelivery() {
        return productCanDelivery;
    }

    public void setProductCanDelivery(ProductCanDelivery productCanDelivery) {
        this.productCanDelivery = productCanDelivery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private class ProductCanDelivery implements Serializable {
        private String state;//是否有货, 状态, ok: 标示可卖
        private String msg;//是否有货消息

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
