package com.crv.ole.trial.callback;

import com.crv.ole.shopping.model.TrialReportInfoData;

/**
 * Created by zhangbo on 2017/8/17.
 */

public interface OnReportItemClickListener {

    void OnReportZanItemClick(TrialReportInfoData.RETURNDATABean.ListBean data,int position);
}
