package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 试用记录返回结果
 * Created by zhangbo on 2017/8/22.
 */

public class TrialItemResponse extends BaseResponseData implements Serializable {
    private TrialItemResult RETURN_DATA;

    public TrialItemResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(TrialItemResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
