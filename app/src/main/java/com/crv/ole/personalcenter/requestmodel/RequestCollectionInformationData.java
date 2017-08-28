package com.crv.ole.personalcenter.requestmodel;

import java.io.Serializable;

/**
 * 作用描述：请求资讯收藏列表参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/19 14:28.
 */

public class RequestCollectionInformationData implements Serializable {
    private String articleFileName;
    private int start, limit;

    public RequestCollectionInformationData() {

    }

    public RequestCollectionInformationData(String name, int start, int limit) {
        this.articleFileName = name;
        this.start = start;
        this.limit = limit;
    }

    public String getArticleFileName() {
        return articleFileName;
    }

    public void setArticleFileName(String articleFileName) {
        this.articleFileName = articleFileName;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
