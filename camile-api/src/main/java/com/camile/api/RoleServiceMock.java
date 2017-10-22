package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.RoleMapper;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;

import java.util.List;

/**
* 降级实现RoleService接口
* Created by lizhihui on 2017/10/21.
*/
public class RoleServiceMock extends ServiceMock<RoleMapper, Role, RoleExample> implements RoleService {

    @Override
    public List<Role> selectRolesByUserId(Long id) {
        return null;
    }
}
