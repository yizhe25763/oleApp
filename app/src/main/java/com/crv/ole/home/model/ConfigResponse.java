package com.crv.ole.home.model;

import java.io.Serializable;

/**
 * 配置文件返回实体类
 * Created by zhangbo on 2017/8/7.
 */

public class ConfigResponse implements Serializable {
    private String code;

    private String msg;

    private String data;

    private String iv;

    private String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
