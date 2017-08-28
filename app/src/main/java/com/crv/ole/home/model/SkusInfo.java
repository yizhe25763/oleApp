package com.crv.ole.home.model;

import java.util.List;
import java.io.Serializable;

/**
 * Created by yanghongjun on 17/6/29.
 */

public class SkusInfo implements Serializable{

    public String id;
    public String skuId;
    public List<String> attrs;
    public String attrString;
    public String price;
    public String marketPrice;
    public int isShow;
}
