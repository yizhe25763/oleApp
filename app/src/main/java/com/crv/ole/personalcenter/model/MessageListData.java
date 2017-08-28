package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class MessageListData extends BaseResponseData {
    private MessageData RETURN_DATA;

    public MessageData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(MessageData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
