package com.camile.common.result;

/**
 * Created by lizhihui on 01/10/2017.
 */

public class Result {
    public static final Result FAILED = new Result(1001, "failed");

    private int code;
    private String message;
    private Object data;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public final static Result SUCCESS(Object obj) {
        Result success = new Result(1001, "success");
        success.data = obj;
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
