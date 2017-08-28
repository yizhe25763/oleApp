package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 订单明细
 * Created by zhangbo on 2017/8/16.
 */

public class OrderItem implements Serializable {

    private int amount;//	购买数量
    private String attrString;//规格
    private String fTotalPrice;//商品价格;
    private String logoUrl;//商品logo地址
    private String moneyTypeName;//币种:RMB
    private String productId;//商品的productId
    private String productName;//商品名称
    private String sellUnitName;//单位 件
    private int signedAmount;//已签收数量
    private String skuId;//skuId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAttrString() {
        return attrString;
    }

    public void setAttrString(String attrString) {
        this.attrString = attrString;
    }

    public String getfTotalPrice() {
        return fTotalPrice;
    }

    public void setfTotalPrice(String fTotalPrice) {
        this.fTotalPrice = fTotalPrice;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMoneyTypeName() {
        return moneyTypeName;
    }

    public void setMoneyTypeName(String moneyTypeName) {
        this.moneyTypeName = moneyTypeName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellUnitName() {
        return sellUnitName;
    }

    public void setSellUnitName(String sellUnitName) {
        this.sellUnitName = sellUnitName;
    }

    public int getSignedAmount() {
        return signedAmount;
    }

    public void setSignedAmount(int signedAmount) {
        this.signedAmount = signedAmount;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
