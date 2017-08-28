package com.crv.ole.personalcenter.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/8/21.
 */

public class OrderInfoReslt implements Serializable {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-08-21 14:38:40
     * RETURN_DATA : {"createTime":"1503293598812","formatCreateTime":"2017-08-21 13:33:18","ticketPayPrice":"0.00","orderAliasCode":"2017082113331870003","deliveryInfo":{"address":"香港特别行政区55666","userName":"as","mobile":"15829911155"},"merchantName":"e万家","buyerReviewInfo":{},"fTotalDeliveryPrice":"10.00","payRecs":[{"paymentName":"在线支付","stateName":"未支付"}],"deliveryInfoExt":{"description":""},"preSaleInfo":{},"refundStateInfo":{"name":"未知"},"processStateInfo":{"name":"待审核","state":"p100"},"souceName":"安卓App","fTotalOrderRealPrice":"45.20","isNeedInvoiceValue":"不需要","orderTypeInfo":{"orderType":"common","name":"普通订单"},"fIntegralPayPrice":"0.00","fTotalOrderPrice":"45.20","items":[{"signedAmount":0,"amount":4,"attrString":"","moneyTypeName":"RMB ","skuId":"sku_790000","sellUnitName":"罐","logoUrl":"http://10.0.147.163/img/2015/8/19/3010006_100X100.jpg","productName":"可口可乐汽水330ml","fTotalPrice":"35.20","productId":"p_870000"}],"buyerUserName":"123uuuu","payStateInfo":{"name":"待支付","state":"p200","endPayTime":"1503322398812"},"isDeliveryPointName":"否","createTimeString":"2017-08-21 13:33:18","cardPayPrice":"0.00"}
     */

    private String RETURN_CODE;
    private String RETURN_DESC;
    private String RETURN_STAMP;
    private RETURNDATABean RETURN_DATA;

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         * createTime : 1503293598812
         * formatCreateTime : 2017-08-21 13:33:18
         * ticketPayPrice : 0.00
         * orderAliasCode : 2017082113331870003
         * deliveryInfo : {"address":"香港特别行政区55666","userName":"as","mobile":"15829911155"}
         * merchantName : e万家
         * buyerReviewInfo : {}
         * fTotalDeliveryPrice : 10.00
         * payRecs : [{"paymentName":"在线支付","stateName":"未支付"}]
         * deliveryInfoExt : {"description":""}
         * preSaleInfo : {}
         * refundStateInfo : {"name":"未知"}
         * processStateInfo : {"name":"待审核","state":"p100"}
         * souceName : 安卓App
         * fTotalOrderRealPrice : 45.20
         * isNeedInvoiceValue : 不需要
         * orderTypeInfo : {"orderType":"common","name":"普通订单"}
         * fIntegralPayPrice : 0.00
         * fTotalOrderPrice : 45.20
         * items : [{"signedAmount":0,"amount":4,"attrString":"","moneyTypeName":"RMB ","skuId":"sku_790000","sellUnitName":"罐","logoUrl":"http://10.0.147.163/img/2015/8/19/3010006_100X100.jpg","productName":"可口可乐汽水330ml","fTotalPrice":"35.20","productId":"p_870000"}]
         * buyerUserName : 123uuuu
         * payStateInfo : {"name":"待支付","state":"p200","endPayTime":"1503322398812"}
         * isDeliveryPointName : 否
         * createTimeString : 2017-08-21 13:33:18
         * cardPayPrice : 0.00
         */

        private String createTime;
        private String formatCreateTime;
        private String ticketPayPrice;
        private String orderAliasCode;
        private DeliveryInfoBean deliveryInfo;
        private String merchantName;
        private BuyerReviewInfoBean buyerReviewInfo;
        private String fTotalDeliveryPrice;
        private DeliveryInfoExtBean deliveryInfoExt;
        private PreSaleInfoBean preSaleInfo;
        private RefundStateInfoBean refundStateInfo;
        private ProcessStateInfoBean processStateInfo;
        private String souceName;
        private String fTotalOrderRealPrice;
        private String isNeedInvoiceValue;
        private OrderTypeInfoBean orderTypeInfo;
        private String fIntegralPayPrice;
        private String fTotalOrderPrice;
        private String buyerUserName;
        private PayStateInfoBean payStateInfo;
        private String isDeliveryPointName;
        private String createTimeString;
        private String cardPayPrice;
        private List<PayRecsBean> payRecs;
        private List<ItemsBean> items;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFormatCreateTime() {
            return formatCreateTime;
        }

        public void setFormatCreateTime(String formatCreateTime) {
            this.formatCreateTime = formatCreateTime;
        }

