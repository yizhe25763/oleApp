package com.crv.ole.net;

import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.crv.sdk.utils.Md5Util;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求参数全部在这里封装
 * 调用接口之前，设置完相关参数后，最后调用一次setSign方法，否则可能会出错
 * <p>
 * 如果调用网络请求是使用ServerApi.request()方法的话，可以不调用setSign()方法，request方法中会自己调用
 * 但是需要判断request方法的返回值是否为null，如果setSign()方法中参数不完整，request方法就会返回null
 * Created by wj_wsf on 2017/7/6 16:45.
 */

public class RequestData<T> {
    private RequestAttrs REQUEST_ATTRS;

    private T REQUEST_DATA;

    public RequestAttrs getRequestAttrsInstance() {
        if (REQUEST_ATTRS != null) {
            return REQUEST_ATTRS;
        }
        Log.e("RequestAttrs","REQUEST_ATTRS Token为"+PreferencesUtils.getInstance().getString(OleConstants.KEY.APP_TOKEN_KEY));
        REQUEST_ATTRS = new RequestAttrs();
        return REQUEST_ATTRS;

    }


    public RequestAttrs getREQUEST_ATTRS() {
        return REQUEST_ATTRS;
    }

    public void setREQUEST_ATTRS(RequestAttrs REQUEST_ATTRS) {
        this.REQUEST_ATTRS = REQUEST_ATTRS;
    }

    public T getREQUEST_DATA() {
        return REQUEST_DATA;
    }

    public void setREQUEST_DATA(T REQUEST_DATA) {
        this.REQUEST_DATA = REQUEST_DATA;
    }

    /**
     * 调用接口之前，设置完相关参数后，最后调用一次此方法，否则可能会出错
     * 返回Boolean值，true表示数据完整，false表示可能某个参数缺失
     */
    public boolean setSign(String sign) {
      /*  if (REQUEST_ATTRS == null || TextUtils.isEmpty(REQUEST_ATTRS.getApi_ID())
                || TextUtils.isEmpty(REQUEST_ATTRS.getApp_Token())
                || TextUtils.isEmpty(REQUEST_ATTRS.getSerial_No())
                || TextUtils.isEmpty(REQUEST_ATTRS.getSign_Method())
                || TextUtils.isEmpty(REQUEST_ATTRS.getTime_Stamp())
                || TextUtils.isEmpty(sign))
            return false;
*/

        StringBuilder sb = new StringBuilder();

        long time = System.currentTimeMillis();

        //api_id
        if (!TextUtil.isEmpty(REQUEST_ATTRS.getApi_ID())) {
            sb.append("Api_ID=")
                    .append(REQUEST_ATTRS.getApi_ID())
                    .append("&");
        }

        //Token
        if (!TextUtil.isEmpty(REQUEST_ATTRS.getApp_Token())) {
            sb.append("App_Token=")
                    .append(REQUEST_ATTRS.getApp_Token())
                    .append("&");

        } else if (!TextUtil.isEmpty(OleConstants.appToken)) {
            sb.append("App_Token=")
                    .append(PreferencesUtils.getInstance().getString(OleConstants.KEY.APP_TOKEN_KEY))
                    .append("&");
        }

        //Serial_No
        if (!TextUtil.isEmpty(REQUEST_ATTRS.getSerial_No())) {
            sb.append("Serial_No=")
                    .append(REQUEST_ATTRS.getSerial_No())
                    .append("&");
        } else {
            sb.append("Serial_No=")
                    .append(String.valueOf(time))
                    .append("&");
        }

        //Sign_Method
        if (!TextUtil.isEmpty(REQUEST_ATTRS.getSign_Method())) {
            sb.append("Sign_Method=")
                    .append(REQUEST_ATTRS.getSign_Method())
                    .append("&");
        } else {
            sb.append("Sign_Method=")
                    .append("MD5")
                    .append("&");
        }


        //Time_Stamp
        if (!TextUtil.isEmpty(REQUEST_ATTRS.getTime_Stamp())) {
            sb.append("Time_Stamp=")
                    .append(REQUEST_ATTRS.getTime_Stamp())
                    .append("&");
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            sb.append("Time_Stamp=")
                    .append(sf.format(new Date(time)))
                    .append("&");
        }
        //Sign
        if (!TextUtil.isEmpty(sign)) {
            sb.append("Sign=")
                    .append(sign)
                    .append("&");
        }

        //REQUEST_DATA
        if (REQUEST_DATA != null) {
            sb.append("REQUEST_DATA=")
                    .append(REQUEST_DATA != null ? new Gson().toJson(REQUEST_DATA) : "{}");
        }
        Log.e("签名前原始字符串：" + sb.toString());
        REQUEST_ATTRS.setSign(Md5Util.getMD5Str(sb.toString()));
        return true;
    }


    /**
     * 接口请求基础字段
     * 这个类在实例化的时候会初始化掉大部分字段
     * 只要设置下Api_ID的值，最后调用一次setSign()方法，返回true就可以开始调用接口了
     */
    public class RequestAttrs {
        private String App_Token;
        private String Api_ID;
        private String Time_Stamp;
        private String Sign_Method;
        private String Sign;
        private String Serial_No;

        public RequestAttrs() {
            long time = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Time_Stamp = sf.format(new Date(time));
            Serial_No = String.valueOf(time);
            Sign_Method = "MD5";
            App_Token = PreferencesUtils.getInstance().getString(OleConstants.KEY.APP_TOKEN_KEY);
        }

        public String getApp_Token() {
            return App_Token;
        }

        public void setApp_Token(String app_Token) {
            App_Token = app_Token;
        }

        public String getApi_ID() {
            return Api_ID;
        }

        public void setApi_ID(String api_ID) {
            Api_ID = api_ID;
        }

        public String getTime_Stamp() {
            return Time_Stamp;
        }

        public String getSign_Method() {
            return Sign_Method;
        }

        public String getSign() {
            return Sign;
        }

        public void setSign(String sign) {
            Sign = sign;
        }

        public String getSerial_No() {
            return Serial_No;
        }

        public void setSerial_No(String serial_No) {
            Serial_No = serial_No;
        }

        public void setTime_Stamp(String time_Stamp) {
            Time_Stamp = time_Stamp;
        }

        public void setSign_Method(String sign_Method) {
            Sign_Method = sign_Method;
        }

    }

}
