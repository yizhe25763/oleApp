package com.crv.ole.information.model;

import java.util.List;

/**
 * Created by fanhaoyi on 2017/7/22.
 */

public class CommentListResult {


    /**
     * RETURN_DATA : {"commentList":[{"commentData":"iiiiiiiiiieeeeee","commentId":"acl_management2_80000","commentImage":[""],"commentator":"liukuo","createTime":"2017-07-19 15:00:30","isLike":"0","likes":"0"},{"commentData":"iiiiiiiiiieeeeee","commentId":"acl_management2_80001","commentImage":[""],"commentator":"qiqiqiq","createTime":"2017-07-19 15:00:42","isLike":"0","likes":"0"}]}
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-07-22 12:50:13
     */

    private RETURNDATABean RETURN_DATA;
    private String RETURN_CODE;
    private String RETURN_DESC;
    private String RETURN_STAMP;

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

    public static class RETURNDATABean {
        private List<CommentListBean> commentList;

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * commentData : iiiiiiiiiieeeeee
             * commentId : acl_management2_80000
             * commentImage : [""]
             * commentator : liukuo
             * createTime : 2017-07-19 15:00:30
             * isLike : 0
             * likes : 0
             */

            private String commentData;
            private String commentId;
            private String commentator;
            private String createTime;
            private String isLike;
            private String likes;
            private List<String> commentImage;

            public String getCommentData() {
                return commentData;
            }

            public void setCommentData(String commentData) {
                this.commentData = commentData;
            }

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getCommentator() {
                return commentator;
            }

            public void setCommentator(String commentator) {
                this.commentator = commentator;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getIsLike() {
                return isLike;
            }

            public void setIsLike(String isLike) {
                this.isLike = isLike;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public List<String> getCommentImage() {
                return commentImage;
            }

            public void setCommentImage(List<String> commentImage) {
                this.commentImage = commentImage;
            }
        }
    }
}

