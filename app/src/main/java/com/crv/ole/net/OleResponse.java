/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crv.ole.net;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class OleResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    //接口响应编码
    private String RETURN_CODE;
    //接口响应说明
    private String RETURN_DESC;
    //接口响应时间戳
    private String RETURN_STAMP;

    private T RETURN_DATA;

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

    public T getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(T RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tcode=" + RETURN_CODE + "\n" +//
                "\tmsg='" + RETURN_DESC + "\'\n" +//
                "\tmsg='" + RETURN_STAMP + "\'\n" +//
                "\tdata=" + RETURN_DATA + "\n" +//
                '}';
    }
}
