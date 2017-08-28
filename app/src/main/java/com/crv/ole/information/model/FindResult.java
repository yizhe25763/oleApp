package com.crv.ole.information.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanhaoyi on 2017/7/21.
 */

public class FindResult implements Serializable{
    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : success
     * RETURN_STAMP : 2017-08-26 11:49:06
     * RETURN_DATA : {"columnList":[{"id":"c_1830000","description":"","name":"杂志","images":[{"base64":"","type":"","url":""}],"childColumn":[{"id":"c_1830003","description":"","name":"生活不凡","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/8/26/99503308007020301899142.png"}]},{"id":"c_1830004","description":"","name":"生活家","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/2/16/85400102489109679419143.png"}]},{"id":"c_1830006","description":"","name":"OLE'专栏","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/2/16/85400090839445794527641.png"}]}]},{"id":"c_1830001","description":"","name":"专题","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/2/16/85400084580855250834660.png"}],"childColumn":[]}]}
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
        private List<ColumnListBean> columnList;

        public List<ColumnListBean> getColumnList() {
            return columnList;
        }

        public void setColumnList(List<ColumnListBean> columnList) {
            this.columnList = columnList;
        }

        public static class ColumnListBean {
            /**
             * id : c_1830000
             * description :
             * name : 杂志
             * images : [{"base64":"","type":"","url":""}]
             * childColumn : [{"id":"c_1830003","description":"","name":"生活不凡","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/8/26/99503308007020301899142.png"}]},{"id":"c_1830004","description":"","name":"生活家","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/2/16/85400102489109679419143.png"}]},{"id":"c_1830006","description":"","name":"OLE'专栏","images":[{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/2/16/85400090839445794527641.png"}]}]
             */

            private String id;
            private String description;
            private String name;
            private List<ImagesBean> images;
            private List<ChildColumnBean> childColumn;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public List<ChildColumnBean> getChildColumn() {
                return childColumn;
            }

            public void setChildColumn(List<ChildColumnBean> childColumn) {
                this.childColumn = childColumn;
            }

            public static class ImagesBean {
                /**
                 * base64 :
                 * type :
                 * url :
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

            public static class ChildColumnBean {
                /**
                 * id : c_1830003
                 * description :
                 * name : 生活不凡
                 * images : [{"base64":"","type":"HTTP","url":"http://10.0.147.163/img/2017/8/26/99503308007020301899142.png"}]
                 */

                private String id;
                private String description;
                private String name;
                private List<ImagesBeanX> images;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<ImagesBeanX> getImages() {
                    return images;
                }

                public void setImages(List<ImagesBeanX> images) {
                    this.images = images;
                }

                public static class ImagesBeanX {
                    /**
                     * base64 :
                     * type : HTTP
                     * url : http://10.0.147.163/img/2017/8/26/99503308007020301899142.png
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
}
