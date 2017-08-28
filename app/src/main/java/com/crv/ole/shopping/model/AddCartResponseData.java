package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.util.Map;

/**
 * Created by lihongshi on 2017/8/5.
 * 批量添加到购物车返回结果实体类
 * 示例
 * <p>
 * {
 * "RETURN_CODE": "S0A00000",
 * "RETURN_DESC": "加入购物车成功",
 * "RETURN_STAMP": "2017-07-12 19:28:26",
 * "RETURN_DATA": {
 * "p_880015": {
 * "code": "S0A00000",
 * "msg": "操作成功"
 * }
 * }
 * }
 */
public class AddCartResponseData extends BaseResponseData {

    private RETURN_DATA RETURN_DATA;

    public void setRETURN_DATA(RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public class ProductDetail {

        private String code;
        private String msg;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

    }

    public class RETURN_DATA {
        private Map<String, ProductDetail> map;

        public Map<String, ProductDetail> getMap() {
            return map;
        }

        public void setMap(Map<String, ProductDetail> map) {
            this.map = map;
        }
    }

}