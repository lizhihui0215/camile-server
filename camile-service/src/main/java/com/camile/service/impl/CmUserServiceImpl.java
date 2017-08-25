package com.camile.service.impl;

import com.camile.common.annotation.InitService;
import com.camile.common.base.ServiceImpl;
import com.camile.dao.mapper.CmUserMapper;
import com.camile.dao.model.CmUser;
import com.camile.dao.model.CmUserExample;
import com.camile.api.CmUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* CmUserService实现
* Created by lizhihui on 2017/8/25.
*/
@Service
@Transactional
@InitService
public class CmUserServiceImpl extends ServiceImpl<CmUserMapper, CmUser, CmUserExample> implements CmUserService {

    private static Logger _log = LoggerFactory.getLogger(CmUserServiceImpl.class);

    @Autowired
    CmUserMapper cmUserMapper;

}