package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/20.
 */

public class CheckMemberByMobileInfoResultBean extends BaseResponseData implements Serializable{
    private CheckMemberByMobileInfoResult RETURN_DATA;

    public CheckMemberByMobileInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(CheckMemberByMobileInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class CheckMemberByMobileInfoResult{
        private String flag;
        private String flag2;
        private String sysid;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getFlag2() {
            return flag2;
        }

        public void setFlag2(String flag2) {
            this.flag2 = flag2;
        }

        public String getSysid() {
            return sysid;
        }

        public void setSysid(String sysid) {
            this.sysid = sysid;
        }
    }

    /**
     * 传参
     */
    public class CheckMemberByMobileInfo{
        private String channel;
        private String mobile;
        private String focusshopid;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFocusshopid() {
            return focusshopid;
        }

        public void setFocusshopid(String focusshopid) {
            this.focusshopid = focusshopid;
        }
    }
}
