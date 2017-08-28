package com.crv.ole.information.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/7/21.
 */

public class InfoBean implements Serializable {

    private String RETURN_CODE;
    private String RETURN_DESC;
    private String RETURN_STAMP;
    private RETURNDATABean RETURN_DATA;

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

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         */

        private InfoDetailBean infoDetail;

        public InfoDetailBean getInfoDetail() {
            return infoDetail;
        }

        public void setInfoDetail(InfoDetailBean infoDetail) {
            this.infoDetail = infoDetail;
        }

        public static class InfoDetailBean {

            private String createTime;
            private int likeCount;
            private boolean whetherFavorite;
            private int commentCount;
            private String id;
            private String content;
            private String author;
            private String title;
            private String coverImg;
            private int browseCount;
            private String source;
            private int favoriteCount;
            private boolean whetherLike;
            private List<ImgContentBean> imgContent;
            private List<?> productInfoList;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public boolean isWhetherFavorite() {
                return whetherFavorite;
            }

            public void setWhetherFavorite(boolean whetherFavorite) {
                this.whetherFavorite = whetherFavorite;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCoverImg() {
                return coverImg;
            }

            public void setCoverImg(String coverImg) {
                this.coverImg = coverImg;
            }

            public int getBrowseCount() {
                return browseCount;
            }

            public void setBrowseCount(int browseCount) {
                this.browseCount = browseCount;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public int getFavoriteCount() {
                return favoriteCount;
            }

            public void setFavoriteCount(int favoriteCount) {
                this.favoriteCount = favoriteCount;
            }

            public boolean isWhetherLike() {
                return whetherLike;
            }

            public void setWhetherLike(boolean whetherLike) {
                this.whetherLike = whetherLike;
            }

            public List<ImgContentBean> getImgContent() {
                return imgContent;
            }

            public void setImgContent(List<ImgContentBean> imgContent) {
                this.imgContent = imgContent;
            }

            public List<?> getProductInfoList() {
                return productInfoList;
            }

            public void setProductInfoList(List<?> productInfoList) {
                this.productInfoList = productInfoList;
            }

            public static class ImgContentBean {
                /**
                 * base64 :
                 * type : HTTP
                 * url : http://10.0.147.163/img/2017/8/16/97400033915503950960175.png
                 */

                private String base64;
                private String type;
                private String url;

                public String getBase64() {
                    return base64;
                }

                public void setBase64(String base64) {
                    this.base64 = base64;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
