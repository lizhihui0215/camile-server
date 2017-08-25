package com.camile.dao.mapper;

import com.camile.dao.model.CmUser;
import com.camile.dao.model.CmUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmUserMapper {
    long countByExample(CmUserExample example);

    int deleteByExample(CmUserExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(CmUser record);

    int insertSelective(CmUser record);

    List<CmUser> selectByExample(CmUserExample example);

    CmUser selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") CmUser record, @Param("example") CmUserExample example);

    int updateByExample(@Param("record") CmUser record, @Param("example") CmUserExample example);

    int updateByPrimaryKeySelective(CmUser record);

    int updateByPrimaryKey(CmUser record);
}