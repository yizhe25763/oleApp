package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class LogisticsListData extends BaseResponseData {
    private List<LogisticsData> RETURN_DATA;

    public List<LogisticsData> getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(List<LogisticsData> RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class LogisticsData{
        private String date,time;
        private String name;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
