package com.crv.ole.home.model;

/**
 * Created by wj_wsf on 2017/7/6 17:46.
 */

public class ConfigData {
    private APIConfig apiConfig;
    private SMSConfig smsConfig;

    public APIConfig getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(APIConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public SMSConfig getSmsConfig() {
        return smsConfig;
    }

    public void setSmsConfig(SMSConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    public class APIConfig {
        private String apiPlatformUrl;
        private String appToken;
        private String sign;

        public String getApiPlatformUrl() {
            return apiPlatformUrl;
        }

        public void setApiPlatformUrl(String apiPlatformUrl) {
            this.apiPlatformUrl = apiPlatformUrl;
        }

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public class SMSConfig {
        private String maxSendSmsInDay;
        private String encryptAppKey;

        public String getMaxSendSmsInDay() {
            return maxSendSmsInDay;
        }

        public void setMaxSendSmsInDay(String maxSendSmsInDay) {
            this.maxSendSmsInDay = maxSendSmsInDay;
        }

        public String getEncryptAppKey() {
            return encryptAppKey;
        }

        public void setEncryptAppKey(String encryptAppKey) {
            this.encryptAppKey = encryptAppKey;
        }
    }
}
