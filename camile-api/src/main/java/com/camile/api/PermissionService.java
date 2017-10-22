package com.camile.api;

import com.camile.common.base.Service;
import com.camile.dao.model.Permission;
import com.camile.dao.model.PermissionExample;

import java.util.List;

/**
* PermissionService接口
* Created by lizhihui on 2017/10/21.
*/
public interface PermissionService extends Service<Permission, PermissionExample> {

    List<Permission> selectPermissionsByRoleId(Long id);
}