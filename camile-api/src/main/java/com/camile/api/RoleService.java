package com.camile.api;

import com.camile.common.base.Service;
import com.camile.dao.model.Role;
import com.camile.dao.model.RoleExample;

import java.util.List;

/**
* RoleService接口
* Created by lizhihui on 2017/10/21.
*/
public interface RoleService extends Service<Role, RoleExample> {

    List<Role> selectRolesByUserId(Long id);
}