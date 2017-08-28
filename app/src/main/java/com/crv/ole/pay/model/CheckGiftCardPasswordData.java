package com.crv.ole.pay.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3 10:58.
 */

public class CheckGiftCardPasswordData extends BaseResponseData implements Serializable {
    private CheckGiftCardData RETURN_DATA;

    public CheckGiftCardData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(CheckGiftCardData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

}
