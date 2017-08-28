package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

/**
 * 退款退货单列表
 * Created by zhangbo on 2017/8/23.
 */

public class RefundListResponse extends BaseResponseData {
    private RefundListResult RETURN_DATA;

    public RefundListResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RefundListResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
