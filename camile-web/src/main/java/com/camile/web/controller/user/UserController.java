package com.camile.web.controller.user;

import com.camile.api.UserService;
import com.camile.common.base.Response;
import com.camile.common.result.Result;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;
import com.camile.dao.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "user", description = "用户相关API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "根据角色id获取所有用户")
    @GetMapping(value = "/{roleId}")
    @RequiresRoles("admin")
    public Response<List<User>> usersByRoleId(@PathVariable String roleId) {
        List<User> roles = this.userService.selectByRoleId(roleId);
        return new Response<>(Result.SUCCESS(roles));
    }
}
