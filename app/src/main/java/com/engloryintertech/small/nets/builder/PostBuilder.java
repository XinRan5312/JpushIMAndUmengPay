package com.engloryintertech.small.nets.builder;

import com.engloryintertech.small.nets.request.PostRequest;
import com.engloryintertech.small.nets.request.RequestCall;
import com.engloryintertech.small.tools.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostBuilder extends OkHttpRequestBuilder<PostBuilder> implements HasParamsable {

    private List<FileInput> files = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostRequest(url, tag, params, headers, files,id).build();
    }

    /** add file 的参数 一个集合*/
    public PostBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            if(!Common.isStringNull(filename)){
                this.files.add(new FileInput(key, filename, files.get(filename)));
            }
        }
        return this;
    }

    /** add file 的参数 多次调用put进去*/
    public PostBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }

    /**上传文件需要参数时 不需要参数就不需要*/
    @Override
    public PostBuilder params(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

}
