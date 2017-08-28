package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by Fairy on 2017/8/9.
 */

public class ProductSaleInfoData extends BaseResponseData {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : success
     * RETURN_STAMP : 2017-08-07 15:17:39
     * RETURN_DATA : {"rule":{"name":"订单使用券满50用2","ruleId":"rule_OUC_2590004","beginDate":"2017-08-03 00:00:00","endDate":"2018-08-31 23:59:59"}}
     */
    private RETURNDATABean RETURN_DATA;

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         * rule : {"name":"订单使用券满50用2","ruleId":"rule_OUC_2590004","beginDate":"2017-08-03 00:00:00","endDate":"2018-08-31 23:59:59"}
         */

        private RuleBean rule;

        public RuleBean getRule() {
            return rule;
        }

        public void setRule(RuleBean rule) {
            this.rule = rule;
        }

        public static class RuleBean {
            /**
             * name : 订单使用券满50用2
             * ruleId : rule_OUC_2590004
             * beginDate : 2017-08-03 00:00:00
             * endDate : 2018-08-31 23:59:59
             */

            private String name;
            private String ruleId;
            private String beginDate;
            private String endDate;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRuleId() {
                return ruleId;
            }

            public void setRuleId(String ruleId) {
                this.ruleId = ruleId;
            }

            public String getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(String beginDate) {
                this.beginDate = beginDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }
        }
    }
}
