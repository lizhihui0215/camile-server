package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.UserMapper;
import com.camile.dao.model.User;
import com.camile.dao.model.UserExample;

import java.util.List;

/**
* 降级实现UserService接口
* Created by lizhihui on 2017/10/21.
*/
public class UserServiceMock extends ServiceMock<UserMapper, User, UserExample> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return null;
    }

    @Override
    public List<User> selectByRoleId(String roleId) {
        return null;
    }
}
