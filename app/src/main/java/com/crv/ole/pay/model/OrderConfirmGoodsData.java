package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：确认订单页面信息中商品信息
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:39.
 */

public class OrderConfirmGoodsData implements Serializable {
    private int amount;
    private String attrsString, icon, productId, productName, realSkuId, sellUnitName, skuId,
            taxPrice, totalDealPrice, totalPrice, totalTaxPrice, unitDealPrice, unitPrice,
            totalBalancePrice,totalDepositPrice,balancePrice,depositPrice;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAttrsString() {
        return attrsString;
    }

    public void setAttrsString(String attrsString) {
        this.attrsString = attrsString;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getRealSkuId() {
        return realSkuId;
    }

    public void setRealSkuId(String realSkuId) {
        this.realSkuId = realSkuId;
    }

    public String getSellUnitName() {
        return sellUnitName;
    }

    public void setSellUnitName(String sellUnitName) {
        this.sellUnitName = sellUnitName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getTotalDealPrice() {
        return totalDealPrice;
    }

    public void setTotalDealPrice(String totalDealPrice) {
        this.totalDealPrice = totalDealPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalTaxPrice() {
        return totalTaxPrice;
    }

    public void setTotalTaxPrice(String totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
    }

    public String getUnitDealPrice() {
        return unitDealPrice;
    }

    public void setUnitDealPrice(String unitDealPrice) {
        this.unitDealPrice = unitDealPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalBalancePrice() {
        return totalBalancePrice;
    }

    public void setTotalBalancePrice(String totalBalancePrice) {
        this.totalBalancePrice = totalBalancePrice;
    }

    public String getTotalDepositPrice() {
        return totalDepositPrice;
    }

    public void setTotalDepositPrice(String totalDepositPrice) {
        this.totalDepositPrice = totalDepositPrice;
    }

    public String getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(String balancePrice) {
        this.balancePrice = balancePrice;
    }

    public String getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(String depositPrice) {
        this.depositPrice = depositPrice;
    }
}
