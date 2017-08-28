package com.crv.ole.trial.model;

import java.io.Serializable;
import java.util.List;

/**
 * 试用商品详情
 * Created by zhangbo on 2017/8/18.
 */

public class TrialProductDetilData implements Serializable {

    private String activeId;//活动ID

    private Brand band;//品牌

    private String cash;//运费金额

    private Country country;//国家

    private String createTime;

    private String[] freight;

    private String id;

    private int integral;

    private boolean isFreight;//是否免运费

    private boolean isMember;//是否会员

    private List<MobileContent> mobileContent;//商品详情图片链接

    private String originPlace;//产地

    private String priority;//显示优先级

    private String productDescription;//商品描述

    private String productId;//商品ID

    private List<MobileContent> productImg;//商品图片

    private String responsible;

    private String sellNum;// 参与活动商品的个数

    private String sellUnit;//单位

    private String shelfLife;//保质期

    private String spec;//规格 50-60个 数量

    private String state;//是否可用

    private String applicationState;//申请状态 0:未通过 ,1:通过，2:待审核

    private TemperatureControl temperatureControl;//温控

    private AddressInfo addressInfo;//地址信息

    private boolean isApplication;//是否申请过

    private boolean isNotice;

    private String distanceEnd;//结束时间 秒级别

    private int totalRecords;//总申请人数

    private String name;

    private float price;

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public Brand getBand() {
        return band;
    }

    public void setBand(Brand band) {
        this.band = band;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String[] getFreight() {
        return freight;
    }

    public void setFreight(String[] freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public boolean isFreight() {
        return isFreight;
    }

    public void setFreight(boolean freight) {
        isFreight = freight;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public List<MobileContent> getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(List<MobileContent> mobileContent) {
        this.mobileContent = mobileContent;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<MobileContent> getProductImg() {
        return productImg;
    }

    public void setProductImg(List<MobileContent> productImg) {
        this.productImg = productImg;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getSellNum() {
        return sellNum;
    }

    public void setSellNum(String sellNum) {
        this.sellNum = sellNum;
    }

    public String getSellUnit() {
        return sellUnit;
    }

    public void setSellUnit(String sellUnit) {
        this.sellUnit = sellUnit;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public TemperatureControl getTemperatureControl() {
        return temperatureControl;
    }

    public void setTemperatureControl(TemperatureControl temperatureControl) {
        this.temperatureControl = temperatureControl;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public boolean isApplication() {
        return isApplication;
    }

    public void setApplication(boolean application) {
        isApplication = application;
    }

    public String getDistanceEnd() {
        return distanceEnd;
    }

    public void setDistanceEnd(String distanceEnd) {
        this.distanceEnd = distanceEnd;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public void setNotice(boolean notice) {
        isNotice = notice;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(String applicationState) {
        this.applicationState = applicationState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
