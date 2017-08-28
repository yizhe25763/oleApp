package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/7/12 14:17.
 */

public class CouponResponseData extends BaseResponseData implements Serializable {
    private RETURN_DATA RETURN_DATA;


    public RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }


    public class RETURN_DATA implements Serializable {

        private List<VoucherList> voucherList;

        public void setVoucherList(List<VoucherList> voucherList) {
            this.voucherList = voucherList;
        }

        public List<VoucherList> getVoucherList() {
            return voucherList;
        }

    }


    public class VoucherList implements Serializable {

        private String amount;
        private String catchBatchId;
        private String effectedEnd;
        private String outerName;
        private String ruleRemarkDes;

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmount() {
            return amount;
        }

        public void setCatchBatchId(String catchBatchId) {
            this.catchBatchId = catchBatchId;
        }

        public String getCatchBatchId() {
            return catchBatchId;
        }

        public void setEffectedEnd(String effectedEnd) {
            this.effectedEnd = effectedEnd;
        }

        public String getEffectedEnd() {
            return effectedEnd;
        }

        public void setOuterName(String outerName) {
            this.outerName = outerName;
        }

        public String getOuterName() {
            return outerName;
        }

        public void setRuleRemarkDes(String ruleRemarkDes) {
            this.ruleRemarkDes = ruleRemarkDes;
        }

        public String getRuleRemarkDes() {
            return ruleRemarkDes;
        }

    }
}
