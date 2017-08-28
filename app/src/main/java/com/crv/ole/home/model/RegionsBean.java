package com.crv.ole.home.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by lihongshi on 2017/7/20.
 */

@Table(name = "t_region")
public class RegionsBean {
    @Column(name = "t_id", isId = true, autoGen = true)
    public int t_id;

    @Column(name = "ID")
    private String id;

    @Column(name = "parentId")
    private String parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "pos")
    private String pos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }


    @Override
    public String toString() {
        return "RegionsBean{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", pos='" + pos + '\'' +
                '}';
    }
}

