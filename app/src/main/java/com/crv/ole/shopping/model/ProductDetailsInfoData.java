
package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.util.List;

/**
 * Created by Fairy on 2017/8/18.
 */

public class ProductDetailsInfoData extends BaseResponseData {
    private RETURNDATABean RETURN_DATA;

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class RETURNDATABean {
        private boolean hasPromotion;       //  是否有促销活动
        private boolean hasTrialReports;    //  是否有试用报告
        private String merchantId;
        private String name;
        private String originPlace;
        private String sellUnit;
        private int sellableAmount;     //  可卖数量
        private String sellingPoint;    //  卖点
        private String shelfLife;       //  保质期
        private String spec;
        private String tag;
        private ArrivalNotifyBean arrivalNotify;
        private BrandBean brand;
        private CountryBean country;
        private List<ImagesBean> images;
        private List<ImagesBean> mobileContent;
        private KeepInfosBean keepInfos;
        private PromotionNotifyBean promotionNotify;    //  是否设置了促销提醒
        private TemperatureControlBean temperatureControl;
        private SkuPriceBean skuPrice;

        public boolean isHasPromotion() {
            return hasPromotion;
        }

        public void setHasPromotion(boolean hasPromotion) {
            this.hasPromotion = hasPromotion;
        }

        public boolean isHasTrialReports() {
            return hasTrialReports;
        }

        public void setHasTrialReports(boolean hasTrialReports) {
            this.hasTrialReports = hasTrialReports;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginPlace() {
            return originPlace;
        }

        public void setOriginPlace(String originPlace) {
            this.originPlace = originPlace;
        }

        public String getSellUnit() {
            return sellUnit;
        }

        public void setSellUnit(String sellUnit) {
            this.sellUnit = sellUnit;
        }

        public int getSellableAmount() {
            return sellableAmount;
        }

        public void setSellableAmount(int sellableAmount) {
            this.sellableAmount = sellableAmount;
        }

        public String getSellingPoint() {
            return sellingPoint;
        }

        public void setSellingPoint(String sellingPoint) {
            this.sellingPoint = sellingPoint;
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

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public ArrivalNotifyBean getArrivalNotify() {
            return arrivalNotify;
        }

        public void setArrivalNotify(ArrivalNotifyBean arrivalNotify) {
            this.arrivalNotify = arrivalNotify;
        }

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public CountryBean getCountry() {
            return country;
        }

        public void setCountry(CountryBean country) {
            this.country = country;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public List<ImagesBean> getMobileContent() {
            return mobileContent;
        }

        public void setMobileContent(List<ImagesBean> mobileContent) {
            this.mobileContent = mobileContent;
        }

        public KeepInfosBean getKeepInfos() {
            return keepInfos;
        }

        public void setKeepInfos(KeepInfosBean keepInfos) {
            this.keepInfos = keepInfos;
        }

        public PromotionNotifyBean getPromotionNotify() {
            return promotionNotify;
        }

        public void setPromotionNotify(PromotionNotifyBean promotionNotify) {
            this.promotionNotify = promotionNotify;
        }

        public TemperatureControlBean getTemperatureControl() {
            return temperatureControl;
        }

        public void setTemperatureControl(TemperatureControlBean temperatureControl) {
            this.temperatureControl = temperatureControl;
        }

        public SkuPriceBean getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(SkuPriceBean skuPrice) {
            this.skuPrice = skuPrice;
        }

        /**
         * 到货提醒设置
         */
        public class ArrivalNotifyBean {
            private String bindKey;
            private String msg;
            private boolean state;

            public String getBindKey() {
                return bindKey;
            }

            public void setBindKey(String bindKey) {
                this.bindKey = bindKey;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public boolean isState() {
                return state;
            }

            public void setState(boolean state) {
                this.state = state;
            }
        }

        /**
         * 品牌
         */
        public class BrandBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        /**
         * 产地国
         */
        public class CountryBean {
            private String imgUrl;
            private String CName;
            private String name;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getCName() {
                return CName;
            }

            public void setCName(String CName) {
                this.CName = CName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        /**
         * 图片
         */
        public class ImagesBean {
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        /**
         * 收藏信息
         */
        public class KeepInfosBean {
            private String des;
            private boolean isKeep;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public boolean isIsKeep() {
                return isKeep;
            }

            public void setIsKeep(boolean isKeep) {
                this.isKeep = isKeep;
            }
        }

        /**
         * 促销提醒设置
         */
        public class PromotionNotifyBean {
            private String bindKey;
            private String msg;
            private boolean state;

            public String getBindKey() {
                return bindKey;
            }

            public void setBindKey(String bindKey) {
                this.bindKey = bindKey;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public boolean isState() {
                return state;
            }

            public void setState(boolean state) {
                this.state = state;
            }
        }

        /**
         * 温控属性
         */
        public class TemperatureControlBean {
            private String des;
            private String state;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }

        /**
         * 价格
         */
        public class SkuPriceBean {
            private boolean hasSpecialPrice;
            private String loginUserId;
            private String specialMsg;
            private CurPriceBean curPrice;
            private MarketPriceBean marketPrice;
            private SpecialPirceBean specialPirce;
            private List<MembersPricesBean> membersPrices;

            public boolean isHasSpecialPrice() {
                return hasSpecialPrice;
            }

            public void setHasSpecialPrice(boolean hasSpecialPrice) {
                this.hasSpecialPrice = hasSpecialPrice;
            }

            public String getLoginUserId() {
                return loginUserId;
            }

            public void setLoginUserId(String loginUserId) {
                this.loginUserId = loginUserId;
            }

            public String getSpecialMsg() {
                return specialMsg;
            }

            public void setSpecialMsg(String specialMsg) {
                this.specialMsg = specialMsg;
            }

            public CurPriceBean getCurPrice() {
                return curPrice;
            }

            public void setCurPrice(CurPriceBean curPrice) {
                this.curPrice = curPrice;
            }

            public MarketPriceBean getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(MarketPriceBean marketPrice) {
                this.marketPrice = marketPrice;
            }

            public SpecialPirceBean getSpecialPirce() {
                return specialPirce;
            }

            public void setSpecialPirce(SpecialPirceBean specialPirce) {
                this.specialPirce = specialPirce;
            }

            public List<MembersPricesBean> getMembersPrices() {
                return membersPrices;
            }

            public void setMembersPrices(List<MembersPricesBean> membersPrices) {
                this.membersPrices = membersPrices;
            }

            public class CurPriceBean {
                /**
                 * moneyTypeId : moneytype_RMB
                 * skuId : sku_2810001
                 * curMemberLv : -2
                 * unitPrice : 50.00
                 * isSpecialPrice : Y
                 */

                private String moneyTypeId;
                private String skuId;
                private int curMemberLv;
                private String unitPrice;
                private String isSpecialPrice;

                public String getMoneyTypeId() {
                    return moneyTypeId;
                }

                public void setMoneyTypeId(String moneyTypeId) {
                    this.moneyTypeId = moneyTypeId;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }

                public int getCurMemberLv() {
                    return curMemberLv;
                }

                public void setCurMemberLv(int curMemberLv) {
                    this.curMemberLv = curMemberLv;
                }

                public String getUnitPrice() {
                    return unitPrice;
                }

                public void setUnitPrice(String unitPrice) {
                    this.unitPrice = unitPrice;
                }

                public String getIsSpecialPrice() {
                    return isSpecialPrice;
                }

                public void setIsSpecialPrice(String isSpecialPrice) {
                    this.isSpecialPrice = isSpecialPrice;
                }
            }

            public class MarketPriceBean {
                /**
                 * moneyTypeId : moneytype_RMB
                 * skuId : sku_2810001
                 * unitPrice : 200.00
                 * isSpecialPrice : N
                 */

                private String moneyTypeId;
                private String skuId;
                private String unitPrice;
                private String isSpecialPrice;

                public String getMoneyTypeId() {
                    return moneyTypeId;
                }

                public void setMoneyTypeId(String moneyTypeId) {
                    this.moneyTypeId = moneyTypeId;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }

                public String getUnitPrice() {
                    return unitPrice;
                }

                public void setUnitPrice(String unitPrice) {
                    this.unitPrice = unitPrice;
                }

                public String getIsSpecialPrice() {
                    return isSpecialPrice;
                }

                public void setIsSpecialPrice(String isSpecialPrice) {
                    this.isSpecialPrice = isSpecialPrice;
                }
            }

            public class SpecialPirceBean {
                /**
                 * moneyTypeId : moneytype_RMB
                 * skuId : sku_2810001
                 * unitPrice : 50.00
                 * isSpecialPrice : Y
                 */

                private String moneyTypeId;
                private String skuId;
                private String unitPrice;
                private String isSpecialPrice;

                public String getMoneyTypeId() {
                    return moneyTypeId;
                }

                public void setMoneyTypeId(String moneyTypeId) {
                    this.moneyTypeId = moneyTypeId;
                }

                public String getSkuId() {
                    return skuId;
                }

                public void setSkuId(String skuId) {
                    this.skuId = skuId;
                }

                public String getUnitPrice() {
                    return unitPrice;
                }

                public void setUnitPrice(String unitPrice) {
                    this.unitPrice = unitPrice;
                }

                public String getIsSpecialPrice() {
                    return isSpecialPrice;
                }

                public void setIsSpecialPrice(String isSpecialPrice) {
                    this.isSpecialPrice = isSpecialPrice;
                }
            }

            public class MembersPricesBean {
                /**
                 * groupId : c_1880004
                 * groupName : Ole会员
                 * price : {"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"}
                 * memberLv : 0
                 */

                private String groupId;
                private String groupName;
                private PriceBean price;
                private int memberLv;

                public String getGroupId() {
                    return groupId;
                }

                public void setGroupId(String groupId) {
                    this.groupId = groupId;
                }

                public String getGroupName() {
                    return groupName;
                }

                public void setGroupName(String groupName) {
                    this.groupName = groupName;
                }

                public PriceBean getPrice() {
                    return price;
                }

                public void setPrice(PriceBean price) {
                    this.price = price;
                }

                public int getMemberLv() {
                    return memberLv;
                }

                public void setMemberLv(int memberLv) {
                    this.memberLv = memberLv;
                }

                public class PriceBean {
                    /**
                     * moneyTypeId : moneytype_RMB
                     * skuId : sku_2810001
                     * unitPrice : 200.00
                     * isSpecialPrice : N
                     */

                    private String moneyTypeId;
                    private String skuId;
                    private String unitPrice;
                    private String isSpecialPrice;

                    public String getMoneyTypeId() {
                        return moneyTypeId;
                    }

                    public void setMoneyTypeId(String moneyTypeId) {
                        this.moneyTypeId = moneyTypeId;
                    }

                    public String getSkuId() {
                        return skuId;
                    }

                    public void setSkuId(String skuId) {
                        this.skuId = skuId;
                    }

                    public String getUnitPrice() {
                        return unitPrice;
                    }

                    public void setUnitPrice(String unitPrice) {
                        this.unitPrice = unitPrice;
                    }

                    public String getIsSpecialPrice() {
                        return isSpecialPrice;
                    }

                    public void setIsSpecialPrice(String isSpecialPrice) {
                        this.isSpecialPrice = isSpecialPrice;
                    }
                }
            }
        }
    }
}
