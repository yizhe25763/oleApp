package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/21.
 * 会员注册开卡相关
 */

public class OpenMemberCardResultBean extends BaseResponseData implements Serializable {
    private OpenMemberCardResult RETURN_DATA;

    public OpenMemberCardResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(OpenMemberCardResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class OpenMemberCardResult {
        private String memberid;
        private String cardno;

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }
    }
}
