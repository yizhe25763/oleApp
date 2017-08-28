package com.crv.ole.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/8/3.
 */

public class SYBean implements Serializable {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : 操作成功
     * RETURN_STAMP : 2017-08-03 20:24:20
     * RETURN_DATA : [{"oleFloors_i":[{"imgUrl":"http://10.0.147.163/img/2017/7/24/93400064719150795883579.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/24/93400064719150795883579.jpeg","id":"file_3970002","parp":"#零食#","selected":false,"linkTo":"http://10.0.147.163/","description":"","openInNewPage":"_blank","parh":"美好时光"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970004","parp":"#联想#","selected":false,"linkTo":"http://10.0.147.163/","description":"","openInNewPage":"_blank","parh":"非常美好"}]},{"oleFloors_h":"第一期","oleFloors_i":[{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970005","parp":"海苔","selected":false,"linkTo":"http://10.0.147.163","description":"","openInNewPage":"_blank","parh":"美好时光"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970005","parp":"假的","selected":false,"linkTo":"","description":"","openInNewPage":"_blank","parh":"真的吗"}]},{"oleFloors_h":"第二期","oleFloors_i":[{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970005","parp":"是是是","selected":false,"linkTo":"http://10.0.147.163","description":"","openInNewPage":"_blank","parh":"请亲实时"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970005","parp":"#零食#","selected":false,"linkTo":"","description":"","openInNewPage":"_blank","parh":"速度打啊速度"}]}]
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
         * oleFloors_i : [{"imgUrl":"http://10.0.147.163/img/2017/7/24/93400064719150795883579.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/24/93400064719150795883579.jpeg","id":"file_3970002","parp":"#零食#","selected":false,"linkTo":"http://10.0.147.163/","description":"","openInNewPage":"_blank","parh":"美好时光"},{"imgUrl":"http://10.0.147.163/img/2017/7/21/93000091566664042358564.jpeg","fileId":"global1_localgroup1_filesystemserver1@/2017/7/21/93000091566664042358564.jpeg","id":"file_3970004","parp":"#联想#","selected":false,"linkTo":"http://10.0.147.163/","description":"","openInNewPage":"_blank","parh":"非常美好"}]
         * oleFloors_h : 第一期
         */

        private String oleFloors_h;
        private List<OleFloorsIBean> oleFloors_i;

        public String getOleFloors_h() {
            return oleFloors_h;
        }

        public void setOleFloors_h(String oleFloors_h) {
            this.oleFloors_h = oleFloors_h;
        }

        public List<OleFloorsIBean> getOleFloors_i() {
            return oleFloors_i;
        }

        public void setOleFloors_i(List<OleFloorsIBean> oleFloors_i) {
            this.oleFloors_i = oleFloors_i;
        }

        public static class OleFloorsIBean {
            /**
             * imgUrl : http://10.0.147.163/img/2017/7/24/93400064719150795883579.jpeg
             * fileId : global1_localgroup1_filesystemserver1@/2017/7/24/93400064719150795883579.jpeg
             * id : file_3970002
             * parp : #零食#
             * selected : false
             * linkTo : http://10.0.147.163/
             * description :
             * openInNewPage : _blank
             * parh : 美好时光
             */

            private String imgUrl;
            private String fileId;
            private String id;
            private String parp;
            private boolean selected;
            private String linkTo;
            private String description;
            private String openInNewPage;
            private String parh;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getFileId() {
                return fileId;
            }

            public void setFileId(String fileId) {
                this.fileId = fileId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParp() {
                return parp;
            }

            public void setParp(String parp) {
                this.parp = parp;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public String getLinkTo() {
                return linkTo;
            }

            public void setLinkTo(String linkTo) {
                this.linkTo = linkTo;
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

            public String getParh() {
                return parh;
            }

            public void setParh(String parh) {
                this.parh = parh;
            }
        }
    }
}
