package com.camile.web.controller.user;

import com.camile.api.RoleService;
import com.camile.common.base.Response;
import com.camile.common.result.AuthResult;
import com.camile.common.result.Result;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;
import com.camile.dao.model.User;
import com.camile.web.controller.auth.AuthController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lizhihui on 26/10/2017.
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "role", description = "角色相关API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {

    private static Logger _log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "角色")
    @GetMapping(value = "/roles")
    @RequiresRoles("admin")
    public Response<List<Role>> roles() {
        List<Role> roles = this.roleService.selectByExample(new RoleExample());
        return new Response<>(Result.SUCCESS(roles));
    }

}
