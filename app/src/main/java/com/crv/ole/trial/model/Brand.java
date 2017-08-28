package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 品牌
 * Created by zhangbo on 2017/8/18.
 */

public class Brand implements Serializable {
    private String columntype;

    private String hasChildren;

    private String id;

    private String name;

    private String title;

    public String getColumntype() {
        return columntype;
    }

    public void setColumntype(String columntype) {
        this.columntype = columntype;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
