package com.crv.ole.pay.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3 10:58.
 */

public class PayMethodListData extends BaseResponseData implements Serializable {
    private PayMethodData RETURN_DATA;

    public PayMethodData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(PayMethodData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

}
