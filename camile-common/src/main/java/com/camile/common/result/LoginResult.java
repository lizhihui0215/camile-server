package com.camile.common.result;

public class LoginResult extends Result{
    public static final LoginResult INVALID_LENGTH = new LoginResult(10001, "长度不正确！");
    public static final LoginResult EMPTY_USERNAME = new LoginResult(10101, "用户名不能为空！");
    public static final LoginResult EMPTY_PASSWORD = new LoginResult(10102, "密码不能为空！");
    public static final LoginResult INVALID_USERNAME = new LoginResult(10103, "用户不存在！");
    public static final LoginResult INVALID_PASSWORD = new LoginResult(10104, "密码错误");
    public static final LoginResult INVALID_ACCOUNT = new LoginResult(10105, "账户错误");

    private LoginResult(int code, String message) {
        super(code, message);
    }
}
