package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 试用记录
 * <p>
 * 支付超时 48小时 提示支付超时
 * 填写试用报告超时 14天 提示填写试用报告超时
 * <p>
 * Created by zhangbo on 2017/8/17.
 */

public class TrialItemData implements Serializable {

    private String id;
    private String userId; //用户ID
    private String activityId; //活动ID
    private String productId; //商品ID
    private String productObjId;//活动商品ID
    private String productName;//商品名称
    private String productLogo;//商品Logo
    private String orderId;//订单ID
    private int applyState;//申请状态 0:未通过 ,1:通过，2:待审核
    private String aliasCode;//外部订单号
    /**
     * 0非历史订单 1历史订单
     */
    private int isHistory;
    /**
     * TA001:订单已取消
     * TA002:未付款
     * TA003:试用报告已提交
     * TA004:试用报告未提交
     * TA005:试用报告提交超时
     * TP006:仍在物流配送中
     */
    private String orderState;//订单状态
    private long createTime;//创建时间

    private PostageInfo postageInfo;//邮费信息

    public PostageInfo getPostageInfo() {
        return postageInfo;
    }

    public void setPostageInfo(PostageInfo postageInfo) {
        this.postageInfo = postageInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getApplyState() {
        return applyState;
    }

    public void setApplyState(int applyState) {
        this.applyState = applyState;
    }

    public int getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(int isHistory) {
        this.isHistory = isHistory;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getProductObjId() {
        return productObjId;
    }

    public void setProductObjId(String productObjId) {
        this.productObjId = productObjId;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }
}
