package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.UserRoleMapper;
import com.camile.dao.model.UserRole;
import com.camile.dao.model.UserRoleExample;

/**
* 降级实现UserRoleService接口
* Created by lizhihui on 2017/10/21.
*/
public class UserRoleServiceMock extends ServiceMock<UserRoleMapper, UserRole, UserRoleExample> implements UserRoleService {

}
