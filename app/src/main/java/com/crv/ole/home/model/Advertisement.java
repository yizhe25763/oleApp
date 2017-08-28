package com.crv.ole.home.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 广告
 * Created by Administrator on 2016-06-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Advertisement
{
    @Override
    public String toString()
    {
        return "Advernt{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    /**
     * id : 1
     * image : /upload/1.jpg
     * title : 首页
     * link : http://www.baidu.com
     */

    private int id;
    private String image;
    private int images;
    private String title;
    private String link;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getImages()
    {
        return images;
    }

    public void setImages(int images)
    {
        this.images = images;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
