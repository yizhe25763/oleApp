package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/26 15:31.
 */

public class TrialReportGoodsListData extends BaseResponseData implements Serializable {
    private List<TrialReportGoodsItemData> RETURN_DATA;

    public List<TrialReportGoodsItemData> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<TrialReportGoodsItemData> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
