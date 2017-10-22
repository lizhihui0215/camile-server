package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.RolePermissionMapper;
import com.camile.dao.model.RolePermission;
import com.camile.dao.model.RolePermissionExample;

/**
* 降级实现RolePermissionService接口
* Created by lizhihui on 2017/10/21.
*/
public class RolePermissionServiceMock extends ServiceMock<RolePermissionMapper, RolePermission, RolePermissionExample> implements RolePermissionService {

}
