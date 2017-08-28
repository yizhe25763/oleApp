package com.crv.ole.shopping.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 搜索历史实体类
 * Created by wj_wsf on 2017/7/5 11:14.
 */
@Table(name = "SearchHistoryData")
public class SearchHistoryData {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "text")
    private String text;
    @Column(name = "datetime")
    private long datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "SearchHistoryData{" +
                "id=" + id +
                ",text=" + text +
                ",datetime=" + datetime +
                "}";
    }
}
