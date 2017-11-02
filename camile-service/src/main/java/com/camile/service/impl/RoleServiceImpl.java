package com.camile.service.impl;

import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.RoleMapper;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;
import com.camile.api.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* RoleService实现
* Created by lizhihui on 2017/10/21.
*/
@Service
@Transactional
@InitService
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role, RoleExample> implements RoleService {

    private static Logger _log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

}