package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fairy on 2017/7/12.
 * 会员积分相关
 */

public class PointInfoResultBean extends BaseResponseData implements Serializable {
    private PointInfoResult RETURN_DATA;

    public PointInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(PointInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class PointInfoResult implements Serializable {
        private String pagesize;
        private String pageindex;
        private String recordcount;
        private List<PointInfo> items;

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public String getPageindex() {
            return pageindex;
        }

        public void setPageindex(String pageindex) {
            this.pageindex = pageindex;
        }

        public String getRecordcount() {
            return recordcount;
        }

        public void setRecordcount(String recordcount) {
            this.recordcount = recordcount;
        }

        public List<PointInfo> getItems() {
            return items;
        }

        public void setItems(List<PointInfo> items) {
            this.items = items;
        }
    }

    public class PointInfo {
        private String _rowid;
        private String _ssflag;
        private String directflag;
        private String memberid;
        private String name;
        private String occurdate;
        private String point;
        private String recorddate;
        private String resultpoint;
        private String sheettype;
        private String shopid;

        public String get_rowid() {
            return _rowid;
        }

        public void set_rowid(String _rowid) {
            this._rowid = _rowid;
        }

        public String get_ssflag() {
            return _ssflag;
        }

        public void set_ssflag(String _ssflag) {
            this._ssflag = _ssflag;
        }

        public String getDirectflag() {
            return directflag;
        }

        public void setDirectflag(String directflag) {
            this.directflag = directflag;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOccurdate() {
            return occurdate;
        }

        public void setOccurdate(String occurdate) {
            this.occurdate = occurdate;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getRecorddate() {
            return recorddate;
        }

        public void setRecorddate(String recorddate) {
            this.recorddate = recorddate;
        }

        public String getResultpoint() {
            return resultpoint;
        }

        public void setResultpoint(String resultpoint) {
            this.resultpoint = resultpoint;
        }

        public String getSheettype() {
            return sheettype;
        }

        public void setSheettype(String sheettype) {
            this.sheettype = sheettype;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }
    }
}
