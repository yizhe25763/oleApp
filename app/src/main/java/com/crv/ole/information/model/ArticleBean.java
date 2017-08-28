package com.crv.ole.information.model;

import java.util.HashMap;

/**
 * Created by yanghongjun on 17/8/23.
 */

public class ArticleBean {

    public String pageName;
    public HashMap<String, String> params;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }


    @Override
    public String toString() {
        return "ArticleBean{" +
                "pageName='" + pageName + '\'' +
                ", params=" + params +
                '}';
    }
}
