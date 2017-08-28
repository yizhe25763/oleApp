package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;
import com.crv.ole.trial.model.AddressInfo;
import com.crv.ole.trial.model.DeliveryInfoExt;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fairy on 2017/7/14.
 * 订单信息
 */

public class OrderData extends BaseResponseData implements Serializable {

    private BuyerReviewInfo buyerReviewInfo;//买家评论状态 br100：未评论 br101: 已评论

    private String buyerUserName;//购买人名称
    private String cardPayPrice;//预付卡抵扣金额（单位：元）
    private String createTimeString;//订单创建时间
    private String formatCreateTime;//转换后的订单创建时间2017-08-17 13:57:15
    private String fIntegralPayPrice;//积分券抵扣金额（单位：元）
    private String fTotalDeliveryPrice;//总运费
    private String fTotalOrderPrice;//订单总价（包含运费等，单位元）
    private String fTotalOrderRealPrice;//订单实际支付价格（单位：元）
    private String isDeliveryPointName;//	是否自提订单
    private String isNeedInvoiceValue;//是否需要发票;

    private List<OrderItem> items;//订单商品明细

    private String merchantName;//商家名称

    private String orderAliasCode;//外部订单号

    private OrderTypeInfo orderTypeInfo;//订单类型 【普通订单：common】,【preSale：预售订单】,【试用订单：tryuse】

    private List<PayRecs> payRecs;//支付记录

    private PayStateInfo payStateInfo; //p200：待支付 p201: 已支付

    private ProcessStateInfo processStateInfo;//	订单处理状态信息【待审核：p100】,【已确认 待出库：p101】,【已出库：p102】,【已签收：p112】,【已取消：p111】

    private String souceName;//订单来源 order('前台订单','前台订单')

    private String ticketPayPrice;//优惠券抵扣金额（单位：元）

    private AddressInfo deliveryInfo; //收货地址

    private DeliveryInfoExt deliveryInfoExt;//备注

    private PreSaleInfo preSaleInfo;//预售信息

    private RefundStateInfo refundStateInfo;

    public BuyerReviewInfo getBuyerReviewInfo() {
        return buyerReviewInfo;
    }

    public void setBuyerReviewInfo(BuyerReviewInfo buyerReviewInfo) {
        this.buyerReviewInfo = buyerReviewInfo;
    }

    public String getBuyerUserName() {
        return buyerUserName;
    }

    public void setBuyerUserName(String buyerUserName) {
        this.buyerUserName = buyerUserName;
    }

    public String getCardPayPrice() {
        return cardPayPrice;
    }

    public void setCardPayPrice(String cardPayPrice) {
        this.cardPayPrice = cardPayPrice;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getfIntegralPayPrice() {
        return fIntegralPayPrice;
    }

    public void setfIntegralPayPrice(String fIntegralPayPrice) {
        this.fIntegralPayPrice = fIntegralPayPrice;
    }

    public String getfTotalDeliveryPrice() {
        return fTotalDeliveryPrice;
    }

    public void setfTotalDeliveryPrice(String fTotalDeliveryPrice) {
        this.fTotalDeliveryPrice = fTotalDeliveryPrice;
    }

    public String getfTotalOrderPrice() {
        return fTotalOrderPrice;
    }

    public void setfTotalOrderPrice(String fTotalOrderPrice) {
        this.fTotalOrderPrice = fTotalOrderPrice;
    }

    public String getfTotalOrderRealPrice() {
        return fTotalOrderRealPrice;
    }

    public void setfTotalOrderRealPrice(String fTotalOrderRealPrice) {
        this.fTotalOrderRealPrice = fTotalOrderRealPrice;
    }

    public String getIsDeliveryPointName() {
        return isDeliveryPointName;
    }

    public void setIsDeliveryPointName(String isDeliveryPointName) {
        this.isDeliveryPointName = isDeliveryPointName;
    }

    public String getIsNeedInvoiceValue() {
        return isNeedInvoiceValue;
    }

    public void setIsNeedInvoiceValue(String isNeedInvoiceValue) {
        this.isNeedInvoiceValue = isNeedInvoiceValue;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderAliasCode() {
        return orderAliasCode;
    }

    public void setOrderAliasCode(String orderAliasCode) {
        this.orderAliasCode = orderAliasCode;
    }

    public OrderTypeInfo getOrderTypeInfo() {
        return orderTypeInfo;
    }

    public void setOrderTypeInfo(OrderTypeInfo orderTypeInfo) {
        this.orderTypeInfo = orderTypeInfo;
    }

    public List<PayRecs> getPayRecs() {
        return payRecs;
    }

    public void setPayRecs(List<PayRecs> payRecs) {
        this.payRecs = payRecs;
    }

    public PayStateInfo getPayStateInfo() {
        return payStateInfo;
    }

    public void setPayStateInfo(PayStateInfo payStateInfo) {
        this.payStateInfo = payStateInfo;
    }

    public ProcessStateInfo getProcessStateInfo() {
        return processStateInfo;
    }

    public void setProcessStateInfo(ProcessStateInfo processStateInfo) {
        this.processStateInfo = processStateInfo;
    }

    public String getSouceName() {
        return souceName;
    }

    public void setSouceName(String souceName) {
        this.souceName = souceName;
    }

    public String getTicketPayPrice() {
        return ticketPayPrice;
    }

    public void setTicketPayPrice(String ticketPayPrice) {
        this.ticketPayPrice = ticketPayPrice;
    }

    public String getFormatCreateTime() {
        return formatCreateTime;
    }

    public void setFormatCreateTime(String formatCreateTime) {
        this.formatCreateTime = formatCreateTime;
    }

    public AddressInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(AddressInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public DeliveryInfoExt getDeliveryInfoExt() {
        return deliveryInfoExt;
    }

    public void setDeliveryInfoExt(DeliveryInfoExt deliveryInfoExt) {
        this.deliveryInfoExt = deliveryInfoExt;
    }

    public PreSaleInfo getPreSaleInfo() {
        return preSaleInfo;
    }

    public void setPreSaleInfo(PreSaleInfo preSaleInfo) {
        this.preSaleInfo = preSaleInfo;
    }

    public RefundStateInfo getRefundStateInfo() {
        return refundStateInfo;
    }

    public void setRefundStateInfo(RefundStateInfo refundStateInfo) {
        this.refundStateInfo = refundStateInfo;
    }
}
