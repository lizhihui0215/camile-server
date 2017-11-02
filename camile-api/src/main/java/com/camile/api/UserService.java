package com.camile.api;

import com.camile.common.base.Service;
import com.camile.dao.model.User;
import com.camile.dao.model.UserExample;

import java.util.List;

/**
* UserService接口
* Created by lizhihui on 2017/10/21.
*/
public interface UserService extends Service<User, UserExample> {

    User selectByUsername(String username);

    List<User> selectByRoleId(String roleId);
}