package com.camile.service.impl;

import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.RolePermissionMapper;
import com.camile.dao.model.RolePermission;
import com.camile.dao.model.RolePermissionExample;
import com.camile.api.RolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* RolePermissionService实现
* Created by lizhihui on 2017/10/21.
*/
@Service
@Transactional
@InitService
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission, RolePermissionExample> implements RolePermissionService {

    private static Logger _log = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

}