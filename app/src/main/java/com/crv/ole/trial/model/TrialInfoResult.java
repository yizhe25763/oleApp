package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 试用专题返回结果
 * Created by zhangbo on 2017/8/12.
 */

public class TrialInfoResult extends BaseResponseData implements Serializable {

    private TrialInfoData RETURN_DATA;

    public TrialInfoData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(TrialInfoData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
