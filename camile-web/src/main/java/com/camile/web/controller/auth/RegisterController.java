package com.camile.web.controller.auth;

import com.camile.api.UserService;
import com.camile.common.base.Response;
import com.camile.common.result.Result;
import com.camile.dao.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "注册")
    @PostMapping(value = "/signup")
    public Response<Void> signup(@RequestBody User user) {

        int count = this.userService.insertSelective(user);

        if (count == 1) return new Response<>(Result.SUCCESS(null));

        return new Response<Void>(Result.FAILED("注册失败！"));
    }
}
