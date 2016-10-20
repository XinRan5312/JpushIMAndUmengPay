package com.engloryintertech.small.nets.callback;

import com.engloryintertech.small.tools.Tools;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallback extends Callback<String> {

    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        String str = response.body().string();
        Tools.debugger("StringCallback", "parseNetworkResponse : " + str);
        return str;
    }
}
