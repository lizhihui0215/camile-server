package com.camile.web.controller;

import com.camile.common.base.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController extends Controller {
    private static Logger _log = LoggerFactory.getLogger(LoginController.class);

}
