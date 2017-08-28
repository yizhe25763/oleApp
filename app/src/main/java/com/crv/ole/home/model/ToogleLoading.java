package com.crv.ole.home.model;

/**
 * Created by yanghongjun on 17/8/17.
 */

public class ToogleLoading {
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String visible;

    @Override
    public String toString() {
        return "ToogleLoading{" +
                "visible='" + visible + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    String content;




}
