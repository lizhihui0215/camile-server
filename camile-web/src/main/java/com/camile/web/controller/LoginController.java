package com.camile.web.controller;

import com.camile.api.CmUserService;
import com.camile.common.base.Controller;
import com.camile.common.base.Response;
import com.camile.dao.model.CmUser;
import com.camile.dao.model.CmUserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/login")
public class LoginController extends Controller {
    private static Logger _log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CmUserService userService;

    @RequestMapping("/auth")
    public Response<CmUser> login(@RequestParam String username, @RequestParam String password) {

        CmUserExample cmUserExample = new CmUserExample();
        cmUserExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);

        CmUser cmUser = this.userService.selectFirstByExample(cmUserExample);

        Response<CmUser> response = new Response<>();

        response.setSuccess(cmUser == null);

        if (cmUser == null) {
            response.setMessage("用户不存在，或密码错误！");
        }

        response.setResults(cmUser);

        return response;
    }

}
