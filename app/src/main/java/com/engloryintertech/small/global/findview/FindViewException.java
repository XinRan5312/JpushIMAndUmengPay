package com.engloryintertech.small.global.findview;

/**
 * Created by qixinh on 16/9/19.
 */
public class FindViewException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FindViewException() {
        super();
    }

    public FindViewException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FindViewException(String detailMessage) {
        super(detailMessage);
    }

    public FindViewException(Throwable throwable) {
        super(throwable);
    }
}
