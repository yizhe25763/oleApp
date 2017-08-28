package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/19.
 * 获取短信验证码接口相关
 */

public class SendMsgCodeInfoResultBean extends BaseResponseData implements Serializable {
    private SendMsgCodeInfoResult RETURN_DATA;

    public SendMsgCodeInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(SendMsgCodeInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class SendMsgCodeInfoResult{
        private int msgSendAmount;

        public int getMsgSendAmount() {
            return msgSendAmount;
        }

        public void setMsgSendAmount(int msgSendAmount) {
            this.msgSendAmount = msgSendAmount;
        }
    }

    /**
     * 传参1
     */
    public class SendMsgCodeInfo1 {
        private String iv;
        private String sendParam;

        public String getIv() {
            return iv;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }

        public String getSendParam() {
            return sendParam;
        }

        public void setSendParam(String sendParam) {
            this.sendParam = sendParam;
        }

        @Override
        public String toString() {
            return "SendMsgCodeInfo1{" +
                    "iv='" + iv + '\'' +
                    ", sendParam='" + sendParam + '\'' +
                    '}';
        }
    }



    /**
     * 传参2
     */
    public class  SendMsgCodeInfo2 {
        private String sendPhone;
        private String sendType;
        private String validateCode;

        public String getSendPhone() {
            return sendPhone;
        }

        public void setSendPhone(String sendPhone) {
            this.sendPhone = sendPhone;
        }

        public String getSendType() {
            return sendType;
        }

        public void setSendType(String sendType) {
            this.sendType = sendType;
        }

        public String getValidateCode() {
            return validateCode;
        }

        public void setValidateCode(String validateCode) {
            this.validateCode = validateCode;
        }
    }
}
