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
package com.crv.ole.net.convertor;

import android.text.TextUtils;

import com.crv.ole.BaseApplication;
import com.crv.ole.net.Convert;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 解析接口返回数据，可以根据不同需要做相应的改变，但是一个数据类型只能有一个Converter
 * ================================================
 */
public class JsonConvert<T> implements Converter<T> {
    //TOKEN 失效发送消息
    public static final String TOKEN_DISMISS_MESSAGE = "token_dismis";
    private Type type;
    private Class<T> clazz;

    public JsonConvert() {
    }

    public JsonConvert(Type type) {
        this.type = type;
    }

    public JsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        Log.v("convertResponse");
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        Log.e(response + "");
//        Log.e(response.body().string());
        Log.d("响应的headers：" + response.headers().toString());
        if (!TextUtils.isEmpty(response.headers().get("Cookie")) && response.headers().get("Cookie").indexOf("isid") != -1) {
            BaseApplication.getInstance().setRequestCookieParams(response.headers().get("Cookie"));
        }

        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<?> rawType) {
        try {
            if (rawType == null) {
                return null;
            }
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            JsonReader jsonReader = new JsonReader(body.charStream());
            if (rawType == String.class) {
                //noinspection unchecked
                return (T) body.string();
            } else if (rawType == JSONObject.class) {
                //noinspection unchecked
                return (T) new JSONObject(body.string());
            } else if (rawType == JSONArray.class) {
                //noinspection unchecked
                return (T) new JSONArray(body.string());
            } else {
                T t = Convert.fromJson(jsonReader, rawType);
         /*   JSONObject result = new JSONObject(Convert.toJson(t));
            String code = "";
            if (result.has("code")) {
                code = result.getString("code");
                Log.d("code：" + code);
            }
            if (result.has("RETURN_CODE")) {
                code = result.getString("RETURN_CODE");
                Log.d("code：" + code);
            }

            //TODO TOKEN失效CODE
            if ("E1B02013".equals(code)) {
                EventBus.getDefault().post(TOKEN_DISMISS_MESSAGE);
            }*/
                response.close();
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null)
            return null;
        ResponseBody body = response.body();
        if (body == null)
            return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
        T t = Convert.fromJson(jsonReader, type);
        response.close();
        return t;
    }

}
