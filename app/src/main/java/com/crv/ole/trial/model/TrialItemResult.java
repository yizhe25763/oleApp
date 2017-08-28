package com.crv.ole.trial.model;

import java.io.Serializable;
import java.util.List;

/**
 * 试用记录返回结果
 * Created by zhangbo on 2017/8/17.
 */

public class TrialItemResult implements Serializable {

    private List<TrialItemData> list;
    private int allCount;//总数
    private int NumOfPage;//页数

    public List<TrialItemData> getList() {
        return list;
    }

    public void setList(List<TrialItemData> list) {
        this.list = list;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getNumOfPage() {
        return NumOfPage;
    }

    public void setNumOfPage(int numOfPage) {
        NumOfPage = numOfPage;
    }
}
