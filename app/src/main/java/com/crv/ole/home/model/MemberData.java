package com.crv.ole.home.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by yanghongjun on 17/7/21.
 */

public class MemberData extends BaseResponseData {

    private Member RETURN_DATA;

    public Member getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(Member RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class Member {

        private String memberlevel;
        private String memberid;

        public String getMemberlevel() {
            return memberlevel;
        }

        public void setMemberlevel(String memberlevel) {
            this.memberlevel = memberlevel;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }
    }
}
