package com.camile.web.controller;

import com.camile.api.CmUserService;
import com.camile.common.base.Controller;
import com.camile.dao.model.CmUser;
import com.camile.dao.model.CmUserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/login")
public class LoginController extends Controller {
    private static Logger _log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CmUserService userService;

    @RequestMapping("/test")
    public List<CmUser> test() {
        CmUserExample cmUserExample = new CmUserExample();
        List<CmUser> cmUsers = this.userService.selectByExample(cmUserExample);

        return cmUsers;
    }

}
