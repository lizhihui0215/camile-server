package com.camile.common.result;

/**
 * Created by lizhihui on 01/10/2017.
 */

public class Result {
    private static final int GLOABLE_EXCEPTION = 0x1;

    private int code;
    private String message;
    private Object data;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result SUCCESS(Object obj) {
        Result success = new Result(1001, "success");
        success.data = obj;
        return success;
    }

    public static Result FAILED(int code, String message) {
        return new Result(code, message);
    }

    public static Result FAILED(String message) {
        return Result.FAILED(GLOABLE_EXCEPTION, message);
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
