package com.crv.ole.login.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by wj_wsf on 2017/7/7 13:10.
 */

public class LoginResultBean extends BaseResponseData implements Serializable {
    private LoginResult RETURN_DATA;

    public LoginResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(LoginResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class LoginResult {
        private String userId;
        private String token;
        private String loginId;

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


        @Override
        public String toString() {
            return "LoginResult{" +
                    "userId='" + userId + '\'' +
                    ", token='" + token + '\'' +
                    ", loginId='" + loginId + '\'' +
                    '}';
        }
    }
}