        public String getTicketPayPrice() {
            return ticketPayPrice;
        }

        public void setTicketPayPrice(String ticketPayPrice) {
            this.ticketPayPrice = ticketPayPrice;
        }

        public String getOrderAliasCode() {
            return orderAliasCode;
        }

        public void setOrderAliasCode(String orderAliasCode) {
            this.orderAliasCode = orderAliasCode;
        }

        public DeliveryInfoBean getDeliveryInfo() {
            return deliveryInfo;
        }

        public void setDeliveryInfo(DeliveryInfoBean deliveryInfo) {
            this.deliveryInfo = deliveryInfo;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public BuyerReviewInfoBean getBuyerReviewInfo() {
            return buyerReviewInfo;
        }

        public void setBuyerReviewInfo(BuyerReviewInfoBean buyerReviewInfo) {
            this.buyerReviewInfo = buyerReviewInfo;
        }

        public String getFTotalDeliveryPrice() {
            return fTotalDeliveryPrice;
        }

        public void setFTotalDeliveryPrice(String fTotalDeliveryPrice) {
            this.fTotalDeliveryPrice = fTotalDeliveryPrice;
        }

        public DeliveryInfoExtBean getDeliveryInfoExt() {
            return deliveryInfoExt;
        }

        public void setDeliveryInfoExt(DeliveryInfoExtBean deliveryInfoExt) {
            this.deliveryInfoExt = deliveryInfoExt;
        }

        public PreSaleInfoBean getPreSaleInfo() {
            return preSaleInfo;
        }

        public void setPreSaleInfo(PreSaleInfoBean preSaleInfo) {
            this.preSaleInfo = preSaleInfo;
        }

        public RefundStateInfoBean getRefundStateInfo() {
            return refundStateInfo;
        }

        public void setRefundStateInfo(RefundStateInfoBean refundStateInfo) {
            this.refundStateInfo = refundStateInfo;
        }

        public ProcessStateInfoBean getProcessStateInfo() {
            return processStateInfo;
        }

        public void setProcessStateInfo(ProcessStateInfoBean processStateInfo) {
            this.processStateInfo = processStateInfo;
        }

        public String getSouceName() {
            return souceName;
        }

        public void setSouceName(String souceName) {
            this.souceName = souceName;
        }

        public String getFTotalOrderRealPrice() {
            return fTotalOrderRealPrice;
        }

        public void setFTotalOrderRealPrice(String fTotalOrderRealPrice) {
            this.fTotalOrderRealPrice = fTotalOrderRealPrice;
        }

        public String getIsNeedInvoiceValue() {
            return isNeedInvoiceValue;
        }

        public void setIsNeedInvoiceValue(String isNeedInvoiceValue) {
            this.isNeedInvoiceValue = isNeedInvoiceValue;
        }

        public OrderTypeInfoBean getOrderTypeInfo() {
            return orderTypeInfo;
        }

        public void setOrderTypeInfo(OrderTypeInfoBean orderTypeInfo) {
            this.orderTypeInfo = orderTypeInfo;
        }

        public String getFIntegralPayPrice() {
            return fIntegralPayPrice;
        }

        public void setFIntegralPayPrice(String fIntegralPayPrice) {
            this.fIntegralPayPrice = fIntegralPayPrice;
        }

        public String getFTotalOrderPrice() {
            return fTotalOrderPrice;
        }

        public void setFTotalOrderPrice(String fTotalOrderPrice) {
            this.fTotalOrderPrice = fTotalOrderPrice;
        }

        public String getBuyerUserName() {
            return buyerUserName;
        }

        public void setBuyerUserName(String buyerUserName) {
            this.buyerUserName = buyerUserName;
        }

        public PayStateInfoBean getPayStateInfo() {
            return payStateInfo;
        }

        public void setPayStateInfo(PayStateInfoBean payStateInfo) {
            this.payStateInfo = payStateInfo;
        }

        public String getIsDeliveryPointName() {
            return isDeliveryPointName;
        }

        public void setIsDeliveryPointName(String isDeliveryPointName) {
            this.isDeliveryPointName = isDeliveryPointName;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getCardPayPrice() {
            return cardPayPrice;
        }

        public void setCardPayPrice(String cardPayPrice) {
            this.cardPayPrice = cardPayPrice;
        }

        public List<PayRecsBean> getPayRecs() {
            return payRecs;
        }

        public void setPayRecs(List<PayRecsBean> payRecs) {
            this.payRecs = payRecs;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class DeliveryInfoBean {
            /**
             * address : 香港特别行政区55666
             * userName : as
             * mobile : 15829911155
             */

            private String address;
            private String userName;
            private String mobile;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class BuyerReviewInfoBean {
            private String name;
            private String state;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        public static class DeliveryInfoExtBean {
            /**
             * description :
             */

            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class PreSaleInfoBean {

            private String balanceEndTime;
            private String balanceBeginTime;
            private String balance;//尾款
            private String preSaleType; ///【 定金预售：1】,【定金预售 ，尾款不确定：2】，【全款预售：3】
            private String stockingTime;//
            private String deposit;//定金
            private String depositEndTime;
            private String depositBeginTime;
            private String preSalePayState;//预售支付状态 预售支付状态:0,定金未支付,1:定金已支付,尾款未支付,2:定金与尾款都已支付

            public String getBalanceEndTime() {
                return balanceEndTime;
            }

            public void setBalanceEndTime(String balanceEndTime) {
                this.balanceEndTime = balanceEndTime;
            }

            public String getBalanceBeginTime() {
                return balanceBeginTime;
            }

            public void setBalanceBeginTime(String balanceBeginTime) {
                this.balanceBeginTime = balanceBeginTime;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getPreSaleType() {
                return preSaleType;
            }

            public void setPreSaleType(String preSaleType) {
                this.preSaleType = preSaleType;
            }

            public String getStockingTime() {
                return stockingTime;
            }

            public void setStockingTime(String stockingTime) {
                this.stockingTime = stockingTime;
            }

            public String getDeposit() {
                return deposit;
            }

            public void setDeposit(String deposit) {
                this.deposit = deposit;
            }

            public String getDepositEndTime() {
                return depositEndTime;
            }

            public void setDepositEndTime(String depositEndTime) {
                this.depositEndTime = depositEndTime;
            }

            public String getDepositBeginTime() {
                return depositBeginTime;
            }

            public void setDepositBeginTime(String depositBeginTime) {
                this.depositBeginTime = depositBeginTime;
            }

            public String getPreSalePayState() {
                return preSalePayState;
            }

            public void setPreSalePayState(String preSalePayState) {
                this.preSalePayState = preSalePayState;
            }

        }

        public static class RefundStateInfoBean {
            /**
             * name : 未知
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ProcessStateInfoBean {
            /**
             * name : 待审核
             * state : p100
             */

            private String name;
            private String state;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        public static class OrderTypeInfoBean {
            /**
             * orderType : common
             * name : 普通订单
             */

            private String orderType;
            private String name;

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class PayStateInfoBean {
            /**
             * name : 待支付
             * state : p200
             * endPayTime : 1503322398812
             */

            private String name;
            private String state;
            private String endPayTime;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getEndPayTime() {
                return endPayTime;
            }

            public void setEndPayTime(String endPayTime) {
                this.endPayTime = endPayTime;
            }
        }

        public static class PayRecsBean {
            /**
             * paymentName : 在线支付
             * stateName : 未支付
             */

            private String paymentName;
            private String stateName;

            public String getPaymentName() {
                return paymentName;
            }

            public void setPaymentName(String paymentName) {
                this.paymentName = paymentName;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }
        }

        public static class ItemsBean {
            /**
             * signedAmount : 0
             * amount : 4
             * attrString :
             * moneyTypeName : RMB
             * skuId : sku_790000
             * sellUnitName : 罐
             * logoUrl : http://10.0.147.163/img/2015/8/19/3010006_100X100.jpg
             * productName : 可口可乐汽水330ml
             * fTotalPrice : 35.20
             * productId : p_870000
             */

            private int signedAmount;
            private int amount;
            private String attrString;
            private String moneyTypeName;
            private String skuId;
            private String sellUnitName;
            private String logoUrl;
            private String productName;
            private String fTotalPrice;
            private String productId;
            private Boolean isCheck;

            public Boolean getCheck() {
                return isCheck;
            }

            public void setCheck(Boolean check) {
                isCheck = check;
            }

            public int getSignedAmount() {
                return signedAmount;
            }

            public void setSignedAmount(int signedAmount) {
                this.signedAmount = signedAmount;
            }

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

            public String getMoneyTypeName() {
                return moneyTypeName;
            }

            public void setMoneyTypeName(String moneyTypeName) {
                this.moneyTypeName = moneyTypeName;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public String getSellUnitName() {
                return sellUnitName;
            }

            public void setSellUnitName(String sellUnitName) {
                this.sellUnitName = sellUnitName;
            }

            public String getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(String logoUrl) {
                this.logoUrl = logoUrl;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getFTotalPrice() {
                return fTotalPrice;
            }

            public void setFTotalPrice(String fTotalPrice) {
                this.fTotalPrice = fTotalPrice;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }
        }
    }
}
