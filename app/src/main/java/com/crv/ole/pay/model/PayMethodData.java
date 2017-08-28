package com.crv.ole.pay.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 16:58.
 */

public class PayMethodData implements Serializable {
    private List<String> codAliasCodes;
    private List<RealPayData> realPayRecs;

    public List<String> getCodAliasCodes() {
        return codAliasCodes;
    }

    public void setCodAliasCodes(List<String> codAliasCodes) {
        this.codAliasCodes = codAliasCodes;
    }

    public List<RealPayData> getRealPayRecs() {
        return realPayRecs;
    }

    public void setRealPayRecs(List<RealPayData> realPayRecs) {
        this.realPayRecs = realPayRecs;
    }
}
