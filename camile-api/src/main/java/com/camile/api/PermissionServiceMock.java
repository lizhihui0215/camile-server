package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.PermissionMapper;
import com.camile.dao.model.Permission;
import com.camile.dao.model.PermissionExample;

import java.util.List;

/**
* 降级实现PermissionService接口
* Created by lizhihui on 2017/10/21.
*/
public class PermissionServiceMock extends ServiceMock<PermissionMapper, Permission, PermissionExample> implements PermissionService {

    @Override
    public List<Permission> selectPermissionsByRoleId(Long id) {
        return null;
    }
}
