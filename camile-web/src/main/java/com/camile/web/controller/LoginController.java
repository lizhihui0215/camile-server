package com.camile.web.controller;
import com.camile.api.UserService;
import com.camile.common.base.Controller;
import com.camile.common.base.Response;
import com.camile.common.result.LoginResult;
import com.camile.common.util.RedisUtil;
import com.camile.dao.model.User;
import com.camile.dao.model.UserExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/login")
@Api(value = "登录", description = "用户认证相关操作！")
public class LoginController extends Controller {
    private static Logger _log = LoggerFactory.getLogger(LoginController.class);

    // 全局会话key
    private final static String CAMILE_SERVER_SESSION_ID = "camile-server-session-id";
    // 全局会话key列表
    private final static String CAMILE_SERVER_SESSION_IDS = "camile-server-session-ids";
    // code key
    private final static String CAMILE_SERVER_CODE = "camile-server-code";

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "登录")
    @RequestMapping("/auth")
    public Response<User> login(@RequestParam String username, @RequestParam String password, @RequestParam boolean remember) {

        if (StringUtils.isBlank(username)) return new Response<>(LoginResult.EMPTY_USERNAME);
        if (StringUtils.isBlank(password)) return new Response<>(LoginResult.EMPTY_PASSWORD);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        String hasCode = (String) RedisUtil.get(CAMILE_SERVER_SESSION_ID + "_" + session);

        if (StringUtils.isBlank(hasCode)) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            usernamePasswordToken.setRememberMe(remember);

            try {
                subject.login(usernamePasswordToken);
            }catch (UnknownAccountException e) {
                return new Response<>(LoginResult.INVALID_USERNAME);
            }catch (IncorrectCredentialsException e) {
                return new Response<>(LoginResult.INVALID_PASSWORD);
            }catch (LockedAccountException e) {
                return new Response<>(LoginResult.INVALID_ACCOUNT);
            }

            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(CAMILE_SERVER_SESSION_IDS, sessionId.toString());

            String code = UUID.randomUUID().toString();
            RedisUtil.set(CAMILE_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
        }

        User user = (User) session.getAttribute("user");
        return new Response<>(LoginResult.SUCCESS(user));
    }

}
