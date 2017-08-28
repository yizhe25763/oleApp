package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihongshi on 2017/7/25.
 */
public class TrackBeanResponseData extends BaseResponseData {

    private RETURN_DATA RETURN_DATA;

    public TrackBeanResponseData.RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(TrackBeanResponseData.RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class Traces implements Serializable {

        private String time;//操作时间
        private String context;//配送信息

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getContext() {
            return context;
        }

    }


    public static class RETURN_DATA implements Serializable {
        private String billNo; //物流单号
        private String productImg;//商品图片
        private List<Traces> traces;
        private String name;//物流商名称
        private String state;//订单配送状态
        private String telephone;//物流商联系电话

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public String getBillNo() {
            return billNo;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public String getProductImg() {
            return productImg;
        }

        public void setTraces(List<Traces> traces) {
            this.traces = traces;
        }

        public List<Traces> getTraces() {
            return traces;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getTelephone() {
            return telephone;
        }

    }


}


