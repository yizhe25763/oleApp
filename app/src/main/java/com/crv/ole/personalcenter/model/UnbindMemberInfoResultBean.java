package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/21.
 * 会员解除绑定相关
 */

public class UnbindMemberInfoResultBean extends BaseResponseData implements Serializable {
    private UnbindMemberInfoResult RETURN_DATA;

    public UnbindMemberInfoResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(UnbindMemberInfoResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class UnbindMemberInfoResult{
        private String flag;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
