package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by Fairy on 2017/8/16.
 * 商品试用报告点赞
 */

public class ZanBean extends BaseResponseData{
    private ZanBean.RETURNDATABean RETURN_DATA;

    public ZanBean.RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(ZanBean.RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        private int likesCount;
        private int status;

        public int getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(int likesCount) {
            this.likesCount = likesCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
