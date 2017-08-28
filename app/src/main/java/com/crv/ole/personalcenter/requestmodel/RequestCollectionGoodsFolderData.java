package com.crv.ole.personalcenter.requestmodel;

import java.io.Serializable;

/**
 * 作用描述：请求商品收藏编辑参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/19 14:28.
 */

public class RequestCollectionGoodsFolderData implements Serializable {
    private String favorType, file, favoriteClassName;

    public RequestCollectionGoodsFolderData() {

    }

    public String getFavorType() {
        return favorType;
    }

    public void setFavorType(String favorType) {
        this.favorType = favorType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFavoriteClassName() {
        return favoriteClassName;
    }

    public void setFavoriteClassName(String favoriteClassName) {
        this.favoriteClassName = favoriteClassName;
    }
}
