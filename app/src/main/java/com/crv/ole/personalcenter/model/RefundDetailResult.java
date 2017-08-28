package com.crv.ole.personalcenter.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/8/25.
 */

public class RefundDetailResult implements Serializable {


    /**
     * RETURN_DATA : {"loginId":"nihao","formatCreateTime":"2017-06-26 16:45:42","aliasCode":"824020170626","orderAliasCode":"2017062616330961020","remark":"","reason":"生产商问题","deliveryNo":"","cusRemark":"吞吞吐吐","states":{"refundState":{"stateName":"未退款","state":"Refund_0"},"warehousingState":{"stateName":"未入库","state":"Warehousing_N"},"approveState":{"reason":"","stateName":"同意","state":"state_1","lastModifyUserId":"u_0","state_1":{"lastModifyUserId":"u_0","lastModifyTime":"2017-06-26 16:49:36"},"state_2":{"lastModifyUserId":"","lastModifyTime":""},"state_3":{"lastModifyUserId":"","lastModifyTime":""},"lastModifyTime":"2017-06-26 16:49:36"},"type":{"desc":"退货退款","state":"1"},"refundOrderState":{}},"id":"o_common_8280041_refund_3100000","createUserName":"nihao","items":[{"signedAmount":2,"imgUrl":"http://10.0.147.163/img/2015/8/19/3010006.jpg","cartItemId":"cartItem_9630035","priceInfo":{"fUnitPrice":"100.00"},"name":"护舒宝轻柔纯棉感轻薄护垫无香40片","exchangedAmount":1,"realSkuId":"SKU450007","productId":"p_880015"}],"duty":"商家责任","userName":"nihao","fMoney":"100.00","createUserId":"u_3960000"}
     */

    private RETURNDATABean RETURN_DATA;
    private String RETURN_CODE;
    private String RETURN_STAMP;
    private String RETURN_DESC;

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         * loginId : nihao
         * formatCreateTime : 2017-06-26 16:45:42
         * aliasCode : 824020170626
         * orderAliasCode : 2017062616330961020
         * remark :
         * reason : 生产商问题
         * deliveryNo :
         * cusRemark : 吞吞吐吐
         * states : {"refundState":{"stateName":"未退款","state":"Refund_0"},"warehousingState":{"stateName":"未入库","state":"Warehousing_N"},"approveState":{"reason":"","stateName":"同意","state":"state_1","lastModifyUserId":"u_0","state_1":{"lastModifyUserId":"u_0","lastModifyTime":"2017-06-26 16:49:36"},"state_2":{"lastModifyUserId":"","lastModifyTime":""},"state_3":{"lastModifyUserId":"","lastModifyTime":""},"lastModifyTime":"2017-06-26 16:49:36"},"type":{"desc":"退货退款","state":"1"},"refundOrderState":{}}
         * id : o_common_8280041_refund_3100000
         * createUserName : nihao
         * items : [{"signedAmount":2,"imgUrl":"http://10.0.147.163/img/2015/8/19/3010006.jpg","cartItemId":"cartItem_9630035","priceInfo":{"fUnitPrice":"100.00"},"name":"护舒宝轻柔纯棉感轻薄护垫无香40片","exchangedAmount":1,"realSkuId":"SKU450007","productId":"p_880015"}]
         * duty : 商家责任
         * userName : nihao
         * fMoney : 100.00
         * createUserId : u_3960000
         */

        private String loginId;
        private String formatCreateTime;
        private String aliasCode;
        private String orderAliasCode;
        private String remark;
        private String reason;
        private String deliveryNo;
        private String cusRemark;
        private StatesBean states;
        private String id;
        private String createUserName;
        private String duty;
        private String userName;
        private String fMoney;
        private String createUserId;
        private List<ItemsBean> items;

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getFormatCreateTime() {
            return formatCreateTime;
        }

        public void setFormatCreateTime(String formatCreateTime) {
            this.formatCreateTime = formatCreateTime;
        }

        public String getAliasCode() {
            return aliasCode;
        }

        public void setAliasCode(String aliasCode) {
            this.aliasCode = aliasCode;
        }

        public String getOrderAliasCode() {
            return orderAliasCode;
        }

