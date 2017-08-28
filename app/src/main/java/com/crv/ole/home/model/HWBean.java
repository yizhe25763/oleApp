package com.crv.ole.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/8/2.
 */

public class HWBean implements Serializable {
    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-08-03 10:35:02
     * RETURN_DATA : [{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564_450X320.jpeg","time":"时间：11月09日-12月30日","index":1,"imgLinkTo":"http://10.0.147.163/","para":"今夜正好","discount":"七折"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/92900245724410401567922_450X320.jpeg","time":"时间：12月09日-1月30日","index":2,"imgLinkTo":"http://10.0.147.163/","para":"美酒当歌","discount":"八折"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564_450X320.jpeg","time":"时间：3月09日-4月30日","index":3,"imgLinkTo":"http://10.0.147.163/","para":"一个一个来","discount":""},{"imgUrl":"http://10.0.147.163/img/2017/7/24/93400064719150795883579_450X320.jpeg","time":"时间：10月09日-12月30日","index":4,"imgLinkTo":"http://10.0.147.163/","para":"期望很大","discount":""}]
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
         * imgUrl : http://10.0.147.163/img/2017/7/21/93000091566664042358564_450X320.jpeg
         * time : 时间：11月09日-12月30日
         * index : 1
         * imgLinkTo : http://10.0.147.163/
         * para : 今夜正好
         * discount : 七折
         */

        private String imgUrl;
        private String time;
        private int index;
        private String imgLinkTo;
        private String para;
        private String discount;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getImgLinkTo() {
            return imgLinkTo;
        }

        public void setImgLinkTo(String imgLinkTo) {
            this.imgLinkTo = imgLinkTo;
        }

        public String getPara() {
            return para;
        }

        public void setPara(String para) {
            this.para = para;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }
}
