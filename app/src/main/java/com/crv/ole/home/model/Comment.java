package com.crv.ole.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 评论
 * Created by Administrator on 2017/1/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Parcelable
{
    private String grade;
    private int age;
    private int serviceId;
    private String nickName;
    private String content;
    private String image;
    private String categoryName;
    private String createDate;
    private String title;

    public String getImage()
    {
        return image;
    }


    public void setImage(String image)
    {
        this.image = image;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }


    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    public Comment()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.grade);
        dest.writeInt(this.age);
        dest.writeInt(this.serviceId);
        dest.writeString(this.nickName);
        dest.writeString(this.content);
        dest.writeString(this.image);
        dest.writeString(this.categoryName);
        dest.writeString(this.createDate);
        dest.writeString(this.title);
    }

    protected Comment(Parcel in)
    {
        this.grade = in.readString();
        this.age = in.readInt();
        this.serviceId = in.readInt();
        this.nickName = in.readString();
        this.content = in.readString();
        this.image = in.readString();
        this.categoryName = in.readString();
        this.createDate = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>()
    {
        @Override
        public Comment createFromParcel(Parcel source)
        {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size)
        {
            return new Comment[size];
        }
    };
}
