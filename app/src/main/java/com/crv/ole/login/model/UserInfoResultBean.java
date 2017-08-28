package com.crv.ole.login.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by yanghongjun on 17/7/17.
 */

public class UserInfoResultBean extends BaseResponseData implements Serializable{

    private UserInfo RETURN_DATA;

    public UserInfo getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(UserInfo RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }


    public class UserInfo {
        private String birthday;
        private String sex;
        private String nickname;
        private String hotty;
        private String userimage;
        private String email;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return this.sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHotty() {
            return hotty;
        }

        public void setHotty(String hotty) {
            this.hotty = hotty;
        }

        public String getUserimage() {
            return userimage;
        }

        public void setUserimage(String userimage) {
            this.userimage = userimage;
        }

    }


}
