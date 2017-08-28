package com.crv.ole.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanghongjun on 17/7/20.
 */

public class ImageListData implements Serializable {


    private String code;
    private String msg;
    private List<Data> data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


    public class Data {

        private String createTime;
        private String dateTime;
        private int width;
        private String columnId;
        private String existWatermark;
        private String url;
        private int pos;
        private int size;
        private String id;
        private String fileId;
        private String thumbnail_url;
        private int height;
        private String thumbnail;
        private String fileName;
        private String md5;
        private String parameters;
        private double uploadTime;
        private String fieldName;
        private String fullpath;
        private String showImageUrl;
        private String key;
        private String metaData;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }

        public String getColumnId() {
            return columnId;
        }

        public void setExistWatermark(String existWatermark) {
            this.existWatermark = existWatermark;
        }

        public String getExistWatermark() {
            return existWatermark;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getPos() {
            return pos;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getMd5() {
            return md5;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        public String getParameters() {
            return parameters;
        }

        public double getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(double uploadTime) {
            this.uploadTime = uploadTime;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFullpath(String fullpath) {
            this.fullpath = fullpath;
        }

        public String getFullpath() {
            return fullpath;
        }

        public void setShowImageUrl(String showImageUrl) {
            this.showImageUrl = showImageUrl;
        }

        public String getShowImageUrl() {
            return showImageUrl;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setMetaData(String metaData) {
            this.metaData = metaData;
        }

        public String getMetaData() {
            return metaData;
        }

    }
}
