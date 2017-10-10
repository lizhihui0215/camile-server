package com.camile.api;

import com.camile.common.base.Service;
import com.camile.dao.model.User;
import com.camile.dao.model.UserExample;

/**
* UserService接口
* Created by lizhihui on 2017/10/1.
*/
public interface UserService extends Service<User, UserExample> {

    User selectByUsername(String username);
}