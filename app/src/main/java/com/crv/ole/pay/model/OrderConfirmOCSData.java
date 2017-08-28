package com.crv.ole.pay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:32.
 */

public class OrderConfirmOCSData implements Serializable {
    private String merchantName, selectedDeliveryRuleId, totalPayPrice, totalOrderProductPrice,
            totalTaxPrice, totalDiscountPrice,balanceBeginTime,balanceEndTime,depositBeginTime,
            depositEndTime,totalBalancePrice,totalDepositPrice;
    private int totalDeliveryFee, totalGoodsNum, totalWeight;
    private ArrayList<OrderConfirmAllCardsData> allCardBatchs;
    private ArrayList<OrderConfirmCouponData> allCardRules;
    private List<OrderConfirmDeliveryData> availableDeliveryRuleResults;
    private List<OrderConfirmGoodsData> buyItems;
    private List<OrderConfirmPayMehtodData> paymentList;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSelectedDeliveryRuleId() {
        return selectedDeliveryRuleId;
    }

    public void setSelectedDeliveryRuleId(String selectedDeliveryRuleId) {
        this.selectedDeliveryRuleId = selectedDeliveryRuleId;
    }

    public int getTotalDeliveryFee() {
        return totalDeliveryFee;
    }

    public void setTotalDeliveryFee(int totalDeliveryFee) {
        this.totalDeliveryFee = totalDeliveryFee;
    }

    public int getTotalGoodsNum() {
        return totalGoodsNum;
    }

    public void setTotalGoodsNum(int totalGoodsNum) {
        this.totalGoodsNum = totalGoodsNum;
    }

    public String getTotalPayPrice() {
        return totalPayPrice;
    }

    public void setTotalPayPrice(String totalPayPrice) {
        this.totalPayPrice = totalPayPrice;
    }

    public String getTotalOrderProductPrice() {
        return totalOrderProductPrice;
    }

    public void setTotalOrderProductPrice(String totalOrderProductPrice) {
        this.totalOrderProductPrice = totalOrderProductPrice;
    }

    public String getTotalTaxPrice() {
        return totalTaxPrice;
    }

    public void setTotalTaxPrice(String totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public ArrayList<OrderConfirmAllCardsData> getAllCardBatchs() {
        return allCardBatchs;
    }

    public void setAllCardBatchs(ArrayList<OrderConfirmAllCardsData> allCardBatchs) {
        this.allCardBatchs = allCardBatchs;
    }

    public ArrayList<OrderConfirmCouponData> getAllCardRules() {
        return allCardRules;
    }

    public void setAllCardRules(ArrayList<OrderConfirmCouponData> allCardRules) {
        this.allCardRules = allCardRules;
    }

    public List<OrderConfirmDeliveryData> getAvailableDeliveryRuleResults() {
        return availableDeliveryRuleResults;
    }

    public void setAvailableDeliveryRuleResults(List<OrderConfirmDeliveryData> availableDeliveryRuleResults) {
        this.availableDeliveryRuleResults = availableDeliveryRuleResults;
    }

    public List<OrderConfirmGoodsData> getBuyItems() {
        return buyItems;
    }

    public void setBuyItems(List<OrderConfirmGoodsData> buyItems) {
        this.buyItems = buyItems;
    }

    public List<OrderConfirmPayMehtodData> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<OrderConfirmPayMehtodData> paymentList) {
        this.paymentList = paymentList;
    }

    public String getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(String totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public String getBalanceBeginTime() {
        return balanceBeginTime;
    }

    public void setBalanceBeginTime(String balanceBeginTime) {
        this.balanceBeginTime = balanceBeginTime;
    }

    public String getBalanceEndTime() {
        return balanceEndTime;
    }

    public void setBalanceEndTime(String balanceEndTime) {
        this.balanceEndTime = balanceEndTime;
    }

    public String getDepositBeginTime() {
        return depositBeginTime;
    }

    public void setDepositBeginTime(String depositBeginTime) {
        this.depositBeginTime = depositBeginTime;
    }

    public String getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(String depositEndTime) {
        this.depositEndTime = depositEndTime;
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
}
