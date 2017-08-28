package com.crv.ole.net.convertor;

import com.crv.ole.net.Convert;
import com.crv.ole.net.DemoModel;
import com.crv.ole.utils.Log;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wj_wsf on 2017/7/4 11:00.
 */

public class DemoModelConvert<T> implements Converter<T> {
    private Class<T> clazz;

    public DemoModelConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        Log.d("DemoModelConvert---->>>>convertResponse");
//        Log.d("convertResponse:" + response.body().string());
        if (clazz == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());
        T t = Convert.fromJson(jsonReader, clazz);
//        Gson gson = new Gson();
//        T t = gson.fromJson(response.body().string(), clazz);
        response.close();
        Log.i("convertResponse请求结果：" + (t == null));
        return t;
    }
}
