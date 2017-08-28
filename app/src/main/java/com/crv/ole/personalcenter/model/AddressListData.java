package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class AddressListData extends BaseResponseData {
    private List<Address> RETURN_DATA;

    public List<Address> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<Address> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public  class Address implements Serializable{

        private  String id;
        private  String  postalCode;
        private  boolean  isDefault;
        private String userName;
        private String longitude;
        private String regionId;
        private String addressId;
        private String address;
        private  String  phone;
        private  String  mobile;
        private  String  userLongitude;
        private  String  subdistrict;
        private  String  houseNumber;


        @Override
        public String toString() {
            return "Address{" +
                    "id='" + id + '\'' +
                    ", postalCode='" + postalCode + '\'' +
                    ", isDefault=" + isDefault +
                    ", userName='" + userName + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", regionId='" + regionId + '\'' +
                    ", addressId='" + addressId + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", userLongitude='" + userLongitude + '\'' +
                    ", subdistrict='" + subdistrict + '\'' +
                    ", houseNumber='" + houseNumber + '\'' +
                    '}';
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(String userLongitude) {
            this.userLongitude = userLongitude;
        }

        public String getSubdistrict() {
            return subdistrict;
        }

        public void setSubdistrict(String subdistrict) {
            this.subdistrict = subdistrict;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }



    }
}
