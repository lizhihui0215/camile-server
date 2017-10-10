package com.camile.web.shiro.filter;

import com.camile.web.shiro.session.ShiroSessionDao;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by lizhihui on 01/10/2017.
 */
public class ShiroAuthenticationFilter extends AuthenticationFilter {
    private final static Logger _log = LoggerFactory.getLogger(ShiroAuthenticationFilter.class);

    // 局部会话key
    private final static String CAMILE_CLIENT_SESSION_ID = "camile-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String CAMILE_CLIENT_SESSION_IDS = "camile-client-session-ids";

    @Autowired
    ShiroSessionDao shiroSessionDao;

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
