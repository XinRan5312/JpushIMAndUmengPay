package com.engloryintertech.small.nets.builder;

import java.util.Map;

/**参数*/
public interface HasParamsable {
    OkHttpRequestBuilder params(Map<String, Object> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
