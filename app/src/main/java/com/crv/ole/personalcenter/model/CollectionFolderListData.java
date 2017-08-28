package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 收藏中文件夹列表实体类
 */
public class CollectionFolderListData extends BaseResponseData implements Serializable {
    //接口响应具体数据
    private CollectionFolderData RETURN_DATA;

    public CollectionFolderData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(CollectionFolderData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class CollectionFolderData implements Serializable {
        private List<FolderData> fileList, collectionList;
        private int total;
        private String limit, start;

        public List<FolderData> getFileList() {
            return fileList;
        }

        public void setFileList(List<FolderData> fileList) {
            this.fileList = fileList;
        }

        public List<FolderData> getCollectionList() {
            return collectionList;
        }

        public void setCollectionList(List<FolderData> collectionList) {
            this.collectionList = collectionList;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }
    }

    public static class FolderData implements Serializable {
        private String articleFileName, articleFileImage, title, bannerImageFileId, tag, articleType, articleId;

        public String getArticleFileName() {
            return articleFileName;
        }

        public void setArticleFileName(String articleFileName) {
            this.articleFileName = articleFileName;
        }

        public String getArticleFileImage() {
            return articleFileImage;
        }

        public void setArticleFileImage(String articleFileImage) {
            this.articleFileImage = articleFileImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBannerImageFileId() {
            return bannerImageFileId;
        }

        public void setBannerImageFileId(String bannerImageFileId) {
            this.bannerImageFileId = bannerImageFileId;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getArticleType() {
            return articleType;
        }

        public void setArticleType(String articleType) {
            this.articleType = articleType;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }
    }
}
