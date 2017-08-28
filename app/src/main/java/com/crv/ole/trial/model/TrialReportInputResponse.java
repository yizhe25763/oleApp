package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by zhangbo on 2017/8/25.
 */

public class TrialReportInputResponse extends BaseResponseData implements Serializable {
    private TrialReportInputDate RETURN_DATA;

    public TrialReportInputDate getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(TrialReportInputDate RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
