package com.camile.web.controller.auth;
import com.camile.api.UserService;
import com.camile.common.base.Controller;
import com.camile.common.base.Response;
import com.camile.common.result.AuthResult;
import com.camile.common.result.Result;
import com.camile.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "auth", description = "用户认证相关操作！", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController extends Controller {
    private static Logger _log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "/signin")
    public Response<User> login(@RequestParam String username, @RequestParam String password, @RequestParam boolean rememberMe) {

        if (StringUtils.isBlank(username)) return new Response<>(AuthResult.EMPTY_USERNAME);
        if (StringUtils.isBlank(password)) return new Response<>(AuthResult.EMPTY_PASSWORD);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        if (subject.isAuthenticated()) {
            User user = (User) session.getAttribute("user");
            return new Response<>(AuthResult.SUCCESS(user));
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(rememberMe);

        try {
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException e) {
            return new Response<>(AuthResult.INVALID_USERNAME);
        }catch (IncorrectCredentialsException e) {
            return new Response<>(AuthResult.INVALID_PASSWORD);
        }catch (LockedAccountException e) {
            return new Response<>(AuthResult.INVALID_ACCOUNT);
        }

        User user = (User) session.getAttribute("user");
        return new Response<>(AuthResult.SUCCESS(user));
    }


    @ApiOperation(value = "是否认登录")
    @GetMapping(value = "/isAuthenticated")
    public Response<Boolean> isAuthenticated() {
        Subject subject = SecurityUtils.getSubject();

        User user = (User) subject.getSession().getAttribute("user");

        if (subject.isRemembered() && user == null) {
            user = userService.selectByUsername(String.valueOf(subject.getPrincipal()));
            subject.getSession().setAttribute("user", user);
        }

        return new Response<>(AuthResult.SUCCESS(subject.isAuthenticated() || subject.isRemembered()));
    }

    @ApiOperation(value = "退出登录")
    @GetMapping(value = "/signout")
    @RequiresUser
    public Response<Void> logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        return new Response<>(AuthResult.SUCCESS(null));
    }

    @ApiOperation(value = "注册")
    @PostMapping(value = "/signup")
    public Response<Void> signup(@RequestBody User user) {
        int count = this.userService.insertSelective(user);
        if (count == 1) return new Response<>(Result.SUCCESS(null));
        return new Response<>(Result.FAILED("注册失败！"));
    }

}
