package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 提交用户信息返回
 * Created by zhangbo on 2017/8/8.
 */

public class SubmitUserInfoResponse extends BaseResponseData implements Serializable {

    private String RETURN_DATA;

    public String getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(String RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
