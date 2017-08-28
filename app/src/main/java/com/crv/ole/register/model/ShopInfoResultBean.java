package com.crv.ole.register.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fairy on 2017/7/25.
 * 门店列表相关
 */

public class ShopInfoResultBean extends BaseResponseData {
    private List<RETURNDATABean> RETURN_DATA;

    public List<RETURNDATABean> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<RETURNDATABean> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean implements Serializable {
        /**
         * id : sh_25
         * citycode : c_region_11206
         * shopid : VA1B
         * area : 南区
         * provincecode : c_region_11017
         * province : 广西壮族自治区
         * areacode : a_1002
         * shopname : Ole南宁万象城店
         * city : 南宁市
         */

        private String id;
        private String citycode;
        private String shopid;
        private String area;
        private String provincecode;
        private String province;
        private String areacode;
        private String shopname;
        private String city;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getProvincecode() {
            return provincecode;
        }

        public void setProvincecode(String provincecode) {
            this.provincecode = provincecode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
