package com.crv.ole.trial.model;

import java.io.Serializable;
import java.util.List;

/**
 * 试用活动商品
 * Created by zhangbo on 2017/8/17.
 */

public class TrialProduct implements Serializable {

    private String id;
    private String skuId;
    private String name;
    private String barcode;
    private String columnName;
    private String productImage;
    private String productObjId;
    private String activeId;//活动ID
    private boolean isMember;//
    private boolean isFreight;//
    private String period;//2017-08-15 15:50-2017-08-30 15:50 活动时间
    private String[] freight;
    private String cash;
    private int integral;//
    private String createTime;
    private String state;
    private boolean isNotice;
    private String sellNum;// 商品数量
    private String priority;
    private String postage;//66元或88积分 邮费
    private String memberPrice;//会员价
    private List<MobileContent> mobileContent;

    private String productDescription;


    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public List<MobileContent> getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(List<MobileContent> mobileContent) {
        this.mobileContent = mobileContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductObjId() {
        return productObjId;
    }

    public void setProductObjId(String productObjId) {
        this.productObjId = productObjId;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isFreight() {
        return isFreight;
    }

    public void setFreight(boolean freight) {
        isFreight = freight;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String[] getFreight() {
        return freight;
    }

    public void setFreight(String[] freight) {
        this.freight = freight;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public void setNotice(boolean notice) {
        isNotice = notice;
    }

    public String getSellNum() {
        return sellNum;
    }

    public void setSellNum(String sellNum) {
        this.sellNum = sellNum;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
