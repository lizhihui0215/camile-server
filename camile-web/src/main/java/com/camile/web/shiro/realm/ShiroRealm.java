package com.camile.web.shiro.realm;

import com.camile.api.UserService;
import com.camile.dao.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lizhihui on 01/10/2017.
 */
public class ShiroRealm extends AuthorizingRealm {
    private static Logger _log = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        User user = userService.selectByUsername(username);

        if (null == user) throw new UnknownAccountException();

        if (!user.getPassword().equals(password)) throw new IncorrectCredentialsException();

//        if (user.getLocked()) throw new LockedAccountException();
        SecurityUtils.getSubject().getSession().setAttribute("user", user);

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
