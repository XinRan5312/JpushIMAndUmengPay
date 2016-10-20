package com.engloryintertech.small.model.bean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class GeneralBean {

    private String Message;
    private String Result;

    public GeneralBean(String message, String result) {
        Message = message;
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}