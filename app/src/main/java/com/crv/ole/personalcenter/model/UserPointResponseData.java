package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by lihongshi on 2017/7/26.
 * 用户总积分
 */

public class UserPointResponseData extends BaseResponseData {

    private UserPointBean RETURN_DATA;

    public UserPointBean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(UserPointBean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

}
