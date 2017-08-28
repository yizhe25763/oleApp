package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanghongjun on 17/8/1.
 */

public class HotSearchBean extends BaseResponseData implements Serializable {

    private HotSearch RETURN_DATA;

    public HotSearch getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(HotSearch RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }


    public class HotSearch{
       private List<String> hotWord;

        public List<String> getHotWord() {
            return hotWord;
        }

        public void setHotWord(List<String> hotWord) {
            this.hotWord = hotWord;
        }

    }

}
