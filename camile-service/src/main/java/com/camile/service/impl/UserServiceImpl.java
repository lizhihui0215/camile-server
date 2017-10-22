package com.camile.service.impl;

import com.camile.api.UserService;
import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.UserMapper;
import com.camile.dao.model.User;
import com.camile.dao.model.UserExample;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UserService实现
* Created by lizhihui on 2017/10/21.
*/
@Service
@Transactional
@InitService
public class UserServiceImpl extends ServiceImpl<UserMapper, User, UserExample> implements UserService {

    private static Logger _log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectByUsername(String username) {
        UserExample userExample = new UserExample();

        userExample.createCriteria().andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(users)) return null;

        return users.get(0);
    }
}