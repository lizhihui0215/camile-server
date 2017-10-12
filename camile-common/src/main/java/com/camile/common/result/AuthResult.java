package com.camile.common.result;

public class AuthResult extends Result{
    public static final AuthResult INVALID_LENGTH = new AuthResult(10001, "长度不正确！");
    public static final AuthResult EMPTY_USERNAME = new AuthResult(10101, "用户名不能为空！");
    public static final AuthResult EMPTY_PASSWORD = new AuthResult(10102, "密码不能为空！");
    public static final AuthResult INVALID_USERNAME = new AuthResult(10103, "用户不存在！");
    public static final AuthResult INVALID_PASSWORD = new AuthResult(10104, "密码错误");
    public static final AuthResult INVALID_ACCOUNT = new AuthResult(10105, "账户错误");

    private AuthResult(int code, String message) {
        super(code, message);
    }
}
