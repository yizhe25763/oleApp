package com.crv.ole.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.crv.ole.home.model.Comment;
import com.crv.ole.home.model.ImageBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Administrator on 2016-05-05.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceBean implements Parcelable
{

    /**
     * id : 56
     * categoryId : A03A09A01
     * pubUser : 10
     * title : 被群围
     * introductions : 一只穿云箭，千军万马来相见！
     * serviceMode : 2
     * offlinePrice : 100.0
     * telPrice : 1.0
     * weeks :
     * owerAddress : 龙园大厦
     * remark :
     * status : 1
     * createDate : 2017-01-06 20:45:10
     * createName :
     * updateDate :
     * updateName :
     * longitude : 114.1106
     * latitude : 22.5871
     * reject :
     * turnover : 0
     * categoryName : 滴滴打人
     * distance : 18.27
     * age : 1
     * userName :
     * realName : N
     * nickName : 且听风吟
     * grade : 1
     * sesamePoint : 1
     * sex : 1
     * image : /upload/sc/images/201701061649127qVadCyJ.png
     * gradeNum : 0
     * relation : 0
     */

    private int id;
    private String categoryId;
    private int pubUser;
    private String title;
    private String introductions;
    private String serviceMode;
    private String offlinePrice;
    private String telPrice;
    private String weeks;
    private String orderNo;
    private String owerAddress;
    private String remark;
    private int status;
    private String createDate;
    private String createName;
    private String updateDate;
    private String updateName;
    private double longitude;
    private double latitude;
    private String reject;
    private int turnover;
    private int visitor;
    private String categoryName;
    private String distance;
    private int age;
    private String userName;
    private String realName;
    private String companyAuth;
    private String nickName;
    private int grade;
    private int sesamePoint;
    private int sex;
    private String image;
    private int gradeNum;
    private String gradeTotal;
    private int grade1;
    private int grade2;
    private int grade3;
    private int grade4;
    private int grade5;
    private int relation;
    private int orderId;
    private int lastDay;
    private int accept;
    private int userRelation;
    private int userAccept;
    private int serviceUser;
    private int orderUser;
    private String unit;
    private String categoryImage;
    private String gradeName;
    private String orderDate;

    public int getOrderUser()
    {
        return orderUser;
    }

    public void setOrderUser(int orderUser)
    {
        this.orderUser = orderUser;
    }

    public int getServiceUser()
    {
        return serviceUser;
    }

    public void setServiceUser(int serviceUser)
    {
        this.serviceUser = serviceUser;
    }

    public String getCompanyAuth()
    {
        return companyAuth;
    }

    public void setCompanyAuth(String companyAuth)
    {
        this.companyAuth = companyAuth;
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getGradeName()
    {
        return gradeName;
    }

    public void setGradeName(String gradeName)
    {
        this.gradeName = gradeName;
    }

    public String getCategoryImage()
    {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage)
    {
        this.categoryImage = categoryImage;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    private List<Comment> serviceGradeList;

    public List<Comment> getServiceGradeList()
    {
        return serviceGradeList;
    }

    public void setServiceGradeList(List<Comment> serviceGradeList)
    {
        this.serviceGradeList = serviceGradeList;
    }

    public String getGradeTotal()
    {
        return gradeTotal == null ? "0" : gradeTotal;
    }

    public void setGradeTotal(String gradeTotal)
    {
        this.gradeTotal = gradeTotal;
    }

    public int getGrade1()
    {
        return grade1;
    }

    public void setGrade1(int grade1)
    {
        this.grade1 = grade1;
    }

    public int getGrade2()
    {
        return grade2;
    }

    public void setGrade2(int grade2)
    {
        this.grade2 = grade2;
    }

    public int getGrade3()
    {
        return grade3;
    }

    public void setGrade3(int grade3)
    {
        this.grade3 = grade3;
    }

    public int getGrade4()
    {
        return grade4;
    }

    public void setGrade4(int grade4)
    {
        this.grade4 = grade4;
    }

    public int getGrade5()
    {
        return grade5;
    }

    public void setGrade5(int grade5)
    {
        this.grade5 = grade5;
    }

    public int getLastDay()
    {
        return lastDay;
    }

    public void setLastDay(int lastDay)
    {
        this.lastDay = lastDay;
    }

    private List<ImageBean> serviceImagesList;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getPubUser()
    {
        return pubUser;
    }

    public void setPubUser(int pubUser)
    {
        this.pubUser = pubUser;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIntroductions()
    {
        return introductions;
    }

    public void setIntroductions(String introductions)
    {
        this.introductions = introductions;
    }

    public String getServiceMode()
    {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode)
    {
        this.serviceMode = serviceMode;
    }

    public String getOfflinePrice()
    {
        return offlinePrice == null ? "0" : offlinePrice;
    }

    public void setOfflinePrice(String offlinePrice)
    {
        this.offlinePrice = offlinePrice;
    }

    public String getTelPrice()
    {
        return telPrice == null ? "0" : telPrice;
    }

    public void setTelPrice(String telPrice)
    {
        this.telPrice = telPrice;
    }

    public String getWeeks()
    {
        return weeks;
    }

    public void setWeeks(String weeks)
    {
        this.weeks = weeks;
    }

    public String getOwerAddress()
    {
        return owerAddress;
    }

    public void setOwerAddress(String owerAddress)
    {
        this.owerAddress = owerAddress;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getReject()
    {
        return reject;
    }

    public void setReject(String reject)
    {
        this.reject = reject;
    }

    public int getTurnover()
    {
        return turnover;
    }

    public int getVisitor()
    {
        return visitor;
    }

    public void setVisitor(int visitor)
    {
        this.visitor = visitor;
    }

    public void setTurnover(int turnover)
    {
        this.turnover = turnover;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getDistance()
    {
        return distance == null ? "0" : distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public int getSesamePoint()
    {
        return sesamePoint;
    }

    public void setSesamePoint(int sesamePoint)
    {
        this.sesamePoint = sesamePoint;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getGradeNum()
    {
        return gradeNum;
    }

    public void setGradeNum(int gradeNum)
    {
        this.gradeNum = gradeNum;
    }

    public int getRelation()
    {
        return relation;
    }

    public void setRelation(int relation)
    {
        this.relation = relation;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getAccept()
    {
        return accept;
    }

    public void setAccept(int accept)
    {
        this.accept = accept;
    }

    public List<ImageBean> getServiceImagesList()
    {
        return serviceImagesList;
    }

    public void setServiceImagesList(List<ImageBean> serviceImagesList)
    {
        this.serviceImagesList = serviceImagesList;
    }

    public int getUserRelation()
    {
        return userRelation;
    }

    public void setUserRelation(int userRelation)
    {
        this.userRelation = userRelation;
    }

    public int getUserAccept()
    {
        return userAccept;
    }

    public void setUserAccept(int userAccept)
    {
        this.userAccept = userAccept;
    }

    public String getUnit()
    {
        return unit == null ? "" : unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public ServiceBean()
    {
    }

    public ServiceBean(int orderId)
    {
        this.orderId = orderId;
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
        dest.writeString(this.categoryId);
        dest.writeInt(this.pubUser);
        dest.writeString(this.title);
        dest.writeString(this.introductions);
        dest.writeString(this.serviceMode);
        dest.writeString(this.offlinePrice);
        dest.writeString(this.telPrice);
        dest.writeString(this.weeks);
        dest.writeString(this.orderNo);
        dest.writeString(this.owerAddress);
        dest.writeString(this.remark);
        dest.writeInt(this.status);
        dest.writeString(this.createDate);
        dest.writeString(this.createName);
        dest.writeString(this.updateDate);
        dest.writeString(this.updateName);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.reject);
        dest.writeInt(this.turnover);
        dest.writeString(this.categoryName);
        dest.writeString(this.distance);
        dest.writeInt(this.age);
        dest.writeString(this.userName);
        dest.writeString(this.realName);
        dest.writeString(this.nickName);
        dest.writeInt(this.grade);
        dest.writeInt(this.sesamePoint);
        dest.writeInt(this.sex);
        dest.writeString(this.image);
        dest.writeInt(this.gradeNum);
        dest.writeString(this.gradeTotal);
        dest.writeInt(this.grade1);
        dest.writeInt(this.grade2);
        dest.writeInt(this.grade3);
        dest.writeInt(this.grade4);
        dest.writeInt(this.grade5);
        dest.writeInt(this.relation);
        dest.writeInt(this.orderId);
        dest.writeInt(this.lastDay);
        dest.writeInt(this.accept);
        dest.writeInt(this.userRelation);
        dest.writeInt(this.userAccept);
        dest.writeString(this.unit);
        dest.writeString(this.categoryImage);
        dest.writeString(this.gradeName);
        dest.writeInt(this.visitor);
        dest.writeInt(this.serviceUser);
        dest.writeInt(this.orderUser);
        dest.writeString(this.orderDate);
        dest.writeString(this.companyAuth);
        dest.writeTypedList(serviceGradeList);
        dest.writeTypedList(serviceImagesList);
    }

    private ServiceBean(Parcel in)
    {
        this.id = in.readInt();
        this.categoryId = in.readString();
        this.pubUser = in.readInt();
        this.title = in.readString();
        this.introductions = in.readString();
        this.serviceMode = in.readString();
        this.offlinePrice = in.readString();
        this.telPrice = in.readString();
        this.weeks = in.readString();
        this.orderNo = in.readString();
        this.owerAddress = in.readString();
        this.remark = in.readString();
        this.status = in.readInt();
        this.createDate = in.readString();
        this.createName = in.readString();
        this.updateDate = in.readString();
        this.updateName = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.reject = in.readString();
        this.turnover = in.readInt();
        this.categoryName = in.readString();
        this.distance = in.readString();
        this.age = in.readInt();
        this.userName = in.readString();
        this.realName = in.readString();
        this.nickName = in.readString();
        this.grade = in.readInt();
        this.sesamePoint = in.readInt();
        this.sex = in.readInt();
        this.image = in.readString();
        this.gradeNum = in.readInt();
        this.gradeTotal = in.readString();
        this.grade1 = in.readInt();
        this.grade2 = in.readInt();
        this.grade3 = in.readInt();
        this.grade4 = in.readInt();
        this.grade5 = in.readInt();
        this.relation = in.readInt();
        this.orderId = in.readInt();
        this.lastDay = in.readInt();
        this.accept = in.readInt();
        this.userRelation = in.readInt();
        this.userAccept = in.readInt();
        this.unit = in.readString();
        this.categoryImage = in.readString();
        this.gradeName = in.readString();
        this.visitor = in.readInt();
        this.serviceUser = in.readInt();
        this.orderUser = in.readInt();
        this.orderDate = in.readString();
        this.companyAuth = in.readString();
        this.serviceGradeList = in.createTypedArrayList(Comment.CREATOR);
        this.serviceImagesList = in.createTypedArrayList(ImageBean.CREATOR);
    }

    public static final Creator<ServiceBean> CREATOR = new Creator<ServiceBean>()
    {
        public ServiceBean createFromParcel(Parcel source)
        {
            return new ServiceBean(source);
        }

        public ServiceBean[] newArray(int size)
        {
            return new ServiceBean[size];
        }
    };
}
