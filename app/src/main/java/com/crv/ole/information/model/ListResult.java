package com.crv.ole.information.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/7/21.
 */

public class ListResult implements Serializable {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : success
     * RETURN_STAMP : 2017-08-18 14:45:08
     * RETURN_DATA : {"total":2,"information":[{"createTime":"2017-07-27 15:27:05","likeCount":5,"whetherFavorite":false,"columnId":"c_1830001","descriptions":"关键字","id":"a_360000","author":"作者","title":"专题测试2","browseCount":202,"coverImg":"http://10.0.147.163/img/2015/6/30/2370004.jpg","source":"","favoriteCount":5,"whetherLike":false},{"createTime":"2017-07-21 14:33:04","likeCount":7,"whetherFavorite":false,"columnId":"c_1830001","descriptions":"","id":"a_350001","author":"佚名","title":"专题测试1","browseCount":213,"coverImg":"http://10.0.147.163/img/2017/7/24/93400498454088723243221.jpeg","source":"编译","favoriteCount":6,"whetherLike":false}]}
     */

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
         * total : 2
         * information : [{"createTime":"2017-07-27 15:27:05","likeCount":5,"whetherFavorite":false,"columnId":"c_1830001","descriptions":"关键字","id":"a_360000","author":"作者","title":"专题测试2","browseCount":202,"coverImg":"http://10.0.147.163/img/2015/6/30/2370004.jpg","source":"","favoriteCount":5,"whetherLike":false},{"createTime":"2017-07-21 14:33:04","likeCount":7,"whetherFavorite":false,"columnId":"c_1830001","descriptions":"","id":"a_350001","author":"佚名","title":"专题测试1","browseCount":213,"coverImg":"http://10.0.147.163/img/2017/7/24/93400498454088723243221.jpeg","source":"编译","favoriteCount":6,"whetherLike":false}]
         */

        private int total;
        private List<InformationBean> information;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<InformationBean> getInformation() {
            return information;
        }

        public void setInformation(List<InformationBean> information) {
            this.information = information;
        }

        public static class InformationBean {
            /**
             * createTime : 2017-07-27 15:27:05
             * likeCount : 5
             * whetherFavorite : false
             * columnId : c_1830001
             * descriptions : 关键字
             * id : a_360000
             * author : 作者
             * title : 专题测试2
             * browseCount : 202
             * coverImg : http://10.0.147.163/img/2015/6/30/2370004.jpg
             * source :
             * favoriteCount : 5
             * whetherLike : false
             */

            private String createTime;
            private int likeCount;
            private boolean whetherFavorite;
            private String columnId;
            private String descriptions;
            private String id;
            private String author;
            private String title;
            private int browseCount;
            private String coverImg;
            private String source;
            private int favoriteCount;
            private boolean whetherLike;
            private String iconUrl;

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

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

            public String getColumnId() {
                return columnId;
            }

            public void setColumnId(String columnId) {
                this.columnId = columnId;
            }

            public String getDescriptions() {
                return descriptions;
            }

            public void setDescriptions(String descriptions) {
                this.descriptions = descriptions;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getBrowseCount() {
                return browseCount;
            }

            public void setBrowseCount(int browseCount) {
                this.browseCount = browseCount;
            }

            public String getCoverImg() {
                return coverImg;
            }

            public void setCoverImg(String coverImg) {
                this.coverImg = coverImg;
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
        }
    }
}
