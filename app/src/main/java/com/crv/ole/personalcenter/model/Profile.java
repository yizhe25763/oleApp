package com.crv.ole.personalcenter.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanghongjun on 17/7/7.
 */

public class Profile implements Serializable {

    private ArrayList<Hotty> hobbys ;
    public ArrayList<Hotty> getHobbys() {
        return hobbys;
    }
    public void setHobbys(ArrayList<Hotty> hobbys) {
        this.hobbys = hobbys;
    }

}
