package com.crv.ole.personalcenter.requestmodel;

import java.io.Serializable;

/**
 * 作用描述：请求资讯收藏夹编辑参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/19 14:28.
 */

public class RequestCollectionInformationFolderData implements Serializable {
    private String articleFileName, articleId, newFileName, imageName;
    private String action;

    public RequestCollectionInformationFolderData() {

    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getArticleFileName() {
        return articleFileName;
    }

    public void setArticleFileName(String articleFileName) {
        this.articleFileName = articleFileName;
    }
}
