package com.crv.ole.home.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wj_wsf on 2017/6/28 15:32.
 */

public class SpecialListData extends BaseResponseData implements Serializable {
    //接口响应具体数据
    private List<SpecialItemModel> RETURN_DATA;

    public List<SpecialItemModel> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<SpecialItemModel> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class SpecialItemModel implements Serializable {
        private int url;
        private String title, desc;
        private int collectionCount, readCount;

        public SpecialItemModel(String title, int url, String desc, int collectionCount, int readCount) {
            this.title = title;
            this.url = url;
            this.desc = desc;
            this.collectionCount = collectionCount;
            this.readCount = readCount;
        }

        public int getUrl() {
            return url;
        }

        public void setUrl(int url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getCollectionCount() {
            return collectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }
    }
}
