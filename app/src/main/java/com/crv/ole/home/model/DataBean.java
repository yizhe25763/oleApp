package com.crv.ole.home.model;

public class DataBean {

    public String name;
    public String unitName;
    public String imageUrl;
    public String parp;
    public String id;
    private String linkTo;
    private String openInNewPage;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getParp() {
        return parp;
    }

    public void setParp(String parp) {
        this.parp = parp;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "name='" + name + '\'' +
                ", unitName='" + unitName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", parp='" + parp + '\'' +
                ", id='" + id + '\'' +
                ", openInNewPage='" + openInNewPage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public String getOpenInNewPage() {
        return openInNewPage;
    }

    public void setOpenInNewPage(String openInNewPage) {
        this.openInNewPage = openInNewPage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}