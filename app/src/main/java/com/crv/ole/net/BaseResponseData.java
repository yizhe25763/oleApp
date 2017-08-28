package com.crv.ole.net;

import java.io.Serializable;

/**
 * 接口请求数据响应基类
 * Created by wj_wsf on 2017/6/29 14:48.
 */

public class BaseResponseData implements Serializable {
    //接口响应编码
    private String RETURN_CODE;
    //接口响应说明
    private String RETURN_DESC;
    //接口响应时间戳
    private String RETURN_STAMP;

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

}
