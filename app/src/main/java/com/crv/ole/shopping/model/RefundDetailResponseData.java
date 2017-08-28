package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.util.List;

/**
 * Created by lihongshi on 2017/8/23.
 * 退款退货
 */

public class RefundDetailResponseData extends BaseResponseData {

    private RETURN_DATA RETURN_DATA;

    public RefundDetailResponseData.RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RefundDetailResponseData.RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class RETURN_DATA {

        private String aliasCode;
        private String createUserId;
        private String createUserName;
        private String cusRemark;
        private String fMoney;
        private String formatCreateTime;
        private String id;
        private List<Items> items;
        private String loginId;
        private OrderInfo orderInfo;
        private String remark;
        private States states;
        private String userName;

        public void setAliasCode(String aliasCode) {
            this.aliasCode = aliasCode;
        }

        public String getAliasCode() {
            return aliasCode;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCusRemark(String cusRemark) {
            this.cusRemark = cusRemark;
        }

        public String getCusRemark() {
            return cusRemark;
        }

        public void setFMoney(String fMoney) {
            this.fMoney = fMoney;
        }

        public String getFMoney() {
            return fMoney;
        }

        public void setFormatCreateTime(String formatCreateTime) {
            this.formatCreateTime = formatCreateTime;
        }

        public String getFormatCreateTime() {
            return formatCreateTime;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setStates(States states) {
            this.states = states;
        }

        public States getStates() {
            return states;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

    }


    public class PriceInfo {

        private String fUnitPrice;

        public void setFUnitPrice(String fUnitPrice) {
            this.fUnitPrice = fUnitPrice;
        }

        public String getFUnitPrice() {
            return fUnitPrice;
        }

    }


    public class Items {

        private String cartItemId;
        private int exchangedAmount;
        private String imgUrl;
        private String name;
        private PriceInfo priceInfo;
        private String productId;
        private String realSkuId;
        private int signedAmount;

        public void setCartItemId(String cartItemId) {
            this.cartItemId = cartItemId;
        }

        public String getCartItemId() {
            return cartItemId;
        }

        public void setExchangedAmount(int exchangedAmount) {
            this.exchangedAmount = exchangedAmount;
        }

        public int getExchangedAmount() {
            return exchangedAmount;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setPriceInfo(PriceInfo priceInfo) {
            this.priceInfo = priceInfo;
        }

        public PriceInfo getPriceInfo() {
            return priceInfo;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductId() {
            return productId;
        }

        public void setRealSkuId(String realSkuId) {
            this.realSkuId = realSkuId;
        }

        public String getRealSkuId() {
            return realSkuId;
        }

        public void setSignedAmount(int signedAmount) {
            this.signedAmount = signedAmount;
        }

        public int getSignedAmount() {
            return signedAmount;
        }

    }


    public class Duty {

        private String name;
        private int value;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }


    public class OrderInfo {

        private Duty duty;
        private Reason reason;

        public void setDuty(Duty duty) {
            this.duty = duty;
        }

        public Duty getDuty() {
            return duty;
        }

        public void setReason(Reason reason) {
            this.reason = reason;
        }

        public Reason getReason() {
            return reason;
        }

    }


    public class ApproveState {

        private String state;
        private String stateName;

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateName() {
            return stateName;
        }

    }

    /**
     * Auto-generated: 2017-08-23 17:11:15
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Reason {

        private String id;
        private String name;
        private String value;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }


    public class States {

        private ApproveState approveState;
        private RefundOrderState refundOrderState;
        private RefundState refundState;
        private WarehousingState warehousingState;

        public void setApproveState(ApproveState approveState) {
            this.approveState = approveState;
        }

        public ApproveState getApproveState() {
            return approveState;
        }

        public void setRefundOrderState(RefundOrderState refundOrderState) {
            this.refundOrderState = refundOrderState;
        }

        public RefundOrderState getRefundOrderState() {
            return refundOrderState;
        }

        public void setRefundState(RefundState refundState) {
            this.refundState = refundState;
        }

        public RefundState getRefundState() {
            return refundState;
        }

        public void setWarehousingState(WarehousingState warehousingState) {
            this.warehousingState = warehousingState;
        }

        public WarehousingState getWarehousingState() {
            return warehousingState;
        }

    }

    /**
     * Auto-generated: 2017-08-23 17:11:15
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class WarehousingState {

        private String state;
        private String stateName;

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateName() {
            return stateName;
        }

    }

    /**
     * Auto-generated: 2017-08-23 17:11:15
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class RefundOrderState {

        private int state;
        private int stateName;

        public void setState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public void setStateName(int stateName) {
            this.stateName = stateName;
        }

        public int getStateName() {
            return stateName;
        }

    }

    /**
     * Auto-generated: 2017-08-23 17:11:15
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class RefundState {

        private String state;
        private String stateName;

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateName() {
            return stateName;
        }

    }

}
