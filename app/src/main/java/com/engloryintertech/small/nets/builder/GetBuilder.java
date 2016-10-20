package com.engloryintertech.small.nets.builder;

import android.net.Uri;

import com.engloryintertech.small.nets.request.GetRequest;
import com.engloryintertech.small.nets.request.RequestCall;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**get请求*/
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        return new GetRequest(url, tag, params, headers,id).build();
    }

    /**url地址*/
    protected String appendParams(String url, Map<String, Object> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key) + "");
        }
        return builder.build().toString();
    }

    @Override
    public GetBuilder params(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

}
