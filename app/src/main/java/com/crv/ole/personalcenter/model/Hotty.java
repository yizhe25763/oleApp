package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * Created by yanghongjun on 17/7/8.
 */

public class Hotty implements Serializable{

    private String name;
    private boolean love;
    private String no;

    public boolean isLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }

    public Hotty(String name, boolean love, String no) {
        this.name = name;
        this.love = love;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
