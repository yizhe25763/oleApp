package com.crv.ole.shopping.model;

import java.io.Serializable;

/**
 * @Title: ImageItem.java
 * @Package com.crc.cre.crv.ewj.bean
 * @Description: 图片对象
 * Copyright:ewj.com All Rights Reserved
 * @author Rocky
 * @version V1.0
 * Create Date: 2015-3-7 上午11:33:09
 */
public class PhotoInfo implements Serializable
{
	private static final long serialVersionUID = -7188270558443739436L;
	public String imageId;
	public String thumbnailPath;
	public String sourcePath;
	public boolean isSelected = false;
	public boolean isNetResource = true;//是否为网络资源图片

	public PhotoInfo(){

	}

	public PhotoInfo(String imageId, String thumbnailPath, String sourcePath, boolean isSelected, boolean isNetResource) {
		this.imageId = imageId;
		this.thumbnailPath = thumbnailPath;
		this.sourcePath = sourcePath;
		this.isSelected = isSelected;
		this.isNetResource = isNetResource;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public boolean isNetResource() {
		return isNetResource;
	}

	public void setNetResource(boolean netResource) {
		isNetResource = netResource;
	}
}
