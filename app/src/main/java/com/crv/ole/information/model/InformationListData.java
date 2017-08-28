package com.crv.ole.information.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：咨询列表实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/17 16:51.
 */

public class InformationListData extends BaseResponseData implements Serializable {
    //接口响应具体数据
    private List<InformationData> RETURN_DATA;

    public List<InformationData> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<InformationData> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class InformationData implements Serializable {
        private int url;
        private String name;

        public InformationData(int url, String name) {
            this.url = url;
            this.name = name;
        }

        public int getUrl() {
            return url;
        }

        public void setUrl(int url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
