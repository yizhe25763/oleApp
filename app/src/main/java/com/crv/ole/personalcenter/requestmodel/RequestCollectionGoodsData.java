package com.crv.ole.personalcenter.requestmodel;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：请求商品收藏列表参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/19 14:28.
 */

public class RequestCollectionGoodsData implements Serializable {
    private String favorType;
    private String objId;//传递数组,分割
    private int page, pageSize;

    public RequestCollectionGoodsData() {

    }

    public RequestCollectionGoodsData(String name, int start, int limit) {
        this.favorType = name;
        this.page = start;
        this.pageSize = limit;
    }

    public String getFavorType() {
        return favorType;
    }

    public void setFavorType(String favorType) {
        this.favorType = favorType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
