package com.camile.common.base;

import com.camile.common.result.Result;

/**
 * 响应结果
 * @param <T>
 */
public class Response<T> {
    private int code;

    private String message;

    private T results;

    public Response(Result result) {
        this.code = result.getCode();
        this.message = result.getMessage();
        this.results = (T) result.getData();
    }

    public Response(int code, String message, T results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
