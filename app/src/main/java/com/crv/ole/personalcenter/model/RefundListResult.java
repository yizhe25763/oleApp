package com.crv.ole.personalcenter.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangbo on 2017/8/23.
 */

public class RefundListResult implements Serializable {
    private int pageCount;
    private int totalCount;
    private int curPage;

    private List<RefundListData> recordList;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<RefundListData> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RefundListData> recordList) {
        this.recordList = recordList;
    }
}
