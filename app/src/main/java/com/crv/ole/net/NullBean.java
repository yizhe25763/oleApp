package com.crv.ole.net;

import java.io.Serializable;

/**
 * Created by fanhaoyi on 2017/7/13.
 * 针对部分接口请求无入参的情况，但是还必须上传REQUEST_DATA字段，使用的时只需 setREQUEST_DATA(new NullBean())即可
 */

public class NullBean  implements Serializable{
}
