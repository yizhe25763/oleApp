package com.crv.ole.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2016-05-05.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageBean implements Parcelable
{

    /**
     * id : 28
     * sort : 0
     * imageUrl : /upload/sc/images/20170104165429gcmgV9iW.png
     * serviceId : 39
     */

    private int id;
    private int sort;
    private String imageUrl;
    private int serviceId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeInt(this.sort);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.serviceId);
    }

    public ImageBean()
    {
    }

    private ImageBean(Parcel in)
    {
        this.id = in.readInt();
        this.sort = in.readInt();
        this.imageUrl = in.readString();
        this.serviceId = in.readInt();
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>()
    {
        public ImageBean createFromParcel(Parcel source)
        {
            return new ImageBean(source);
        }

        public ImageBean[] newArray(int size)
        {
            return new ImageBean[size];
        }
    };
}
