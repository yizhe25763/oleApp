package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 试用商品详情返回结果
 * Created by zhangbo on 2017/8/18.
 */

public class TrialProductDetilResult extends BaseResponseData implements Serializable {
    private TrialProductDetilData RETURN_DATA;

    public TrialProductDetilData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(TrialProductDetilData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
