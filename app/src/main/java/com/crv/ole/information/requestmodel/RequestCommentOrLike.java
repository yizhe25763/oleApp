package com.crv.ole.information.requestmodel;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：资讯中评论点赞收藏等功能请求基础参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/21 17:28.
 */

public class RequestCommentOrLike implements Serializable {
    /**
     * isComment:0 添加评价，1 点赞 2 取消点赞，3文章收藏或新建文件夹，4 取消收藏，5 评论点赞，6取消评论点赞，7删除收藏文件夹，8修改文件夹
     * articleId:文章Id
     * commentData:评论内容 点赞时不用发送 评论时必须发送
     * commentImage:评论图片 点赞时不用发送 没有也可以不发送,逗号隔开
     * commentId:评论点赞 取消时才需要
     * articleFileName:收藏文件夹名，收藏操作时要传
     * articleFileImage:收藏文件夹图片，新建文件夹时用
     * newFileName:修改文件夹，文件夹名，articleFileName原文件夹名
     * newFileImage:修改文件夹，文件夹图片，
     */
    private String isComment, commentData, commentImage, commentId, articleFileName,
            articleFileImage, newFileName, newFileImage, articleType;
    private String articleId;

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentData() {
        return commentData;
    }

    public void setCommentData(String commentData) {
        this.commentData = commentData;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

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

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getNewFileImage() {
        return newFileImage;
    }

    public void setNewFileImage(String newFileImage) {
        this.newFileImage = newFileImage;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
}
