package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 收藏中文件夹列表实体类
 */
public class CollectionGoodsFolderListData extends BaseResponseData implements Serializable {
    //接口响应具体数据
    private CollectionGoodsFolderData RETURN_DATA;

    public CollectionGoodsFolderData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(CollectionGoodsFolderData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class CollectionGoodsFolderData implements Serializable {
        private int page, pageSize, pageTotal;
        private List<GoodsFolderData> favorTypeList;

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

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public List<GoodsFolderData> getFavorTypeList() {
            return favorTypeList;
        }

        public void setFavorTypeList(List<GoodsFolderData> favorTypeList) {
            this.favorTypeList = favorTypeList;
        }
    }

    public static class GoodsFolderData implements Serializable {
        private String userId, id, createTime, favoriteClassName, imgUrl;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFavoriteClassName() {
            return favoriteClassName;
        }

        public void setFavoriteClassName(String favoriteClassName) {
            this.favoriteClassName = favoriteClassName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
