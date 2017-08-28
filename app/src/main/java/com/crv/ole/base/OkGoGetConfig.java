package com.crv.ole.base;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.GetRequest;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class OkGoGetConfig<T> {
    private String url;
    private Converter<T> converter;

    private GetRequest<T> get;

    private OkGoGetConfig(Buidler builder) {
        this.url = builder.url;
        this.converter = builder.converter;
        get = OkGo.<T>get(url)
                .converter(converter);
    }


    public static Buidler builder() {
        return new Buidler();
    }


    public static final class Buidler<T> {
        private String url;
        private Converter<T> converter;

        private Buidler() {
        }

        public Buidler url(String url) {
            this.url = url;
            return this;
        }

        public Buidler converter(Converter<T> converter) {
            this.converter = converter;
            return this;
        }
        public OkGoGetConfig build() {
            return new OkGoGetConfig(this);
        }

    }

    /**
     * Get请求
     *
     * @return
     */
    public GetRequest<T> get() {
        if (get == null) {
            get = OkGo.<T>get(url)
                    .converter(converter);
        }
        return get;
    }
}
