package com.camile.service.impl;

import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.PermissionMapper;
import com.camile.dao.model.Permission;
import com.camile.dao.model.PermissionExample;
import com.camile.api.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* PermissionService实现
* Created by lizhihui on 2017/10/21.
*/
@Service
@Transactional
@InitService
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission, PermissionExample> implements PermissionService {

    private static Logger _log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long id) {
        return permissionMapper.selectPermissionsByRoleId(id);
    }
}