package com.crv.ole.pay.requestmodel;

import java.util.List;

/**
 * 作用描述：订单提交需要的参数
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3 16:01.
 */

public class RequestOrderConfirmModel {

    //buyingDevice	设备型号，如:iphone6
    //memo	订单备注
    //merchantId	需要结算的商家Id,不传就结算购物车所有的商家
    //source	订单来源,不传默认前台订单 mobile_androidApp:安卓App mobile_androidWeb:安卓触屏 mobile_iosApp:苹果App mobile_iosWeb:苹果触屏
    //orderType	订单类型,common:普通订单,preSale:预售订单，不传默认普通订单
    //preSaleMobile	预售尾款通知手机号码,预售尾款支付通知手机号码
    private String buyingDevice, memo, merchantId, invoiceTitle, source = "mobile_androidApp", orderType, preSaleMobile;
    //积分支付金额，不传默认不使用积分支付
    private float integralPay;

    //prepayCards	预付卡支付信息
    private List<PrepayCard> prepayCards;

    //购物券支付信息列表
    private List<DisscountCouponInfo> selectedCardBatchAmounts;

    public String getBuyingDevice() {
        return buyingDevice;
    }

    public void setBuyingDevice(String buyingDevice) {
        this.buyingDevice = buyingDevice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getIntegralPay() {
        return integralPay;
    }

    public void setIntegralPay(float integralPay) {
        this.integralPay = integralPay;
    }

    public List<PrepayCard> getPrepayCards() {
        return prepayCards;
    }

    public void setPrepayCards(List<PrepayCard> prepayCards) {
        this.prepayCards = prepayCards;
    }

    public List<DisscountCouponInfo> getSelectedCardBatchAmounts() {
        return selectedCardBatchAmounts;
    }

    public void setSelectedCardBatchAmounts(List<DisscountCouponInfo> selectedCardBatchAmounts) {
        this.selectedCardBatchAmounts = selectedCardBatchAmounts;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPreSaleMobile() {
        return preSaleMobile;
    }

    public void setPreSaleMobile(String preSaleMobile) {
        this.preSaleMobile = preSaleMobile;
    }

    /**
     * 预付卡信息
     */
    public static class PrepayCard {
        private String cardNo;
        private String usedAmount;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getUsedAmount() {
            return usedAmount;
        }

        public void setUsedAmount(String usedAmount) {
            this.usedAmount = usedAmount;
        }
    }

    /**
     * 优惠券信息
     */
    public static class DisscountCouponInfo {
        private String cardBatchId, selectedNumber;

        public String getCardBatchId() {
            return cardBatchId;
        }

        public void setCardBatchId(String cardBatchId) {
            this.cardBatchId = cardBatchId;
        }

        public String getSelectedNumber() {
            return selectedNumber;
        }

        public void setSelectedNumber(String selectedNumber) {
            this.selectedNumber = selectedNumber;
        }
    }
}
