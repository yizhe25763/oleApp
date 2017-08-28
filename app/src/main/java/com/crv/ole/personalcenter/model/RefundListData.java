package com.crv.ole.personalcenter.model;

import com.crv.ole.shopping.model.RefundDetailResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 退款退货单数据实体
 * Created by zhangbo on 2017/8/23.
 */

public class RefundListData implements Serializable {
    private String aliasCode;//退款单号
    private long createTime;//退款单申请时间戳
    private String createUserId;//申请人ID
    private String createUserName;//申请人名称
    private String fMoney;//	退款总金额
    private String formatCreateTime;//申请时间
    private String id;
    private String merchantId;//商家ID
    private String orderAliasCode;//订单的外部订单号
    private String orderType;//退款单类型（不需要）

    private BaseState processState;//过程状态

    private String totalRefundPrice;//退款意向金额

    private BaseState type;//退款单类型

    private String totalCount;//总数量

    private List<RefundDetailResponseData.Items> items;

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getfMoney() {
        return fMoney;
    }

    public void setfMoney(String fMoney) {
        this.fMoney = fMoney;
    }

    public String getFormatCreateTime() {
        return formatCreateTime;
    }

    public void setFormatCreateTime(String formatCreateTime) {
        this.formatCreateTime = formatCreateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderAliasCode() {
        return orderAliasCode;
    }

    public void setOrderAliasCode(String orderAliasCode) {
        this.orderAliasCode = orderAliasCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BaseState getProcessState() {
        return processState;
    }

    public void setProcessState(BaseState processState) {
        this.processState = processState;
    }

    public String getTotalRefundPrice() {
        return totalRefundPrice;
    }

    public void setTotalRefundPrice(String totalRefundPrice) {
        this.totalRefundPrice = totalRefundPrice;
    }

    public BaseState getType() {
        return type;
    }

    public void setType(BaseState type) {
        this.type = type;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<RefundDetailResponseData.Items> getItems() {
        return items;
    }

    public void setItems(List<RefundDetailResponseData.Items> items) {
        this.items = items;
    }
}
