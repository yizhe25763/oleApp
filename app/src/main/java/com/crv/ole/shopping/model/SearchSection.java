package com.crv.ole.shopping.model;

import java.util.List;

/**
 * Created by yanghongjun on 17/7/31.
 */

public class SearchSection {

    private String sectionName;

    private List<SearchHistoryData> searchList;


    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<SearchHistoryData> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<SearchHistoryData> searchList) {
        this.searchList = searchList;
    }
}
