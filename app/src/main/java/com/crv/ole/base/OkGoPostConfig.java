package com.crv.ole.base;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.PostRequest;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class OkGoPostConfig<T> {
    private String url;
    private String cookie;
    private String cacheKey;
    private boolean isSaveCache;
    private Object json;
    private Converter<T> converter;

    private PostRequest<T> post;

    private OkGoPostConfig(Buidler builder) {
        this.url = builder.url;
        this.cookie = builder.cookie;
        this.cacheKey = builder.cacheKey;
        this.isSaveCache = builder.isSaveCache;
        this.json = builder.json;
        this.converter = builder.converter;
        post = OkGo.<T>post(url)
                .headers("Cookie", cookie)
                .cacheKey(cacheKey)//这里完全同okgo的配置一样
                .cacheMode(isSaveCache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                .upJson(new Gson().toJson(json))
                .converter(converter);
    }


    public static Buidler builder() {
        return new Buidler();
    }


    public static final class Buidler<T> {
        private String url;
        private String cookie;
        private String cacheKey;
        private boolean isSaveCache;
        private Object json;
        private Converter<T> converter;

        private Buidler() {
        }

        public Buidler url(String url) {
            this.url = url;
            return this;
        }

        public Buidler cookie(String cookie) {
            this.cookie = cookie;
            return this;
        }

        public Buidler cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public Buidler isSaveCache(Boolean isSaveCache) {
            this.isSaveCache = isSaveCache;
            return this;
        }

        public Buidler json(Object json) {
            this.json = json;
            return this;
        }

        public Buidler converter(Converter<T> converter) {
            this.converter = converter;
            return this;
        }


        public OkGoPostConfig build() {
            return new OkGoPostConfig(this);
        }

    }

    /**
     * Post请求
     *
     * @return
     */
    public PostRequest<T> getPost() {
        if (post == null) {
            post = OkGo.<T>post(url)
                    .headers("Cookie", cookie)
                    .cacheKey(cacheKey)//这里完全同okgo的配置一样
                    .cacheMode(isSaveCache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .upJson(new Gson().toJson(json))
                    .converter(converter);
        }
        return post;
    }
}
