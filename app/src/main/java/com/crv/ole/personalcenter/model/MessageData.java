package com.crv.ole.personalcenter.model;

import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/14 14:57.
 */

public class MessageData {
    private int pageCount, totalCount;
    private List<MessageItemData> list;

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

    public List<MessageItemData> getList() {
        return list;
    }

    public void setList(List<MessageItemData> list) {
        this.list = list;
    }
}
