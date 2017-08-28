package com.crv.ole.information.model;

import java.util.ArrayList;

/**
 * Created by yanghongjun on 17/8/23.
 */

public class ArticleImageBean {

    ArrayList<String> imgs;
    int  index;

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "ArticleImageBean{" +
                "imgs=" + imgs +
                ", index='" + index + '\'' +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
