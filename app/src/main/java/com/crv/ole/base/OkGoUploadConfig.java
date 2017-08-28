package com.crv.ole.base;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.List;

/**
 * 上传文件配置
 * Created by zhangbo on 2017/8/5.
 */

public class OkGoUploadConfig<T> {
    private String url;
    private Converter<T> converter;

    private PostRequest<T> upload;

    private List<File> files;

    private OkGoUploadConfig(Buidler builder) {
        this.url = builder.url;

        this.converter = builder.converter;

        this.files = builder.files;

        upload = OkGo.<T>post(url)
                .addFileParams("file", files)
                .converter(converter);
    }


    public static Buidler builder() {
        return new Buidler();
    }


    public static final class Buidler<T> {
        private String url;
        private Converter<T> converter;
        private List<File> files;

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

        public Buidler files(List<File> files) {
            this.files = files;
            return this;
        }

        public OkGoUploadConfig build() {
            return new OkGoUploadConfig(this);
        }

    }

    /**
     * 上传文件请求
     *
     * @return
     */
    public PostRequest<T> upload() {
        if (upload == null) {
            upload = OkGo.<T>post(url)
                    .addFileParams("file", files)
                    .converter(converter);
        }
        return upload;
    }
}
