
package com.crv.ole.shopping.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/7/31.
 */

public class PreSaleResult implements Serializable {
    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-08-07 15:39:20
     * RETURN_DATA : {"spec":"50-60个","temperatureControl":{"des":"常温","state":"1"},"mobileContent":[],"originPlace":"","tag":"","keepInfos":{"des":"未收藏","isKeep":false},"sellingPoint":"此处是卖点，卖点 卖点","preSaleAttr":{"jRule":{"createTime":1501486923629,"beginLongTime":"1501832626000","scope":"","desc":"预售","displayAmount":10,"endLongTime":"1502351026000","deposit":"30.00","beginTime":"2017-08-04 15:43:46","depositBeginLongTime":"1501487026000","endTime":"2017-08-10 15:43:46","type":"1","depositBeginTime":"2017-07-31 15:43:46","id":"preSaleRule_320000","balance":"70.00","rate":1,"lastmodifiedTime":1501487342881,"items":[{"id":"p_2840002","imgUrl":"../upload/nopic_100.gif","name":"雪花啤酒","memberPrice":"100.00"},{"id":"p_2530028","imgUrl":"../upload/nopic_100.gif","name":"小熊SNJ-5015多功能酸奶机 白色","memberPrice":"139.00"},{"id":"p_2560002","imgUrl":"../upload/nopic_100.gif","name":"【全国配送】 Mino Yaki 美浓烧日本陶瓷酒盅清酒杯礼盒JJ-78117 蓝变","memberPrice":"159.00"}],"depositEndLongTime":"1501832626000","name":"预售7.31","depositEndTime":"2017-08-04 15:43:46","mid":"head_merchant","createUserId":"u_0","totalPrice":"100.00"},"state":{"des":"尾款支付时间","now":1502091598212,"code":"2"},"bookAmount":"0","bindState":{}},"country":{"imgUrl":"http://10.0.147.163/img/2016/8/11/7830039.png","CName":"韩国","name":"KOREA"},"shelfLife":"15-30天","skuPrice":{"marketPrice":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"},"loginUserId":"","specialMsg":"","membersPrices":[{"groupId":"c_1880004","groupName":"Ole会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"},"memberLv":0},{"groupId":"c_1880005","groupName":"华润通V1会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"110.00","isSpecialPrice":"N"},"memberLv":1},{"groupId":"c_1880006","groupName":"华润通V2会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"85.00","isSpecialPrice":"N"},"memberLv":2},{"groupId":"c_1880007","groupName":"华润通V3会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"70.00","isSpecialPrice":"N"},"memberLv":3}],"specialPirce":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"50.00","isSpecialPrice":"Y"},"hasSpecialPrice":true,"curPrice":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","curMemberLv":-2,"unitPrice":"50.00","isSpecialPrice":"Y"}},"name":"雪花啤酒","images":[{"url":"http://10.0.147.163/img/2015/9/9/3310078_100X100.jpg"},{"url":"http://10.0.147.163/img/2015/10/20/3850017_100X100.jpg"},{"url":"http://10.0.147.163/img/2017/2/15/85200103423902881023311_100X100.png"},{"url":"http://10.0.147.163/img/2016/6/24/7350177_100X100.jpg"}],"brand":{"id":"c_brand_other","name":"其他"},"merchantId":"m_730001","sellUnit":"组"}
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
         * spec : 50-60个
         * temperatureControl : {"des":"常温","state":"1"}
         * mobileContent : []
         * originPlace :
         * tag :
         * keepInfos : {"des":"未收藏","isKeep":false}
         * sellingPoint : 此处是卖点，卖点 卖点
         * preSaleAttr : {"jRule":{"createTime":1501486923629,"beginLongTime":"1501832626000","scope":"","desc":"预售","displayAmount":10,"endLongTime":"1502351026000","deposit":"30.00","beginTime":"2017-08-04 15:43:46","depositBeginLongTime":"1501487026000","endTime":"2017-08-10 15:43:46","type":"1","depositBeginTime":"2017-07-31 15:43:46","id":"preSaleRule_320000","balance":"70.00","rate":1,"lastmodifiedTime":1501487342881,"items":[{"id":"p_2840002","imgUrl":"../upload/nopic_100.gif","name":"雪花啤酒","memberPrice":"100.00"},{"id":"p_2530028","imgUrl":"../upload/nopic_100.gif","name":"小熊SNJ-5015多功能酸奶机 白色","memberPrice":"139.00"},{"id":"p_2560002","imgUrl":"../upload/nopic_100.gif","name":"【全国配送】 Mino Yaki 美浓烧日本陶瓷酒盅清酒杯礼盒JJ-78117 蓝变","memberPrice":"159.00"}],"depositEndLongTime":"1501832626000","name":"预售7.31","depositEndTime":"2017-08-04 15:43:46","mid":"head_merchant","createUserId":"u_0","totalPrice":"100.00"},"state":{"des":"尾款支付时间","now":1502091598212,"code":"2"},"bookAmount":"0","bindState":{}}
         * country : {"imgUrl":"http://10.0.147.163/img/2016/8/11/7830039.png","CName":"韩国","name":"KOREA"}
         * shelfLife : 15-30天
         * skuPrice : {"marketPrice":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"},"loginUserId":"","specialMsg":"","membersPrices":[{"groupId":"c_1880004","groupName":"Ole会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"},"memberLv":0},{"groupId":"c_1880005","groupName":"华润通V1会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"110.00","isSpecialPrice":"N"},"memberLv":1},{"groupId":"c_1880006","groupName":"华润通V2会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"85.00","isSpecialPrice":"N"},"memberLv":2},{"groupId":"c_1880007","groupName":"华润通V3会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"70.00","isSpecialPrice":"N"},"memberLv":3}],"specialPirce":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"50.00","isSpecialPrice":"Y"},"hasSpecialPrice":true,"curPrice":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","curMemberLv":-2,"unitPrice":"50.00","isSpecialPrice":"Y"}}
         * name : 雪花啤酒
         * images : [{"url":"http://10.0.147.163/img/2015/9/9/3310078_100X100.jpg"},{"url":"http://10.0.147.163/img/2015/10/20/3850017_100X100.jpg"},{"url":"http://10.0.147.163/img/2017/2/15/85200103423902881023311_100X100.png"},{"url":"http://10.0.147.163/img/2016/6/24/7350177_100X100.jpg"}]
         * brand : {"id":"c_brand_other","name":"其他"}
         * merchantId : m_730001
         * sellUnit : 组
         */

        private String spec;
        private TemperatureControlBean temperatureControl;
        private String originPlace;
        private String tag;
        private KeepInfosBean keepInfos;
        private String sellingPoint;
        private PreSaleAttrBean preSaleAttr;
        private CountryBean country;
        private String shelfLife;
        private SkuPriceBean skuPrice;
        private String name;
        private BrandBean brand;
        private String merchantId;
        private String sellUnit;
        private List<?> mobileContent;
        private List<ImagesBean> images;
        private int sellableAmount;//可卖数量

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public TemperatureControlBean getTemperatureControl() {
            return temperatureControl;
        }

        public void setTemperatureControl(TemperatureControlBean temperatureControl) {
            this.temperatureControl = temperatureControl;
        }

        public String getOriginPlace() {
            return originPlace;
        }

        public void setOriginPlace(String originPlace) {
            this.originPlace = originPlace;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public KeepInfosBean getKeepInfos() {
            return keepInfos;
        }

        public void setKeepInfos(KeepInfosBean keepInfos) {
            this.keepInfos = keepInfos;
        }

        public String getSellingPoint() {
            return sellingPoint;
        }

        public void setSellingPoint(String sellingPoint) {
            this.sellingPoint = sellingPoint;
        }

        public PreSaleAttrBean getPreSaleAttr() {
            return preSaleAttr;
        }

        public void setPreSaleAttr(PreSaleAttrBean preSaleAttr) {
            this.preSaleAttr = preSaleAttr;
        }

        public CountryBean getCountry() {
            return country;
        }

        public void setCountry(CountryBean country) {
            this.country = country;
        }

        public String getShelfLife() {
            return shelfLife;
        }

        public void setShelfLife(String shelfLife) {
            this.shelfLife = shelfLife;
        }

        public SkuPriceBean getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(SkuPriceBean skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getSellUnit() {
            return sellUnit;
        }

        public void setSellUnit(String sellUnit) {
            this.sellUnit = sellUnit;
        }

        public List<?> getMobileContent() {
            return mobileContent;
        }

        public void setMobileContent(List<?> mobileContent) {
            this.mobileContent = mobileContent;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public int getSellableAmount() {
            return sellableAmount;
        }

        public void setSellableAmount(int sellableAmount) {
            this.sellableAmount = sellableAmount;
        }

        public static class TemperatureControlBean {
            /**
             * des : 常温
             * state : 1
             */

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

        public static class KeepInfosBean {
            /**
             * des : 未收藏
             * isKeep : false
             */

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

        public static class PreSaleAttrBean {
            /**
             * jRule : {"createTime":1501486923629,"beginLongTime":"1501832626000","scope":"","desc":"预售","displayAmount":10,"endLongTime":"1502351026000","deposit":"30.00","beginTime":"2017-08-04 15:43:46","depositBeginLongTime":"1501487026000","endTime":"2017-08-10 15:43:46","type":"1","depositBeginTime":"2017-07-31 15:43:46","id":"preSaleRule_320000","balance":"70.00","rate":1,"lastmodifiedTime":1501487342881,"items":[{"id":"p_2840002","imgUrl":"../upload/nopic_100.gif","name":"雪花啤酒","memberPrice":"100.00"},{"id":"p_2530028","imgUrl":"../upload/nopic_100.gif","name":"小熊SNJ-5015多功能酸奶机 白色","memberPrice":"139.00"},{"id":"p_2560002","imgUrl":"../upload/nopic_100.gif","name":"【全国配送】 Mino Yaki 美浓烧日本陶瓷酒盅清酒杯礼盒JJ-78117 蓝变","memberPrice":"159.00"}],"depositEndLongTime":"1501832626000","name":"预售7.31","depositEndTime":"2017-08-04 15:43:46","mid":"head_merchant","createUserId":"u_0","totalPrice":"100.00"}
             * state : {"des":"尾款支付时间","now":1502091598212,"code":"2"}
             * bookAmount : 0
             * bindState : {}
             */

            private JRuleBean jRule;
            private StateBean state;
            private String bookAmount;
            private BindStateBean bindState;

            public JRuleBean getJRule() {
                return jRule;
            }

            public void setJRule(JRuleBean jRule) {
                this.jRule = jRule;
            }

            public StateBean getState() {
                return state;
            }

            public void setState(StateBean state) {
                this.state = state;
            }

            public String getBookAmount() {
                return bookAmount;
            }

            public void setBookAmount(String bookAmount) {
                this.bookAmount = bookAmount;
            }

            public BindStateBean getBindState() {
                return bindState;
            }

            public void setBindState(BindStateBean bindState) {
                this.bindState = bindState;
            }

            public static class JRuleBean {
                /**
                 * createTime : 1501486923629
                 * beginLongTime : 1501832626000
                 * scope :
                 * desc : 预售
                 * displayAmount : 10
                 * endLongTime : 1502351026000
                 * deposit : 30.00
                 * beginTime : 2017-08-04 15:43:46
                 * depositBeginLongTime : 1501487026000
                 * endTime : 2017-08-10 15:43:46
                 * type : 1
                 * depositBeginTime : 2017-07-31 15:43:46
                 * id : preSaleRule_320000
                 * balance : 70.00
                 * rate : 1
                 * lastmodifiedTime : 1501487342881
                 * items : [{"id":"p_2840002","imgUrl":"../upload/nopic_100.gif","name":"雪花啤酒","memberPrice":"100.00"},{"id":"p_2530028","imgUrl":"../upload/nopic_100.gif","name":"小熊SNJ-5015多功能酸奶机 白色","memberPrice":"139.00"},{"id":"p_2560002","imgUrl":"../upload/nopic_100.gif","name":"【全国配送】 Mino Yaki 美浓烧日本陶瓷酒盅清酒杯礼盒JJ-78117 蓝变","memberPrice":"159.00"}]
                 * depositEndLongTime : 1501832626000
                 * name : 预售7.31
                 * depositEndTime : 2017-08-04 15:43:46
                 * mid : head_merchant
                 * createUserId : u_0
                 * totalPrice : 100.00
                 */

                private long createTime;
                private String beginLongTime;
                private String scope;
                private String desc;
                private int displayAmount;
                private String endLongTime;
                private String deposit;
                private String beginTime;
                private String depositBeginLongTime;
                private String endTime;
                private String type;
                private String depositBeginTime;
                private String id;
                private String balance;
                private int rate;
                private long lastmodifiedTime;
                private String depositEndLongTime;
                private String name;
                private String depositEndTime;
                private String mid;
                private String createUserId;
                private String totalPrice;
                private List<ItemsBean> items;

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public String getBeginLongTime() {
                    return beginLongTime;
                }

                public void setBeginLongTime(String beginLongTime) {
                    this.beginLongTime = beginLongTime;
                }

                public String getScope() {
                    return scope;
                }

                public void setScope(String scope) {
                    this.scope = scope;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public int getDisplayAmount() {
                    return displayAmount;
                }

                public void setDisplayAmount(int displayAmount) {
                    this.displayAmount = displayAmount;
                }

                public String getEndLongTime() {
                    return endLongTime;
                }

                public void setEndLongTime(String endLongTime) {
                    this.endLongTime = endLongTime;
                }

                public String getDeposit() {
                    return deposit;
                }

                public void setDeposit(String deposit) {
                    this.deposit = deposit;
                }

                public String getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(String beginTime) {
                    this.beginTime = beginTime;
                }

                public String getDepositBeginLongTime() {
                    return depositBeginLongTime;
                }

                public void setDepositBeginLongTime(String depositBeginLongTime) {
                    this.depositBeginLongTime = depositBeginLongTime;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getDepositBeginTime() {
                    return depositBeginTime;
                }

                public void setDepositBeginTime(String depositBeginTime) {
                    this.depositBeginTime = depositBeginTime;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getBalance() {
                    return balance;
                }

                public void setBalance(String balance) {
                    this.balance = balance;
                }

                public int getRate() {
                    return rate;
                }

                public void setRate(int rate) {
                    this.rate = rate;
                }

                public long getLastmodifiedTime() {
                    return lastmodifiedTime;
                }

                public void setLastmodifiedTime(long lastmodifiedTime) {
                    this.lastmodifiedTime = lastmodifiedTime;
                }

                public String getDepositEndLongTime() {
                    return depositEndLongTime;
                }

                public void setDepositEndLongTime(String depositEndLongTime) {
                    this.depositEndLongTime = depositEndLongTime;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDepositEndTime() {
                    return depositEndTime;
                }

                public void setDepositEndTime(String depositEndTime) {
                    this.depositEndTime = depositEndTime;
                }

                public String getMid() {
                    return mid;
                }

                public void setMid(String mid) {
                    this.mid = mid;
                }

                public String getCreateUserId() {
                    return createUserId;
                }

                public void setCreateUserId(String createUserId) {
                    this.createUserId = createUserId;
                }

                public String getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(String totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public List<ItemsBean> getItems() {
                    return items;
                }

                public void setItems(List<ItemsBean> items) {
                    this.items = items;
                }

                public static class ItemsBean {
                    /**
                     * id : p_2840002
                     * imgUrl : ../upload/nopic_100.gif
                     * name : 雪花啤酒
                     * memberPrice : 100.00
                     */

                    private String id;
                    private String imgUrl;
                    private String name;
                    private String memberPrice;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getImgUrl() {
                        return imgUrl;
                    }

                    public void setImgUrl(String imgUrl) {
                        this.imgUrl = imgUrl;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getMemberPrice() {
                        return memberPrice;
                    }

                    public void setMemberPrice(String memberPrice) {
                        this.memberPrice = memberPrice;
                    }
                }
            }

            public static class StateBean {

                private String code;//0：预售未开始 1：订金支付时间段 2：尾款支付时间段 3：预售活动结束
                private String des;//描述
                private double now;//系统当前时间的时间戳

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getDes() {
                    return des;
                }

                public void setDes(String des) {
                    this.des = des;
                }

                public double getNow() {
                    return now;
                }

                public void setNow(double now) {
                    this.now = now;
                }
            }

            public static class BindStateBean {

                private String bindKey;//为空则还没有设置提醒
                private String msg;//提醒描述	string
                private boolean state;//是否设置了此类提醒

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
        }

        public static class CountryBean {
            /**
             * imgUrl : http://10.0.147.163/img/2016/8/11/7830039.png
             * CName : 韩国
             * name : KOREA
             */

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

        public static class SkuPriceBean {
            /**
             * marketPrice : {"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"}
             * loginUserId :
             * specialMsg :
             * membersPrices : [{"groupId":"c_1880004","groupName":"Ole会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"200.00","isSpecialPrice":"N"},"memberLv":0},{"groupId":"c_1880005","groupName":"华润通V1会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"110.00","isSpecialPrice":"N"},"memberLv":1},{"groupId":"c_1880006","groupName":"华润通V2会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"85.00","isSpecialPrice":"N"},"memberLv":2},{"groupId":"c_1880007","groupName":"华润通V3会员","price":{"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"70.00","isSpecialPrice":"N"},"memberLv":3}]
             * specialPirce : {"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","unitPrice":"50.00","isSpecialPrice":"Y"}
             * hasSpecialPrice : true
             * curPrice : {"moneyTypeId":"moneytype_RMB","skuId":"sku_2810001","curMemberLv":-2,"unitPrice":"50.00","isSpecialPrice":"Y"}
             */

            private MarketPriceBean marketPrice;
            private String loginUserId;
            private String specialMsg;
            private SpecialPirceBean specialPirce;
            private boolean hasSpecialPrice;
            private CurPriceBean curPrice;
            private List<MembersPricesBean> membersPrices;

            public MarketPriceBean getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(MarketPriceBean marketPrice) {
                this.marketPrice = marketPrice;
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

            public SpecialPirceBean getSpecialPirce() {
                return specialPirce;
            }

            public void setSpecialPirce(SpecialPirceBean specialPirce) {
                this.specialPirce = specialPirce;
            }

            public boolean isHasSpecialPrice() {
                return hasSpecialPrice;
            }

            public void setHasSpecialPrice(boolean hasSpecialPrice) {
                this.hasSpecialPrice = hasSpecialPrice;
            }

            public CurPriceBean getCurPrice() {
                return curPrice;
            }

            public void setCurPrice(CurPriceBean curPrice) {
                this.curPrice = curPrice;
            }

            public List<MembersPricesBean> getMembersPrices() {
                return membersPrices;
            }

            public void setMembersPrices(List<MembersPricesBean> membersPrices) {
                this.membersPrices = membersPrices;
            }

            public static class MarketPriceBean {
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

            public static class SpecialPirceBean {
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

            public static class CurPriceBean {
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

            public static class MembersPricesBean {
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

                public static class PriceBean {
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

        public static class BrandBean {
            /**
             * id : c_brand_other
             * name : 其他
             */

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

        public static class ImagesBean {
            /**
             * url : http://10.0.147.163/img/2015/9/9/3310078_100X100.jpg
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
