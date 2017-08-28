package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/7/24 16:16.
 */

public class CollectionResultData extends BaseResponseData implements Serializable {
    private ResultData RETURN_DATA;

    public ResultData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(ResultData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class ResultData implements Serializable {
        private int likes;
        private int collections;

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getCollections() {
            return collections;
        }

        public void setCollections(int collections) {
            this.collections = collections;
        }
    }
}
