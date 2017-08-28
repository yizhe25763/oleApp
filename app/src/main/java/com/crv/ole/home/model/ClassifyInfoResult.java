package com.crv.ole.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fairy on 2017/8/3.
 * 超市 - 搜索及分类页相关
 */

public class ClassifyInfoResult implements Serializable {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-08-03 17:57:42
     * RETURN_DATA : [{"sort_t":"青青草原","sort_list":[{"content":"以爱之名","recordId":"","linkTo":"http://10.0.147.163","openInNewPage":"_blank"},{"content":"心想事成","recordId":"","linkTo":"http://10.0.147.163","openInNewPage":"_blank"}],"sort_i":{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","description":"","openInNewPage":"_blank","imgLinkTo":"http://10.0.147.163"}},{"sort_t":"我也不知道","sort_list":[{"recordId":"","content":"真的假的","linkTo":"http://10.0.147.163","openInNewPage":"_blank"},{"recordId":"","content":"随便写写","linkTo":"http://10.0.147.163","openInNewPage":"_blank"}],"sort_i":{"imgUrl":"http://10.0.147.163/img/2017/7/26/93802812131547570615541.jpeg","description":"","openInNewPage":"_blank","imgLinkTo":"http://10.0.147.163"}}]
     */

    private String RETURN_CODE;
    private String RETURN_DESC;
    private String RETURN_STAMP;
    private List<RETURNDATABean> RETURN_DATA;

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

    public List<RETURNDATABean> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<RETURNDATABean> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         * sort_t : 青青草原
         * sort_list : [{"content":"以爱之名","recordId":"","linkTo":"http://10.0.147.163","openInNewPage":"_blank"},{"content":"心想事成","recordId":"","linkTo":"http://10.0.147.163","openInNewPage":"_blank"}]
         * sort_i : {"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","description":"","openInNewPage":"_blank","imgLinkTo":"http://10.0.147.163"}
         */

        private String sort_t;
        private SortIBean sort_i;
        private List<SortListBean> sort_list;

        public String getSort_t() {
            return sort_t;
        }

        public void setSort_t(String sort_t) {
            this.sort_t = sort_t;
        }

        public SortIBean getSort_i() {
            return sort_i;
        }

        public void setSort_i(SortIBean sort_i) {
            this.sort_i = sort_i;
        }

        public List<SortListBean> getSort_list() {
            return sort_list;
        }

        public void setSort_list(List<SortListBean> sort_list) {
            this.sort_list = sort_list;
        }

        public static class SortIBean {
            /**
             * imgUrl : http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg
             * description :
             * openInNewPage : _blank
             * imgLinkTo : http://10.0.147.163
             */

            private String imgUrl;
            private String description;
            private String openInNewPage;
            private String imgLinkTo;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getOpenInNewPage() {
                return openInNewPage;
            }

            public void setOpenInNewPage(String openInNewPage) {
                this.openInNewPage = openInNewPage;
            }

            public String getImgLinkTo() {
                return imgLinkTo;
            }

            public void setImgLinkTo(String imgLinkTo) {
                this.imgLinkTo = imgLinkTo;
            }
        }

        public static class SortListBean implements Serializable{
            /**
             * content : 以爱之名
             * recordId :
             * linkTo : http://10.0.147.163
             * openInNewPage : _blank
             */

            private String content;
            private String recordId;
            private String linkTo;
            private String openInNewPage;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRecordId() {
                return recordId;
            }

            public void setRecordId(String recordId) {
                this.recordId = recordId;
            }

            public String getLinkTo() {
                return linkTo;
            }

            public void setLinkTo(String linkTo) {
                this.linkTo = linkTo;
            }

            public String getOpenInNewPage() {
                return openInNewPage;
            }

            public void setOpenInNewPage(String openInNewPage) {
                this.openInNewPage = openInNewPage;
            }
        }
    }
}
