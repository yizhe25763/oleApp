package com.crv.ole.information.requestmodel;

/**
 * Created by fanhaoyi on 2017/7/21.
 */

public class ListBeanRequest {

    private String columnId;
    private int limit;
    private String title;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
}
