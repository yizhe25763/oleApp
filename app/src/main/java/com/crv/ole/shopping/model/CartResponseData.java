package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihongshi on 2017/8/2.
 * 购物车
 */
public class CartResponseData extends BaseResponseData implements Serializable {

    private RETURN_DATA RETURN_DATA;

    public void setRETURN_DATA(RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public class RETURN_DATA {

        private int convertTime;
        private List<Carts> carts;
        private int getUserTime;
        private int time;
        private int getOcsTime;
        private String userId;
        private int getCartTime;

        public void setConvertTime(int convertTime) {
            this.convertTime = convertTime;
        }

        public int getConvertTime() {
            return convertTime;
        }

        public void setCarts(List<Carts> carts) {
            this.carts = carts;
        }

        public List<Carts> getCarts() {
            return carts;
        }

        public void setGetUserTime(int getUserTime) {
            this.getUserTime = getUserTime;
        }

        public int getGetUserTime() {
            return getUserTime;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTime() {
            return time;
        }

        public void setGetOcsTime(int getOcsTime) {
            this.getOcsTime = getOcsTime;
        }

        public int getGetOcsTime() {
            return getOcsTime;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setGetCartTime(int getCartTime) {
            this.getCartTime = getCartTime;
        }

        public int getGetCartTime() {
            return getCartTime;
        }

    }

    public class Carts {

        private String finalPayAmount;
        private String totalDeliveryFee;
        private String merchantId;
        private String cartId;
        private String totalTaxPrice;
        private String cartType;
        private List<BuyItems> buyItems;
        private List<OrderRuleTargets> orderRuleTargets;
        private String totalOrderProductPrice;
        private String merchantName;
        private String totalPayPrice;

        public void setFinalPayAmount(String finalPayAmount) {
            this.finalPayAmount = finalPayAmount;
        }

        public String getFinalPayAmount() {
            return finalPayAmount;
        }

        public void setTotalDeliveryFee(String totalDeliveryFee) {
            this.totalDeliveryFee = totalDeliveryFee;
        }

        public String getTotalDeliveryFee() {
            return totalDeliveryFee;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getCartId() {
            return cartId;
        }

        public void setTotalTaxPrice(String totalTaxPrice) {
            this.totalTaxPrice = totalTaxPrice;
        }

        public String getTotalTaxPrice() {
            return totalTaxPrice;
        }

        public void setCartType(String cartType) {
            this.cartType = cartType;
        }

        public String getCartType() {
            return cartType;
        }

        public void setBuyItems(List<BuyItems> buyItems) {
            this.buyItems = buyItems;
        }

        public List<BuyItems> getBuyItems() {
            return buyItems;
        }

        public void setOrderRuleTargets(List<OrderRuleTargets> orderRuleTargets) {
            this.orderRuleTargets = orderRuleTargets;
        }

        public List<OrderRuleTargets> getOrderRuleTargets() {
            return orderRuleTargets;
        }

        public void setTotalOrderProductPrice(String totalOrderProductPrice) {
            this.totalOrderProductPrice = totalOrderProductPrice;
        }

        public String getTotalOrderProductPrice() {
            return totalOrderProductPrice;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setTotalPayPrice(String totalPayPrice) {
            this.totalPayPrice = totalPayPrice;
        }

        public String getTotalPayPrice() {
            return totalPayPrice;
        }

    }


    public class OrderRuleTargets {

        private String actionType;
        private int amount;
        private List<AvailablePresents> availablePresents;
        private String ruleName;
        private List<BuyItems> buyItems;
        private String ruleId;
        private String type;
        private String userFriendlyMessage;
        private String promotionLink;

        public String getPromotionLink() {
            return promotionLink;
        }

        public void setPromotionLink(String promotionLink) {
            this.promotionLink = promotionLink;
        }

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public String getActionType() {
            return actionType;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAvailablePresents(List<AvailablePresents> availablePresents) {
            this.availablePresents = availablePresents;
        }

        public List<AvailablePresents> getAvailablePresents() {
            return availablePresents;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setBuyItems(List<BuyItems> buyItems) {
            this.buyItems = buyItems;
        }

        public List<BuyItems> getBuyItems() {
            return buyItems;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setUserFriendlyMessage(String userFriendlyMessage) {
            this.userFriendlyMessage = userFriendlyMessage;
        }

        public String getUserFriendlyMessage() {
            return userFriendlyMessage;
        }

    }


    public static class BuyItems implements Serializable {
        private String unitPrice;
        private String realSkuId;
        private String productId;
        private String selectedOrderRuleId;
        private String totalPrice;
        private List<String> lowPricePresents;
        private String cartId;
        private String icon;
        private List<OrderAvailableRules> orderAvailableRules;
        private String productName;
        private String totalPayPrice;
        private String number;
        private String itemId;
        private boolean checked;
        private List<AvailableRuleResults> availableRuleResults;
        private String objType;
        private String skuId;
        private List<String> freePresents;
        private String cartType;
        private String preSaleType;
        private String depositBeginTime; //	订金开始支付时间	string
        private String depositEndTime; //订金结束支付时间	string
        private String depositPrice; //定金	number
        private String diffPrice; //差价

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

        public String getDepositPrice() {
            return depositPrice;
        }

        public void setDepositPrice(String depositPrice) {
            this.depositPrice = depositPrice;
        }

        public String getPreSaleType() {
            return preSaleType;
        }

        public void setPreSaleType(String preSaleType) {
            this.preSaleType = preSaleType;
        }

        public String getCartType() {
            return cartType;
        }

        public void setCartType(String cartType) {
            this.cartType = cartType;
        }

        public boolean isChecked() {
            return checked;
        }

        public String getDiffPrice() {
            return diffPrice;
        }

        public void setDiffPrice(String diffPrice) {
            this.diffPrice = diffPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setRealSkuId(String realSkuId) {
            this.realSkuId = realSkuId;
        }

        public String getRealSkuId() {
            return realSkuId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductId() {
            return productId;
        }

        public void setSelectedOrderRuleId(String selectedOrderRuleId) {
            this.selectedOrderRuleId = selectedOrderRuleId;
        }

        public String getSelectedOrderRuleId() {
            return selectedOrderRuleId;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setLowPricePresents(List<String> lowPricePresents) {
            this.lowPricePresents = lowPricePresents;
        }

        public List<String> getLowPricePresents() {
            return lowPricePresents;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getCartId() {
            return cartId;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return icon;
        }

        public void setOrderAvailableRules(List<OrderAvailableRules> orderAvailableRules) {
            this.orderAvailableRules = orderAvailableRules;
        }

        public List<OrderAvailableRules> getOrderAvailableRules() {
            return orderAvailableRules;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductName() {
            return productName;
        }

        public void setTotalPayPrice(String totalPayPrice) {
            this.totalPayPrice = totalPayPrice;
        }

        public String getTotalPayPrice() {
            return totalPayPrice;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean getChecked() {
            return checked;
        }

        public void setAvailableRuleResults(List<AvailableRuleResults> availableRuleResults) {
            this.availableRuleResults = availableRuleResults;
        }

        public List<AvailableRuleResults> getAvailableRuleResults() {
            return availableRuleResults;
        }

        public void setObjType(String objType) {
            this.objType = objType;
        }

        public String getObjType() {
            return objType;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setFreePresents(List<String> freePresents) {
            this.freePresents = freePresents;
        }

        public List<String> getFreePresents() {
            return freePresents;
        }

    }


    public class AvailableRuleResults implements Serializable {

        private String actionType;
        private String ruleName;
        private String ruleId;
        private String type;
        private String userFriendlyMessage;

        public void setActionType(String actionType) {
            this.actionType = actionType;
        }

        public String getActionType() {
            return actionType;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setUserFriendlyMessage(String userFriendlyMessage) {
            this.userFriendlyMessage = userFriendlyMessage;
        }

        public String getUserFriendlyMessage() {
            return userFriendlyMessage;
        }

    }


    public class AvailablePresents {

        private String number;
        private String realSkuId;
        private boolean needChooseSku;
        private String productId;
        private String price;
        private String icon;
        private String name;
        private String skuId;
        private boolean selected;

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setRealSkuId(String realSkuId) {
            this.realSkuId = realSkuId;
        }

        public String getRealSkuId() {
            return realSkuId;
        }

        public void setNeedChooseSku(boolean needChooseSku) {
            this.needChooseSku = needChooseSku;
        }

        public boolean getNeedChooseSku() {
            return needChooseSku;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductId() {
            return productId;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return icon;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean getSelected() {
            return selected;
        }

    }


    public class OrderAvailableRules implements Serializable {

        private String excludeOtherOrderRule;
        private String name;
        private String description;
        private String id;

        public void setExcludeOtherOrderRule(String excludeOtherOrderRule) {
            this.excludeOtherOrderRule = excludeOtherOrderRule;
        }

        public String getExcludeOtherOrderRule() {
            return excludeOtherOrderRule;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

    }

}
