package com.crv.sdk.utils;

/**
 * 地图工具类
 * 根据角度获取图片，方向值
 * Created by Administrator on 2016-06-04.
 */
public class MapUtil {
    /**
     * 根据方向获取图标名
     *
     * @param degree
     * @return
     */
    public static String getCarImgName(double degree) {

        if (degree >= 360) {
            degree = degree - 360;
        }
        int i = ((int) degree / 90) * 4;
        double s = degree % 90;
        if (s == 90) {
            i += 5;
        } else if (s == 0) {
            i += 1;
        } else if (s > 0 && s <= 22.5) {
            i += 2;
        } else if (s > 22.5 && s < 67.5) {
            i += 3;
        } else if (s >= 67.5 && s < 90) {
            i += 4;
        }

        return "C" + i;
    }

    /**
     * 根据角度获取方向
     *
     * @param degree
     * @return
     */
    public static int getDirection(double degree) {

        if (degree >= 360) {
            degree = degree - 360;
        }
        int i = ((int) degree / 90) * 4;
        double s = degree % 90;
        if (s == 90) {
            i += 5;
        } else if (s == 0) {
            i += 1;
        } else if (s > 0 && s <= 22.5) {
            i += 2;
        } else if (s > 22.5 && s < 67.5) {
            i += 3;
        } else if (s >= 67.5 && s < 90) {
            i += 4;
        }

        return i;
    }
}
