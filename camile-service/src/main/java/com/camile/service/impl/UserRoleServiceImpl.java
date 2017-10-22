package com.camile.service.impl;

import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.UserRoleMapper;
import com.camile.dao.model.UserRole;
import com.camile.dao.model.UserRoleExample;
import com.camile.api.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserRoleService实现
* Created by lizhihui on 2017/10/21.
*/
@Service
@Transactional
@InitService
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole, UserRoleExample> implements UserRoleService {

    private static Logger _log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

}