package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/19.
 * 注册会员（返回数据）
 */

public class RegisterMemberInfoResultBean extends BaseResponseData implements Serializable{
    private RegisterMemberInfoResult RETURN_DATA;

    public RegisterMemberInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RegisterMemberInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class RegisterMemberInfoResult{
        private String token;
        private String userId;
        private String isOpenCard;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getIsOpenCard() {
            return isOpenCard;
        }

        public void setIsOpenCard(String isOpenCard) {
            this.isOpenCard = isOpenCard;
        }
    }

    /**
     * 传参
     */
    public class RegisterMemberInfo {
        private String mobilePhone;
        private String password;
        private String validateCode;
        private String parentId;

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getValidateCode() {
            return validateCode;
        }

        public void setValidateCode(String validateCode) {
            this.validateCode = validateCode;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
