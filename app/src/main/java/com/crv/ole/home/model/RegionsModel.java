package com.crv.ole.home.model;

import com.crv.ole.db.DBHelper;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.NullBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;

import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lihongshi on 2017/7/20.
 * 省市区相关逻辑
 */

public class RegionsModel {

    private class RegionsResponseData extends BaseResponseData {
        private RegionsReturnData RETURN_DATA;

        public RegionsReturnData getRETURN_DATA() {
            return RETURN_DATA;
        }

        public void setRETURN_DATA(RegionsReturnData RETURN_DATA) {
            this.RETURN_DATA = RETURN_DATA;
        }

    }

    private class RegionsReturnData {
        private HashMap<String, RegionsBean> provinces;
        private HashMap<String, RegionsBean> districts;
        private HashMap<String, RegionsBean> cities;

        public HashMap<String, RegionsBean> getProvinces() {
            return provinces;
        }

        public void setProvinces(HashMap<String, RegionsBean> provinces) {
            this.provinces = provinces;
        }

        public HashMap<String, RegionsBean> getDistricts() {
            return districts;
        }

        public void setDistricts(HashMap<String, RegionsBean> districts) {
            this.districts = districts;
        }

        public HashMap<String, RegionsBean> getCities() {
            return cities;
        }

        public void setCities(HashMap<String, RegionsBean> cities) {
            this.cities = cities;
        }

    }

    public interface RegionSynaCallBack {
        void onSynaSuccess();

        void onSynaFail(String error);
    }

    /**
     * 判断本地是否有省市区数据
     *
     * @return
     */
    public static boolean isRegionDataExit() {
        try {
            TableEntity<RegionsBean> tableEntity = DBHelper.getInstance().getDbManager().getTable(RegionsBean.class);
            return tableEntity.tableIsExist();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 同步省市区数据到本地数据库
     *
     * @param sign
     * @param callBack
     * @return
     */
    public static void syncData(String sign, RegionSynaCallBack callBack) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_REGIONS);
        requestData.setREQUEST_DATA(new NullBean());

        ServerApi.request(false, requestData, RegionsResponseData.class, sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<RegionsResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(RegionsResponseData response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            HashMap<String, RegionsBean> provinces = response.getRETURN_DATA().getProvinces();
                            HashMap<String, RegionsBean> districts = response.getRETURN_DATA().getDistricts();
                            HashMap<String, RegionsBean> cities = response.getRETURN_DATA().getCities();
                            try { //清除老数据
                                DBHelper.getInstance().getDbManager().dropTable(RegionsBean.class);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            saveRegion(provinces);
                            saveRegion(districts);
                            saveRegion(cities);
                            if (callBack != null) {
                                callBack.onSynaSuccess();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("省市区数据同步错误:" + e.getMessage());
                        if (callBack != null) {
                            callBack.onSynaFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d("省市区数据同步完成");
                    }
                });

    }

    private static boolean saveRegion(HashMap<String, RegionsBean> map) {
        List<RegionsBean> list = new ArrayList<>();
        for (String key : map.keySet()) {
            RegionsBean provice = map.get(key);
            list.add(provice);
        }
        try {
            DBHelper.getInstance().getDbManager().save(list);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取所有省份
     *
     * @return
     */
    public static List<RegionsBean> getAllProvince() {
        List<RegionsBean> list = null;
        try {
            list = DBHelper.getInstance().getDbManager().selector(RegionsBean.class).where("type", "=", "province").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 根据省获取所有市
     *
     * @param provinceId 省ID
     * @return
     */
    public static List<RegionsBean> getCities(String provinceId) {
        List<RegionsBean> list = null;
        try {
            list = DBHelper.getInstance().getDbManager().selector(RegionsBean.class).
                    where("parentId", "=", provinceId).and("type", "=", "city").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 根据城市ID获取所有县
     *
     * @param cityId 城市id
     * @return
     */
    public static List<RegionsBean> getDistrictes(String cityId) {
        List<RegionsBean> list = null;
        try {
            list = DBHelper.getInstance().getDbManager().selector(RegionsBean.class).
                    where("parentId", "=", cityId).and("type", "=", "district").findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 根据id获取省市区
     *
     * @param id
     */
    public static Map<String, RegionsBean> getProviceAndCityAndDistrictbyId(String id) {
        Map<String, RegionsBean> map = new HashMap<>();
        RegionsBean district, city = null, province = null;
        try {
            district = DBHelper.getInstance().getDbManager().selector(RegionsBean.class)
                    .where("ID", "=", id).and("type", "=", "district").findFirst();
            if (district != null) {
                city = DBHelper.getInstance().getDbManager().selector(RegionsBean.class)
                        .where("ID", "=", district.getParentId()).and("type", "=", "city").findFirst();
                if (city != null) {
                    province = DBHelper.getInstance().getDbManager().selector(RegionsBean.class)
                            .where("ID", "=", city.getParentId()).and("type", "=", "province").findFirst();
                }
            } else {
                city = DBHelper.getInstance().getDbManager().selector(RegionsBean.class)
                        .where("ID", "=", id).and("type", "=", "city").findFirst();
                province = DBHelper.getInstance().getDbManager().selector(RegionsBean.class)
                        .where("ID", "=", city != null ? city.getParentId() : id).and("type", "=", "province").findFirst();

            }
            map.put("province", province);
            map.put("city", city);
            map.put("district", district);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return map;
    }

}
