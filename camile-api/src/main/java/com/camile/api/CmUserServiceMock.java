package com.camile.api;

import com.camile.common.base.ServiceMock;
import com.camile.dao.mapper.CmUserMapper;
import com.camile.dao.model.CmUser;
import com.camile.dao.model.CmUserExample;

/**
* 降级实现CmUserService接口
* Created by lizhihui on 2017/8/25.
*/
public class CmUserServiceMock extends ServiceMock<CmUserMapper, CmUser, CmUserExample> implements CmUserService {

}