        public void setOrderAliasCode(String orderAliasCode) {
            this.orderAliasCode = orderAliasCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getDeliveryNo() {
            return deliveryNo;
        }

        public void setDeliveryNo(String deliveryNo) {
            this.deliveryNo = deliveryNo;
        }

        public String getCusRemark() {
            return cusRemark;
        }

        public void setCusRemark(String cusRemark) {
            this.cusRemark = cusRemark;
        }

        public StatesBean getStates() {
            return states;
        }

        public void setStates(StatesBean states) {
            this.states = states;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFMoney() {
            return fMoney;
        }

        public void setFMoney(String fMoney) {
            this.fMoney = fMoney;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class StatesBean {
            /**
             * refundState : {"stateName":"未退款","state":"Refund_0"}
             * warehousingState : {"stateName":"未入库","state":"Warehousing_N"}
             * approveState : {"reason":"","stateName":"同意","state":"state_1","lastModifyUserId":"u_0","state_1":{"lastModifyUserId":"u_0","lastModifyTime":"2017-06-26 16:49:36"},"state_2":{"lastModifyUserId":"","lastModifyTime":""},"state_3":{"lastModifyUserId":"","lastModifyTime":""},"lastModifyTime":"2017-06-26 16:49:36"}
             * type : {"desc":"退货退款","state":"1"}
             * refundOrderState : {}
             */

            private RefundStateBean refundState;
            private WarehousingStateBean warehousingState;
            private ApproveStateBean approveState;
            private TypeBean type;
            private RefundOrderStateBean refundOrderState;

            public RefundStateBean getRefundState() {
                return refundState;
            }

            public void setRefundState(RefundStateBean refundState) {
                this.refundState = refundState;
            }

            public WarehousingStateBean getWarehousingState() {
                return warehousingState;
            }

            public void setWarehousingState(WarehousingStateBean warehousingState) {
                this.warehousingState = warehousingState;
            }

            public ApproveStateBean getApproveState() {
                return approveState;
            }

            public void setApproveState(ApproveStateBean approveState) {
                this.approveState = approveState;
            }

            public TypeBean getType() {
                return type;
            }

            public void setType(TypeBean type) {
                this.type = type;
            }

            public RefundOrderStateBean getRefundOrderState() {
                return refundOrderState;
            }

            public void setRefundOrderState(RefundOrderStateBean refundOrderState) {
                this.refundOrderState = refundOrderState;
            }

            public static class RefundStateBean {
                /**
                 * stateName : 未退款
                 * state : Refund_0
                 */

                private String stateName;
                private String state;

                public String getStateName() {
                    return stateName;
                }

                public void setStateName(String stateName) {
                    this.stateName = stateName;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }

            public static class WarehousingStateBean {
                /**
                 * stateName : 未入库
                 * state : Warehousing_N
                 */

                private String stateName;
                private String state;

                public String getStateName() {
                    return stateName;
                }

                public void setStateName(String stateName) {
                    this.stateName = stateName;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }

            public static class ApproveStateBean {
                /**
                 * reason :
                 * stateName : 同意
                 * state : state_1
                 * lastModifyUserId : u_0
                 * state_1 : {"lastModifyUserId":"u_0","lastModifyTime":"2017-06-26 16:49:36"}
                 * state_2 : {"lastModifyUserId":"","lastModifyTime":""}
                 * state_3 : {"lastModifyUserId":"","lastModifyTime":""}
                 * lastModifyTime : 2017-06-26 16:49:36
                 */

                private String reason;
                private String stateName;
                private String state;
                private String lastModifyUserId;
                private State1Bean state_1;
                private State2Bean state_2;
                private State3Bean state_3;
                private String lastModifyTime;

                public String getReason() {
                    return reason;
                }

                public void setReason(String reason) {
                    this.reason = reason;
                }

                public String getStateName() {
                    return stateName;
                }

                public void setStateName(String stateName) {
                    this.stateName = stateName;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getLastModifyUserId() {
                    return lastModifyUserId;
                }

                public void setLastModifyUserId(String lastModifyUserId) {
                    this.lastModifyUserId = lastModifyUserId;
                }

                public State1Bean getState_1() {
                    return state_1;
                }

                public void setState_1(State1Bean state_1) {
                    this.state_1 = state_1;
                }

                public State2Bean getState_2() {
                    return state_2;
                }

                public void setState_2(State2Bean state_2) {
                    this.state_2 = state_2;
                }

                public State3Bean getState_3() {
                    return state_3;
                }

                public void setState_3(State3Bean state_3) {
                    this.state_3 = state_3;
                }

                public String getLastModifyTime() {
                    return lastModifyTime;
                }

                public void setLastModifyTime(String lastModifyTime) {
                    this.lastModifyTime = lastModifyTime;
                }

                public static class State1Bean {
                    /**
                     * lastModifyUserId : u_0
                     * lastModifyTime : 2017-06-26 16:49:36
                     */

                    private String lastModifyUserId;
                    private String lastModifyTime;

                    public String getLastModifyUserId() {
                        return lastModifyUserId;
                    }

                    public void setLastModifyUserId(String lastModifyUserId) {
                        this.lastModifyUserId = lastModifyUserId;
                    }

                    public String getLastModifyTime() {
                        return lastModifyTime;
                    }

                    public void setLastModifyTime(String lastModifyTime) {
                        this.lastModifyTime = lastModifyTime;
                    }
                }

                public static class State2Bean {
                    /**
                     * lastModifyUserId :
                     * lastModifyTime :
                     */

                    private String lastModifyUserId;
                    private String lastModifyTime;

                    public String getLastModifyUserId() {
                        return lastModifyUserId;
                    }

                    public void setLastModifyUserId(String lastModifyUserId) {
                        this.lastModifyUserId = lastModifyUserId;
                    }

                    public String getLastModifyTime() {
                        return lastModifyTime;
                    }

                    public void setLastModifyTime(String lastModifyTime) {
                        this.lastModifyTime = lastModifyTime;
                    }
                }

                public static class State3Bean {
                    /**
                     * lastModifyUserId :
                     * lastModifyTime :
                     */

                    private String lastModifyUserId;
                    private String lastModifyTime;

                    public String getLastModifyUserId() {
                        return lastModifyUserId;
                    }

                    public void setLastModifyUserId(String lastModifyUserId) {
                        this.lastModifyUserId = lastModifyUserId;
                    }

                    public String getLastModifyTime() {
                        return lastModifyTime;
                    }

                    public void setLastModifyTime(String lastModifyTime) {
                        this.lastModifyTime = lastModifyTime;
                    }
                }
            }

            public static class TypeBean {
            }

            public static class RefundOrderStateBean {

                private String lastModifyTime;

                public String getLastModifyTime() {
                    return lastModifyTime;
                }

                public void setLastModifyTime(String lastModifyTime) {
                    this.lastModifyTime = lastModifyTime;
                }
            }
        }

        public static class ItemsBean {
            /**
             * signedAmount : 2
             * imgUrl : http://10.0.147.163/img/2015/8/19/3010006.jpg
             * cartItemId : cartItem_9630035
             * priceInfo : {"fUnitPrice":"100.00"}
             * name : 护舒宝轻柔纯棉感轻薄护垫无香40片
             * exchangedAmount : 1
             * realSkuId : SKU450007
             * productId : p_880015
             */

            private int signedAmount;
            private String imgUrl;
            private String cartItemId;
            private PriceInfoBean priceInfo;
            private String name;
            private int exchangedAmount;
            private String realSkuId;
            private String productId;

            public int getSignedAmount() {
                return signedAmount;
            }

            public void setSignedAmount(int signedAmount) {
                this.signedAmount = signedAmount;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getCartItemId() {
                return cartItemId;
            }

            public void setCartItemId(String cartItemId) {
                this.cartItemId = cartItemId;
            }

            public PriceInfoBean getPriceInfo() {
                return priceInfo;
            }

            public void setPriceInfo(PriceInfoBean priceInfo) {
                this.priceInfo = priceInfo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getExchangedAmount() {
                return exchangedAmount;
            }

            public void setExchangedAmount(int exchangedAmount) {
                this.exchangedAmount = exchangedAmount;
            }

            public String getRealSkuId() {
                return realSkuId;
            }

            public void setRealSkuId(String realSkuId) {
                this.realSkuId = realSkuId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public static class PriceInfoBean {
                /**
                 * fUnitPrice : 100.00
                 */

                private String fUnitPrice;

                public String getFUnitPrice() {
                    return fUnitPrice;
                }

                public void setFUnitPrice(String fUnitPrice) {
                    this.fUnitPrice = fUnitPrice;
                }
            }
        }
    }

}
