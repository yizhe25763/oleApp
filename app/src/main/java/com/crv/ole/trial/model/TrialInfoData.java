package com.crv.ole.trial.model;

import java.io.Serializable;
import java.util.List;

/**
 * 试用专题信息
 * Created by zhangbo on 2017/8/12.
 */

public class TrialInfoData implements Serializable {
    private ActiveObj activeObj;

    private List<TrialProduct> productList;

    public ActiveObj getActiveObj() {
        return activeObj;
    }

    public void setActiveObj(ActiveObj activeObj) {
        this.activeObj = activeObj;
    }

    public List<TrialProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<TrialProduct> productList) {
        this.productList = productList;
    }
}
