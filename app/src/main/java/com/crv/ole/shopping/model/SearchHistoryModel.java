package com.crv.ole.shopping.model;

import com.crv.ole.db.DBHelper;
import com.crv.ole.utils.Log;
import com.crv.sdk.utils.TextUtil;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghongjun on 17/8/1.
 */

public class SearchHistoryModel {


    public static void insertHistory(String text){

        Log.i("来了:"+text);
        if (TextUtil.isEmpty(text)) return;

        if (querySearchHistoryBytext(text).size()>0){
            return;
        }
        SearchHistoryData item = new SearchHistoryData();
        item.setDatetime(System.currentTimeMillis());
        item.setText(text);
        try {
            DBHelper.getInstance().getDbManager().saveBindingId(item);
        } catch (DbException e) {
            Log.i("保存失败:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<SearchHistoryData> queryAllSearchHistory(){
        return  querySearchHistoryBytext(null);

    }

    public static List<SearchHistoryData> querySearchHistoryBytext(String text){
        List<SearchHistoryData> list = null;
        try {
            if (text==null){
                list = DBHelper.getInstance().getDbManager().selector(SearchHistoryData.class).findAll();
            }else{
                list = DBHelper.getInstance().getDbManager().selector(SearchHistoryData.class).where("text", "=", text).findAll();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<>() : list;
    }


    public static void deleteAll(){
        try {
            DBHelper.getInstance().getDbManager().delete(SearchHistoryData.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
