package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/19.
 * 绑定会员卡相关
 */

public class BindMemberInfoResultBean extends BaseResponseData implements Serializable {
    public BindMemberInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(BindMemberInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    private BindMemberInfoResult RETURN_DATA;



    public class BindMemberInfoResult{
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

    /**
     * 传参
     */
    public class BindMemberInfo{
        private String mobile;
        private String guestname;
        private String idcard;
        private String cardno;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGuestname() {
            return guestname;
        }

        public void setGuestname(String guestname) {
            this.guestname = guestname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }
    }
}
