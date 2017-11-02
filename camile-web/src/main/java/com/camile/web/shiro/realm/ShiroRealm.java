package com.camile.web.shiro.realm;

import com.camile.api.PermissionService;
import com.camile.api.RoleService;
import com.camile.api.UserService;
import com.camile.dao.model.Permission;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;
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

import java.util.List;

/**
 * Created by lizhihui on 01/10/2017.
 */
public class ShiroRealm extends AuthorizingRealm {
    private static Logger _log = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());

        final User user = userService.selectByUsername(username);

        final List<Role> roleInfos = user.getRoles();
        for (Role role : roleInfos) {
            // 添加角色
            _log.debug("%s add { %s }",user.getUsername(),role);

            authorizationInfo.addRole(role.getRoleSign());

            final List<Permission> permissions = permissionService.selectPermissionsByRoleId(role.getId());
            for (Permission permission : permissions) {
                // 添加权限
                _log.debug("%s add { %s }",user.getUsername(),permission);
                authorizationInfo.addStringPermission(permission.getPermissionSign());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = String.valueOf(token.getPrincipal());
        String password = String.valueOf((char[])token.getCredentials());

        User user = userService.selectByUsername(username);

        if (null == user) throw new UnknownAccountException();

        if (!user.getPassword().equals(password)) throw new IncorrectCredentialsException();

//        if (user.getLocked()) throw new LockedAccountException();
        SecurityUtils.getSubject().getSession().setAttribute("user", user);

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
