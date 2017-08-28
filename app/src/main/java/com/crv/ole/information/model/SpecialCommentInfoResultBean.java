package com.crv.ole.information.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fairy on 2017/7/21.
 * 获取文章评论相关
 */

public class SpecialCommentInfoResultBean extends BaseResponseData implements Serializable {
    private SpecialCommentInfoResult RETURN_DATA;

    public SpecialCommentInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(SpecialCommentInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class SpecialCommentInfoResult{
        private List<SpecialCommentListInfo> commentList;
        private List<SpecialArticleListInfo> articleInfo;

        public List<SpecialCommentListInfo> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<SpecialCommentListInfo> commentList) {
            this.commentList = commentList;
        }

        public List<SpecialArticleListInfo> getArticleInfo() {
            return articleInfo;
        }

        public void setArticleInfo(List<SpecialArticleListInfo> articleInfo) {
            this.articleInfo = articleInfo;
        }
    }

    /**
     * 评论信息
     */
    public class SpecialCommentListInfo{
        private String commentId;
        private String createTime;
        private String commentator;
        private String commentData;
        private String[] commentImage;
        private String likes;
        private String isLike;
        private String nickName;
        private String logo;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCommentator() {
            return commentator;
        }

        public void setCommentator(String commentator) {
            this.commentator = commentator;
        }

        public String getCommentData() {
            return commentData;
        }

        public void setCommentData(String commentData) {
            this.commentData = commentData;
        }

        public String[] getCommentImage() {
            return commentImage;
        }

        public void setCommentImage(String[] commentImage) {
            this.commentImage = commentImage;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }
    }

    /**
     * 文章信息
     */
    public class SpecialArticleListInfo{
        private String articleId;
        private String HtmlTitle;
        private String schedule;
        private String content;
        private String author;
        private String authorImageFile;
        private String introduction;
        private String tag;
        private String totalLike;
        private String totalComment;
        private String totalCollection;
        private String isArticleLike;
        private String isCollection;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getHtmlTitle() {
            return HtmlTitle;
        }

        public void setHtmlTitle(String htmlTitle) {
            HtmlTitle = htmlTitle;
        }

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorImageFile() {
            return authorImageFile;
        }

        public void setAuthorImageFile(String authorImageFile) {
            this.authorImageFile = authorImageFile;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTotalLike() {
            return totalLike;
        }

        public void setTotalLike(String totalLike) {
            this.totalLike = totalLike;
        }

        public String getTotalComment() {
            return totalComment;
        }

        public void setTotalComment(String totalComment) {
            this.totalComment = totalComment;
        }

        public String getTotalCollection() {
            return totalCollection;
        }

        public void setTotalCollection(String totalCollection) {
            this.totalCollection = totalCollection;
        }

        public String getIsArticleLike() {
            return isArticleLike;
        }

        public void setIsArticleLike(String isArticleLike) {
            this.isArticleLike = isArticleLike;
        }

        public String getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(String isCollection) {
            this.isCollection = isCollection;
        }
    }
}
